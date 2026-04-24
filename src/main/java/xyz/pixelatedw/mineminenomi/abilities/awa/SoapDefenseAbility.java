package xyz.pixelatedw.mineminenomi.abilities.awa;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SoapDefenseAbility extends Ability {
   private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "awa_awa_no_mi");
   private static final long MAX_DURATION = 200;

   public SoapDefenseAbility() {
      super(FRUIT);
   }

   @Override
   protected void startUsing(LivingEntity entity) {
      if (!entity.level().isClientSide) {
         entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 4, false, false));
      }
   }

   @Override
   protected void onTick(LivingEntity entity, long duration) {
      if (duration > MAX_DURATION || entity.isInWater()) {
         this.stop(entity);
      }
   }

   @Override
   protected void stopUsing(LivingEntity entity) {
      if (!entity.level().isClientSide) {
         entity.removeEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN);
      }
      this.startCooldown(entity, 50 + getDuration(entity));
   }

   @Override
   public Component getDisplayName() {
      return Component.translatable("ability.mineminenomi.soap_defense");
   }
}
