// script.js - updated to load states/markets/items/prices from data.json
// --- CONFIG ---
const DATA_URL = 'https://raw.githubusercontent.com/mohamedkam000/sooq_prices/main/data.json';
// If you host data.json locally in same folder, use './data.json'

// --- DOM ---
const cardsGrid = document.getElementById('cardsGrid');
const gridView = document.getElementById('gridView');
const detailView = document.getElementById('detailView');
const detailContent = document.getElementById('detailContent');

let fetchedData = {};
let states = [];
const BASE = '/sooq_prices/';

const fallbackStates = [
];

async function initializeApp() {
  try {
    const res = await fetch(DATA_URL);
    if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
    const data = await res.json();
    fetchedData = data || {};
    states = fetchedData.states || [];
    console.log('Fetched data.json successfully');
  } catch (err) {
    console.warn('Failed to fetch data.json:', err);
    // fallback
    states = fallbackStates.length ? fallbackStates : (fetchedData.states || []);
  }
  renderCards();

  const path = location.pathname.replace(BASE, '').replace(/^\/+/, '') || '/';
  navigateTo(path, { push: false });
}

function renderCards() {
  cardsGrid.innerHTML = '';
  if (!states || states.length === 0) {
    cardsGrid.innerHTML = '<div style="padding:2rem">No states found in data.json</div>';
    return;
  }

  states.forEach(state => {
    const el = document.createElement('article');
    el.className = 'card';
    el.tabIndex = 0;
    el.innerHTML = `
      <div class="img" style="background-image:url('${state.img || ''}')"></div>
      <div class="title-band">
        <div style="display:flex;flex-direction:column">
          <div class="city">${state.name}</div>
          <div class="desc">Select state to explore its markets</div>
        </div>
      </div>
      <div class="meta">
        <div class="desc">Click to see markets inside this state</div>
      </div>
    `;
    const tag = document.createElement('div');
    tag.className = 'tag';
    tag.textContent = 'Alpha';
    el.appendChild(tag);
    el.addEventListener('click', () => showMarkets(state));
    el.addEventListener('keypress', (e) => { if (e.key === 'Enter') showMarkets(state); });
    cardsGrid.appendChild(el);
  });
}

function navigateTo(path, opts = { push: true }) {
  const url = BASE + path.replace(/^\/+/, '');
  if (opts.push) history.pushState({ path }, '', url);

  if (path === '/' || path === '' || path === 'index.html') {
    showGrid();
    return;
  }

  const stateMatch = path.match(/^state\/([a-z0-9_-]+)/i);
  if (stateMatch) {
    const state = states.find(s => s.id === stateMatch[1]);
    if (state) { showMarkets(state); return; }
  }

  const marketMatch = path.match(/^state\/([a-z0-9_-]+)\/market\/([a-z0-9_-]+)/i);
  if (marketMatch) {
    const state = states.find(s => s.id === marketMatch[1]);
    const market = state?.markets?.find(m => m.id === marketMatch[2]);
    if (state && market) { showGoods(state, market); return; }
  }

  const goodMatch = path.match(/^state\/([a-z0-9_-]+)\/market\/([a-z0-9_-]+)\/good\/([a-z0-9_-]+)/i);
  if (goodMatch) {
    const state = states.find(s => s.id === goodMatch[1]);
    const market = state?.markets?.find(m => m.id === goodMatch[2]);
    const good = market?.goods?.find(g => g.id === goodMatch[3]);
    if (state && market && good) { showItemDetail(state, market, good); return; }
  }

  showGrid();
}

function showGrid() {
  detailView.classList.add('hidden');
  gridView.classList.remove('hidden');
  renderCards();
  document.title = 'Sooq Price';
  history.replaceState({ path: '/' }, '', BASE);
}

function showMarkets(state) {
  detailContent.innerHTML = '';
  detailView.classList.remove('hidden');
  gridView.classList.add('hidden');

  detailContent.innerHTML = `
    <h2>${state.name} Markets</h2>
    <div class="market-grid"></div>
    <button class="btn" id="backBtn">Back</button>
  `;

  const marketGrid = detailContent.querySelector('.market-grid');

  (state.markets || []).forEach(market => {
    const el = document.createElement('article');
    el.className = 'card';
    el.innerHTML = `
      <div class="img" style="background-image:url('${market.img || ''}')"></div>
      <div class="title-band">
        <div style="display:flex;flex-direction:column">
          <div class="city">${market.name}</div>
          <div class="desc">Select market to explore goods</div>
        </div>
      </div>
      <div class="meta">
        <div class="desc">Click to see goods in this market</div>
      </div>
    `;
    const tag = document.createElement('div');
    tag.className = 'tag';
    tag.textContent = 'Market';
    el.appendChild(tag);
    el.addEventListener('click', () => showGoods(state, market));
    marketGrid.appendChild(el);
  });

  const backBtn = detailContent.querySelector('#backBtn');
  backBtn.addEventListener('click', () => showGrid());
  history.pushState({ path: `state/${state.id}` }, '', `${BASE}state/${state.id}`);
}

