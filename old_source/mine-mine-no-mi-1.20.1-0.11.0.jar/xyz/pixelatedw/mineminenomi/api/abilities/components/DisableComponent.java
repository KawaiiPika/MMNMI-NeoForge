package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartDisablePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopDisablePacket;

public class DisableComponent extends AbilityComponent<IAbility> {
   private boolean isDisabled;
   private float startDisableTime;
   private float disableTime;
   private boolean isInfinite;
   private final PriorityEventPool<IStartDisableEvent> startDisableEvents = new PriorityEventPool<IStartDisableEvent>();
   private final PriorityEventPool<IEndDisableEvent> stopDisableEvents = new PriorityEventPool<IEndDisableEvent>();

   public DisableComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.DISABLE.get(), ability);
      this.setTickRate(1);
   }

   public void postInit(IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((component) -> {
         component.addPreRenderEvent(0, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.isDisabled()) {
               component.setMaxValue(this.startDisableTime);
               component.setCurrentValue(this.disableTime);
               if (ClientConfig.isDisplayInPercentage()) {
                  float percentage = this.disableTime / this.startDisableTime * 100.0F;
                  percentage = Mth.m_14036_(percentage, 0.0F, 100.0F);
                  Object[] var10001 = new Object[]{percentage};
                  String percentageText = String.format("%.0f", var10001) + "%";
                  component.setDisplayText(percentageText);
               }

               component.setSlotColor(0.0F, 0.0F, 0.0F);
               component.setIconColor(0.4F, 0.4F, 0.4F);
            }

         });
         component.addPostRenderEvent(0, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.isDisabled()) {
               RendererHelper.drawIcon(ModResources.DISABLED_ABILITY, stack, x + 4.0F, y + 4.0F, 0.0F, 16.0F, 16.0F);
               RenderSystem.setShaderTexture(0, ModResources.WIDGETS);
            }

         });
      });
   }

   public DisableComponent addStartEvent(IStartDisableEvent event) {
      this.startDisableEvents.addEvent(event);
      return this;
   }

   public DisableComponent addStartEvent(int priority, IStartDisableEvent event) {
      this.startDisableEvents.addEvent(priority, event);
      return this;
   }

   public DisableComponent addEndEvent(IEndDisableEvent event) {
      this.stopDisableEvents.addEvent(event);
      return this;
   }

   public DisableComponent addEndEvent(int priority, IEndDisableEvent event) {
      this.stopDisableEvents.addEvent(priority, event);
      return this;
   }

   protected void doTick(LivingEntity entity) {
      this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get()).ifPresent((component) -> component.setDisabled(this.isDisabled()));
      if (this.isDisabled()) {
         if (this.disableTime > 0.0F) {
            AttributeInstance inst = entity.m_21051_((Attribute)ModAttributes.TIME_PROGRESSION.get());
            double timeProgression = (double)1.0F;
            if (inst != null) {
               timeProgression = inst.m_22135_();
            }

            this.disableTime = (float)((double)this.disableTime - (double)1.0F * timeProgression);
         } else if (this.disableTime <= 0.0F && !this.isInfinite) {
            this.stopDisable(entity);
            return;
         }
      }

   }

   public boolean isDisabled() {
      return this.isDisabled;
   }

   public float getStartDisableTime() {
      return this.startDisableTime;
   }

   public float getDisableTime() {
      return this.disableTime;
   }

   public boolean isInfinite() {
      return this.isInfinite;
   }

   public void startDisable(LivingEntity entity, float time) {
      this.ensureIsRegistered();
      if (!this.isDisabled()) {
         if (time < 0.0F) {
            this.isInfinite = true;
         } else {
            this.isInfinite = false;
         }

         this.startDisableTime = time;
         this.disableTime = time;
         this.isDisabled = true;
         this.startDisableEvents.dispatch((event) -> event.startDisable(entity, this.getAbility()));
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SStartDisablePacket(entity, this.getAbility(), time), entity);
         }

      }
   }

   public void stopDisable(LivingEntity entity) {
      if (this.isDisabled()) {
         this.stopDisableEvents.dispatch((event) -> event.endDisable(entity, this.getAbility()));
         this.disableTime = 0.0F;
         this.isDisabled = false;
         this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((c) -> c.resetDecoration());
         if (!entity.m_9236_().f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(new SStopDisablePacket(entity, this.getAbility()), entity);
         }

      }
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128350_("disableTime", this.disableTime);
      nbt.m_128350_("startDisableTime", this.startDisableTime);
      nbt.m_128379_("isInfinite", this.isInfinite);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.disableTime = nbt.m_128457_("disableTime");
      this.startDisableTime = nbt.m_128457_("startDisableTime");
      this.isInfinite = nbt.m_128471_("isInfinite");
      if (this.disableTime > 0.0F) {
         this.isDisabled = true;
      }

   }

   @FunctionalInterface
   public interface IEndDisableEvent {
      void endDisable(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IStartDisableEvent {
      void startDisable(LivingEntity var1, IAbility var2);
   }
}
