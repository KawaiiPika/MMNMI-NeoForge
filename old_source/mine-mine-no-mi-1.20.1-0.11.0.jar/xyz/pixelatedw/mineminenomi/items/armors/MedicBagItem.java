package xyz.pixelatedw.mineminenomi.items.armors;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;

public class MedicBagItem extends ModArmorItem {
   public MedicBagItem() {
      super(ModMaterials.SIMPLE_LEATHER_MATERIAL, "medic_bag", Type.CHESTPLATE, (new Item.Properties()).m_41503_(2000));
   }
}
