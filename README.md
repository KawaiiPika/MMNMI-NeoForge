# Mine Mine no Mi - NeoForge 1.21.1 Port

## Project Overview
This project is a massive undertaking to port the "Mine Mine no Mi" Minecraft mod from legacy versions (1.20.1 Forge) to **NeoForge 1.21.1**.

The mod is currently being systematically migrated module by module, with a focus on modernizing the architecture (e.g., shifting from NBT to Data Components, and from Forge Capabilities to Data Attachments) while maintaining a **faithful port** of the original experience.

---

## 📊 Porting Progress Summary

| System | Status | Details |
|--------|--------|---------|
| **Core Registries** | ✅ 100% | Items, Blocks, Abilities, Creative Tabs, Dimensions, Features. |
| **Data Persistence** | ✅ 100% | Migrated to NeoForge Data Attachments & Data Components. |
| **Items** | ✅ 100% | Utility items and iconic fruits ported. Weapons/Armors finished and utilize modern Vanilla Data Components. |
| **Crew System** | ✅ 100% | Recent successful port. |
| **Abilities** | 🔶 ~45% | Port Active Devil Fruit Abilities (Batch 1) fully complete. Port Passive Devil Fruit Abilities in progress alongside unit tests. |
| **UI / Client** | ✅ 100% | Safely isolated to client.* packages with Dist.CLIENT. Ability HUD, Combat Mode, and core UI screens functional. |
| **World Generation** | ✅ 100% | Legacy structures and features reconnected via modern JSON data. |
| **Data Generation** | ✅ 100% | Worldgen, language, sound, and particle providers implemented. |
| **GameTest Framework**| ✅ 100% | Successfully integrated for automated testing. |
| **Mobs** | 🔶 Partial | Base `OPEntity` and `GruntEntity` functional. Specialized AI pending. |
| **Animations & Morphs**| ❌ 0% | Legacy `animations` and `morphs` packages pending migration. |

---

## 📊 Porting Progress Summary (Deep Dive)

| System | Old Source Files | New Source Files | Progress |
|--------|------------------|------------------|----------|
| **Abilities** | 593 | 231 | 38.95% |
| **Animations** | 81 | 0 | 0.00% |
| **Api** | 372 | 94 | 25.27% |
| **Audio** | 1 | 0 | 0.00% |
| **Blocks** | 35 | 1 | 2.86% |
| **Challenges** | 45 | 0 | 0.00% |
| **Commands** | 23 | 7 | 30.43% |
| **Config** | 11 | 0 | 0.00% |
| **Containers** | 1 | 0 | 0.00% |
| **Data** | 95 | 2 | 2.11% |
| **Datagen** | 105 | 0 | 0.00% |
| **Effects** | 61 | 28 | 45.90% |
| **Entities** | 319 | 52 | 16.30% |
| **Handlers** | 49 | 0 | 0.00% |
| **Init** | 73 | 33 | 45.21% |
| **Integrations** | 6 | 0 | 0.00% |
| **Items** | 66 | 46 | 69.70% |
| **Mixins** | 49 | 0 | 0.00% |
| **Models** | 133 | 0 | 0.00% |
| **Morphs** | 51 | 0 | 0.00% |
| **Packets** | 129 | 0 | 0.00% |
| **Particles** | 144 | 2 | 1.39% |
| **Quests** | 23 | 36 | 156.52% |
| **Renderers** | 98 | 0 | 0.00% |
| **Ui** | 42 | 8 | 19.05% |
| **World** | 14 | 8 | 57.14% |

## 💡 Expert Recommendations & Guidelines

1. **Animations & Morphs**: Do not attempt to port the old `AnimationCapability.java` generic boilerplate. Since `ModDataAttachments.ANIMATION_DATA` is successfully implemented, simply store plain Java animation state objects directly on the entities. For 1.21.1, strongly consider dropping heavy manual rendering math in favor of Vanilla's modern data-driven keyframe animations using `HierarchicalModel` and `AnimationDefinition` which natively supports keyframes and interpolation.
2. **Weapons, Armors, and Tools**: Use `ItemAbilities` over `ToolActions` since NeoForge renamed the old `ToolActions` system. Utilize Built-In Data Maps like `neoforge:strippables` and `neoforge:waxables` instead of hardcoding interactions. Use the `neoforge:furnace_fuels` data map instead of overriding methods, unless burn time depends strictly on dynamic Data Components.
3. **Networking for Abilities and UI**: Ensure you are using the new 1.21.1 `CustomPacketPayload` and `StreamCodec` system registered via `PayloadRegistrar`. Since NeoForge payload handlers execute on the network thread by default, you **must** wrap logic that modifies the world, damages an entity, or alters player stats in `context.enqueueWork(() -> { ... })` to push it to the main server thread, preventing `ConcurrentModificationException` crashes.
4. **Mobs and AI**: When implementing AI that searches for targets or abilities that affect an area, continue using efficient AABB (Axis-Aligned Bounding Box) queries (`level.getEntitiesOfClass(...)`) and in-memory filtering rather than nested loops to maintain server TPS.
5. **Testing**: Since abilities require complex setup (Entity mocks, Level mocks, PlayerStats), consider implementing an Object Mother or Test Data Builder pattern for your test suite (e.g., `TestEntityBuilder` class) to automate player mocking with pre-configured Data Attachments. The Expand Lightweight Bootstrap Test Suite Task is actively being worked on, referencing the recently committed unit tests for Sube Sube abilities and Haki damage calculations.
6. **Implement Automated Recipe & Model Datagen**: This is a critical upcoming goal since automated asset generation for recipes and models via GatherDataEvent is essential and currently at 0.00%.

