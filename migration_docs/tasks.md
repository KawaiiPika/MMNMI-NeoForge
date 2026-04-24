# Migration Task List

## Completed
- [x] Set up NeoForge 1.21.1 workspace and build scripts.
- [x] Implement base `AkumaNoMiItem` with Data Component support.
- [x] Create dynamic Creative Tabs logic scanning for mod items.
- [x] Register first batch of iconic Devil Fruits (Gomu, Mera, etc.).
- [x] Migrate all legacy Worldgen JSON data (Features, Structures).
- [x] Set up Ability and Haki registry infrastructure.
- [x] Create basic Ability Bar HUD overlay.
- [x] **Port `ModWeapons`**: Registered iconic swords (Kikoku, Shusui, Yoru) and set up `ModTiers`.
- [x] **Port `ModArmors`**: Registered Marine and Pirate outfits and set up `ModMaterials`.
- [x] **Block Items**: Registered `BlockItem` for all blocks in `ModBlocks`.
- [x] **Ability Icons**: Implemented rendering logic for ability icons and selection highlight in the HUD.
- [x] **Logia Fruits Complete**: Ported core abilities for Mera, Magu, Pika, Hie, Goro, Yami, Moku, and Suna.
- [x] **Fruit-Locked Abilities**: Abilities now require the correct Devil Fruit to be used or equipped.
- [x] **Automatic Ability Mapping**: Eating a fruit now automatically assigns its signature abilities to the player's bar.
- [x] **Entity Registration**: Registered all required projectiles for the new abilities (Dai Funka, Meigo, Ryusei Kazan, Yasakani, Amaterasu, Partisan, Sango, White Blow, Desert Spada).
- [x] **Essential Stat Commands**: Ported `BellyCommand`, `BountyCommand`, `DorikiCommand`, `ExtolCommand`, `HakiExpCommand`, and `RemoveDFCommand`.
- [x] **Haki System Core**: Implemented Stamina-based Haki system (Busoshoku/Kenbunshoku) with drain and overuse mechanics.
- [x] **Ability API**: Base `Ability` class with result-driven validation and toggle support.
- [x] **UI Framework**: Ported core widgets and screens (Abilities Menu, Player Stats, Character Creator).
- [x] **Port all missing Weapons**: Registering remaining swords, guns, bows and special weapons from `old_source` completed.
- [x] **Modernize Custom Entity Spawning Data**: Custom projectiles and complex mobs now correctly implement `IEntityWithComplexSpawn`.
- [x] **Migrate Inventory and Energy Systems to ResourceHandler**: `WhiteWalkieStorageContainer` uses `ItemContainerContents` and Cyborg energy relies on Data Attachments.
- [x] **Update Plant and Crop Logic to SpecialPlantable**: Finished via the `TangerineCropsBlock` migration.
- [x] **Implement NeoForge Data Maps for Block/Item Properties**: Citing the new `furnace_fuels.json` and `strippables.json` data maps.
- [x] **Compliance Audits Passed**: Clean Up Event Listener Architecture and Rework Client-to-Server Ability Networking are fully aligned with NeoForge 1.21.1 architecture.

## Immediate Priority
- [ ] **Network Synching**: Ensure `selectedAbilitySlot` and other stats are synced correctly to the client.
- [/] **Port remaining Ability logic**: Fruit abilities and Fighting Styles.
  - [x] **Batch 1: Logias & Simple Paramecias**: Yuki, Yomi, Gasu, Sube, Bari.
  - [/] **Batch 2: Gravity & Weight Paramecias**: Zushi, Kilo, Ton (if exists), Fuwa (if exists).
  - [ ] **Batch 3: Creation & Manipulation Paramecias**: Doru, Awa, Doa, Ori, Beta, Kuku, Baku, Kira.
  - [ ] **Batch 4: Body & Misc Paramecias**: Horu, Bane, Deka, Jiki, Sabi, Kachi, Mini, Nagi, Kobu, Karu.
  - [ ] **Batch 5: Remaining Zoans (Models)**: Ryu Ryu (Allosaurus, Brachiosaurus, Pteranodon), Zou Zou, Tori Tori (Phoenix), Neko Neko (Leopard).
  - [ ] **Batch 6: Partially Ported Cleanup**: Finish missing abilities for Magu, Goro, Pika, Moku, Ope, Kage, Ito, Doku, Hana, Horo, Wara, Mero, Pero, Bara, Bomu, Suke, Noro, Yami, Suna.
  - [ ] **Batch 7: Fighting Styles - Swordsman**: Santoryu, Ittoryu.
  - [ ] **Batch 8: Fighting Styles - Martial Arts & Tech**: Brawler, Fishman Karate, Cyborg, Mink/Electro, Hasshoken, Ryusoken.
  - [ ] **Batch 9: Fighting Styles - Misc**: Missing parts of Art of Weather, Snipper, Doctor, etc.
