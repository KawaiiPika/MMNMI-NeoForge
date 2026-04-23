1. Use `run_in_bash_session` to read `src/main/java_old/xyz/pixelatedw/mineminenomi/api/events/entity/CrewEvent.java`. Modify `src/main/java/xyz/pixelatedw/mineminenomi/api/events/entity/CrewEvent.java` using `replace_with_git_merge_diff` to add `Join`, `Leave`, and `Kick` inner classes that implement `ICancellableEvent`. Use `read_file` to verify the changes.
2. Use `run_in_bash_session` with `cat << 'EOF'` to create `src/main/java/xyz/pixelatedw/mineminenomi/api/events/JollyRogerEvent.java` and port the old event into a NeoForge Event. Verify the file is created with `ls src/main/java/xyz/pixelatedw/mineminenomi/api/events/`.
3. Use `run_in_bash_session` to copy `FactionsWorldData` from `src/main/java_old/xyz/pixelatedw/mineminenomi/data/world/FactionsWorldData.java` to `src/main/java/xyz/pixelatedw/mineminenomi/data/world/FactionsWorldData.java`. Modify it using `replace_with_git_merge_diff` to fix compilation issues (e.g., removing `WyHelper.getResourceName` if needed or porting it). Verify with `./gradlew compileJava`.
4. Use `run_in_bash_session` with `cat << 'EOF'` to port `FactionHelper` by creating `src/main/java/xyz/pixelatedw/mineminenomi/api/helpers/FactionHelper.java`. Verify with `./gradlew compileJava`.
5. Create packets: Use `run_in_bash_session` with `cat << 'EOF'` to create the following files in `src/main/java/xyz/pixelatedw/mineminenomi/networking/packets/`:
  - `SOpenCrewScreenPacket.java`
  - `SOpenJollyRogerEditorScreenPacket.java`
  - `SSyncStrikerCrewPacket.java`
  - `CKickFromCrewPacket.java`
  - `CLeaveCrewPacket.java`
  - `CUpdateJollyRogerPacket.java`
  - `COpenCrewScreenPacket.java`
  - `COpenJollyRogerEditorScreenPacket.java`
  Verify the files are created with `ls src/main/java/xyz/pixelatedw/mineminenomi/networking/packets/`.
6. Use `replace_with_git_merge_diff` to register the new packets in `src/main/java/xyz/pixelatedw/mineminenomi/networking/ModNetworking.java`. Verify by compiling with `./gradlew compileJava`.
7. Use `run_in_bash_session` with `cat << 'EOF'` to port the `SSimpleMessageScreenEventPacket` logic by creating `src/main/java/xyz/pixelatedw/mineminenomi/networking/packets/SSimpleMessageScreenEventPacket.java` and port `IEventReceiverScreen` by creating `src/main/java/xyz/pixelatedw/mineminenomi/api/ui/IEventReceiverScreen.java`. Verify they are created using `ls`. Register it in `ModNetworking`.
8. Use `run_in_bash_session` with `cat << 'EOF'` to port the following UI files to `src/main/java/xyz/pixelatedw/mineminenomi/client/gui/screens/`:
  - `CrewDetailsScreen.java`
  - `JollyRogerEditorScreen.java`
  And to `src/main/java/xyz/pixelatedw/mineminenomi/client/gui/panels/`:
  - `CrewMembersScrollPanel.java`
  - `JollyRogerElementsScrollPanel.java`
  And to `src/main/java/xyz/pixelatedw/mineminenomi/client/gui/components/`:
  - `JollyRogerElementColorComponent.java`
  Verify these files are created using `ls`.
9. Use `./gradlew compileJava` to verify all the changes compile correctly and fix any remaining missing methods.
10. Complete pre-commit steps to ensure proper testing, verification, review, and reflection are done.
11. Run tests with `./gradlew test` to ensure the ported system functions correctly and introduces no regressions.
12. Submit the changes.
