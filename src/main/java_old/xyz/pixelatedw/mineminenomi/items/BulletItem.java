package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public abstract class BulletItem extends Item {
   public BulletItem(Item.Properties props) {
      super(props);
   }

   public abstract NuProjectileEntity createProjectile(Level var1, LivingEntity var2);
}
