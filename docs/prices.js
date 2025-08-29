document.addEventListener("DOMContentLoaded", () => {
  const url = "https://raw.githubusercontent.com/mohamedkam000/prices/main/data.json";

  fetch(url)
    .then(res => res.json())
    .then(data => {
      // Update prices
      document.getElementById("tomatoes_b").textContent = data.tomatoes_b + " SDG";
      document.getElementById("potatoes_k").textContent = data.potatoes_k + " SDG";
      document.getElementById("apple_1").textContent = data.tomatoes_b + " SDG";
      document.getElementById("orange_100").textContent = data.potatoes_k + " SDG";
      document.getElementById("watermelon").textContent = data.tomatoes_b + " SDG";
      document.getElementById("grape_k").textContent = data.potatoes_k + " SDG";
      
    })
    .catch(err => {
      console.error("Error loading data:", err);
    });
});
