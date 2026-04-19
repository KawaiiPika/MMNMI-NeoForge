package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.item.ArmorItem.Type;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.items.armors.ModArmorItem;

public class ModArmors {
    
    // Marine Set
    public static final DeferredItem<ModArmorItem> MARINE_HEAD = ModRegistry.ITEMS.register("marine_hat", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> MARINE_CHEST = ModRegistry.ITEMS.register("marine_shirt", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> MARINE_LEGS = ModRegistry.ITEMS.register("marine_pants", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> MARINE_FEET = ModRegistry.ITEMS.register("marine_boots", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));

    // Pirate Set
    public static final DeferredItem<ModArmorItem> PIRATE_CHEST = ModRegistry.ITEMS.register("pirate_shirt", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> PIRATE_LEGS = ModRegistry.ITEMS.register("pirate_pants", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> PIRATE_FEET = ModRegistry.ITEMS.register("pirate_boots", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));

    // Sniper
    public static final DeferredItem<ModArmorItem> SNIPER_GOGGLES = ModRegistry.ITEMS.register("sniper_goggles", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));

    public static final DeferredItem<ModArmorItem> MEDIC_BAG = ModRegistry.ITEMS.register("medic_bag", 
            () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));

    public static void init() {}
}
