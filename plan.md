1. Add `src/main/resources/data/neoforge/data_maps/item/furnace_fuels.json` mapping for furnace fuels logic to replace `FurnaceFuelBurnTimeEvent`.
    - `mineminenomi:flame_dial`: 10000
    - `mineminenomi:wanted_poster_package`: 300
    - `mineminenomi:vivre_card`: 50
2. Add `src/main/resources/data/neoforge/data_maps/block/strippables.json` to handle stripping logs.
    - `mineminenomi:mangrove_log` -> `mineminenomi:stripped_mangrove_log`
    - `mineminenomi:mangrove_wood` -> `mineminenomi:stripped_mangrove_wood`
3. Verify the files `src/main/resources/data/neoforge/data_maps/item/furnace_fuels.json` and `src/main/resources/data/neoforge/data_maps/block/strippables.json` contents using `cat`.
4. Remove the `changeItemFuel` method from `src/main/java_old/xyz/pixelatedw/mineminenomi/handlers/WorldEventHandler.java`.
5. Run all relevant tests (e.g., using `./gradlew test`) to ensure the changes are correct and have not introduced regressions.
6. Complete pre-commit steps to ensure proper testing, verification, review, and reflection are done.
7. Submit the changes.
