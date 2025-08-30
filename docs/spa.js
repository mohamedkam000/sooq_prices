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
      const parser = new DOMParser();
      const doc = parser.parseFromString(html, "text/html");
      const newMain = doc.querySelector("main");

      const currentMain = document.querySelector("main");

      // Fade out current page
      currentMain.classList.add("fade-out");

      currentMain.addEventListener("animationend", () => {
        // Replace with new content
        currentMain.replaceWith(newMain);

        // Fade in new content
        newMain.classList.add("fade-in");

        // Push state
        if (pushState) {
          history.pushState({ page: url }, "", url);
        }

        // Run price loader only if needed
        if (newMain.querySelector(".price-list") && typeof loadPrices === "function") {
        loadPrices();
        }
      }, { once: true });
    })
    .catch(err => console.error("Failed to load page:", err));
}

