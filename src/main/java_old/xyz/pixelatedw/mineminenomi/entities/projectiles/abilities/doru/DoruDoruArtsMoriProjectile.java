package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class DoruDoruArtsMoriProjectile extends NuProjectileEntity {
   public DoruDoruArtsMoriProjectile(EntityType<? extends DoruDoruArtsMoriProjectile> type, Level world) {
      super(type, world);
   }

   public DoruDoruArtsMoriProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.DORU_DORU_ARTS_MORI.get(), world, player, ability);
      this.setDamage(20.0F);
      this.setMaxLife(40);
      super.setPhysical();
   }
}
