# Migration History

## Phase 1: Foundation (Initial Setup)
- Established the NeoForge 1.21.1 environment.
- Configured `build.gradle` and `mods.toml`.
- Set up the main mod class and event bus listeners.

## Phase 2: Registry Infrastructure
- Ported the `ModRegistry` and `ModRegistries` systems.
- Implemented `ModDataComponents` and `ModDataAttachments` for player data persistence (replacing legacy NBT/Capabilities).
- Ported the `Ability` and `Haki` base classes and registry.

## Phase 3: Item & Data Component Migration
- Refactored `AkumaNoMiItem` to use the new 1.21.1 Data Component system.
- Created `ModFruits` to handle the large number of Devil Fruit items.
- Implemented a dynamic `CreativeModeTab` that automatically populates based on item class types.

## Phase 4: World Generation Reconnection
- Copied all legacy `data/worldgen` JSON files.
- Re-registered custom structure processors (`IgnoreWaterlogging`, `StoneTexture`).
- Re-connected `PoneglyphFeature` and `BoulderFeature`.

## Phase 5: Client & UI
- Created `AbilityBarOverlay` to handle the combat mode UI.
- Implemented `InputEvents` for combat mode toggling and ability selection.
- Registered GUI layers in `ClientEvents`.

## Phase 6: Weapons & Combat
- Initiated the porting of all legacy weapons from `old_source`.
- Expanded `ModTiers` and `ModWeapons` to include all iconic One Piece armaments.
- Refactored weapon classes (`ModSwordItem`, `ModGunItem`) for NeoForge 1.21.1.

## Phase 7: Systems Modernization & Optimization
- Implemented automated asset generation (Recipes, Models, Language, Sounds, Particles) natively yielding 100% DataGen coverage.
- Integrated GameTest framework for robust automated testing environments.
- Handled legacy damage logic eradication routing to `LivingDamageEvent.Post` and `ILivingEntityExtension`.
- Implemented Java 21 Virtual Threads (`StructuredTaskScope`) to prevent non-blocking I/O operations from freezing the client render thread.
- Migrated Dials to Vanilla Data Components and bridging them with `ItemAccessEnergyHandler`.
- Audited Network Payloads handlers with `context.enqueueWork()` and confirmed 100% adherence to 1.21.1 standards natively.
