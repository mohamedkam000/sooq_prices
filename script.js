const states = [
  {
    id: 'khartoum',
    name: 'Khartoum',
    img: 'https://images.unsplash.com/photo-1659864216522-494efbd76895?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8a2hhcnRvdW18ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&q=60&w=1200',
    markets: [
      {
        id: 'central',
        name: 'Central Market',
        img: 'https://www.sudanakhbar.com/wp-content/uploads/2022/11/%D8%B5%D9%8A%D9%86%D9%8A%D8%A9-%D8%A7%D9%84%D8%B3%D9%88%D9%82-%D8%A7%D9%84%D9%85%D8%B1%D9%83%D8%B2%D9%8A-%D8%A7%D9%84%D8%AE%D8%B1%D8%B7%D9%88%D9%85.jpg',
        goods: [
          {
            id: 'vegetables',
            name: 'Vegetables',
            img: 'https://sudafax.com/wp-content/uploads/2018/10/%D8%AE%D8%B6%D8%A7%D8%B1.jpg',
            items: [
              {
                id: 'tomatoes',
                name: 'Tomatoes',
                img: 'https://media.istockphoto.com/id/1419141035/photo/cut-red-tomato-close-up-in-a-box.jpg?s=612x612&w=0&k=20&c=eROI2zV4l1ozwwhdcdxgfqiKfynOZ-lAyv0FDUKyGl0=',
                quant: [
                  {
                    id: 'single',
                    name: 'Single',
                    price_key: 'tomatoes_s'
                  },
                  {
                    id: 'multiple',
                    name: 'Multiple',
                    price_key: 'tomatoes_m'
                  }
                ]
              },
              {
                id: 'carrots',
                name: 'Carrots',
                img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpLDKq28AEJ0xY4yeCJJ3Zcb55CFd9Wb1hCwD2nxdd4Zr-AXFPxl6L0Tg&s=10',
                quant: [
                  {
                    id: 'single',
                    name: 'Single',
                    price_key: 'carrots_s'
                  },
                  {
                    id: 'multiple',
                    name: 'Multiple',
                    price_key: 'carrots_m'
                  }
                ]
              }
            ]
          },
          {
            id: 'fruits',
            name: 'Fruits',
            img: 'https://arabic.news.cn/2022-05/01/1310581082_16513444999741n.jpg',
            items: [
              {
                id: 'apples',
                name: 'Apples',
                img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTd-FnM96HFu7Qo8Ls-4g4Hv6aZcVwXCAo5wKVdhlxFDSlOQAJdVUvkJmw&s=10',
                quant: [
                  {
                    id: 'single',
                    name: 'Single',
                    price_key: 'apples_s'
                  },
                  {
                    id: 'multiple',
                    name: 'Multiple',
                    price_key: 'apples_m'
                  }
                ]
              },
              {
                id: 'watermelon',
                name: 'Watermelon',
                img: 'https://bagelsandlasagna.com/wp-content/uploads/2024/05/ripe-watermelon-AdobeStock-low-res-1024x575.jpeg',
                quant: [
                  {
                    id: 'single',
                    name: 'Single',
                    price_key: 'watermelon_s'
                  },
                  {
                    id: 'multiple',
                    name: 'Multiple',
                    price_key: 'watermelon_m'
                  }
                ]
              },
              {
                id: 'oranges',
                name: 'Oranges',
                img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiYWJja7ljHmJKufcD2q7NcOunbKZv5Mglj67WpYJ7zSMaHKvfm6Bx2Us&s=10',
                quant: [
                  {
                    id: 'single',
                    name: 'Single',
                    price_key: 'oranges_s'
                  },
                  {
                    id: 'multiple',
                    name: 'Multiple',
                    price_key: 'oranges_m'
                  }
                ]
              },
              {
                id: 'bananas',
                name: 'Bananas',
                img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGoUt8-4dpt_fd_q_GoEpYpEBX__QlGiYm7YjniW4O699DEt59NttcEs7G&s=10',
                quant: [
                  {
                    id: 'single',
                    name: 'Single',
                    price_key: 'bananas_s'
                  },
                  {
                    id: 'multiple',
                    name: 'Multiple',
                    price_key: 'bananas_m'
                  }
                ]
              }
            ]
          },
          {
            id: 'grains',
            name: 'Grains',
            img: ''
          },
          {
            id: 'beverages',
            name: 'Beverages',
            img: ''
          },
          {
            id: 'meat',
            name: 'Meat',
            img: ''
          }
        ]
      },
      {
        id: 'sabreen',
        name: 'Sabreen Market',
        img: 'https://alnawrs.com/wp-content/uploads/2025/06/%D8%B3%D9%88%D9%82-%D8%B5%D8%A7%D8%A8%D8%B1%D9%8A%D9%86.png',
        goods: []
      },
      {
        id: 'arabi',
        name: 'Al Souq Al Arabi',
        img: 'https://mujaz.alahdath.news/wp-content/uploads/2025/07/%D8%A7%D9%84%D8%B3%D9%88%D9%82-%D8%A7%D9%84%D8%B9%D8%B1%D8%A8%D9%8A-_-%D8%A7%D9%84%D8%AE%D8%B1%D8%B7%D9%88%D9%85--scaled.jpg',
        goods: []
      },
      {
        id: 'bahri',
        name: 'Bahri Market',
        img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs02t35M8vuXehiN0HdQ_AyBlrrUQlxzNXWQ&s',
        goods: []
      },
      {
        id: 'local',
        name: 'Local Market',
        img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs02t35M8vuXehiN0HdQ_AyBlrrUQlxzNXWQ&s',
        goods: []
      }
    ]
  },
  {
    id: 'jazeera',
    name: 'Al-Jazeera',
    img: 'https://www.alnilin.com/wp-content/uploads/2023/09/madani_kush-780x470.jpg',
    markets: []
  },
  {
    id: 'rivernile',
    name: 'River Nile',
    img: 'https://www.alnilin.com/wp-content/uploads/2023/09/madani_kush-780x470.jpg',
    markets: []
  },
  {
    id: 'nsudan',
    name: 'Northern Sudan',
    img: 'https://sudanjournal.com/wp-content/uploads/2021/01/a1bd13c5fc813f0c17a64c8d52265180.jpg',
    markets: []
  },
  {
    id: 'rsea',
    name: 'Red Sea',
    img: 'https://i0.wp.com/arabscountries.com/wp-content/uploads/2022/11/The-Red-Sea-1.jpg',
    markets: []
  },
  {
    id: 'kassala',
    name: 'Kassala',
    img: 'https://saqraljidyanews.com/wp-content/uploads/2020/08/d6e7a4bc43ae7ef56f2bcf0bb6571f6e.jpg',
    markets: []
  },
  {
    id: 'sinnar',
    name: 'Sinnar',
    img: 'https://kushnews.net/wp-content/uploads/2023/09/sinnar_kush.jpg',
    markets: []
  }
];

