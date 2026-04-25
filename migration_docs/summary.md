# Mine Mine no Mi - Porting Status Summary

## Project Overview
This project is a port of the "Mine Mine no Mi" Minecraft mod from legacy versions to **NeoForge 1.21.1**. 

## Handover Blurb for the Next Agent
> "I am continuing the port of the 'Mine Mine no Mi' mod to NeoForge 1.21.1. The project structure is established, and the base registries (Items, Blocks, Abilities, Creative Tabs) are functional. I have recently migrated the Devil Fruit registration system (`ModFruits.java`) and reconnected the Worldgen JSON data. The current priority is to port the Weapons and Armor systems (`ModWeapons.java`, `ModArmors.java`), register BlockItems for all custom blocks, and finalize the Ability HUD rendering logic. Haki data and ability logic are the next major milestones after items are fully registered."

## Faithful Port Analysis
A thorough comparison between `old_source` and the current `src` has revealed the following:
- **Structural Alignment**: The current port follows the original package structure closely for core systems like `abilities`, `entities`, and `init`.
- **System Modernization**: Architecture has been successfully updated to 1.21.1 standards (Data Components, Data Attachments).
- **Gaps Identified**:
    - **Missing Systems**: Challenges, Animations, Morphs (Zoan), Audio, and Curios integration.
    - **Missing Items**: Bullets.
    - **Missing Entities**: Specialized Mobs and their custom AI goals.
- **Quest System**: The quest system in `src` appears to be more modular/expanded than the legacy `swordsman`-only structure.

## Current Status
- **Registries**: Items, Blocks, Fruits, Abilities, Creative Tabs, and Worldgen features are registered.
- **Data**: All legacy worldgen JSONs have been moved to the new `data/mineminenomi/worldgen` folder.
- **Data Generation**: Worldgen, language, sound, and particle providers implemented successfully (100%).
- **Client**: UI/Client safely isolated to `client.*` packages with `Dist.CLIENT`. Ability HUD overlay is created and icon rendering is implemented. Core UI screens (Abilities, Stats, Character Creator) are ported (100%).
- **GameTest Framework**: Successfully integrated for automated testing.
- **Items**: Utility items, iconic Devil Fruits, and Weapons/Armors ported and utilize modern Vanilla Data Components.
- **Dials**: Successfully migrated to Vanilla Data Components and `ItemAccessEnergyHandler`.
- **Concurrency**: Implemented Java 21 Virtual Threads and `StructuredTaskScope` for non-blocking I/O (e.g., `JollyRoger.java`).
- **Damage Logic**: Legacy damage logic successfully routed to `LivingDamageEvent.Post` and `ILivingEntityExtension`.
- **System Audits**: Fully compliant network payload handling and `ToolActions` to `ItemAbilities` migration without need for changes.
- **Abilities**: Approximately 45% of abilities (228 files) have been ported, including Haki, Rokushiki, and several core Devil Fruits.
- **World Generation Refactoring**: Replaced manual worldgen structure JSON files with `DatapackBuiltinEntriesProvider`.
- **Logical Side Checks**: Enforced proper server/client logical side checks in base abilities and AI.
- **Datagen Enhancements**: Fixed duplicate datagen outputs in `processResources` and programmatically mapped item textures.
- **Networking Tests**: Implemented networking validation tests for `CustomPacketPayload`s.
- **JSpecify Refactoring**: Updated type-use `@Nullable` annotations codebase-wide.

## Key Files
- `xyz.pixelatedw.mineminenomi.init.ModFruits`: Devil Fruit registration.
- `xyz.pixelatedw.mineminenomi.init.ModItems`: General item registration.
- `xyz.pixelatedw.mineminenomi.data.entity.PlayerStats`: Core persistent data structure.
- `xyz.pixelatedw.mineminenomi.client.gui.overlays.AbilityBarOverlay`: UI rendering logic.
- `src/main/resources/data/mineminenomi/worldgen`: Reconnected worldgen data.

## Missing Components (Pending Port)
- **Advanced Mob AI**: Custom goals for Marine/Pirate entities.
- **Animations package**: Handlers for all custom player/entity animations.
- **Morphs package**: Logic for Zoan and specific Paramecia transformations.
- **Challenges System**: Faction-specific arena challenges.
- **Remaining Fruits**: ~25 fruits still not started.

## Expert Recommendations
- **Animations & Morphs**: Avoid porting legacy `AnimationCapability.java`. Use `ModDataAttachments.ANIMATION_DATA` and prefer Vanilla's modern data-driven keyframe animations (`HierarchicalModel` and `AnimationDefinition`).
- **Weapons, Armors, and Tools**: Use NeoForge's `ItemAbilities` over the deprecated `ToolActions`. Rely on Built-In Data Maps (`neoforge:strippables`, `neoforge:waxables`, `neoforge:furnace_fuels`) for block interactions and fuel states.
- **Networking**: For Ability and UI logic, use `CustomPacketPayload` and `StreamCodec`. Always wrap world, entity, or player stat modifications in `context.enqueueWork(() -> { ... })` to push execution to the main server thread.
- **Mobs and AI**: Optimize area-of-effect abilities and target searches by using efficient AABB queries (`level.getEntitiesOfClass(...)`) paired with in-memory filtering.
- **Testing Strategy**: For complex ability tests, adopt an Object Mother / Test Data Builder pattern (e.g., `TestEntityBuilder`) to handle the repetitive setup of mock entities, levels, and PlayerStats.


## Deep Dive Progress Analysis

Comparing file counts between old and new source:
------------------------------------------------
Package              Old        New        Progress
abilities            593        231        38.95%
animations           81         0          0.00%
api                  372        94         25.27%
audio                1          0          0.00%
blocks               35         1          2.86%
challenges           45         0          0.00%
commands             23         7          30.43%
config               11         0          0.00%
containers           1          0          0.00%
data                 95         2          2.11%
datagen              105        0          0.00%
effects              61         28         45.90%
entities             319        52         16.30%
handlers             49         0          0.00%
init                 73         33         45.21%
integrations         6          0          0.00%
items                66         46         69.70%
mixins               49         0          0.00%
models               133        0          0.00%
morphs               51         0          0.00%
packets              129        0          0.00%
particles            144        2          1.39%
quests               23         36         156.52%
renderers            98         0          0.00%
ui                   42         8          19.05%
world                14         8          57.14%
