document.addEventListener("DOMContentLoaded", () => {
  const root = document.documentElement;
  const storageKey = 'ds-theme';
  const accentKey = 'ds-accent';
  const themeColorMeta = document.querySelector('meta[name="theme-color"]');

  // Set theme
  const setThemeAttr = mode => {
    root.setAttribute('data-theme', mode);
    localStorage.setItem(storageKey, mode);
    document.querySelectorAll('.theme-toggle button').forEach(btn => {
      btn.setAttribute('aria-pressed', String(btn.dataset.theme === mode));
    });
  };

  const savedTheme = localStorage.getItem(storageKey) || 'system';
  setThemeAttr(savedTheme);

  document.querySelectorAll('.theme-toggle button').forEach(btn => {
    btn.addEventListener('click', () => {
      setThemeAttr(btn.dataset.theme);
      updateStatusBarColor(btn.dataset.theme);
    });
  });

  // Accent
  const savedAccent = localStorage.getItem(accentKey);
  if (savedAccent) setAccent(savedAccent);

  document.getElementById('randomAccent')?.addEventListener('click', () => {
    const palette = ['#E11D48','#F59E0B','#10B981','#0EA5E9','#6366F1','#8B5CF6','#EC4899','#22D3EE','#84CC16','#F43F5E'];
    const pick = palette[Math.floor(Math.random()*palette.length)];
    setAccent(pick);
    localStorage.setItem(accentKey, pick);
  });

  function setAccent(color) {
    const hsl = color.includes('%') ? color : hexToHslString(color);
    root.style.setProperty('--accent', hsl);
    if (themeColorMeta) themeColorMeta.setAttribute('content', hslToHex(hsl));
  }

  function updateStatusBarColor(theme) {
    if (!themeColorMeta) return;
    if(theme === 'light') themeColorMeta.setAttribute('content','#ffffff');
    else if(theme === 'dark') themeColorMeta.setAttribute('content','#111111');
    else themeColorMeta.setAttribute('content','#f0f0f0');
  }

  // Utils
  function hexToHslString(hex){
    hex = hex.replace('#','');
    if(hex.length===3) hex=hex.split('').map(x=>x+x).join('');
    const r=parseInt(hex.substring(0,2),16)/255;
    const g=parseInt(hex.substring(2,4),16)/255;
    const b=parseInt(hex.substring(4,6),16)/255;
    const max=Math.max(r,g,b), min=Math.min(r,g,b);
    let h,s,l=(max+min)/2;
    if(max===min){h=s=0;}
    else{
      const d=max-min;
      s=l>0.5?d/(2-max-min):d/(max+min);
      switch(max){
        case r:h=(g-b)/d+(g<b?6:0);break;
        case g:h=(b-r)/d+2;break;
        case b:h=(r-g)/d+4;break;
      }
      h/=6;
    }
    return `${Math.round(h*360)} ${Math.round(s*100)}% ${Math.round(l*100)}%`;
  }

  function hslToHex(hslString){
    const [h,s,l]=hslString.replace(/%/g,'').split(' ').map(Number);
    const S=s/100,L=l/100;
    const C=(1-Math.abs(2*L-1))*S,X=C*(1-Math.abs((h/60)%2-1)),m=L-C/2;
    let r=0,g=0,b=0;
    if(0<=h&&h<60){r=C;g=X;b=0;}
    else if(60<=h&&h<120){r=X;g=C;b=0;}
    else if(120<=h&&h<180){r=0;g=C;b=X;}
    else if(180<=h&&h<240){r=0;g=X;b=C;}
    else if(240<=h&&h<300){r=X;g=0;b=C;}
    else {r=C;g=0;b=X;}
    const R=Math.round((r+m)*255),G=Math.round((g+m)*255),B=Math.round((b+m)*255);
    return `#${toHex(R)}${toHex(G)}${toHex(B)}`;
  }

  function toHex(n){return n.toString(16).padStart(2,'0');}
});
