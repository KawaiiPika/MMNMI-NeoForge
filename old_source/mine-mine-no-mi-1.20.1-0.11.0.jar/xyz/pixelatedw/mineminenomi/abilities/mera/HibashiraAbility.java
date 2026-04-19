package xyz.pixelatedw.mineminenomi.abilities.mera;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.mera.HibashiraParticleEffect;

public class HibashiraAbility extends Ability {
   private static final int ON_HOLD = 100;
   private static final int MIN_COOLDOWN = 200;
   private static final int MAX_COOLDOWN = 250;
   private static final double PILLAR_SIZE = (double)3.5F;
   private static final float DAMAGE = 5.0F;
   public static final RegistryObject<AbilityCore<HibashiraAbility>> INSTANCE = ModRegistry.registerAbility("hibashira", "Hibashira", (id, name) -> {
      Component[] DESCRIPTION = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a fire pillar extending both upwards and downwards, burning every enemy within it", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HibashiraAbility::new)).addDescriptionLine(DESCRIPTION).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 250.0F), ContinuousComponent.getTooltip(100.0F), DealDamageComponent.getTooltip(5.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final Interval particleInterval = new Interval(2);
   private final Interval clearHitsInterval = new Interval(20);

   public HibashiraAbility(AbilityCore<HibashiraAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTrackerComponent, this.dealDamageComponent});
      this.addCanUseCheck(MeraHelper::canUseMeraAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 100.0F);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.particleInterval.restartIntervalToZero();
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (super.canUse(entity).isFail()) {
         this.continuousComponent.stopContinuity(entity);
      }

      if (this.particleInterval.canTick()) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HIBASHIRA.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), HibashiraParticleEffect.NO_DETAILS);
      }

      List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(entity.m_20182_(), entity.m_9236_(), (double)3.5F, (double)10.0F, (double)3.5F, ModEntityPredicates.getEnemyFactions(entity));
      DamageSource source = this.dealDamageComponent.getDamageSource(entity);
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      sourceHandler.setUnavoidable();

      for(LivingEntity target : targets) {
         if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 5.0F, source)) {
            target.m_20254_(4);
         }
      }

      if (this.clearHitsInterval.canTick()) {
         this.hitTrackerComponent.clearHits();
      }

      AbilityHelper.slowEntityFall(entity);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      float cooldown = 200.0F + this.continuousComponent.getContinueTime() / 2.0F;
      super.cooldownComponent.startCooldown(entity, cooldown);
   }
}