- [ ] **Haki Progression Logic**: Implement the logic for earning and spending Haki/Doriki points.
- [ ] **Mob AI**: Migrate specialized AI goals from legacy code.
  - [ ] **Batch 1: Base & Ambient Mobs**: Civilians, Animals, Traders, Barkeepers.
  - [ ] **Batch 2: Marines Hierarchy**: Grunts, Captains, Trainers, Brutes, Snipers, Pacifistas.
  - [ ] **Batch 3: Pirates Hierarchy**: Pirates, Bandits, Notorious, Captains.
  - [ ] **Batch 4: Bosses & Special Entities**: OPBossEntity, OPEntity, WorldGov.

## Missing Systems (Gap Analysis)
- [ ] **Animations**: 45+ legacy animations (Backflip, BattoStrike, etc.) and specialized ones for Fruits/Styles.
  - [ ] **Batch 1: Combat & Movement Basics**: Backflip, BattoStrike, Dash, Leap, Block, Dodges.
  - [ ] **Batch 2: Weapon-Specific Animations**: Sword swings, gun aiming, sniper poses.
  - [ ] **Batch 3: Devil Fruit Animations**: Transformations, logia morphs, specific attack poses.
  - [ ] **Batch 4: Fighting Style Animations**: Rokushiki moves, Fishman Karate, Black Leg kicks.
- [ ] **Morphs**: 30+ legacy morphs for Zoan transformations and specific fruit powers (Bane, Deka, etc.).
- [ ] **Challenges System**: Arenas and faction-specific challenges (Arlong, Buggy, Krieg, etc.).
- [ ] **Audio System**: Legacy audio handling and custom sound events.
- [ ] **Dials**: Impact, Flame, Breath, and other Skypiean dials.
- [ ] **Bullets**: Special ammo for guns.
- [ ] **Integrations**: Curios integration (Priority for faithful port).

## Pending Commands
- [ ] `AbilityCommand`, `QuestCommand`, `ChallengeCommand`, `CheckFruitsCommand`, `FGCommand`, `LoyaltyCommand`, etc.
  - [ ] **Batch 1: Player & Stats Commands**: AbilityCommand, QuestCommand, ChallengeCommand, CheckFruitsCommand.
  - [ ] **Batch 2: Admin & World Commands**: FGCommand, LoyaltyCommand, Crew commands.

## Future Milestones
- [ ] **Zoan Transformation System**: Requires a robust model-swapping and attribute modifier system.
- [ ] **Ship Generation**: Reconnecting complex structure generation for ships in worldgen.
- [ ] **Data-Driven Armor**: Transition to 1.21.1's new armor trim and asset systems if desired.

## Testing Tasks
- [ ] **ProgressionService**: Implement and verify unit tests for `grantTrainingPoints` (Logic implemented in `src/test/java/xyz/pixelatedw/mineminenomi/services/ProgressionServiceTest.java`, but requires environment setup to run).
- [ ] Complete implementation and verification of TankyudonAbility (Ryu Ryu no Mi: Model Pteranodon).

## Expert Recommendations & Technical Guidelines
- [ ] **Animations System**: Refactor legacy animations to use Vanilla `HierarchicalModel` and `AnimationDefinition`.
- [ ] **Weapons & Tools**: Migrate legacy `ToolActions` to `ItemAbilities` and implement built-in Data Maps (`neoforge:strippables`, `neoforge:waxables`, `neoforge:furnace_fuels`).
- [ ] **Networking Thread Safety**: Ensure all `CustomPacketPayload` ability/UI handlers that modify game state are wrapped in `context.enqueueWork`.
- [ ] **Mob AI & Abilities**: Replace nested loops with AABB spatial queries (`level.getEntitiesOfClass`) and in-memory filtering for targeting.
- [ ] **Testing Strategy**: Implement a Test Data Builder / Object Mother pattern (e.g., `TestEntityBuilder`) to streamline the setup of complex mock entities and `PlayerStats` in the test suite.

## Post-Migration Tasks (Phase 3)
- [ ] **Task:** Establish 1.20.1-legacy Backport Branch
  - **Priority:** Low / Post-Migration (Phase 3)
  - **Description:** Create a long-lived 1.20.1-legacy branch originating from the original pre-port codebase. Selectively cherry-pick version-agnostic system optimizations developed during the 1.21.1 port.
  - **Approved Backports:** AI Spatial Query optimizations, Object Mother testing patterns, and Mockito static mocking upgrades.
  - **Rejected Backports:** Do not attempt to backport Java 21 Virtual Threads, Vanilla Data Components, Data Attachments, or the new 1.21.1 Networking/Damage pipelines.
