# Donut Tracker

A **client-side, read-only** Fabric mod that:

- Shows a **toast notification** (top right) when another player pays you
- Tracks your **session profit/loss** from chat and shows it on a small HUD
- Includes an **EV calculator** helper for working out expected value

## What it does NOT do
It only reads chat messages that the game already shows you. It never sends
commands, clicks, bets, or automates anything. Always check your server's
rules before using any mod.

## Setup
1. Install JDK 21 (Minecraft 1.21.11 is the last obfuscated version; Minecraft 26.x needs a different setup) and open the project in IntelliJ IDEA (or any Gradle IDE).
2. Run `gradle build` (or `./gradlew build` if you generated the wrapper with `gradle wrapper`).
3. The jar appears in `build/libs/`.

## Configuring the chat patterns
Open `ChatPatterns.java` and edit the regexes so they match the server's real
message format. Copy a real chat line, and adjust the pattern until it matches.

## Changing Minecraft version
Edit `gradle.properties` with the values from https://fabricmc.net/develop,
and if the build fails, check the toast/HUD class names for that version.

## License
MIT
