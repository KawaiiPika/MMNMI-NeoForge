package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.ArtOfWeatherHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuVerticalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ThunderstormProjectile extends NuVerticalLightningEntity {
   public ThunderstormProjectile(EntityType<? extends ThunderstormProjectile> type, Level world) {
      super(type, world);
   }

   public ThunderstormProjectile(Level world, LivingEntity thrower, double posX, double posY, double posZ, int height, float maxTravelDistance, IAbility ability) {
      super((EntityType)ModProjectiles.THUNDERSTORM.get(), thrower, posX, posY, posZ, height, maxTravelDistance, 25.0F, ability);
      this.setAngle(30);
      this.setBranches(1);
      this.setSegments(15);
      this.setColor(ArtOfWeatherHelper.AOW_LIGHTNING_COLOR);
      this.setSize(0.25F);
      this.setDamage(60.0F);
      this.setMaxLife(20);
   }
}
