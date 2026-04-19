package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class TamaitoProjectile extends NuProjectileEntity {
   public TamaitoProjectile(EntityType<? extends TamaitoProjectile> type, Level world) {
      super(type, world);
   }

   public TamaitoProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.TAMAITO.get(), world, player, ability);
      this.setDamage(15.0F);
      this.setMaxLife(30);
      this.setArmorPiercing(0.25F);
   }
}
