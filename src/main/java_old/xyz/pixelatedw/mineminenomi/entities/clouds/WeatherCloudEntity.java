package xyz.pixelatedw.mineminenomi.entities.clouds;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.RainTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderboltTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderstormTempo;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.CoolBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WeatherCloudEntity extends CloudEntity {
   private static final int PARTICLE_RATE = 5;
   private static final int TICK_RATE = 50;
   private List<WeatherBallProjectile> weatherBalls = new ArrayList();
   private boolean charged = false;
   private boolean superCharged = false;
   private int particleTick = 5;
   private int updateTick = 50;
   private IAbilityData props;

   public WeatherCloudEntity(EntityType<? extends WeatherCloudEntity> type, Level world) {
      super(type, world);
   }

   public WeatherCloudEntity(Level world, LivingEntity owner) {
      super((EntityType)ModEntities.WEATHER_CLOUD.get(), world, owner);
      this.setOwner(owner);
      this.setLife(200);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (--this.particleTick <= 0) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.WEATHER_CLOUD.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
            if (this.charged || this.superCharged) {
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHARGED_WEATHER_CLOUD.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
            }

            this.particleTick = 5;
         }

         if (--this.updateTick > 0) {
            return;
         }

         this.updateTick = 50;
         if (this.props == null) {
            this.props = (IAbilityData)AbilityCapability.get(this.getOwner()).orElse((Object)null);
         }

         if (this.getLife() <= 0 || this.getOwner() == null || this.props == null) {
            this.m_146870_();
            return;
         }

         this.setLife(this.getLife() - 1);
         Stream var10000 = this.weatherBalls.stream();
         Objects.requireNonNull(ThunderBallProjectile.class);
         var10000 = var10000.filter(ThunderBallProjectile.class::isInstance);
         Objects.requireNonNull(ThunderBallProjectile.class);
         List<ThunderBallProjectile> thunderBallsList = (List)var10000.map(ThunderBallProjectile.class::cast).collect(Collectors.toList());
         var10000 = this.weatherBalls.stream();
         Objects.requireNonNull(CoolBallProjectile.class);
         var10000 = var10000.filter(CoolBallProjectile.class::isInstance);
         Objects.requireNonNull(CoolBallProjectile.class);
         List<CoolBallProjectile> coolBallsList = (List)var10000.map(CoolBallProjectile.class::cast).collect(Collectors.toList());
         int thunderBalls = thunderBallsList.size();
         int coolBalls = coolBallsList.size();
         ThunderstormTempo thunderstormTempo = (ThunderstormTempo)this.props.getPassiveAbility((AbilityCore)ThunderstormTempo.INSTANCE.get());
         boolean canUseThunderstorm = thunderstormTempo != null && thunderstormTempo.canUse(this.getOwner()).isSuccess() && thunderBalls >= 5;
         if (canUseThunderstorm) {
            this.superCharged = true;
            thunderstormTempo.useTempo(this.getOwner(), this);
            return;
         }

         ThunderboltTempo thunderboltTempo = (ThunderboltTempo)this.props.getPassiveAbility((AbilityCore)ThunderboltTempo.INSTANCE.get());
         boolean canUseThunderbolt = thunderboltTempo != null && thunderboltTempo.canUse(this.getOwner()).isSuccess() && thunderBalls > 0;
         if (canUseThunderbolt) {
            this.charged = true;
            thunderboltTempo.useTempo(this.getOwner(), this);
            return;
         }

         RainTempo rainTempo = (RainTempo)this.props.getEquippedOrPassiveAbility((AbilityCore)RainTempo.INSTANCE.get());
         boolean canUseRain = rainTempo != null && rainTempo.canUse(this.getOwner()).isSuccess() && coolBalls >= 3;
         if (canUseRain) {
            rainTempo.use(this.getOwner());
            return;
         }
      }

   }

   public boolean isCharged() {
      return this.charged;
   }

   public boolean isSuperCharged() {
      return this.superCharged;
   }

   public void addWeatherBall(WeatherBallProjectile ball) {
      this.weatherBalls.add(ball);
   }
}
