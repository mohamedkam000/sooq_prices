<div align="center">
<div align="center">
  <img src="app/sooq_price.png" width="600" />
</div>

<h1 align="center">Sooq Price</h1>

<p align="center">
  <b>A lightweight, modern multi‑platform for tracking and comparing local market prices. Available as both an Android app and a website, built for clarity, speed, and real-world utility.</b>
</p>

---

## Overview

**Sooq Price** is a minimal yet powerful platform designed to help users stay informed about local market prices. Whether you're budgeting, comparing vendors, or just curious about price trends, Sooq Price delivers clean data in a clean UI.

- **Website**  
  Dynamically loads new price data, entries for new items, categories, markets, and states directly from the shared `data.json` file.

- **Android App**  
  Built with Jetpack Compose and Material 3, emphasizing usability, responsiveness, and visual clarity. Now fully syncable with the website, the app dynamically loads new data from the shared source, ensuring it always reflects the latest updates.

Both platforms share the **same exact purpose** and **same exact data source**, ensuring consistency across devices.

---

## Features

- **Material 3 Expressive Design (App)**  
  Smooth transitions, adaptive theming, and a modern aesthetic.

- **Clean & Responsive UI (App + Web)**  
  Optimized for all screen sizes with intuitive navigation.

- **Local Price Snapshot**  
  View up-to-date prices for common goods across multiple markets.

- **Dynamic Data Loading (App + Web)**  
  Both platforms automatically reflect new items, categories, and markets from `data.json`.

- **Offline-Friendly (App)**  
  Sync once, then access data locally without requiring an internet connection.

---

## Uses

- Track price changes over time  
- Compare prices across different markets  
- Make informed buying decisions  
- Stay aware of inflation or seasonal shifts  
- Share price data with others in your community  

---

## Tech Stack

- **Kotlin + Jetpack Compose (App)**
- **Material 3 UI Components (App)**
- **Local & Dynamic JSON Storage (Shared)**
- **Responsive Web Frontend (Website)**

---

## Architecture

The **Sooq Price** ecosystem consists of two platforms — an Android app and a website — both connected to the same shared data source (`data.json`).  

```
                 ┌─────────────────────┐
                 │     data.json       │
                 │  (shared dataset)   │
                 └─────────┬───────────┘
                           │
        ┌──────────────────┴──────────────────┐
        │                                     │
┌───────────────┐                     ┌────────────────┐
│   Website     │                     │ Android App    │
│ - Dynamic     │                     │ - Dynamic      │
│   loading     │                     │   loading      │
│ - Always up   │                     │ - Fully sync   │
│   to date     │                     │ - Material 3   │
└───────────────┘                     └────────────────┘
        │                                     │
        └─────────────── Shared Purpose ──────┘
                 Consistent Market Price Data
────────────────────────────────────────────────────────

```

- Shared Data Source: Both platforms rely on the same data.json file for items, categories, markets, and states.  
- Website: Dynamically reflects new entries as soon as they’re added.  
- Android App: Now fully syncable, dynamically loading updates from the shared dataset.  
- Unified Goal: Deliver consistent, accessible market price information across devices.  

---

Installation (Manual for Android)

Since the app targets API 36 and supports Android 12 (API 31) and above, follow these steps:

- Download the APK  
   From your browser.

- Grant Install Permission  
   Click on the downloaded file, and when prompted, allow the app you're using to install (e.g., Chrome, Files by Google).

- Install the APK  
   Tap the file and proceed with installation.

- Launch the App  
   Open it from your app drawer.

---

Accessing the Website

Simply open the hosted website in your browser. It will always reflect the latest data from data.json.

---

Credits

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/hussein1000">
        <img src="https://github.com/hussein1000.png" width="80" height="80" alt="Hussein Saleh"/><br />
        <sub><b>Hussein Saleh</b></sub>
      </a>
      <br />Market tracking, testing and physical support
    </td>
    <td align="center">
      <a href="https://copilot.microsoft.com">
        <img src="https://copilot.microsoft.com/favicon.ico" width="80" height="80" alt="Microsoft Copilot"/><br />
        <sub><b>Microsoft Copilot</b></sub>
      </a>
      <br />App & website design, debugging, and documentation support
    </td>
  </tr>
</table>