const accents = [
'#06b6d4','#6366f1','#ef4444','#f59e0b','#10b981','#8b5cf6','#ec4899','#f97316',
'#22c55e','#0ea5e9','#ef5350','#a78bfa','#fb7185','#60a5fa','#34d399','#f43f5e',
'#f87171','#4ade80','#60a5fa','#facc15','#c084fc','#f472b6','#38bdf8','#f97316',
'#22c55e','#6366f1','#f43f5e','#3b82f6','#84cc16','#e879f9','#fbbf24','#10b981',
'#7c3aed','#f87171','#2dd4bf','#fcd34d','#e11d48','#6366f1','#f97316','#22d3ee',
'#16a34a','#f59e0b','#8b5cf6','#f472b6','#0ea5e9','#ea580c','#4ade80','#f43f5e',
'#60a5fa','#db2777','#22c55e','#fcd34d','#9333ea','#f87171'
];

const cardsGrid = document.getElementById('cardsGrid');
const gridView = document.getElementById('gridView');
const detailView = document.getElementById('detailView');
const detailContent = document.getElementById('detailContent');
let prices = {};

async function initializeApp() {
  try {
    const res = await fetch('https://raw.githubusercontent.com/mohamedkam000/sooq_prices/main/data.json');
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }
    const data = await res.json();
    prices = data;
    console.log('Successfully fetched prices:', prices);
  } catch (error) {
    console.warn('Failed to fetch prices:', error);
  }
  renderCards();
  navigateTo(location.pathname, { push: false });
}

function getPrice(stateId, marketId, categoryId, itemId, priceKey) {
  try {
    return (
      prices?.[stateId]?.[marketId]?.[categoryId]?.[itemId]?.[priceKey] ||
      'Price N/A'
    );
  } catch {
    return 'Price N/A';
  }
}

function showItemDetail(state, market, good, item) {
  detailContent.innerHTML = `
    <h2>${item.name}</h2>
    <div class="market-grid"></div>
    <button class="btn" id="backBtn">Back</button>
  `;

  const grid = detailContent.querySelector('.market-grid');

  // If the item has quantity variants
  if (item.quant && item.quant.length > 0) {
    item.quant.forEach(quant => {
      // New lookup based on nested structure
      const priceValue = getPrice(state.id, market.id, good.id, item.id, `${item.id}_${quant.id}`); 
      const displayPrice = `${priceValue} SDG`;

      const el = document.createElement('article');
      el.className = 'card';
      el.innerHTML = `
        <div class="title-band" style="display:flex;flex-direction:column;align-items:center;justify-content:center;padding: 30px 10px;">
          <div class="city" style="font-size:2em;font-weight:bold;">${quant.name}</div>
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
  } else {
    // Fallback: single price
    const priceValue = getPrice(state.id, market.id, good.id, item.id, `${item.id}_single`);
    const displayPrice = `${priceValue} SDG`;

    const el = document.createElement('article');
    el.className = 'card';
    el.innerHTML = `
      <div class="title-band" style="display:flex;flex-direction:column;align-items:center;justify-content:center;padding: 30px 10px;">
        <div class="city" style="font-size:2em;font-weight:bold;">${item.name}</div>
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
  }

  const backBtn = detailContent.querySelector('#backBtn');
  backBtn.addEventListener('click', () => showItems(state, market, good));
}

initializeApp();




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