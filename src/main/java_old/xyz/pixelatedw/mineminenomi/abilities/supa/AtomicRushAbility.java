package xyz.pixelatedw.mineminenomi.abilities.supa;

import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AtomicRushAbility extends Ability {
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/atomic_spurt.png");
   private static final float COOLDOWN = 400.0F;
   public static final RegistryObject<AbilityCore<AtomicRushAbility>> INSTANCE = ModRegistry.registerAbility("atomic_rush", "Atomic Rush", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AtomicRushAbility::new)).setIcon(ICON).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F)).build("mineminenomi"));
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private Interval newPosInterval = new Interval(20);
   private Interval dashWait = new Interval(5);
   private int damageTimer = 0;
   private LivingEntity target;

   public AtomicRushAbility(AbilityCore<AtomicRushAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTrackerComponent, this.animationComponent, this.dealDamageComponent, this.rangeComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.target = null;
      this.hitTrackerComponent.clearHits();
      this.continuousComponent.triggerContinuity(entity, WyHelper.secondsToTicks(10.0F));
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.dashWait.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         if (this.target != null && this.dashWait.canTick()) {
            this.hitTrackerComponent.clearHits();
            Vec3 dir = entity.m_20182_().m_82546_(this.target.m_20182_()).m_82541_().m_82542_((double)7.0F, (double)0.0F, (double)7.0F);
            AbilityHelper.setDeltaMovement(entity, -dir.f_82479_, 0.15, -dir.f_82481_);
            this.target = null;
            this.damageTimer = 5;
         }

         if (this.damageTimer > 0) {
            --this.damageTimer;

            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.6F)) {
               if (this.hitTrackerComponent.canHit(target)) {
                  this.dealDamageComponent.hurtTarget(entity, target, 25.0F);
               }
            }
         }

         if (this.newPosInterval.canTick()) {
            List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 40.0F);
            if (targets.size() <= 0) {
               this.continuousComponent.stopContinuity(entity);
            } else {
               LivingEntity target = (LivingEntity)targets.stream().sorted((e1, e2) -> (int)(e1.m_20238_(entity.m_20182_()) - e2.m_20238_(entity.m_20182_()))).findFirst().orElse((Object)null);
               if (target != null) {
                  MobEffectInstance vanishEffect = new MobEffectInstance((MobEffect)ModEffects.VANISH.get(), 5, 0);
                  entity.m_7292_(vanishEffect);
                  WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 100, vanishEffect);
                  BlockPos pos = WyHelper.findValidGroundLocation((Entity)entity, (BlockPos)target.m_20183_(), 5, 5);
                  entity.m_20324_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
                  this.target = target;
                  entity.m_7618_(Anchor.EYES, target.m_20182_().m_82520_((double)0.0F, (double)target.m_20192_(), (double)0.0F));
               }
            }
         }
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 400.0F);
   }

   public void setDashWaitTime(int time) {
      this.dashWait = new Interval(time);
   }
}
