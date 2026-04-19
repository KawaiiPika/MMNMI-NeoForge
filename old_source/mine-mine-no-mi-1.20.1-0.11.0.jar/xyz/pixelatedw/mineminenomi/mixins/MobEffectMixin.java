package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.events.AttributeModifierApplyEvent;
import xyz.pixelatedw.mineminenomi.api.events.AttributeModifierRemoveEvent;

@Mixin({MobEffect.class})
public class MobEffectMixin {
   @Inject(
      method = {"addAttributeModifiers"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;addPermanentModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V",
   shift = Shift.AFTER
)}
   )
   public void mineminenomi$applyAttributesModifiersToEntity(LivingEntity entity, AttributeMap map, int amplifier, CallbackInfo info) {
      AttributeModifierApplyEvent event = new AttributeModifierApplyEvent(entity, map, amplifier);
      MinecraftForge.EVENT_BUS.post(event);
   }

   @Inject(
      method = {"removeAttributeModifiers"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;removeModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V",
   shift = Shift.AFTER
)}
   )
   public void mineminenomi$removeAttributesModifiersFromEntity(LivingEntity entity, AttributeMap map, int amplifier, CallbackInfo info) {
      AttributeModifierRemoveEvent event = new AttributeModifierRemoveEvent(entity, map, amplifier);
      MinecraftForge.EVENT_BUS.post(event);
   }
}
