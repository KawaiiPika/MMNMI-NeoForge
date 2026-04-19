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

## Immediate Priority
- [/] **Port all missing Weapons**: Registering remaining swords, guns, bows and special weapons from `old_source`.
- [ ] **Network Synching**: Ensure `selectedAbilitySlot` and other stats are synced correctly to the client.
- [x] Port Ability API and basic abilities (Haki, Rokushiki)
- [x] Implement Ability HUD with icon rendering and slot selection
- [x] Connect Ability HUD to PlayerStats data
- [x] Implement Networking for Ability Execution and Slot Selection
- [x] Port Base Mob System (OPEntity, GruntEntity)
- [x] Register ModMobs and handle Attributes
- [/] Port remaining Ability logic (Fruit abilities) — see `ability_migration_status.md`
- [ ] Port Haki ability class files (system exists, wrappers missing)
- [ ] Port Rokushiki ability classes (Geppo, Soru, Shigan, Rankyaku, Tekkai, Kamie, Rokuogan)
- [ ] Port EmptyHands, Command, Knockdown passives
- [ ] Port Logia Immunity/Intangibility ability classes
- [ ] Port remaining Mera/Hie/Goro/Pika/Moku logia abilities
- [ ] Port Fishman Karate race abilities (category 4)
- [ ] Port remaining Paramecia (Bari, Ito, Nikyu, Kage, Gura priority)
- [ ] Implement Zoan transformation model-swap system
- [ ] Port remaining Mob logic (Specialized mobs, AI)
- [ ] Implement Haki progression and training
- [ ] Finalize Worldgen (Structures, Ores)

## Commands Porting
- [x] **Essential Stat Commands**: Ported `BellyCommand`, `BountyCommand`, `DorikiCommand`, `ExtolCommand`, `HakiExpCommand`, and `RemoveDFCommand`.
- [ ] **Deferred Commands**: `AbilityCommand`, `QuestCommand`, `ChallengeCommand`, `CheckFruitsCommand`, `FGCommand`, `LoyaltyCommand`, etc., are pending migration of their underlying complex systems.

## Future Milestones
- [x] **Haki Logic**: Implemented Stamina-based Haki system (Busoshoku/Kenbunshoku) with drain and overuse mechanics.
- [x] **Combat Abilities**: Ported Gomu Gomu no Gatling, Bazooka, Rocket and Mera Mera no Mi projectiles.
- [x] **Ability Infrastructure**: Added `onTick` lifecycle support for continuous/charge abilities.
- [x] **Logia Intangibility**: Implemented damage negation with element-specific visual effects.
- [ ] **Data-Driven Armor**: Transition to 1.21.1's new armor trim and asset systems if desired.
