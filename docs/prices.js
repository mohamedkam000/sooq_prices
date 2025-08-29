document.addEventListener("DOMContentLoaded", () => {
  const url = "https://raw.githubusercontent.com/mohamedkam000/prices/main/data.json";

  fetch(url)
    .then(res => res.json())
    .then(data => {
      // Update prices
      document.getElementById("orange_100").textContent = data.orange_100 + " SDG";
      document.getElementById("apple_1").textContent = data.apple_1 + " SDG";
      document.getElementById("grape_k").textContent = data.grape_k + " SDG";
      document.getElementById("watermelon").textContent = data.watermelon + " SDG";

      // Show date and time
      const now = new Date();
      const hours = now.getHours().toString().padStart(2, "0");
      const minutes = now.getMinutes().toString().padStart(2, "0");
      const dateTimeStr = `Data Date: ${data.date} | Updated at: ${hours}:${minutes}`;
      document.getElementById("priceDateTime").textContent = dateTimeStr;
    })
    .catch(err => {
      console.error("Error loading data:", err);
    });
});