function showGoods(state, market) {
  detailContent.innerHTML = `
    <h2>${market.name} — Goods</h2>
    <div class="market-grid"></div>
    <button class="btn" id="backBtn">Back</button>
  `;

  const goodsGrid = detailContent.querySelector('.market-grid');

  (market.goods || []).forEach(good => {
    const el = document.createElement('article');
    el.className = 'card';
    el.innerHTML = `
      <div class="img" style="background-image:url('${good.img || ''}')"></div>
      <div class="title-band">
        <div style="display:flex;flex-direction:column">
          <div class="city">${good.name}</div>
          <div class="desc">Click to see items inside</div>
        </div>
      </div>
      <div class="meta">
        <div class="desc">${good.items ? good.items.length + ' items available' : 'No items'}</div>
      </div>
    `;
    const tag = document.createElement('div');
    tag.className = 'tag';
    tag.textContent = 'Good';
    el.appendChild(tag);

    if (good.items && good.items.length > 0) {
      el.addEventListener('click', () => showItems(state, market, good));
    } else {
      el.addEventListener('click', () => alert('No items inside'));
    }

    goodsGrid.appendChild(el);
  });

  const backBtn = detailContent.querySelector('#backBtn');
  backBtn.addEventListener('click', () => showMarkets(state));
  history.pushState({ path: `state/${state.id}/market/${market.id}` }, '', `${BASE}state/${state.id}/market/${market.id}`);
}

function showItems(state, market, good) {
  detailContent.innerHTML = `
    <h2>${good.name}</h2>
    <div class="market-grid"></div>
    <button class="btn" id="backBtn">Back</button>
  `;
  const itemsGrid = detailContent.querySelector('.market-grid');

  (good.items || []).forEach(item => {
    const el = document.createElement('article');
    el.className = 'card';
    el.innerHTML = `
      <div class="img" style="background-image:url('${item.img || ''}')"></div>
      <div class="title-band" style="display:flex;flex-direction:column;align-items:center;">
        <div class="city" style="font-size:1.2em;font-weight:bold">${item.name}</div>
      </div>
      <div class="meta" style="text-align:center;">
        <div class="desc">Click to see prices</div>
      </div>
    `;
    const tag = document.createElement('div');
    tag.className = 'tag';
    tag.textContent = 'Item';
    el.appendChild(tag);
    el.addEventListener('click', () => showItemDetail(state, market, good, item));
    itemsGrid.appendChild(el);
  });

  const backBtn = detailContent.querySelector('#backBtn');
  backBtn.addEventListener('click', () => showGoods(state, market));
  history.pushState({ path: `state/${state.id}/market/${market.id}/good/${good.id}` }, '', `${BASE}state/${state.id}/market/${market.id}/good/${good.id}`);
}

function showItemDetail(state, market, good, item) {
  detailContent.innerHTML = `
    <h2>${item.name}</h2>
    <div class="market-grid"></div>
    <button class="btn" id="backBtn">Back</button>
  `;

  const grid = detailContent.querySelector('.market-grid');

  if (item.prices && Object.keys(item.prices).length > 0) {
    Object.entries(item.prices).forEach(([label, value]) => {
      const priceValue = (value !== null && value !== undefined) ? value : 'Price N/A';
      const displayPrice = (typeof priceValue === 'number') ? `${priceValue} SDG` : priceValue;

      const el = document.createElement('article');
      el.className = 'card';
      el.innerHTML = `
        <div class="title-band" style="display:flex;flex-direction:column;align-items:center;justify-content:center;padding: 30px 10px;">
          <div class="city" style="font-size:1.6em;font-weight:bold;">${label}</div>
        </div>
        <div class="meta" style="text-align:center;margin-top:10px;padding-bottom: 20px;">
          <div class="price" style="font-size:1.3em;color:var(--accent);">Price: ${displayPrice}</div>
        </div>
      `;
      const tag = document.createElement('div');
      tag.className = 'tag';
      tag.textContent = 'Price';
      el.appendChild(tag);
      grid.appendChild(el);
    });
  } else if (item.quant && item.quant.length > 0 && fetchedData.prices) {
    item.quant.forEach(quant => {
      const priceValue = fetchedData.prices?.[quant.price_key] ?? 'Price N/A';
      const displayPrice = (typeof priceValue === 'number') ? `${priceValue} SDG` : priceValue;

      const el = document.createElement('article');
      el.className = 'card';
      el.innerHTML = `
        <div class="title-band" style="display:flex;flex-direction:column;align-items:center;justify-content:center;padding: 30px 10px;">
          <div class="city" style="font-size:1.6em;font-weight:bold;">${quant.name}</div>
        </div>
        <div class="meta" style="text-align:center;margin-top:10px;padding-bottom: 20px;">
          <div class="price" style="font-size:1.3em;color:var(--accent);">Price: ${displayPrice}</div>
        </div>
      `;
      const tag = document.createElement('div');
      tag.className = 'tag';
      tag.textContent = 'Price';
      el.appendChild(tag);
      grid.appendChild(el);
    });
  } else if (fetchedData.prices) {
    const priceKey = `${item.id}_s`;
    const priceValue = fetchedData.prices[priceKey] ?? 'Price N/A';
    const displayPrice = (typeof priceValue === 'number') ? `${priceValue} SDG` : priceValue;

    const el = document.createElement('article');
    el.className = 'card';
    el.innerHTML = `
        <div class="title-band" style="display:flex;flex-direction:column;align-items:center;justify-content:center;padding: 30px 10px;">
          <div class="city" style="font-size:1.6em;font-weight:bold;">${item.name}</div>
        </div>
        <div class="meta" style="text-align:center;margin-top:10px;padding-bottom: 20px;">
          <div class="price" style="font-size:1.3em;color:var(--accent);">Price: ${displayPrice}</div>
        </div>
      `;
    const tag = document.createElement('div');
    tag.className = 'tag';
    tag.textContent = 'Item';
    el.appendChild(tag);
    grid.appendChild(el);
  } else {
    const el = document.createElement('article');
    el.className = 'card';
    el.innerHTML = `
        <div class="title-band" style="display:flex;flex-direction:column;align-items:center;justify-content:center;padding: 30px 10px;">
          <div class="city" style="font-size:1.2em;font-weight:bold;">No price data available</div>
        </div>
        <div class="meta" style="text-align:center;margin-top:10px;padding-bottom: 20px;">
          <div class="desc">This item has no price entries in data.json</div>
        </div>
      `;
    const tag = document.createElement('div');
    tag.className = 'tag';
    tag.textContent = 'Info';
    el.appendChild(tag);
    grid.appendChild(el);
  }

  const backBtn = detailContent.querySelector('#backBtn');
  backBtn.addEventListener('click', () => showItems(state, market, good));
}

