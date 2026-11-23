# Simple Damage Tint

Simple Damage Tint is a client-side Fabric mod that lets you change the color
and opacity of the flash that appears when an entity takes damage. You can also
apply the tint to armor and armor trims. Open the settings through Mod Menu or
edit `config/simpledamagetint.json` directly. Enable **Rainbow tint** for a cycling
hue that uses your chosen opacity. Turn it off to return to your saved color.

## Build and install

Install **JDK 25** to build the mod. The Gradle wrapper downloads the required
Gradle version. The first build also downloads Minecraft and the build
dependencies.

```powershell
.\gradlew.bat build
```

The build produces one JAR for all 17 Minecraft Java Edition releases from
**1.21 through 26.3**. Copy `build/libs/simpledamagetint-1.1.0+1.21-26.3.jar`
to your `mods` folder, replacing any previous Simple Damage Tint JAR.

Fabric Loader selects the matching renderer at startup. Minecraft versions that
use identical renderer code share the same bundled renderer. The build uses
Fabric Loader 0.19.5. To play, use Java 21 for Minecraft 1.21.x and Java 25 for
Minecraft 26.x.

To build for a single Minecraft version, use its Gradle task. For example, this
command builds the 26.2 JAR in `versions/26.2/build/libs/`.

```powershell
.\gradlew.bat :mc-26.2:build
```

On Linux and macOS, use `./gradlew` in place of `.\gradlew.bat`.

## Source code

`src/common` contains the settings, user interface, shared rendering code, and
assets. For each Minecraft version, `minecraft.gradle` selects the additional
source directories for the GUI, overlay pixel format, and rendering API.
