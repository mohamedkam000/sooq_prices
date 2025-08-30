document.addEventListener("DOMContentLoaded", () => {
  // Intercept clicks on cards
  document.addEventListener("click", e => {
    const card = e.target.closest(".product-card");
    if (card && card.dataset.url && card.dataset.url !== ".html") {
      e.preventDefault();
      loadPage(card.dataset.url);
    }
  });

  // Handle back/forward buttons
  window.addEventListener("popstate", e => {
    if (e.state && e.state.page) {
      loadPage(e.state.page, false);
    }
  });
});

function loadPage(url, pushState = true) {
  fetch(url)
    .then(res => res.text())
    .then(html => {
      // Extract just the <main> part of the page
      const parser = new DOMParser();
      const doc = parser.parseFromString(html, "text/html");
      const newMain = doc.querySelector("main");

      document.querySelector("main").replaceWith(newMain);

      if (pushState) {
        history.pushState({ page: url }, "", url);
      }

      if (typeof loadPrices === "function") {
        loadPrices();
      }
    })
    .catch(err => console.error("Failed to load page:", err));
}
