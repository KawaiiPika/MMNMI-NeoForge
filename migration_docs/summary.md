# Mine Mine no Mi - Porting Status Summary

## Project Overview
This project is a port of the "Mine Mine no Mi" Minecraft mod from legacy versions to **NeoForge 1.21.1**. 

## Handover Blurb for the Next Agent
> "I am continuing the port of the 'Mine Mine no Mi' mod to NeoForge 1.21.1. The project structure is established, and the base registries (Items, Blocks, Abilities, Creative Tabs) are functional. I have recently migrated the Devil Fruit registration system (`ModFruits.java`) and reconnected the Worldgen JSON data. The current priority is to port the Weapons and Armor systems (`ModWeapons.java`, `ModArmors.java`), register BlockItems for all custom blocks, and finalize the Ability HUD rendering logic. Haki data and ability logic are the next major milestones after items are fully registered."

## Current Status
- **Registries**: Items, Blocks, Fruits, Abilities, Creative Tabs, and Worldgen features are registered.
- **Data**: All legacy worldgen JSONs have been moved to the new `data/mineminenomi/worldgen` folder.
- **Client**: Ability HUD overlay is created but needs icon rendering logic.
- **Items**: Most utility items are ported. Devil Fruits are being registered in batches. Weapons porting has started.

## Key Files
- `xyz.pixelatedw.mineminenomi.init.ModFruits`: Current focus for fruit registration.
- `xyz.pixelatedw.mineminenomi.init.ModItems`: General item registration.
- `xyz.pixelatedw.mineminenomi.client.gui.overlays.AbilityBarOverlay`: UI rendering logic.
- `src/main/resources/data/mineminenomi/worldgen`: Reconnected worldgen data.

## Missing Components (Pending Port)
- `ModWeapons`: Swords, Guns, etc.
- `ModArmors`: Marine/Pirate uniforms.
- `ModMobs`: Entity registration and spawn eggs.
- Full Ability Logic: Implementation of specific fruit powers.
- Haki System: Exp tracking and stat boosts.
