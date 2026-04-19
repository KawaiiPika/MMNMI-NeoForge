package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class CoolBallProjectile extends WeatherBallProjectile {
   public CoolBallProjectile(EntityType<? extends CoolBallProjectile> type, Level world) {
      super(type, world);
   }

   public CoolBallProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.COOL_BALL.get(), world, player, ability);
      this.setDamage(0.0F);
   }
}
