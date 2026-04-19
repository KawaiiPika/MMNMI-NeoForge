package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.util.FloatRange;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartCooldownPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopCooldownPacket;

public class CooldownComponent extends AbilityComponent<IAbility> {
   private static final UUID COOLDOWN_BONUS_MANAGER_UUID = UUID.fromString("99be6fa9-bcc6-4c9c-be00-ee024543015d");
   private boolean isOnCooldown;
   private float minCooldown;
   private float maxCooldown;
   private float startCooldown;
   private float cooldown;
   private boolean playReadyAnim = false;
   private float cooldownReadyAnim = 10.0F;
   private final PriorityEventPool<IStartCooldownEvent> startCooldownEvents = new PriorityEventPool<IStartCooldownEvent>();
   private final PriorityEventPool<IDuringCooldownEvent> tickCooldownEvents = new PriorityEventPool<IDuringCooldownEvent>();
   private final PriorityEventPool<IEndCooldownEvent> stopCooldownEvents = new PriorityEventPool<IEndCooldownEvent>();
   private final BonusManager bonusManager;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float ticks) {
      return getTooltip(ticks, ticks);
   }

   public static AbilityDescriptionLine.IDescriptionLine getTooltip(float min, float max) {
      return (entity, ability) -> {
         float minSeconds = (float)Math.round(min / 20.0F);
         float maxSeconds = (float)Math.round(max / 20.0F);
         AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_COOLDOWN, minSeconds, maxSeconds)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS);
         ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> {
            float minBonus = (float)Math.round((comp.getBonusManager().applyBonus(min) - min) / 20.0F);
            float maxBonus = (float)Math.round((comp.getBonusManager().applyBonus(max) - max) / 20.0F);
            AbilityStat.AbilityStatType bonusType = AbilityStat.AbilityStatType.from(minBonus + maxBonus, true);
            statBuilder.withBonus(minBonus, maxBonus, bonusType);
         });
         return statBuilder.buildComponent();
      };
   }

   public CooldownComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get(), ability);
      this.bonusManager = new BonusManager(COOLDOWN_BONUS_MANAGER_UUID);
      this.addBonusManager(this.bonusManager);
   }

   public void postInit(IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((component) -> {
         component.addPreRenderEvent(10, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.isOnCooldown()) {
               component.setIconColor(0.6F, 0.6F, 0.6F);
               component.setMaxValue(this.startCooldown);
               component.setCurrentValue(this.cooldown);
               if (ClientConfig.isDisplayInPercentage()) {
                  float percentage = this.cooldown / this.startCooldown * 100.0F;
                  percentage = Mth.m_14036_(percentage, 0.0F, 100.0F);
                  Object[] var10001 = new Object[]{percentage};
                  String percentageText = String.format("%.0f", var10001) + "%";
                  component.setDisplayText(percentageText);
               }

               component.setSlotColor(1.0F, 0.0F, 0.0F);
            }

         });
         component.addPostRenderEvent(10, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.getCooldown() == 1.0F && !this.playReadyAnim) {
               this.playReadyAnim = true;
               this.cooldownReadyAnim = 30.0F;
            } else if (this.playReadyAnim && this.cooldownReadyAnim > 0.0F) {
               this.cooldownReadyAnim = (float)((double)this.cooldownReadyAnim - (double)1.75F * (double)minecraft.m_91297_());
               if (this.cooldownReadyAnim <= 0.0F) {
                  this.playReadyAnim = false;
               } else if (this.cooldownReadyAnim > 0.0F) {
                  float anim = this.cooldownReadyAnim * 0.03F;
                  anim = Math.max(0.0F, anim);
                  float scale = 1.8F * EasingFunctionHelper.easeOutSine(anim);
                  float alpha = 0.6F * EasingFunctionHelper.easeOutSine(anim);
                  scale = Mth.m_14036_(scale, 1.0F, scale);
                  stack.m_85836_();
                  stack.m_252880_(x, y, 1.0F);
                  stack.m_252880_(12.0F, 12.0F, 0.0F);
                  stack.m_85841_(scale, scale, 1.0F);
                  stack.m_252880_(-12.0F, -12.0F, 0.0F);
                  RenderSystem.enableBlend();
                  rect.setSize(23.0F, 23.0F);
                  rect.setColor(0.0F, 1.0F, 0.5F, alpha);
                  rect.draw(stack, 0.0F, 0.0F);
                  RenderSystem.disableBlend();
                  stack.m_85849_();
               }
            }

         });
      });
   }

   protected void doTick(LivingEntity entity) {
      if (!this.getAbility().hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) || !((DisableComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).isDisabled()) {
         if (entity instanceof Player && PatreonHandler.isDevBuild() && FGCommand.NO_COOLDOWN) {
            this.stopCooldown(entity);
         } else {
            this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> component.setDisabled(this.isOnCooldown()));
            if (this.isOnCooldown()) {
               if (this.cooldown <= 0.0F) {
                  this.stopCooldown(entity);
                  return;
               }

               AttributeInstance inst = entity.m_21051_((Attribute)ModAttributes.TIME_PROGRESSION.get());
               double timeProgression = (double)1.0F;
               if (inst != null) {
                  timeProgression = inst.m_22135_();
               }

               this.cooldown = (float)((double)this.cooldown - (double)this.getTpsFactor() * timeProgression);
               int loops = Math.max(1, (int)this.getTpsFactor());

               for(int i = 0; i < loops; ++i) {
                  this.tickCooldownEvents.dispatch((event) -> event.duringCooldown(entity, this.getAbility()));
               }
            }

         }
      }
   }

   public CooldownComponent addStartEvent(IStartCooldownEvent event) {
      this.startCooldownEvents.addEvent(event);
      return this;
   }

   public CooldownComponent addStartEvent(int priority, IStartCooldownEvent event) {
      this.startCooldownEvents.addEvent(priority, event);
      return this;
   }

   public CooldownComponent addTickEvent(IDuringCooldownEvent event) {
      this.tickCooldownEvents.addEvent(event);
      return this;
   }

   public CooldownComponent addTickEvent(int priority, IDuringCooldownEvent event) {
      this.tickCooldownEvents.addEvent(priority, event);
      return this;
   }

   public CooldownComponent addEndEvent(IEndCooldownEvent event) {
      this.stopCooldownEvents.addEvent(event);
      return this;
   }

   public CooldownComponent addEndEvent(int priority, IEndCooldownEvent event) {
      this.stopCooldownEvents.addEvent(priority, event);
      return this;
   }

   public void startCooldown(LivingEntity entity, float cooldown) {
      this.ensureIsRegistered();
      if (!this.isOnCooldown()) {
         Optional<PoolComponent> poolComponent = this.getAbility().<PoolComponent>getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get());
         float newCooldown = Math.max(0.0F, cooldown);
         newCooldown = this.bonusManager.applyBonus(newCooldown);
         this.startCooldown = newCooldown;
         this.cooldown = newCooldown;
         this.isOnCooldown = true;
         if (!entity.m_9236_().f_46443_) {
            poolComponent.ifPresent((c) -> {
               boolean ignoresCooldown = c.getPools().stream().anyMatch((pool) -> (Boolean)pool.getFlagValue("ignoreCooldown", () -> false));
               if (!ignoresCooldown) {
                  c.startPoolInUse(entity);
               }

            });
         }

         this.startCooldownEvents.dispatch((event) -> event.startCooldown(entity, this.getAbility()));
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SStartCooldownPacket(entity, this.getAbility(), cooldown), entity);
         }

      }
   }

   public void stopCooldown(LivingEntity entity) {
      if (this.isOnCooldown()) {
         this.stopCooldownEvents.dispatch((event) -> event.endCooldown(entity, this.getAbility()));
         this.isOnCooldown = false;
         this.startCooldown = 0.0F;
         this.cooldown = 0.0F;
         this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent(SlotDecorationComponent::resetDecoration);
         if (!entity.m_9236_().f_46443_) {
            this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.POOL.get()).ifPresent((c) -> c.stopPoolInUse(entity));
         }

         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SStopCooldownPacket(entity, this.getAbility()), entity);
         }

      }
   }

   public boolean isOnCooldown() {
      return this.isOnCooldown;
   }

   public float getStartCooldown() {
      return this.startCooldown;
   }

   public float getCooldown() {
      return this.cooldown;
   }

   public float getMinCooldown() {
      return this.minCooldown;
   }

   public float getMaxCooldown() {
      return this.maxCooldown;
   }

   public BonusManager getBonusManager() {
      return this.bonusManager;
   }

   public Component getCooldownTooltip(FloatRange range) {
      float min = range.getMin();
      float max = range.getMax();
      float minSeconds = (float)Math.round(min / 20.0F);
      float maxSeconds = (float)Math.round(max / 20.0F);
      float minBonus = (float)Math.round((this.getBonusManager().applyBonus(min) - min) / 20.0F);
      float maxBonus = (float)Math.round((this.getBonusManager().applyBonus(max) - max) / 20.0F);
      AbilityStat.AbilityStatType bonusType = AbilityStat.AbilityStatType.from(minBonus + maxBonus, true);
      AbilityStat.Builder builder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_COOLDOWN, minSeconds, maxSeconds)).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS).withBonus(minBonus, maxBonus, bonusType);
      return builder.buildComponent();
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128350_("cooldown", this.cooldown);
      nbt.m_128350_("startCooldown", this.startCooldown);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.cooldown = nbt.m_128457_("cooldown");
      this.startCooldown = nbt.m_128457_("cooldown");
      if (this.cooldown > 0.0F) {
         this.isOnCooldown = true;
      }

   }

   @FunctionalInterface
   public interface IDuringCooldownEvent {
      void duringCooldown(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IEndCooldownEvent {
      void endCooldown(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IStartCooldownEvent {
      void startCooldown(LivingEntity var1, IAbility var2);
   }
}
