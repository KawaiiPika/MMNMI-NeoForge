package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.EnumMap;
import java.util.List;

public class ModMaterials {
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SIMPLE_LEATHER = ModRegistry.ARMOR_MATERIALS.register("simple_leather", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("mineminenomi", "simple_leather"))),
            0.0F,
            0.0F
    ));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SIMPLE_WOOL = ModRegistry.ARMOR_MATERIALS.register("simple_wool", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Items.WHITE_WOOL),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("mineminenomi", "simple_wool"))),
            0.0F,
            0.0F
    ));

    public static void init() {}
}
