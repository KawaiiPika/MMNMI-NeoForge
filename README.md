# Mine Mine no Mi - NeoForge 1.21.1 Port

## Project Overview
This project is a massive undertaking to port the "Mine Mine no Mi" Minecraft mod from legacy versions (1.20.1 Forge) to **NeoForge 1.21.1**.

The mod is currently being systematically migrated module by module, with a focus on modernizing the architecture (e.g., shifting from NBT to Data Components, and from Forge Capabilities to Data Attachments).

---

## 📊 Porting Progress Summary

| System | Status | Details |
|--------|--------|---------|
| **Core Registries** | ✅ 100% | Items, Blocks, Abilities, Creative Tabs, Dimensions, Features. |
| **Data Persistence** | ✅ 100% | Migrated to NeoForge Data Attachments & Data Components. |
| **Items** | 🔶 Partial | Utility items and 7 iconic Devil Fruits ported. Weapons/Armors started. |
| **Abilities** | 🔶 ~22% | ~110/500+ files. Haki & Rokushiki core logic complete. |
| **UI / Client** | ✅ 90% | Ability HUD, Combat Mode, Character Creator, and Ability Menus functional. |
| **World Generation** | ✅ 100% | Legacy structures and features reconnected via modern JSON data. |
| **Mobs** | 🔶 Partial | Base `OPEntity` and `GruntEntity` functional. Specialized AI pending. |

---

## ✅ Completed Milestones

- **Modern Data Architecture**: Full implementation of NeoForge Data Attachments for player stats (Belly, Bounty, Doriki, Haki XP, etc.).
- **Ability Framework**: Result-driven validation system with server-client synchronization and toggle support.
- **Combat Mode**: Functional HUD with slot selection (scroll wheel/keys), cooldown rendering, and icon support.
- **Haki & Rokushiki**: 100% logic port for Busoshoku, Kenbunshoku, Geppo, and Soru.
- **Worldgen**: All legacy structures (Marine Ships, Bases, etc.) and features (Poneglyphs, Boulders) are functional in 1.21.1.
- **Character Creator**: Restored the "Book" UI for selecting Factions, Races, and Fighting Styles.

---

## 🚀 Immediate Priorities

1. **Port all missing Weapons & Armors**: Registering remaining swords, guns, and special equipment from `old_source`.
2. **Fruit-Specific Ability Logic**: Migrating the 400+ remaining ability class files (see `migration_docs/ability_migration_status.md`).
3. **Specialized Mob AI**: Porting complex goals like `HandleCannonGoal` and custom entity models.
4. **Zoan Transformations**: Implementation of a model-swap system for Zoan-type fruits.

---

## 📝 Handover Blurb for Next Agent
> "I am continuing the port of the 'Mine Mine no Mi' mod to NeoForge 1.21.1. The project structure is established, and the base registries (Items, Blocks, Abilities, Creative Tabs) are functional. I have recently migrated the Devil Fruit registration system (`ModFruits.java`) and reconnected the Worldgen JSON data. The current priority is to port the Weapons and Armor systems (`ModWeapons.java`, `ModArmors.java`), register BlockItems for all custom blocks, and finalize the Ability HUD rendering logic. Haki data and ability logic are the next major milestones after items are fully registered. Detailed status can be found in the `migration_docs` folder in the project root."

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
./gradlew runClient
```

### Mapping Names
By default, the MDK is configured to use the official mapping names from Mojang. Modders should be aware of the [Mojang Mapping License](https://github.com/NeoForged/NeoForm/blob/main/Mojang.md).

---

## 🔗 Additional Resources
- **Community Documentation**: [https://docs.neoforged.net/](https://docs.neoforged.net/)
- **NeoForged Discord**: [https://discord.neoforged.net/](https://discord.neoforged.net/)
- **Detailed Migration Docs**: See `/migration_docs/` in the project root.
