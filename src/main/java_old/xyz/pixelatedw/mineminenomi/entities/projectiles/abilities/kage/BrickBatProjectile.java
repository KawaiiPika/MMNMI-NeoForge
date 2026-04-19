package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BrickBatProjectile extends NuProjectileEntity {
   public BrickBatProjectile(EntityType<BrickBatProjectile> type, Level world) {
      super(type, world);
   }

   public BrickBatProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.BRICK_BAT.get(), world, player, ability);
      this.setDamage(5.0F);
      this.setPassThroughEntities();
   }
}