---

## ✅ Recent Milestones

- **Faithful Port Analysis**: Completed a deep comparison between `old_source` and the current project to identify and document all missing systems (Bullets, Challenges, etc.).
- **Modern Data Architecture**: Full implementation of NeoForge Data Attachments for player stats (Belly, Bounty, Doriki, Haki XP, etc.).
- **Ability Framework**: Result-driven validation system with server-client synchronization and toggle support.
- **Combat Mode**: Functional HUD with slot selection, cooldown rendering, and icon support.
- **Haki & Rokushiki**: 100% logic port for Busoshoku, Kenbunshoku, Geppo, and Soru.
- **Character Creator**: Restored the "Book" UI for selecting Factions, Races, and Fighting Styles.
- **Damage Logic Modernization**: Completed the routing of legacy damage logic to `LivingDamageEvent.Post` and `ILivingEntityExtension`.
- **Concurrency Implementation**: Implemented Java 21 Virtual Threads and `StructuredTaskScope` for non-blocking I/O (e.g., `JollyRoger.java`).
- **Dials Migration**: Successfully migrated Dials to Vanilla Data Components and `ItemAccessEnergyHandler`.
- **System Audits**: Successfully audited Network Payload Handling and `ToolActions` to `ItemAbilities` migration.
- **World Generation Refactoring**: Replaced manual worldgen structure JSON files with `DatapackBuiltinEntriesProvider`.
- **Logical Side Checks**: Enforced proper server/client logical side checks in base abilities and AI.
- **Datagen Enhancements**: Fixed duplicate datagen outputs in `processResources` and programmatically mapped item textures.
- **Networking Tests**: Implemented networking validation tests for `CustomPacketPayload`s.
- **JSpecify Refactoring**: Updated type-use `@Nullable` annotations codebase-wide.

---

## 🚀 Immediate Priorities

1. **Optimize AI Spatial Queries**: Replace nested loops with efficient AABB (Axis-Aligned Bounding Box) queries and in-memory filtering for targeting in AI and abilities.
2. **Fruit-Specific Ability Logic**: Migrating the 300+ remaining ability class files systematically via planned batches. Batch 1 is done and Batch 2 is the current active focus.
3. **Specialized Mob AI**: Porting complex goals like `HandleCannonGoal` and custom entity models systematically via planned batches (Base/Ambient Mobs, Marines Hierarchy, Pirates Hierarchy, Bosses/Special Entities).
4. **Animations System**: Re-implementing the legacy `animations` package to restore visual fidelity systematically via planned batches (Combat/Movement Basics, Weapon-Specific, Devil Fruit, Fighting Style).
5. **Zoan Morphs & Animations**: Implementation of the `morphs` system for Zoan-type fruits.

---

## 📝 Handover Blurb for Next Agent
> "I am continuing the port of the 'Mine Mine no Mi' mod to NeoForge 1.21.1. The project structure is established, and the base registries (Items, Blocks, Abilities, Creative Tabs) are functional. I have recently migrated the Devil Fruit registration system (`ModFruits.java`) and reconnected the Worldgen JSON data. The current priority is to port the Weapons and Armor systems (`ModWeapons.java`, `ModArmors.java`), register BlockItems for all custom blocks, and finalize the Ability HUD rendering logic. Haki data and ability logic are the next major milestones after items are fully registered. Detailed status and a full gap analysis can be found in the `migration_docs` folder."

---

## 🛠 Installation & Development

### Setup
1. Clone the repository.
2. Open the project in your IDE (IntelliJ IDEA or Eclipse recommended).
3. If missing libraries, run `gradlew --refresh-dependencies`.
4. Use `gradlew clean` to reset if needed.

### Running the Mod
To launch the client for testing, ensure you have Java 21 installed and run:
```bash
chmod +x gradlew && ./gradlew runClient
```

### Mapping Names
By default, the MDK is configured to use the official mapping names from Mojang. Modders should be aware of the [Mojang Mapping License](https://github.com/NeoForged/NeoForm/blob/main/Mojang.md).

---

## 🔗 Additional Resources
- **Detailed Migration Docs**: See `/migration_docs/` for task lists and gap analysis.
- **Community Documentation**: [https://docs.neoforged.net/](https://docs.neoforged.net/)
- **NeoForged Discord**: [https://discord.neoforged.net/](https://discord.neoforged.net/)

## Post-Migration Tasks (Phase 3)
- **Task:** Establish 1.20.1-legacy Backport Branch
- **Priority:** Low / Post-Migration (Phase 3)
- **Description:** Create a long-lived 1.20.1-legacy branch originating from the original pre-port codebase. Selectively cherry-pick version-agnostic system optimizations developed during the 1.21.1 port.
- **Approved Backports:** AI Spatial Query optimizations, Object Mother testing patterns, and Mockito static mocking upgrades.
- **Rejected Backports:** Do not attempt to backport Java 21 Virtual Threads, Vanilla Data Components, Data Attachments, or the new 1.21.1 Networking/Damage pipelines.
