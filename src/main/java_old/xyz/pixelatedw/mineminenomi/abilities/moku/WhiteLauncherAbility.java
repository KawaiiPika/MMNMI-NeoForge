package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WhiteLauncherAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final float RANGE = 1.6F;
   private static final float DAMAGE = 15.0F;
   public static final int ON_HOLD = 15;
   public static final RegistryObject<AbilityCore<WhiteLauncherAbility>> INSTANCE = ModRegistry.registerAbility("white_launcher", "White Launcher", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into smoke and launches them forward", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, WhiteLauncherAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(15.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(15.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DashComponent dashComponent = new DashComponent(this);
   private final Interval particleInterval = new Interval(2);

   public WhiteLauncherAbility(AbilityCore<WhiteLauncherAbility> core) {
      super(core);
      super.addComponents(this.continuousComponent, this.hitTrackerComponent, this.rangeComponent, this.dealDamageComponent, this.dashComponent);
      super.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      super.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.particleInterval.restartIntervalToZero();
      this.hitTrackerComponent.clearHits();
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
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.WHITE_LAUNCHER.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
            }

            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.6F)) {
               if (this.hitTrackerComponent.canHit(target) && entity.m_142582_(target)) {
                  this.dealDamageComponent.hurtTarget(entity, target, 15.0F);
               }
            }

         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
