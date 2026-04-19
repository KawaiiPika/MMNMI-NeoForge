package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({LivingEntity.class})
public interface ILivingEntityMixin {
   @Invoker("onEffectRemoved")
   void invokeOnEffectRemoved(MobEffectInstance var1);

   @Invoker("getJumpPower")
   float invokeGetJumpPower();

   @Accessor("jumping")
   boolean isJumping();
}
