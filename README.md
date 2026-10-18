# Enchant Slot Hints

Client-Mod für Minecraft **1.21.11** (Fabric). Am Enchanting Table steht der
Enchantment-Hinweis jedes der drei Felder direkt im Feld, du musst nicht mehr
hovern. Die unlesbare Galactic-Schrift wird dadurch überdeckt.

Was angezeigt wird: genau der Hinweis, den Vanilla beim Hovern zeigt (ein
Enchantment pro Feld, mit Level). Die Level-Kosten rechts im Feld bleiben sichtbar.

## Bauen (einmalig)

Ich konnte die Mod in meiner Umgebung nicht kompilieren und im Spiel testen.
Der Code ist also ungetestet. Falls beim Bauen oder Starten ein Fehler kommt,
schick mir die Fehlermeldung (bzw. den Crash-Report), dann fixe ich es.

Du brauchst:

1. **Java 21** (JDK). IntelliJ kann das selbst herunterladen.
2. **IntelliJ IDEA Community** (kostenlos) oder eine installierte Gradle-9-Version.

Mit IntelliJ:

1. Ordner `enchant-slot-hints` über *File → Open* öffnen und warten, bis Gradle
   alles geladen hat (dauert beim ersten Mal ein paar Minuten).
2. Rechts auf *Gradle → Tasks → build → build* doppelklicken.
3. Die fertige Mod liegt danach in `build/libs/enchant-slot-hints-1.0.0.jar`
   (nicht die `-sources.jar`).

Mit der Konsole (wenn Gradle installiert ist): im Projektordner `gradle build` ausführen.

## Installieren

Du brauchst **Fabric Loader (0.19.5 oder neuer)** und die **Fabric API** für 1.21.11.
Die `enchant-slot-hints-1.0.0.jar` kommt in den Ordner `.minecraft/mods`.
Die Mod läuft nur auf deinem Client, der Server braucht nichts.

## Andere Version?

Das Projekt ist auf 1.21.11 eingestellt. Für eine andere 1.21.x-Version müssen
die Zahlen in `gradle.properties` und die Versionsangabe in
`src/main/resources/fabric.mod.json` angepasst werden. Sag mir einfach, welche
Version du spielst.
