# Simple Damage Tint

Clientseitige Fabric-Mod für die Farbe und Deckkraft des Treffer-Overlays auf
Entitäten, Rüstung und Rüstungsverzierungen. Die Einstellungen sind optional
über Mod Menu erreichbar und liegen in `config/simpledamagetint.json`.

## Bauen

Für den Build wird ein **JDK 25** benötigt. Die Gradle-Version liefert der
Wrapper. Der erste Build lädt Minecraft und die Build-Abhängigkeiten herunter.

```powershell
.\gradlew.bat build
```

Das erzeugt eine installierbare Datei für alle 17 Java-Releases von **1.21 bis
26.3**: `build/libs/simpledamagetint-1.0.1+1.21-26.3.jar`. Ersetze die bisherige
Simple-Damage-Tint-JAR im Ordner `mods` durch diese Datei. Fabric Loader wählt
beim Start den passenden eingebetteten Renderer. Builds mit identischem Code
teilen sich denselben Renderer. Minecraft 1.21.x benötigt Java 21 und Minecraft
26.x Java 25. Der Build verwendet Fabric Loader 0.19.5.

Einzelne Version bauen, hier am Beispiel von 26.2

```powershell
.\gradlew.bat :mc-26.2:build
```

Das zugehörige JAR liegt in `versions/26.2/build/libs/`. Unter Linux und
macOS wird der Wrapper mit `./gradlew` aufgerufen.

## Quellcode

`src/common` enthält die Konfiguration, die Benutzeroberfläche, gemeinsame
Rendering-Logik und Ressourcen. `minecraft.gradle` wählt zusätzlich die
passenden Quellordner für die GUI, das Overlay-Pixelformat und die
Minecraft-Rendering-API aus.

