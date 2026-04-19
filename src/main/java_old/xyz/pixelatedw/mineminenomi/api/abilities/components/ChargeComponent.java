package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.UUID;
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
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopChargingPacket;

public class ChargeComponent extends AbilityComponent<IAbility> {
   private static final UUID MAX_CHARGE_BONUS_MANAGER_UUID = UUID.fromString("75b893d9-9d02-457e-9c35-02e468586fcc");
   private boolean isCharging;
   private float maxChargeTime;
   private float chargeTime;
   private boolean isCancelable;
   private Predicate<ChargeComponent> isCancelableTest;
   private final PriorityEventPool<IStartChargingEvent> startChargeEvents;
   private final PriorityEventPool<IDuringChargingEvent> tickChargeEvents;
   private final PriorityEventPool<IEndChargingEvent> stopChargeEvents;
   private final BonusManager maxChargeBonusManager;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float ticks) {
      return getTooltip(ticks, ticks);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float min, float max) {
      return (e, a) -> {
         AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_CHARGE, Math.round(min / 20.0F), Math.round(max / 20.0F))).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS);
         return statBuilder.build().getStatDescription();
      };
   }

   public ChargeComponent(IAbility ability) {
      this(ability, false);
      this.addBonusManager(this.maxChargeBonusManager);
   }

   public ChargeComponent(IAbility ability, boolean isCancelable) {
      super((AbilityComponentKey)ModAbilityComponents.CHARGE.get(), ability);
      this.startChargeEvents = new PriorityEventPool<IStartChargingEvent>();
      this.tickChargeEvents = new PriorityEventPool<IDuringChargingEvent>();
      this.stopChargeEvents = new PriorityEventPool<IEndChargingEvent>();
      this.maxChargeBonusManager = new BonusManager(MAX_CHARGE_BONUS_MANAGER_UUID);
      this.isCancelable = isCancelable;
      this.isCancelableTest = (comp) -> true;
   }

   public ChargeComponent(IAbility ability, Predicate<ChargeComponent> isCancelableTest) {
      super((AbilityComponentKey)ModAbilityComponents.CHARGE.get(), ability);
      this.startChargeEvents = new PriorityEventPool<IStartChargingEvent>();
      this.tickChargeEvents = new PriorityEventPool<IDuringChargingEvent>();
      this.stopChargeEvents = new PriorityEventPool<IEndChargingEvent>();
      this.maxChargeBonusManager = new BonusManager(MAX_CHARGE_BONUS_MANAGER_UUID);
      this.isCancelable = true;
      this.isCancelableTest = isCancelableTest;
   }

   public void postInit(IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((component) -> component.addPreRenderEvent(19, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.isCharging()) {
               component.setMaxValue(this.maxChargeTime);
               component.setCurrentValue(this.chargeTime);
               if (ClientConfig.isDisplayInPercentage()) {
                  float percentage = this.chargeTime / this.maxChargeTime * 100.0F;
                  percentage = Mth.m_14036_(percentage, 0.0F, 100.0F);
                  Object[] var10001 = new Object[]{percentage};
                  String percentageText = String.format("%.0f", var10001) + "%";
                  component.setDisplayText(percentageText);
               }

               component.setSlotColor(1.0F, 1.0F, 0.0F);
            }

         }));
   }

   protected void doTick(LivingEntity entity) {
      if (!this.getAbility().hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) || !((DisableComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).isDisabled()) {
         this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> component.setDisabled(this.isCharging()));
         if (this.isCharging()) {
            if (entity.m_21023_((MobEffect)ModEffects.IN_EVENT.get())) {
               this.forceStopCharging(entity);
               return;
            }

            if (entity.m_9236_() != null && !entity.m_9236_().f_46443_) {
               ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)entity.m_9236_());
               Optional<ProtectedArea> area = worldData.getProtectedArea(entity.m_20183_().m_123341_(), entity.m_20183_().m_123342_(), entity.m_20183_().m_123343_());
               if (area.isPresent() && !((ProtectedArea)area.get()).canUseAbility(this.getAbility().getCore())) {
                  this.forceStopCharging(entity);
                  return;
               }

               if (this.chargeTime >= this.maxChargeTime) {
                  this.stopCharging(entity);
                  return;
               }
            }

            AttributeInstance inst = entity.m_21051_((Attribute)ModAttributes.TIME_PROGRESSION.get());
            double timeProgression = (double)1.0F;
            if (inst != null) {
               timeProgression = inst.m_22135_();
            }

            this.chargeTime = (float)((double)this.chargeTime + (double)this.getTpsFactor() * timeProgression);
            int loops = Math.max(1, (int)this.getTpsFactor());

            for(int i = 0; i < loops; ++i) {
               this.tickChargeEvents.dispatch((event) -> event.duringCharging(entity, this.getAbility()));
            }
         }

      }
   }

   public ChargeComponent addStartEvent(IStartChargingEvent event) {
      this.startChargeEvents.addEvent(event);
      return this;
   }

   public ChargeComponent addStartEvent(int priority, IStartChargingEvent event) {
      this.startChargeEvents.addEvent(priority, event);
      return this;
   }

   public ChargeComponent addTickEvent(IDuringChargingEvent event) {
      this.tickChargeEvents.addEvent(event);
      return this;
   }

   public ChargeComponent addTickEvent(int priority, IDuringChargingEvent event) {
      this.tickChargeEvents.addEvent(priority, event);
      return this;
   }

   public ChargeComponent addEndEvent(IEndChargingEvent event) {
      this.stopChargeEvents.addEvent(event);
      return this;
   }

   public ChargeComponent addEndEvent(int priority, IEndChargingEvent event) {
      this.stopChargeEvents.addEvent(priority, event);
      return this;
   }

   public void startCharging(LivingEntity user, float maxChargeTime) {
      this.ensureIsRegistered();
      if (this.isCharging()) {
         if (this.isCancelable() && this.isCancelableTest.test(this)) {
            this.stopCharging(user);
         }

      } else {
         Optional<CooldownComponent> cooldownComponent = this.getAbility().<CooldownComponent>getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
         if (!cooldownComponent.isPresent() || !((CooldownComponent)cooldownComponent.get()).isOnCooldown()) {
            Optional<DisableComponent> disableComponent = this.getAbility().<DisableComponent>getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get());
            if (!disableComponent.isPresent() || !((DisableComponent)disableComponent.get()).isDisabled()) {
               Optional<PoolComponent> poolComponent = this.getAbility().<PoolComponent>getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get());
               if (!poolComponent.isPresent() || !((PoolComponent)poolComponent.get()).isPoolInUse()) {
                  float newMaxChargeTime = Math.max(1.0F, maxChargeTime);
                  newMaxChargeTime = this.maxChargeBonusManager.applyBonus(newMaxChargeTime);
                  this.maxChargeTime = newMaxChargeTime;
                  this.chargeTime = 0.0F;
                  this.isCharging = true;
                  if (user.m_9236_() != null && !user.m_9236_().f_46443_) {
                     poolComponent.ifPresent((c) -> c.startPoolInUse(user));
                  }

                  this.startChargeEvents.dispatch((event) -> event.startCharging(user, this.getAbility()));
                  if (user.m_9236_() != null && !user.m_9236_().f_46443_) {
                     ModNetwork.sendToAllTrackingAndSelf(new SStartChargingPacket(user, this.getAbility(), maxChargeTime), user);
                  }

               }
            }
         }
      }
   }

   public void stopCharging(LivingEntity entity) {
      this.stopChargeEvents.dispatch((event) -> event.endCharging(entity, this.getAbility()));
      this.forceStopCharging(entity);
   }

   public void forceStopCharging(LivingEntity entity) {
      this.isCharging = false;
      this.chargeTime = 0.0F;
      this.maxChargeTime = 0.0F;
      this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((c) -> c.resetDecoration());
      if (entity.m_9236_() != null && !entity.m_9236_().f_46443_) {
         this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).ifPresent((c) -> c.stopPoolInUse(entity));
         ModNetwork.sendToAllTrackingAndSelf(new SStopChargingPacket(entity, this.getAbility()), entity);
      }

   }

   public boolean isCharging() {
      return this.isCharging;
   }

   public float getMaxChargeTime() {
      return this.maxChargeTime;
   }

   public float getChargeTime() {
      return this.chargeTime;
   }

   public float getChargePercentage() {
      float per = this.chargeTime / this.maxChargeTime;
      return Float.isNaN(per) ? 0.0F : per;
   }

   public boolean isCancelable() {
      return this.isCancelable;
   }

   public BonusManager getMaxChargeBonusManager() {
      return this.maxChargeBonusManager;
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128350_("chargeTime", this.chargeTime);
      nbt.m_128350_("maxChargeTime", this.maxChargeTime);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.chargeTime = nbt.m_128457_("chargeTime");
      this.maxChargeTime = nbt.m_128457_("maxChargeTime");
      if (this.chargeTime > 0.0F) {
         this.isCharging = true;
      }

   }

   @FunctionalInterface
   public interface IDuringChargingEvent {
      void duringCharging(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IEndChargingEvent {
      void endCharging(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IStartChargingEvent {
      void startCharging(LivingEntity var1, IAbility var2);
   }
}
