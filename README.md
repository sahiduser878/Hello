# JANN AI: Phase 1
Min Android: 8.0 (API 26). Target/compile: 34.

## Build
1. Install Android Studio (Koala or newer; JDK 17 is bundled).
2. File > Open > select this folder, then let Gradle sync (Studio provisions Gradle;
   or run `gradle wrapper --gradle-version 8.9` if you have Gradle installed).
3. Run on a phone/emulator, or Build > Build APK(s)
   (app/build/outputs/apk/debug/app-debug.apk).

## Manual test
- Home shows logo, greeting, live time, status chip, animated orb.
- "Orb preview" chips switch Idle/Listening/Thinking/Speaking/Error (color + speed change).
- Orb, mic, keyboard, stop, quick cards show an honest "arrives in Phase N" message.
- History/Settings icons open placeholder screens; back returns.
- No permission prompts appear.
No automated tests exist yet and none have been run.
