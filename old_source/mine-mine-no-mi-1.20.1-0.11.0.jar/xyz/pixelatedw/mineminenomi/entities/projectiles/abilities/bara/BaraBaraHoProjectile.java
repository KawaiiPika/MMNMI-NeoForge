package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BaraBaraHoProjectile extends NuProjectileEntity {
   public BaraBaraHoProjectile(EntityType<? extends BaraBaraHoProjectile> type, Level world) {
      super(type, world);
   }

   public BaraBaraHoProjectile(Level world, LivingEntity player, IAbility parent) {
      super((EntityType)ModProjectiles.BARA_BARA_HO.get(), world, player, parent);
      this.setDamage(6.0F);
      this.setMaxLife(12);
      this.setFist();
   }
}
