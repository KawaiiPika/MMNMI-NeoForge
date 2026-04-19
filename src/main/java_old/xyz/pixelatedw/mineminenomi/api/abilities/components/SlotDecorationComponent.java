package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.google.common.base.Strings;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.GCDCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SlotDecorationComponent extends AbilityComponent<IAbility> {
   private static final String FLUSH = "";
   private float slotRed = 1.0F;
   private float slotGreen = 1.0F;
   private float slotBlue = 1.0F;
   private float iconRed = 1.0F;
   private float iconGreen = 1.0F;
   private float iconBlue = 1.0F;
   private float iconAlpha = 1.0F;
   private float maxValue = 1.0F;
   private float currentValue = 0.0F;
   private String displayText = "";
   private final PriorityEventPool<IPreRenderEvent> preRenderEvents = new PriorityEventPool<IPreRenderEvent>();
   private final PriorityEventPool<IPostRenderEvent> postRenderEvents = new PriorityEventPool<IPostRenderEvent>();

   public SlotDecorationComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get(), ability);
      this.setClientSide();
   }

   public void postInit(IAbility ability) {
      this.addPreRenderEvent(100, (entity, minecraft, stack, buffer, rect, x, y, partialTicks) -> {
         if (GCDCapability.isOnGCD(entity) && (Integer)GCDCapability.getGCD(entity).getKey() > 0) {
            ImmutablePair<Integer, Integer> pair = GCDCapability.getGCD(entity);
            this.setCurrentValue((float)(Integer)pair.getKey());
            this.setMaxValue((float)(Integer)pair.getValue());
            this.setSlotColor(0.7F, 0.0F, 0.0F);
            this.setIconColor(0.4F, 0.4F, 0.4F);
         }

      });
   }

   public SlotDecorationComponent addPreRenderEvent(int priority, IPreRenderEvent event) {
      this.preRenderEvents.addEvent(priority, event);
      return this;
   }

   public SlotDecorationComponent addPostRenderEvent(int priority, IPostRenderEvent event) {
      this.postRenderEvents.addEvent(priority, event);
      return this;
   }

   public void setSlotColor(float red, float green, float blue) {
      this.slotRed = red;
      this.slotGreen = green;
      this.slotBlue = blue;
   }

   public void setIconColor(float red, float green, float blue) {
      this.iconRed = red;
      this.iconGreen = green;
      this.iconBlue = blue;
   }

   public void setIconAlpha(float alpha) {
      this.iconAlpha = alpha;
   }

   public void setCurrentValue(float value) {
      this.currentValue = value;
      this.displayText = "";
   }

   public void setDisplayText(String value) {
      this.displayText = value;
   }

   public void setMaxValue(float max) {
      this.maxValue = max;
   }

   public void triggerPreRenderEvents(LivingEntity entity, Minecraft minecraft, PoseStack stack, MultiBufferSource buffer, TexturedRectUI rect, float posX, float posY, float partialTicks) {
      this.preRenderEvents.dispatch((event) -> event.preRender(entity, minecraft, stack, buffer, rect, posX, posY, partialTicks));
   }

   public void triggerPostRenderEvents(LivingEntity entity, Minecraft minecraft, PoseStack stack, MultiBufferSource buffer, TexturedRectUI rect, float posX, float posY, float partialTicks) {
      this.postRenderEvents.dispatch((event) -> event.postRender(entity, minecraft, stack, buffer, rect, posX, posY, partialTicks));
   }

   public float getSlotRed() {
      return this.slotRed;
   }

   public float getSlotGreen() {
      return this.slotGreen;
   }

   public float getSlotBlue() {
      return this.slotBlue;
   }

   public float getIconRed() {
      return this.iconRed;
   }

   public float getIconGreen() {
      return this.iconGreen;
   }

   public float getIconBlue() {
      return this.iconBlue;
   }

   public float getIconAlpha() {
      return this.iconAlpha;
   }

   public float getCurrentValue() {
      return this.currentValue;
   }

   public String getDisplayText() {
      return this.displayText;
   }

   public boolean hasDisplayText() {
      return !Strings.isNullOrEmpty(this.displayText);
   }

   public float getMaxValue() {
      return this.maxValue;
   }

   public void resetDecoration() {
      this.setSlotColor(1.0F, 1.0F, 1.0F);
      this.setIconColor(1.0F, 1.0F, 1.0F);
      this.setIconAlpha(1.0F);
      this.setCurrentValue(0.0F);
   }

   @FunctionalInterface
   public interface IPostRenderEvent {
      void postRender(LivingEntity var1, Minecraft var2, PoseStack var3, MultiBufferSource var4, TexturedRectUI var5, float var6, float var7, float var8);
   }

   @FunctionalInterface
   public interface IPreRenderEvent {
      void preRender(LivingEntity var1, Minecraft var2, PoseStack var3, MultiBufferSource var4, TexturedRectUI var5, float var6, float var7, float var8);
   }
}
