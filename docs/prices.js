function loadPrices() {
  const url = "https://raw.githubusercontent.com/mohamedkam000/prices/main/data.json";

  fetch(url)
    .then(res => res.json())
    .then(data => {
      // Update prices
      if (document.getElementById("orange_100"))
        document.getElementById("orange_100").textContent = data.orange_100 + " SDG";

      if (document.getElementById("apple_1"))
        document.getElementById("apple_1").textContent = data.apple_1 + " SDG";

      if (document.getElementById("grape_k"))
        document.getElementById("grape_k").textContent = data.grape_k + " SDG";

      if (document.getElementById("watermelon"))
        document.getElementById("watermelon").textContent = data.watermelon + " SDG";

      if (document.getElementById("priceDateTime")) {
        const now = new Date();
        document.getElementById("priceDateTime").textContent =
          `آخر تحديث: ${data.date} — ${now.toLocaleTimeString()}`;
      }
    })
    .catch(err => {
      console.error("Error loading data:", err);
    });
}

// Run once on full page load
document.addEventListener("DOMContentLoaded", loadPrices);
