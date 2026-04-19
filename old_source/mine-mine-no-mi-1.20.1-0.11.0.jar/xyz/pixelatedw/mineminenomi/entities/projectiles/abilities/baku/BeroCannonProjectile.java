package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.baku;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BeroCannonProjectile extends NuProjectileEntity {
   public BeroCannonProjectile(EntityType<BeroCannonProjectile> type, Level world) {
      super(type, world);
   }

   public BeroCannonProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.BERO_CANNON.get(), world, player, ability);
      this.setDamage(20.0F);
      this.setMaxLife(50);
      this.setGravity(0.01F);
      this.setPhysical();
   }
}
