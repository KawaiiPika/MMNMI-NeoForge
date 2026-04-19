package xyz.pixelatedw.mineminenomi.items.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.items.BulletItem;

public class KairosekiBulletItem extends BulletItem {
   public KairosekiBulletItem() {
      super(new Item.Properties());
   }

   public NuProjectileEntity createProjectile(Level level, LivingEntity thrower) {
      return new KairosekiBulletProjectile(level, thrower);
   }
}
