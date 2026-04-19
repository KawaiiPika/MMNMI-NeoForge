package xyz.pixelatedw.mineminenomi.items.armors;

import javax.annotation.Nonnull;
import joptsimple.internal.Strings;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Mod3DArmorItem extends ModArmorItem {
   public Mod3DArmorItem(ArmorMaterial material, String textureString, ArmorItem.Type type) {
      this(material, textureString, type, new Item.Properties());
   }

   public Mod3DArmorItem(ArmorMaterial material, String textureString, ArmorItem.Type type, Item.Properties props) {
      super(material, textureString, type, props);
      this.setBaggyTrousers();
   }

   @Nonnull
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      String layerName = "";
      if (!Strings.isNullOrEmpty(type) && this.getMaxLayers() > 0) {
         layerName = String.format("_%s", type);
      }

      return String.format("%s:textures/models/armor/%s%s.png", "mineminenomi", this.textureName, layerName);
   }
}
