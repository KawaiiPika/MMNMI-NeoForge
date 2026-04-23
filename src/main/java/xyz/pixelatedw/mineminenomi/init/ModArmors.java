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

    public static final DeferredItem<ModArmorItem> STRAW_HAT = ModRegistry.ITEMS.register("straw_hat", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> LUFFY_SHIRT = ModRegistry.ITEMS.register("luffys_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> LUFFY_PANTS = ModRegistry.ITEMS.register("luffys_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> LUFFY_SANDALS = ModRegistry.ITEMS.register("luffys_sandals", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> ZORO_CHEST = ModRegistry.ITEMS.register("zoros_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> ZORO_LEGS = ModRegistry.ITEMS.register("zoros_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> ZORO_FEET = ModRegistry.ITEMS.register("zoros_boots", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> FRANKY_GLASSES = ModRegistry.ITEMS.register("frankys_glasses", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> FRANKY_CHEST = ModRegistry.ITEMS.register("frankys_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> SANJI_CHEST = ModRegistry.ITEMS.register("sanjis_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> SANJI_LEGS = ModRegistry.ITEMS.register("sanjis_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> SANJI_FEET = ModRegistry.ITEMS.register("sanjis_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> SENOR_PINK_HEAD = ModRegistry.ITEMS.register("senior_pinks_bonnet", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> SENOR_PINK_CHEST = ModRegistry.ITEMS.register("senior_pinks_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> USOPP_CHEST = ModRegistry.ITEMS.register("usopps_overall", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> USOPP_LEGS = ModRegistry.ITEMS.register("usopps_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> USOPP_FEET = ModRegistry.ITEMS.register("usopps_boots", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> VICE_ADMIRAL_CHEST = ModRegistry.ITEMS.register("vice_admiral_jacket", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> VICE_ADMIRAL_LEGS = ModRegistry.ITEMS.register("vice_admiral_pants", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS)));
    public static final DeferredItem<ModArmorItem> VICE_ADMIRAL_FEET = ModRegistry.ITEMS.register("vice_admiral_boots", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS)));
    public static final DeferredItem<ModArmorItem> CP9_CHEST = ModRegistry.ITEMS.register("cp9_jacket", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> CP9_LEGS = ModRegistry.ITEMS.register("cp9_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> CP9_FEET = ModRegistry.ITEMS.register("cp9_boots", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> KUMA_CHEST = ModRegistry.ITEMS.register("kuma_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> KUMA_LEGS = ModRegistry.ITEMS.register("kuma_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> KUMA_FEET = ModRegistry.ITEMS.register("kuma_boots", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> SMOKER_CHEST = ModRegistry.ITEMS.register("smoker_jacket", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> SMOKER_LEGS = ModRegistry.ITEMS.register("smoker_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> SMOKER_FEET = ModRegistry.ITEMS.register("smoker_boots", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> KURO_GLASSES = ModRegistry.ITEMS.register("kuros_glasses", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> KURO_CHEST = ModRegistry.ITEMS.register("kuros_suit", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> KURO_LEGS = ModRegistry.ITEMS.register("kuros_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> KURO_FEET = ModRegistry.ITEMS.register("kuros_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> PEARL_HAT = ModRegistry.ITEMS.register("pearl_head_armor", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> PEARL_ARMOR = ModRegistry.ITEMS.register("pearl_body_armor", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> PEARL_LEGS = ModRegistry.ITEMS.register("pearl_legs_armor", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> ARLONG_HAT = ModRegistry.ITEMS.register("arlongs_hat", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> ARLONG_CHEST = ModRegistry.ITEMS.register("arlongs_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> ARLONG_LEGS = ModRegistry.ITEMS.register("arlongs_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> ARLONG_FEET = ModRegistry.ITEMS.register("arlongs_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> CHEW_CHEST = ModRegistry.ITEMS.register("chews_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> CHEW_LEGS = ModRegistry.ITEMS.register("chews_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> CHEW_FEET = ModRegistry.ITEMS.register("chews_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> KUROOBI_CHEST = ModRegistry.ITEMS.register("kuroobis_ki", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> KUROOBI_LEGS = ModRegistry.ITEMS.register("kuroobis_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> KUROOBI_FEET = ModRegistry.ITEMS.register("kuroobis_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> CROCODILE_CHEST = ModRegistry.ITEMS.register("crocodiles_suit", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> CROCODILE_LEGS = ModRegistry.ITEMS.register("crocodiles_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> CROCODILE_FEET = ModRegistry.ITEMS.register("crocodiles_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> MR1_CHEST = ModRegistry.ITEMS.register("mr_1s_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> MR1_LEGS = ModRegistry.ITEMS.register("mr_1s_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> MR1_FEET = ModRegistry.ITEMS.register("mr_1s_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> MR3_GLASSES = ModRegistry.ITEMS.register("mr_3s_glasses", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> MR3_CHEST = ModRegistry.ITEMS.register("mr_3s_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> MR3_LEGS = ModRegistry.ITEMS.register("mr_3s_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> MR3_FEET = ModRegistry.ITEMS.register("mr_3s_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> MR5_GLASSES = ModRegistry.ITEMS.register("mr_5s_glasses", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> MR5_CHEST = ModRegistry.ITEMS.register("mr_5s_shirt", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> MR5_LEGS = ModRegistry.ITEMS.register("mr_5s_pants", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.LEGGINGS));
    public static final DeferredItem<ModArmorItem> MR5_FEET = ModRegistry.ITEMS.register("mr_5s_shoes", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.BOOTS));
    public static final DeferredItem<ModArmorItem> BANDANA = ModRegistry.ITEMS.register("bandana", () -> (new ModArmorItem(ModMaterials.SIMPLE_WOOL, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> MH5_GAS_MASK = ModRegistry.ITEMS.register("mh5_gas_mask", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> CHOPPER_HAT = ModRegistry.ITEMS.register("choppers_hat", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> ACE_HAT = ModRegistry.ITEMS.register("aces_hat", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> KIZARU_GLASSES = ModRegistry.ITEMS.register("kizarus_glasses", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> KILLER_MASK = ModRegistry.ITEMS.register("killers_mask", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> LAW_HAT = ModRegistry.ITEMS.register("laws_hat", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> TRICORNE = ModRegistry.ITEMS.register("tricorne", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> SABO_HAT = ModRegistry.ITEMS.register("sabos_hat", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> DOFFY_GLASSES = ModRegistry.ITEMS.register("doflamingos_glasses", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> MIHAWK_HAT = ModRegistry.ITEMS.register("mihawks_hat", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> FLEET_ADMIRAL_HAT = ModRegistry.ITEMS.register("fleet_admirals_hat", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> CELESTIAL_DRAGON_BUBBLE = ModRegistry.ITEMS.register("celestial_dragon_bubble", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> PLUME_HAT = ModRegistry.ITEMS.register("plume_hat", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> BICORNE = ModRegistry.ITEMS.register("bicorne", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> WIDE_BRIM_HAT = ModRegistry.ITEMS.register("wide_brim_hat", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> HEART_GLASSES = ModRegistry.ITEMS.register("heart_glasses", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> CABAJI_SCARF = ModRegistry.ITEMS.register("cabajis_scarf", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> BIG_RED_NOSE = ModRegistry.ITEMS.register("big_red_nose", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> IRON_JAW = ModRegistry.ITEMS.register("iron_jaw", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET));
    public static final DeferredItem<ModArmorItem> WIZARD_HAT = ModRegistry.ITEMS.register("wizard_hat", () -> (new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.HELMET)));
    public static final DeferredItem<ModArmorItem> COLA_BACKPACK = ModRegistry.ITEMS.register("cola_backpack", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> TOMOE_DRUMS = ModRegistry.ITEMS.register("tomoe_drums", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> WOOTZ_STEEL_ARMOR = ModRegistry.ITEMS.register("wootz_steel_armor", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> MARINE_CAPTAIN_CAPE = ModRegistry.ITEMS.register("marine_captain_cape", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> PIRATE_CAPTAIN_CAPE = ModRegistry.ITEMS.register("pirate_captain_cape", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static final DeferredItem<ModArmorItem> FLUFFY_CAPE = ModRegistry.ITEMS.register("fluffy_cape", () -> new ModArmorItem(ModMaterials.SIMPLE_LEATHER, Type.CHESTPLATE));
    public static void init() {}
}
