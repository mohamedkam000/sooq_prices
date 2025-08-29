document.addEventListener("DOMContentLoaded", () => {
  const url = "https://raw.githubusercontent.com/your-username/your-repo/main/data.json"; // change to your link

  fetch(url)
    .then(res => res.json())
    .then(data => {
      // Update prices
      document.getElementById("tomatoes_b").textContent = data.tomatoes_b + " SDG";
      document.getElementById("potatoes_k").textContent = data.potatoes_k + " SDG";
    })
    .catch(err => {
      console.error("Error loading data:", err);
    });
});
