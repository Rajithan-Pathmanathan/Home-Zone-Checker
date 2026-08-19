# 📍 Campus / Home Zone Checker

> **Mobile Application Development — Assignment 4 | Group 2**  
> Metropolitain Institute of Technology (MIT) · 2nd Year · 2nd Semester

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/Min%20API-24%20(Android%207.0)-orange?style=for-the-badge)](https://developer.android.com/studio/releases/platforms)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

---

## 📖 About

**Campus / Home Zone Checker** is an Android application that determines whether the user's current GPS location is **inside or outside a predefined geographic zone**. The app uses the **Fused Location Provider API** to get accurate location data and Android's built-in `Location.distanceTo()` method to calculate the distance from a reference point.

This project was developed as part of a group Android assignment focusing on **Location Services**.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 📍 **Real-time Location** | Uses Fused Location Provider for accurate GPS |
| ✅ / 🚫 **Zone Status** | Clearly shows **Inside Zone** or **Outside Zone** |
| 📏 **Distance Display** | Shows exact distance in metres from the reference point |
| 🗺️ **Zone Info Panel** | Displays reference coordinates and radius at all times |
| 🔒 **Permission Handling** | Graceful runtime permission request flow |
| 📱 **Clean UI** | Simple, demo-friendly interface built with Material 3 |
| 🧪 **Emulator Ready** | Supports mock/simulated location for testing |

---

## 🏗️ Architecture & Responsibilities

The project was divided among **3 team members**, each owning a distinct layer:

```
┌─────────────────────────────────────────────────────┐
│                   MainActivity.kt                   │
│                                                     │
│  [Member 1] Fused Location Provider                 │
│   └─ fusedLocationClient.lastLocation               │
│   └─ Permission handling                            │
│   └─ onRequestPermissionsResult()                   │
│                                                     │
│  [Member 2] Zone Calculation (ZoneChecker.kt)       │
│   └─ ZoneConfig  (lat, lng, radius)                 │
│   └─ ZoneChecker.checkZone()                        │
│   └─ Location.distanceTo()                          │
│                                                     │
│  [Member 3] UI + Testing (activity_main.xml)        │
│   └─ Status card (Inside / Outside Zone)            │
│   └─ Distance card                                  │
│   └─ Zone info card                                 │
│   └─ Error/message area                             │
└─────────────────────────────────────────────────────┘
```

---

## 🗂️ Project Structure

```
Home-Zone-Checker/
├── app/src/main/
│   ├── java/com/example/home_zone_checker/
│   │   ├── MainActivity.kt       ← Entry point, location + UI wiring
│   │   └── ZoneChecker.kt        ← Zone logic, ZoneConfig, ZoneResult
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml ← Full UI layout
│   │   └── values/
│   │       ├── colors.xml        ← App colour palette
│   │       ├── strings.xml       ← All string resources
│   │       └── themes.xml        ← Material 3 theme
│   └── AndroidManifest.xml       ← Permissions + activity declaration
└── build.gradle.kts              ← App-level Gradle config
```

---

## ⚙️ Zone Configuration

The reference zone is configured in `ZoneChecker.kt`:

```kotlin
object ZoneConfig {
    const val REFERENCE_LAT  = 6.972544   // Reference latitude
    const val REFERENCE_LNG  = 79.914655  // Reference longitude
    const val RADIUS_METERS  = 200f       // Zone radius in metres
}
```

To change the zone, simply update these three values.

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (Hedgehog or later)
- Android SDK API 24+
- A physical device **or** Android Emulator with location support

### Setup

```bash
# 1. Clone the repository
git clone https://github.com/Rajithan-Pathmanathan/Home-Zone-Checker.git

# 2. Open in Android Studio
# File → Open → select the cloned folder

# 3. Let Gradle sync automatically

# 4. Run on emulator or device
# Run → Run 'app'
```

### Permissions

The app requests the following permission at runtime:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

---

## 🧪 Testing with Emulator Mock Location

1. Launch the app on the Android Emulator
2. Open **Extended Controls** (the `···` button in the emulator toolbar)
3. Go to **Location** tab
4. Set coordinates **outside** the zone radius and tap **Check My Location**
5. Set coordinates **inside** the zone (near `6.972544, 79.914655`) and tap again
6. Observe the status change between **Outside Zone** 🚫 and **Inside Zone** ✅

### Test Coordinates

| Test Case | Latitude | Longitude | Expected Result |
|-----------|----------|-----------|-----------------|
| Outside Zone | `6.9272` | `79.8612` | 🚫 Outside Zone |
| Inside Zone | `6.9725` | `79.9147` | ✅ Inside Zone |

---

## 📦 Dependencies

| Library | Purpose |
|---------|---------|
| `com.google.android.gms:play-services-location` | Fused Location Provider |
| `androidx.appcompat` | AppCompat activity support |
| `androidx.constraintlayout` | Layout engine |
| `com.google.android.material` | Material 3 components |
| `androidx.core.ktx` | Kotlin extensions |
| `androidx.activity.ktx` | Activity Kotlin extensions |

---

## 👥 Contributors

| # | GitHub | Role |
|---|--------|------|
| 1 | [![Rajithan](https://img.shields.io/badge/Rajithan--Pathmanathan-Member%201-3DDC84?style=flat-square&logo=github)](https://github.com/Rajithan-Pathmanathan) | Fused Location Provider, permissions, project setup |
| 2 | [![Sobashi](https://img.shields.io/badge/SobashiDeSilva-Member%202-7F52FF?style=flat-square&logo=github)](https://github.com/SobashiDeSilva) | Zone calculation logic (`ZoneChecker.kt`, `distanceTo()`) |
| 3 | [![Sarma](https://img.shields.io/badge/SarmaHK-Member%203-FF6B35?style=flat-square&logo=github)](https://github.com/SarmaHK) | UI design, UI–logic integration, emulator testing |

---

## 📄 License

This project is submitted as a university assignment. All rights reserved by the contributors.

---

<p align="center">
  Made with ❤️ by Group 2 · Kelani MIT · Mobile Application Development
</p>
