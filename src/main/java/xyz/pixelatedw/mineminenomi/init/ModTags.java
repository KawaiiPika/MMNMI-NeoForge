package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> GUN_AMMO = tag("gun_ammo");
        public static final TagKey<Item> BAZOOKA_AMMO = tag("bazooka_ammo");
        public static final TagKey<Item> MELEE_ENCHANTABLE_BY_SMITHING = tag("melee_enchantable_by_smithing");
        public static final TagKey<Item> RANGED_ENCHANTABLE_BY_SMITHING = tag("ranged_enchantable_by_smithing");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
        }
    }
}
