package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.WeatherCloudTempo;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;

public class WeatherBallProjectile extends NuProjectileEntity {
   protected static final EntityDimensions WEATHER_CLOUD_SIZE = EntityDimensions.m_20398_(40.0F, 4.0F);
   protected Item weaponUsed;

   public WeatherBallProjectile(EntityType<? extends WeatherBallProjectile> type, Level world) {
      super(type, world);
   }

   public WeatherBallProjectile(EntityType<? extends WeatherBallProjectile> type, Level world, LivingEntity player, IAbility ability) {
      super(type, world, player, ability);
      this.setMaxLife(300);
   }

   public void m_8119_() {
      super.m_8119_();
      AbilityHelper.setDeltaMovement(this, this.m_20184_().f_82479_ / (double)1.5F, this.m_20184_().f_82480_, this.m_20184_().f_82481_ / (double)1.5F);
      if (this.f_19797_ < 200) {
         this.m_20184_().m_82520_((double)0.0F, 0.35, (double)0.0F);
      } else {
         AbilityHelper.setDeltaMovement(this, (double)0.0F, (double)0.0F, (double)0.0F);
      }

      if (!this.m_9236_().f_46443_ && this.getOwner() != null && this.f_19797_ >= 50) {
         Optional<WeatherCloudEntity> weatherCloud = WyHelper.getNearbyEntities(this.m_20182_(), this.m_9236_(), (double)10.0F, (double)4.0F, (double)10.0F, (Predicate)null, WeatherCloudEntity.class).stream().findFirst();
         if (weatherCloud.isPresent()) {
            ((WeatherCloudEntity)weatherCloud.get()).addWeatherBall(this);
            if (this instanceof ThunderBallProjectile) {
               ((WeatherCloudEntity)weatherCloud.get()).setLife(this.getLife() + 200);
            }

            this.m_146870_();
         } else if (this.f_19797_ >= 150) {
            if (this instanceof CoolBallProjectile) {
               List<HeatBallProjectile> heatBalls = WyHelper.<HeatBallProjectile>getNearbyEntities(this.m_20182_(), this.m_9236_(), (double)6.0F, (double)4.0F, (double)6.0F, (Predicate)null, HeatBallProjectile.class);
               IAbilityData props = (IAbilityData)AbilityCapability.get(this.getOwner()).orElse((Object)null);
               if (props == null) {
                  return;
               }

               WeatherCloudTempo weatherCloudTempo = (WeatherCloudTempo)props.getPassiveAbility((AbilityCore)WeatherCloudTempo.INSTANCE.get());
               if (weatherCloudTempo != null && weatherCloudTempo.canUse(this.getOwner()).isSuccess() && heatBalls.size() > 0) {
                  weatherCloudTempo.use(this.getOwner());
                  WeatherCloudEntity newCloud = new WeatherCloudEntity(this.m_9236_(), this.getOwner());
                  newCloud.setSize(WEATHER_CLOUD_SIZE);
                  newCloud.m_7678_(this.m_20185_(), this.m_20186_() + (double)1.0F, this.m_20189_(), 0.0F, 0.0F);
                  AbilityHelper.setDeltaMovement(newCloud, (double)0.0F, (double)0.0F, (double)0.0F);
                  this.m_9236_().m_7967_(newCloud);
                  heatBalls.forEach((e) -> e.m_146870_());
                  this.m_146870_();
               }
            }

         }
      }
   }

   public Item getWeaponUsed() {
      return this.weaponUsed;
   }
}
