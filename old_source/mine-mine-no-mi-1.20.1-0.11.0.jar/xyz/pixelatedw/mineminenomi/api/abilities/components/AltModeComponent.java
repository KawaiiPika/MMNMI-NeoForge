package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAbilityAltModePacket;

public class AltModeComponent<E extends Enum<E>> extends AbilityComponent<IAbility> {
   private final PriorityEventPool<IChangeModeEvent<E>> changeModeEvents;
   private final Class<E> modeClass;
   private final E defaultMode;
   private E currentMode;
   private boolean isModeChangeAutomatic;
   private boolean playReadyAnim;
   private float readyAnim;

   public AltModeComponent(IAbility ability, Class<E> clz, E defaultMode) {
      this(ability, clz, defaultMode, false);
   }

   public AltModeComponent(IAbility ability, Class<E> clz, E defaultMode, boolean isModeChangeAutomatic) {
      super((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get(), ability);
      this.changeModeEvents = new PriorityEventPool<IChangeModeEvent<E>>();
      this.playReadyAnim = false;
      this.readyAnim = 0.0F;
      this.modeClass = clz;
      this.defaultMode = defaultMode;
      this.currentMode = defaultMode;
      this.isModeChangeAutomatic = isModeChangeAutomatic;
   }

   public AltModeComponent<E> addChangeModeEvent(IChangeModeEvent<E> event) {
      this.changeModeEvents.addEvent(event);
      return this;
   }

   public AltModeComponent<E> addChangeModeEvent(int priority, IChangeModeEvent<E> event) {
      this.changeModeEvents.addEvent(priority, event);
      return this;
   }

   public void setMode(LivingEntity entity, Enum<?> mode) {
      this.setMode(entity, mode, true);
   }

   public void revertToDefault(LivingEntity entity) {
      this.setMode(entity, this.defaultMode, true);
   }

   private void setMode(LivingEntity entity, E mode, boolean update) {
      super.ensureIsRegistered();
      if (!this.isMode(mode)) {
         try {
            boolean canChangeMode = !this.changeModeEvents.dispatchCancelable((event) -> !event.changeMode(entity, this.getAbility(), mode));
            if (!canChangeMode) {
               return;
            }

            this.currentMode = mode;
            this.playReadyAnim = true;
         } catch (Exception ex) {
            ex.printStackTrace();
            return;
         }
      }

      if (update && entity instanceof ServerPlayer player) {
         ModNetwork.sendToAllTrackingAndSelf(new SChangeAbilityAltModePacket(player, this.getAbility(), this.getCurrentMode()), player);
         if (!this.isAutomatic()) {
            Component componentMessage = Component.m_237110_(ModI18nAbilities.MESSAGE_ALT_MODE_CHANGE, new Object[]{this.getAbility().getDisplayName().getString(), this.getCurrentMode()});
            player.m_213846_(componentMessage);
         }
      }

   }

   public void setNextInCycle(LivingEntity entity) {
      E[] modes = (E[])(this.modeClass.getEnumConstants());
      E mode = modes[(this.currentMode.ordinal() + 1) % modes.length];
      this.setMode(entity, mode);
   }

   public boolean isMode(E mode) {
      return this.currentMode.equals(mode);
   }

   public E getCurrentMode() {
      return this.currentMode;
   }

   public Class<E> getAltMode() {
      return this.modeClass;
   }

   public boolean isAutomatic() {
      return this.isModeChangeAutomatic;
   }

   public void postInit(IAbility ability) {
      ability.getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get()).ifPresent((component) -> component.addPostRenderEvent(10, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
            if (this.playReadyAnim && this.readyAnim <= 0.0F) {
               this.readyAnim = 30.0F;
            } else if (this.playReadyAnim && this.readyAnim > 0.0F) {
               this.readyAnim = (float)((double)this.readyAnim - (double)1.75F * (double)minecraft.m_91297_());
               if (this.readyAnim <= 0.0F) {
                  this.playReadyAnim = false;
               } else if (this.readyAnim > 0.0F) {
                  float anim = this.readyAnim * 0.03F;
                  anim = Math.max(0.0F, anim);
                  float scale = 1.8F * EasingFunctionHelper.easeOutSine(anim);
                  float alpha = 1.0F * EasingFunctionHelper.easeOutSine(anim);
                  scale = Mth.m_14036_(scale, 1.0F, scale);
                  stack.m_85836_();
                  stack.m_85837_((double)x, (double)y, (double)1.0F);
                  stack.m_85837_((double)12.0F, (double)12.0F, (double)0.0F);
                  stack.m_85841_(scale, scale, 1.0F);
                  stack.m_85837_((double)-12.0F, (double)-12.0F, (double)0.0F);
                  RenderSystem.enableBlend();
                  rect.setSize(23.0F, 23.0F);
                  rect.setColor(1.0F, 0.95F, 0.0F, alpha);
                  rect.draw(stack, 0.0F, 0.0F);
                  RenderSystem.disableBlend();
                  stack.m_85849_();
               }
            }

         }));
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128359_("currentMode", this.currentMode.toString());
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);

      try {
         this.currentMode = Enum.valueOf(this.modeClass, nbt.m_128461_("currentMode"));
      } catch (Exception var3) {
      }

   }

   @FunctionalInterface
   public interface IChangeModeEvent<E extends Enum<E>> {
      boolean changeMode(LivingEntity var1, IAbility var2, E var3);
   }
}
