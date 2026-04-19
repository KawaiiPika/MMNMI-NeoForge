package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ChargedTempoAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public abstract class WeatherBallAbility extends Ability {
   private static final float COOLDOWN = 10.0F;
   private static final Predicate<IAbility> CHARGED_TEMPOS_CHECK = (abl) -> AbilityCategory.STYLE.isAbilityPartofCategory().test(abl) && abl instanceof ChargedTempoAbility;
   private final PoolComponent poolComponent;

   public WeatherBallAbility(AbilityCore<? extends WeatherBallAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.WEATHER_BALLS, new AbilityPool[0]);
      this.addComponents(new AbilityComponent[]{this.poolComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresClimaTact);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Vec3 lookVec = entity.m_20182_().m_82549_(entity.m_20154_());
      ItemStack stack = entity.m_21205_();
      if (stack.m_41619_()) {
         super.cooldownComponent.startCooldown(entity, 10.0F);
      } else {
         entity.m_21205_().m_41622_(1, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
         if (entity.m_6047_()) {
            this.checkForChargeTempo(entity, stack, lookVec);
         } else {
            WeatherBallProjectile proj = this.getWeatherBallEntity(entity, lookVec);
            entity.m_9236_().m_7967_(proj);
         }

         super.cooldownComponent.startCooldown(entity, 10.0F);
      }
   }

   private void checkForChargeTempo(LivingEntity entity, ItemStack stack, Vec3 lookVec) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         if (ArtOfWeatherHelper.chargeWeatherBall(stack, this.getKind())) {
            WyHelper.sendMessage(entity, Component.m_237113_(ArtOfWeatherHelper.getChargesString(stack)));
            WyHelper.spawnParticleEffect(this.getParticleEffect(), entity, lookVec.m_7096_(), entity.m_20188_() - (double)1.0F, lookVec.m_7094_());
            WeatherBallKind[] charge = ArtOfWeatherHelper.getCharges(stack);
            if (charge.length > 0) {
               Set<IAbility> availableTempos = props.getPassiveAbilities(CHARGED_TEMPOS_CHECK);
               if (availableTempos.size() <= 0) {
                  this.failedTempo(entity, lookVec, stack);
                  return;
               }

               boolean failedTempo = true;

               for(IAbility tempo : availableTempos) {
                  if (Arrays.equals(charge, ((ChargedTempoAbility)tempo).getTempoOrder()) && tempo.canUse(entity).isSuccess()) {
                     tempo.use(entity);
                     ArtOfWeatherHelper.emptyCharges(stack);
                     failedTempo = false;
                  }
               }

               if (failedTempo) {
                  this.failedTempo(entity, lookVec, stack);
               }
            }

         }
      }
   }

   private void failedTempo(LivingEntity entity, Vec3 lookVec, ItemStack stack) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FAILED_TEMPO.get(), entity, lookVec.m_7096_(), entity.m_20186_(), lookVec.m_7094_());
      ArtOfWeatherHelper.emptyCharges(stack);
   }

   public abstract WeatherBallKind getKind();

   public abstract ParticleEffect<?> getParticleEffect();

   public abstract WeatherBallProjectile getWeatherBallEntity(LivingEntity var1, Vec3 var2);
}
