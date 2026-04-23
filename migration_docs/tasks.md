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

## Immediate Priority
- [/] **Port all missing Weapons**: Registering remaining swords, guns, bows and special weapons from `old_source`.
- [ ] **Network Synching**: Ensure `selectedAbilitySlot` and other stats are synced correctly to the client.
- [/] **Port remaining Ability logic**: Fruit abilities and Fighting Styles.
- [ ] **Haki Progression Logic**: Implement the logic for earning and spending Haki/Doriki points.
- [ ] **Mob AI**: Migrate specialized AI goals from legacy code.

## Missing Systems (Gap Analysis)
- [ ] **Animations**: 45+ legacy animations (Backflip, BattoStrike, etc.) and specialized ones for Fruits/Styles.
- [ ] **Morphs**: 30+ legacy morphs for Zoan transformations and specific fruit powers (Bane, Deka, etc.).
- [ ] **Challenges System**: Arenas and faction-specific challenges (Arlong, Buggy, Krieg, etc.).
- [ ] **Audio System**: Legacy audio handling and custom sound events.
- [ ] **Dials**: Impact, Flame, Breath, and other Skypiean dials.
- [ ] **Bullets**: Special ammo for guns.
- [ ] **Integrations**: Curios integration (Priority for faithful port).

## Pending Commands
- [ ] `AbilityCommand`, `QuestCommand`, `ChallengeCommand`, `CheckFruitsCommand`, `FGCommand`, `LoyaltyCommand`, etc.

## Future Milestones
- [ ] **Zoan Transformation System**: Requires a robust model-swapping and attribute modifier system.
- [ ] **Ship Generation**: Reconnecting complex structure generation for ships in worldgen.
- [ ] **Data-Driven Armor**: Transition to 1.21.1's new armor trim and asset systems if desired.

## Testing Tasks
- [ ] **ProgressionService**: Implement and verify unit tests for `grantTrainingPoints` (Logic implemented in `src/test/java/xyz/pixelatedw/mineminenomi/services/ProgressionServiceTest.java`, but requires environment setup to run).
