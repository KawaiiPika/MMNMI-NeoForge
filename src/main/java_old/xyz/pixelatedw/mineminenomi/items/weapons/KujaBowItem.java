package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;

public class KujaBowItem extends BowItem implements IMultiChannelColorItem {
   public KujaBowItem() {
      super((new Item.Properties()).m_41487_(1).m_41503_(700));
   }

   public AbstractArrow customArrow(AbstractArrow arrow) {
      arrow.m_36781_(arrow.m_36789_() * (double)2.0F);
      return arrow;
   }

   public int m_6615_() {
      return 32;
   }

   public int getDefaultLayerColor(int layer) {
      return layer == 0 ? 1349907 : -1;
   }

   public boolean canBeDyed() {
      return true;
   }

   public int getMaxLayers() {
      return 2;
   }
}
