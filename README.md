# Waypointer Halloween

Fabric client mod project for Minecraft 1.21.11 with 100 preset coordinate waypoints.

## Download the JAR from GitHub
1. Open the [Actions page](https://github.com/avishyant7/waypointer-halloween/actions).
2. Select **Build Mod JAR** and open the latest successful run (green check).
3. Under **Artifacts**, click **waypointer-halloween-jar** to download the ZIP.
4. Extract the ZIP to get the `.jar` file.

A build starts automatically whenever code is pushed to `main`. You can also start one manually from **Actions → Build Mod JAR → Run workflow**. Artifacts are kept for 30 days.

## Controls
- **Backspace**: remove the closest waypoint only when you are within 1 block of it.
- **H**: toggle the waypoint HUD setting (HUD rendering is not implemented yet).

The waypoint list is currently held in memory. Removed waypoints return when Minecraft restarts.

## Build locally
Use Java 21 and Gradle, then run:

```bash
gradle build
```

The mod JAR will be in `build/libs/`.

## Current scope
The project includes the Fabric setup, 100 coordinates, keybinds, and nearest-waypoint removal. Visible 3D markers and a waypoint HUD are not implemented yet. The GitHub Actions build has not been verified yet.
