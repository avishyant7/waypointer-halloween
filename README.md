# Waypointer Halloween

Fabric client mod project for Minecraft 1.21.11 with 100 preset coordinate waypoints.

## Controls
- **Backspace**: remove the closest waypoint only when you are within 1 block of it.
- **H**: toggle the waypoint HUD setting (HUD rendering is not implemented yet).

The waypoint list is currently held in memory. Removed waypoints return when Minecraft restarts.

## Build
Use Java 21 and Gradle, then run:

```bash
gradle build
```

The mod JAR will be in `build/libs/`.

## Current scope
This first commit includes the Fabric project setup, all 100 coordinates, keybinds, and nearest-waypoint removal. It does not yet render visible 3D markers or a waypoint HUD, and it has not been built/tested in this environment.
