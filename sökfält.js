
const PRODUCTS = [
  { name: 'Färglycka',          category: 'Blommor',     url: '#' },
  { name: 'Höstbucketter',       category: 'Blommor',     url: '#' },
  { name: 'Vårkänsla',            category: 'Blommor',     url: '#' },
  { name: 'Orkidé',              category: 'Krukväxter',  url: '#' },
  { name: 'Blomsterlåda utomhus',category: 'Trädgård',    url: '#' },
  { name: 'Krukväxtmix',         category: 'Krukväxter',  url: '#' },
  { name: 'Säsongens bukett',    category: 'Blommor',     url: '#' },
  { name: 'Gröna favoriter',     category: 'Krukväxter',  url: '#' },
  { name: 'Trädgårdsset',        category: 'Trädgård',    url: '#' },
  
];

const searchWrap = document.querySelector('.search-wrap');
const searchInput = searchWrap.querySelector('input');

const dropdown = document.createElement('div');
dropdown.id = 'search-dropdown';
dropdown.style.cssText = `
  position:absolute; top:calc(100% + 6px); left:0; right:0;
  background:#fff; border:1px solid #e0e0e0; border-radius:10px;
  box-shadow:0 8px 24px rgba(0,0,0,.10); overflow:hidden;
  z-index:999; display:none;
`;
searchWrap.style.position = 'relative';
searchWrap.appendChild(dropdown);

let focusedIndex = -1;

function highlight(text, query) {
  const re = new RegExp(`(${query.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi');
  return text.replace(re, '<strong style="color:#1D9E75">$1</strong>');
}

function renderResults(query) {
  const q = query.trim().toLowerCase();
  dropdown.innerHTML = '';
  focusedIndex = -1;
  if (!q) { dropdown.style.display = 'none'; return; }

  const matches = PRODUCTS.filter(p =>
    p.name.toLowerCase().includes(q) || p.category.toLowerCase().includes(q)
  );

  if (!matches.length) {
    dropdown.innerHTML = `<div style="padding:14px;font-size:13px;color:#888;text-align:center">
      Inga resultat för "${query}"</div>`;
  } else {
    matches.forEach((p, i) => {
      const item = document.createElement('a');
      item.href = p.url;
      item.dataset.index = i;
      item.style.cssText = 'display:flex;align-items:center;gap:10px;padding:10px 14px;' +
        'font-size:14px;color:#1a1a1a;text-decoration:none;cursor:pointer;transition:background .1s';
      item.innerHTML = `<span>${highlight(p.name, query)}</span>
        <span style="margin-left:auto;font-size:11px;background:#f2f2f2;
          border-radius:4px;padding:2px 6px;color:#666">${p.category}</span>`;
      item.addEventListener('mouseenter', () => setFocus(i));
      dropdown.appendChild(item);
    });
  }
  dropdown.style.display = 'block';
}

function setFocus(i) {
  const items = dropdown.querySelectorAll('a');
  items.forEach(el => el.style.background = '');
  if (i >= 0 && items[i]) { items[i].style.background = '#f5f5f5'; focusedIndex = i; }
}

searchInput.addEventListener('input', () => renderResults(searchInput.value));

searchInput.addEventListener('keydown', (e) => {
  const items = dropdown.querySelectorAll('a');
  if (e.key === 'ArrowDown')  { e.preventDefault(); setFocus(Math.min(focusedIndex + 1, items.length - 1)); }
  else if (e.key === 'ArrowUp') { e.preventDefault(); setFocus(Math.max(focusedIndex - 1, 0)); }
  else if (e.key === 'Enter' && focusedIndex >= 0) { e.preventDefault(); items[focusedIndex]?.click(); }
  else if (e.key === 'Escape') { dropdown.style.display = 'none'; searchInput.blur(); }
});

document.addEventListener('mousedown', (e) => {
  if (!searchWrap.contains(e.target)) dropdown.style.display = 'none';
});
// ─────────────────────────────────────────────────────────────