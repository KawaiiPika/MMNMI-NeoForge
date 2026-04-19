package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class NormalBulletProjectile extends NuProjectileEntity {
   public NormalBulletProjectile(EntityType<? extends NormalBulletProjectile> type, Level world) {
      super(type, world);
   }

   public NormalBulletProjectile(Level world, LivingEntity thrower) {
      super((EntityType)ModProjectiles.NORMAL_BULLET.get(), world, thrower, (IAbility)null, SourceElement.NONE, SourceHakiNature.IMBUING, SourceType.PROJECTILE, SourceType.PHYSICAL, SourceType.BLUNT);
      this.setDamage(5.0F);
   }
}
