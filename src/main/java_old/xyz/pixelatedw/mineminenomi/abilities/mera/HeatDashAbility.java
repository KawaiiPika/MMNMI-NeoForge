package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HeatDashAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int ON_HOLD = 15;
   private static final float RANGE = 1.4F;
   public static final RegistryObject<AbilityCore<HeatDashAbility>> INSTANCE = ModRegistry.registerAbility("heat_dash", "Heat Dash", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into fire and launches them forward, setting on fire all enemies around the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HeatDashAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(15.0F), RangeComponent.getTooltip(1.4F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DashComponent dashComponent = new DashComponent(this);
   private final Interval particleInterval = new Interval(2);

   public HeatDashAbility(AbilityCore<HeatDashAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTrackerComponent, this.rangeComponent, this.dashComponent});
      this.addCanUseCheck(MeraHelper::canUseMeraAbilities);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.particleInterval.restartIntervalToZero();
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.MERA_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.5F + this.random.nextFloat() / 3.0F);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.continuousComponent.getContinueTime() > 15.0F) {
            if (entity.m_20096_()) {
               this.continuousComponent.stopContinuity(entity);
            }
         } else {
            if (super.canUse(entity).isFail()) {
               this.continuousComponent.stopContinuity(entity);
            }

            this.dashComponent.dashXYZ(entity, 3.0F);
            if (this.particleInterval.canTick()) {
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HEAT_DASH.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
            }

            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.4F)) {
               if (this.hitTrackerComponent.canHit(target) && entity.m_142582_(target)) {
                  target.m_20254_(2);
               }
            }

         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
