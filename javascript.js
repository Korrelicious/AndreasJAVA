// ── Storage ───────────────────────────────────────────────────────────────────
const STORAGE_KEY = 'javanytt_articles';

function loadArticles() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY)) || [];
  } catch {
    return [];
  }
}

function saveArticles(articles) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(articles));
}

// ── Toast ─────────────────────────────────────────────────────────────────────
function showToast(message, type = 'success') {
  const container = document.getElementById('toast-container');
  const toast = document.createElement('div');
  toast.className = `toast toast--${type}`;
  const icon = type === 'success' ? '✓' : '✕';
  toast.innerHTML = `<span class="toast__icon">${icon}</span><span class="toast__msg">${message}</span>`;
  container.appendChild(toast);

  requestAnimationFrame(() => {
    requestAnimationFrame(() => toast.classList.add('toast--visible'));
  });

  setTimeout(() => {
    toast.classList.remove('toast--visible');
    toast.addEventListener('transitionend', () => toast.remove(), { once: true });
  }, 3200);
}

// ── Render Articles ───────────────────────────────────────────────────────────
function renderArticles() {
  const grid = document.getElementById('articles-grid');
  const articles = loadArticles();

  if (articles.length === 0) {
    grid.innerHTML = '<p class="articles__empty">Inga artiklar skapade ännu.</p>';
    return;
  }

  grid.innerHTML = articles.map((a, i) => `
    <article class="article-card" data-index="${i}">
      <div class="article-card__top">
        <span class="article-card__cat">${escapeHtml(a.category)}</span>
        <time class="article-card__date">${a.date}</time>
      </div>
      <h3 class="article-card__title">${escapeHtml(a.title)}</h3>
      <p class="article-card__body">${escapeHtml(a.body)}</p>
      <div class="article-card__footer">
        <span class="article-card__author">${escapeHtml(a.author)}</span>
        <button class="article-card__delete" data-index="${i}" title="Radera artikel">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
            <polyline points="3 6 5 6 21 6"/>
            <path d="M19 6l-1 14H6L5 6"/>
            <path d="M10 11v6M14 11v6"/>
            <path d="M9 6V4h6v2"/>
          </svg>
          Radera
        </button>
      </div>
    </article>
  `).join('');

  grid.querySelectorAll('.article-card__delete').forEach(btn => {
    btn.addEventListener('click', () => deleteArticle(parseInt(btn.dataset.index)));
  });
}

function deleteArticle(index) {
  const articles = loadArticles();
  const title = articles[index]?.title || 'Artikel';
  articles.splice(index, 1);
  saveArticles(articles);
  renderArticles();
  showToast('"' + title + '" raderades.', 'delete');
}

function escapeHtml(str) {
  return String(str)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

// ── Modal ─────────────────────────────────────────────────────────────────────
function openModal() {
  document.getElementById('article-modal').classList.add('modal--open');
  document.getElementById('modal-title-input').focus();
}

function closeModal() {
  document.getElementById('article-modal').classList.remove('modal--open');
  document.getElementById('article-form').reset();
  clearErrors();
}

function clearErrors() {
  document.querySelectorAll('.form__error').forEach(el => el.textContent = '');
  document.querySelectorAll('.form__input--error').forEach(el => el.classList.remove('form__input--error'));
}

function validateForm(title, body, category) {
  let valid = true;
  if (!title.trim()) {
    document.getElementById('error-title').textContent = 'Titel krävs.';
    document.getElementById('modal-title-input').classList.add('form__input--error');
    valid = false;
  }
  if (!body.trim()) {
    document.getElementById('error-body').textContent = 'Ingress krävs.';
    document.getElementById('modal-body-input').classList.add('form__input--error');
    valid = false;
  }
  if (!category) {
    document.getElementById('error-category').textContent = 'Välj en kategori.';
    document.getElementById('modal-category-input').classList.add('form__input--error');
    valid = false;
  }
  return valid;
}

// ── Init ──────────────────────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  renderArticles();

  // Alla knappar med klassen js-open-modal öppnar modalen
  document.querySelectorAll('.js-open-modal').forEach(btn => {
    btn.addEventListener('click', openModal);
  });

  document.getElementById('modal-close').addEventListener('click', closeModal);
  document.getElementById('modal-cancel').addEventListener('click', closeModal);

  document.getElementById('article-modal').addEventListener('click', (e) => {
    if (e.target === e.currentTarget) closeModal();
  });

  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') closeModal();
  });

  document.getElementById('article-form').addEventListener('submit', (e) => {
    e.preventDefault();
    clearErrors();

    const title    = document.getElementById('modal-title-input').value;
    const body     = document.getElementById('modal-body-input').value;
    const category = document.getElementById('modal-category-input').value;

    if (!validateForm(title, body, category)) return;

    const articles = loadArticles();
    articles.unshift({
      title:    title.trim(),
      body:     body.trim(),
      category,
      author:   'Andreas Andersson',
      date:     new Date().toLocaleDateString('sv-SE', { year: 'numeric', month: 'short', day: 'numeric' })
    });

    saveArticles(articles);
    renderArticles();
    closeModal();
    showToast('"' + title.trim() + '" skapades!', 'success');
  });
});
