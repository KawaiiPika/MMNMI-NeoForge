package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class DaiCircusProjectile extends NuProjectileEntity {
   public DaiCircusProjectile(EntityType<? extends DaiCircusProjectile> type, Level world) {
      super(type, world);
   }

   public DaiCircusProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.DAI_CIRCUS.get(), world, player, ability);
      this.setDamage(4.0F);
      this.setMaxLife(30);
      this.setEntityCollisionSize((double)2.0F);
      this.setPhysical();
   }
}
