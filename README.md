# Mausam

A simple Android weather app. Enter a city (and optionally a country) to get the current weather from the [OpenWeatherMap](https://openweathermap.org/api) API.

## Features
- Search by city name, with an optional country code
- Current weather fetched with Volley
- Animated GIF backgrounds (`android-gif-drawable`)

## Tech stack
Java, Android SDK (compileSdk 30, minSdk 23), AndroidX, Material Components, Volley.

## Build
1. Get a free API key from OpenWeatherMap.
2. Set it as `appid` in `app/src/main/java/com/rameshchoudhary/myapplication/MainActivity.java`.
3. Open the project in Android Studio and run it, or build with:
   ```bash
   ./gradlew assembleDebug
   ```
