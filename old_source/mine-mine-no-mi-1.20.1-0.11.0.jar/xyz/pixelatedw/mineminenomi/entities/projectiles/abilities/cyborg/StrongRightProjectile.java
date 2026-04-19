package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class StrongRightProjectile extends NuProjectileEntity {
   public StrongRightProjectile(EntityType<? extends StrongRightProjectile> type, Level world) {
      super(type, world);
   }

   public StrongRightProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.STRONG_RIGHT.get(), world, player, ability);
      this.setDamage(20.0F);
      this.setMaxLife(15);
      this.setFist();
      this.setEntityCollisionSize((double)1.0F);
   }
}
