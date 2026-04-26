package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class MokoProjectile extends NuProjectileEntity {
   public MokoProjectile(EntityType<? extends MokoProjectile> type, Level world) {
      super(type, world);
   }

   public MokoProjectile(Level world, LivingEntity player) {
      super(ModEntities.MOKO_PROJECTILE.get(), player, world);
      this.setDamage(10.0F);
   }

   @Override
   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity var3 = result.getEntity();
      if (var3 instanceof LivingEntity target) {
         target.setDeltaMovement(target.getDeltaMovement().x, -5.0, target.getDeltaMovement().z);
         target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 10));
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         for(int i = 0; i < 25; ++i) {
            double offsetX = (this.random.nextDouble() - 0.5) / 2.0;
            double offsetY = (this.random.nextDouble() - 0.5) / 2.0;
            double offsetZ = (this.random.nextDouble() - 0.5) / 2.0;
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0, 0, 0, 0);
         }
      }
   }
}