initializeApp();
















/*
 * This table contains the accent colours being used on the website.
 *
 */
const accents = [
'#06b6d4','#6366f1','#ef4444','#f59e0b','#10b981','#8b5cf6','#ec4899','#f97316',
'#22c55e','#0ea5e9','#ef5350','#a78bfa','#fb7185','#60a5fa','#34d399','#f43f5e',
'#f87171','#4ade80','#60a5fa','#facc15','#c084fc','#f472b6','#38bdf8','#f97316',
'#22c55e','#6366f1','#f43f5e','#3b82f6','#84cc16','#e879f9','#fbbf24','#10b981',
'#7c3aed','#f87171','#2dd4bf','#fcd34d','#e11d48','#6366f1','#f97316','#22d3ee',
'#16a34a','#f59e0b','#8b5cf6','#f472b6','#0ea5e9','#ea580c','#4ade80','#f43f5e',
'#60a5fa','#db2777','#22c55e','#fcd34d','#9333ea','#f87171'
];

/*
 * This sets the UI to use the light theme defined in the style file.
 */
document.body.setAttribute("data-theme", "light");

/*
 * This part is supposed to control accent colours.
 * There's a click listener to randomly set a new accent colour.
 * Don't touch this part. Just don't.
 */
const savedAccent = localStorage.getItem('accentColor');
if(savedAccent) setAccent(savedAccent);

function randomAccent() {
  return accents[Math.floor(Math.random()*accents.length)];
}

function setAccent(hex) {
  document.documentElement.style.setProperty('--accent', hex);
}

document.getElementById('titleColored').addEventListener('click', ()=>setAccent(randomAccent()));

/*
 * This makes the title colours dance. Don't touch it unless you have a better idea.
 */
const titleText = 'Sooq Price';
const titleColored = document.getElementById('titleColored');

titleText.split('').forEach((ch,i)=>{
  const span = document.createElement('span');
  span.className='letter';
  span.style.color = accents[i % accents.length];
  span.textContent = ch;
  titleColored.appendChild(span);
});

/*
 * This small script sets the interval for changing colours on the title.
 */
setInterval(() => {
  document.querySelectorAll('.letter').forEach((span, i) => {
    span.style.color = accents[Math.floor(Math.random() * accents.length)];
  });
}, 200);

/*
 * This is the footer. It shows my name based on the accent colour.
 * Unless you want it to glow, don't touch this part.
 */
const fooText = 'Muhammad Kamal';
const fooColored = document.getElementById('footer');
fooText.split('').forEach((ch,i)=>{
  const span = document.createElement('span');
  span.className='letter';
  span.style.color = accents[i % accents.length];
  span.textContent = ch;
  fooColored.appendChild(span);
});
const footer = document.getElementById('footer');

function updateFooter() {
  footer.innerHTML = `<div style="color: var(--accent);">Copyright © 2025 Muhammad Kamal</div>`;
}

updateFooter();

/*
 * This part is responsible for fetching current date/time and showing it.
 * There's a code to refresh the time every specific milliseconds.
 */
const dateEl = document.getElementById('date');

function updateDate() {
  const now = new Date();
  const date = now.toLocaleDateString();
  dateEl.textContent = date;
}

updateDate();