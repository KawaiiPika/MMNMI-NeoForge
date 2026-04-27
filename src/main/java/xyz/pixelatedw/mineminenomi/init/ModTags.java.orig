package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> GUN_AMMO = tag("gun_ammo");
        public static final TagKey<Item> BAZOOKA_AMMO = tag("bazooka_ammo");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
        }
    }
}
