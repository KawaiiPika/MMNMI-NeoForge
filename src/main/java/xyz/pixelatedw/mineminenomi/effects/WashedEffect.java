package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class WashedEffect extends MobEffect {
   public WashedEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "washed_slowdown"), -0.5D, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "washed_jump"), -0.5D, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "washed_damage"), -4.0D, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "washed_attack_speed"), -6.0D, Operation.ADD_VALUE);
   }

   @Override
   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return true;
   }

   @Override
   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (!entity.level().isClientSide) {
         int time = entity.getEffect(net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.wrapAsHolder(this)).getDuration();
         if (time % 10 == 0) {
            SimpleParticleData data = new SimpleParticleData(ModParticleTypes.WASHED.get());
            ((net.minecraft.server.level.ServerLevel)entity.level()).sendParticles(data, entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 0);
         }

         if (entity.isInWater()) {
            entity.removeEffect(net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.wrapAsHolder(this));
         }
      }
      return true;
   }
}
