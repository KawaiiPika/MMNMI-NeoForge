package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.ArtOfWeatherHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuVerticalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ThunderboltProjectile extends NuVerticalLightningEntity {
   public ThunderboltProjectile(EntityType<? extends ThunderboltProjectile> type, Level world) {
      super(type, world);
   }

   public ThunderboltProjectile(Level world, LivingEntity thrower, double posX, double posY, double posZ, int height, float maxTravelDistance, IAbility ability) {
      super((EntityType)ModProjectiles.THUNDERBOLT.get(), thrower, posX, posY, posZ, height, maxTravelDistance, 25.0F, ability);
      this.setAngle(30);
      this.setBranches(1);
      this.setSegments(15);
      this.setColor(ArtOfWeatherHelper.AOW_LIGHTNING_COLOR);
      this.setSize(0.2F);
      this.setDamage(40.0F);
      this.setMaxLife(10);
   }
}
