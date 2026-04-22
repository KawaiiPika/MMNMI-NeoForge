package xyz.pixelatedw.mineminenomi.abilities.awa;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public class GoldenHourAbility extends Ability {
   private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "awa_awa_no_mi");
   private static final long MAX_DURATION = 200;

   public GoldenHourAbility() {
      super(FRUIT);
   }

   @Override
   protected void startUsing(LivingEntity entity) {
   }

   @Override
   protected void onTick(LivingEntity entity, long duration) {
      if (duration > MAX_DURATION) {
         this.stop(entity);
         return;
      }

      if (!entity.level().isClientSide) {
         AABB aabb = entity.getBoundingBox().inflate(10.0D);
         List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && !e.isInWater());

         for(LivingEntity target : targets) {
            if (!target.hasEffect(ModEffects.WASHED)) {
               target.addEffect(new MobEffectInstance(ModEffects.WASHED, 100, 1));
            }

            SimpleParticleData foamData = new SimpleParticleData(ModParticleTypes.AWA_FOAM.get());
            foamData.setSize(1.5F);
            ((net.minecraft.server.level.ServerLevel)entity.level()).sendParticles(foamData, target.getX(), target.getY() + 1.0, target.getZ(), 5, 0.5, 0.5, 0.5, 0.0);
         }

         SimpleParticleData awaData = new SimpleParticleData(ModParticleTypes.AWA2.get());
         awaData.setSize(1.5F);
         ((net.minecraft.server.level.ServerLevel)entity.level()).sendParticles(awaData, entity.getX(), entity.getY() + 1.25, entity.getZ(), 8, 1.0, 1.0, 1.0, 0.0);
      }
   }

   @Override
   protected void stopUsing(LivingEntity entity) {
      this.startCooldown(entity, 200 + (getDuration(entity) * 5));
   }

   @Override
   public Component getDisplayName() {
      return Component.translatable("ability.mineminenomi.golden_hour");
   }
}
