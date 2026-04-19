package xyz.pixelatedw.mineminenomi.items.armors;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModArmorItem extends ArmorItem {

    public ModArmorItem(DeferredHolder<ArmorMaterial, ArmorMaterial> material, ArmorItem.Type type) {
        super(material, type, new Item.Properties());
    }

    public ModArmorItem(DeferredHolder<ArmorMaterial, ArmorMaterial> material, ArmorItem.Type type, Item.Properties props) {
        super(material, type, props);
    }
}
