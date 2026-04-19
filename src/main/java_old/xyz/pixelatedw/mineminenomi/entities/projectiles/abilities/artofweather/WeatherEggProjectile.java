package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.WeatherCloudTempo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class WeatherEggProjectile extends WeatherBallProjectile {
   public WeatherEggProjectile(EntityType<? extends WeatherEggProjectile> type, Level world) {
      super(type, world);
   }

   public WeatherEggProjectile(Level world, LivingEntity owner, IAbility core) {
      super((EntityType)ModProjectiles.WEATHER_EGG.get(), world, owner, core);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.getOwner() != null && this.f_19797_ >= 100) {
         IAbilityData props = (IAbilityData)AbilityCapability.get(this.getOwner()).orElse((Object)null);
         if (props != null) {
            WeatherCloudTempo ability = (WeatherCloudTempo)props.getEquippedOrPassiveAbility((AbilityCore)WeatherCloudTempo.INSTANCE.get());
            boolean canUseAbility = ability != null && ability.canUse(this.getOwner()).isSuccess();
            if (canUseAbility) {
               WeatherCloudEntity cloud = new WeatherCloudEntity(this.m_9236_(), this.getOwner());
               cloud.setSize(WEATHER_CLOUD_SIZE);
               cloud.m_7678_(this.m_20185_(), this.m_20186_() + (double)1.0F, this.m_20189_(), 0.0F, 0.0F);
               AbilityHelper.setDeltaMovement(cloud, (double)0.0F, (double)0.0F, (double)0.0F);
               this.m_9236_().m_7967_(cloud);
            }

            this.m_146870_();
         }
      }
   }
}
