package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetContinuityThresholdPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartContinuityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopContinuityPacket;

public class ContinuousComponent extends AbilityComponent<IAbility> {
   private boolean isContinuous;
   private float thresholdTime;
   private float continueTime;
   private boolean isInfinite;
   private final boolean isParallel;
   private final Predicate<ContinuousComponent> isParallelTest;
   private final PriorityEventPool<IStartContinuousEvent> startContinuousEvents;
   private final PriorityEventPool<IDuringContinuousEvent> tickContinuousEvents;
   private final PriorityEventPool<IEndContinuousEvent> stopContinuousEvents;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip() {
      return getTooltip(-1.0F);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float ticks) {
      return getTooltip(ticks, ticks);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float min, float max) {
      return (e, a) -> {
         float minVal;
         float maxVal;
         if (min == max && min == -1.0F) {
            minVal = Float.POSITIVE_INFINITY;
            maxVal = Float.POSITIVE_INFINITY;
         } else {
            minVal = (float)Math.round(min / 20.0F);
            maxVal = (float)Math.round(max / 20.0F);
         }

         AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_CONTINUE, minVal, maxVal)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS);
         return statBuilder.build().getStatDescription();
      };
   }

   public ContinuousComponent(IAbility ability) {
      this(ability, false);
   }

   public ContinuousComponent(IAbility ability, boolean isParallel) {
      super((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get(), ability);
      this.startContinuousEvents = new PriorityEventPool<IStartContinuousEvent>();
      this.tickContinuousEvents = new PriorityEventPool<IDuringContinuousEvent>();
      this.stopContinuousEvents = new PriorityEventPool<IEndContinuousEvent>();
      this.isParallel = isParallel;
      this.isParallelTest = (comp) -> true;
   }

   public ContinuousComponent(IAbility ability, Predicate<ContinuousComponent> isParallelTest) {
      super((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get(), ability);
      this.startContinuousEvents = new PriorityEventPool<IStartContinuousEvent>();
      this.tickContinuousEvents = new PriorityEventPool<IDuringContinuousEvent>();
      this.stopContinuousEvents = new PriorityEventPool<IEndContinuousEvent>();
      this.isParallel = true;
      this.isParallelTest = isParallelTest;
   }

   public void postInit(IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((component) -> component.addPreRenderEvent(20, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.isContinuous()) {
               component.setMaxValue(this.thresholdTime);
               component.setCurrentValue(this.thresholdTime - this.continueTime);
               if (ClientConfig.isDisplayInPercentage()) {
                  float percentage = (this.thresholdTime - this.continueTime) / this.thresholdTime * 100.0F;
                  percentage = Mth.m_14036_(percentage, 0.0F, 100.0F);
                  Object[] var10001 = new Object[]{percentage};
                  String percentageText = String.format("%.0f", var10001) + "%";
                  component.setDisplayText(percentageText);
               }

               component.setSlotColor(0.0F, 0.0F, 1.0F);
            }

         }));
   }

   protected void doTick(LivingEntity entity) {
      if (!this.getAbility().hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) || !((DisableComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).isDisabled()) {
         this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> component.setDisabled(this.isContinuous()));
         if (this.isContinuous()) {
            if (entity.m_21023_((MobEffect)ModEffects.IN_EVENT.get())) {
               this.stopContinuity(entity);
               return;
            }

            if (!entity.m_9236_().f_46443_) {
               ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)entity.m_9236_());
               Optional<ProtectedArea> area = worldData.getProtectedArea(entity.m_20183_().m_123341_(), entity.m_20183_().m_123342_(), entity.m_20183_().m_123343_());
               if (area.isPresent() && !((ProtectedArea)area.get()).canUseAbility(this.getAbility().getCore())) {
                  this.stopContinuity(entity);
                  return;
               }
            }

            if (!this.isInfinite && this.continueTime >= this.thresholdTime) {
               this.stopContinuity(entity);
               return;
            }

            AttributeInstance inst = entity.m_21051_((Attribute)ModAttributes.TIME_PROGRESSION.get());
            double timeProgression = (double)1.0F;
            if (inst != null) {
               timeProgression = inst.m_22135_();
            }

            this.continueTime = (float)((double)this.continueTime + (double)this.getTpsFactor() * timeProgression);
            int loops = Math.max(1, (int)this.getTpsFactor());

            for(int i = 0; i < loops; ++i) {
               this.tickContinuousEvents.dispatch((event) -> event.duringContinuous(entity, this.getAbility()));
            }
         }

      }
   }

   public <T extends LivingEntity, A extends IAbility> ContinuousComponent addStartEvent(IStartContinuousEvent event) {
      this.startContinuousEvents.addEvent(event);
      return this;
   }

   public <T extends LivingEntity> ContinuousComponent addStartEvent(int priority, IStartContinuousEvent event) {
      this.startContinuousEvents.addEvent(priority, event);
      return this;
   }

   public <T extends LivingEntity> ContinuousComponent addTickEvent(IDuringContinuousEvent event) {
      this.tickContinuousEvents.addEvent(event);
      return this;
   }

   public <T extends LivingEntity> ContinuousComponent addTickEvent(int priority, IDuringContinuousEvent event) {
      this.tickContinuousEvents.addEvent(priority, event);
      return this;
   }

   public <T extends LivingEntity> ContinuousComponent addEndEvent(IEndContinuousEvent event) {
      this.stopContinuousEvents.addEvent(event);
      return this;
   }

   public <T extends LivingEntity> ContinuousComponent addEndEvent(int priority, IEndContinuousEvent event) {
      this.stopContinuousEvents.addEvent(priority, event);
      return this;
   }

   public void triggerContinuity(LivingEntity entity) {
      this.triggerContinuity(entity, -1.0F);
   }

   public void triggerContinuity(LivingEntity entity, float threshold) {
      if (this.isContinuous()) {
         this.stopContinuity(entity);
      } else {
         this.startContinuity(entity, threshold);
      }

   }

   public void startContinuity(LivingEntity entity) {
      this.startContinuity(entity, -1.0F);
   }

   public void startContinuity(LivingEntity entity, float threshold) {
      this.ensureIsRegistered();
      if (!this.isContinuous()) {
         Optional<CooldownComponent> cooldownComponent = this.getAbility().<CooldownComponent>getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
         if (!cooldownComponent.isPresent() || !((CooldownComponent)cooldownComponent.get()).isOnCooldown()) {
            Optional<DisableComponent> disableComponent = this.getAbility().<DisableComponent>getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get());
            if (!disableComponent.isPresent() || !((DisableComponent)disableComponent.get()).isDisabled()) {
               Optional<PoolComponent> poolComponent = this.getAbility().<PoolComponent>getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get());
               if (!poolComponent.isPresent() || !((PoolComponent)poolComponent.get()).isPoolInUse()) {
                  if (threshold < 0.0F) {
                     this.isInfinite = true;
                  } else {
                     this.isInfinite = false;
                  }

                  this.thresholdTime = threshold;
                  this.continueTime = 0.0F;
                  this.isContinuous = true;
                  if (!entity.m_9236_().f_46443_) {
                     poolComponent.ifPresent((c) -> c.startPoolInUse(entity));
                  }

                  this.startContinuousEvents.dispatch((event) -> event.startContinuous(entity, this.getAbility()));
                  if (!entity.m_9236_().f_46443_) {
                     ModNetwork.sendToAllTrackingAndSelf(new SStartContinuityPacket(entity, this.getAbility(), threshold), entity);
                  }

               }
            }
         }
      }
   }

   public void stopContinuity(LivingEntity entity) {
      if (this.isContinuous) {
         if (!entity.m_9236_().f_46443_) {
            this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).ifPresent((c) -> c.stopPoolInUse(entity));
         }

         this.stopContinuousEvents.dispatch((event) -> event.endContinuous(entity, this.getAbility()));
         this.isContinuous = false;
         this.continueTime = 0.0F;
         this.thresholdTime = 0.0F;
         this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((c) -> c.resetDecoration());
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SStopContinuityPacket(entity, this.getAbility()), entity);
         }

      }
   }

   public boolean isContinuous() {
      return this.isContinuous;
   }

   public float getThresholdTime() {
      return this.thresholdTime;
   }

   public void setThresholdTime(LivingEntity entity, float threshold) {
      if (threshold < 0.0F) {
         this.isInfinite = true;
      } else {
         this.isInfinite = false;
      }

      this.thresholdTime = threshold;
      this.continueTime = 0.0F;
      if (!entity.m_9236_().f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(new SSetContinuityThresholdPacket(entity, this.getAbility(), this.thresholdTime), entity);
      }

   }

   public float getContinueTime() {
      return this.continueTime;
   }

   public void increaseContinuityTime(float time) {
      this.continueTime += time;
      this.continueTime = Mth.m_14036_(this.continueTime, 0.0F, this.thresholdTime);
   }

   public void decreaseContinuityTime(float time) {
      this.continueTime -= time;
      this.continueTime = Mth.m_14036_(this.continueTime, 0.0F, this.thresholdTime);
   }

   public boolean isInfinite() {
      return this.isInfinite;
   }

   public boolean isParallel() {
      return this.isParallel && this.isParallelTest.test(this);
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128350_("continueTime", this.getContinueTime());
      nbt.m_128350_("thresholdTime", this.getThresholdTime());
      nbt.m_128379_("isInfinite", this.isInfinite());
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.continueTime = nbt.m_128457_("continueTime");
      this.thresholdTime = nbt.m_128457_("thresholdTime");
      this.isInfinite = nbt.m_128471_("isInfinite");
      if (this.continueTime > 0.0F) {
         this.isContinuous = true;
      }

   }

   @FunctionalInterface
   public interface IDuringContinuousEvent {
      void duringContinuous(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IEndContinuousEvent {
      void endContinuous(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IStartContinuousEvent {
      void startContinuous(LivingEntity var1, IAbility var2);
   }
}
