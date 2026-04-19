package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ThunderBallProjectile extends WeatherBallProjectile {
   public ThunderBallProjectile(EntityType<? extends ThunderBallProjectile> type, Level world) {
      super(type, world);
   }

   public ThunderBallProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.THUNDER_BALL.get(), world, player, ability);
      this.setDamage(2.0F);
   }
}
