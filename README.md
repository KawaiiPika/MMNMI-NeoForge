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
| **Items** | 🔶 Partial | Utility items and 7 iconic fruits ported. Weapons/Armors in progress. |
| **Abilities** | 🔶 ~45% | 228/500+ files. Haki, Rokushiki, and core Logia/Paramecia logic complete. |
| **UI / Client** | ✅ 90% | Ability HUD, Combat Mode, and core UI screens functional. |
| **World Generation** | ✅ 100% | Legacy structures and features reconnected via modern JSON data. |
| **Mobs** | 🔶 Partial | Base `OPEntity` and `GruntEntity` functional. Specialized AI pending. |
| **Animations & Morphs**| ❌ 0% | Legacy `animations` and `morphs` packages pending migration. |

---

## ✅ Recent Milestones

- **Faithful Port Analysis**: Completed a deep comparison between `old_source` and the current project to identify and document all missing systems (Dials, Bullets, Challenges, etc.).
- **Modern Data Architecture**: Full implementation of NeoForge Data Attachments for player stats (Belly, Bounty, Doriki, Haki XP, etc.).
- **Ability Framework**: Result-driven validation system with server-client synchronization and toggle support.
- **Combat Mode**: Functional HUD with slot selection, cooldown rendering, and icon support.
- **Haki & Rokushiki**: 100% logic port for Busoshoku, Kenbunshoku, Geppo, and Soru.
- **Character Creator**: Restored the "Book" UI for selecting Factions, Races, and Fighting Styles.

---

## 🚀 Immediate Priorities

1. **Port all missing Weapons & Armors**: Registering remaining swords, guns, and special equipment from `old_source`.
2. **Fruit-Specific Ability Logic**: Migrating the 300+ remaining ability class files.
3. **Specialized Mob AI**: Porting complex goals like `HandleCannonGoal` and custom entity models.
4. **Animations System**: Re-implementing the legacy `animations` package to restore visual fidelity.
5. **Zoan Transformations**: Implementation of the `morphs` system for Zoan-type fruits.

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
