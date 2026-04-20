package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class RelaxHourProjectile extends AbilityProjectile {

   public RelaxHourProjectile(EntityType<? extends RelaxHourProjectile> type, Level world) {
      super(type, world);
   }

   public RelaxHourProjectile(Level world, LivingEntity player) {
      super((EntityType)ModEntities.RELAX_HOUR_PROJECTILE.get(), player, world);
      this.setDamage(3.0F);
      this.setMaxLife(40);
   }

   @Override
   protected void onHitEntity(EntityHitResult hit) {
      // super.onHitEntity(hit);
      Entity hitEntity = hit.getEntity();
      if (hitEntity instanceof LivingEntity living && !this.level().isClientSide) {
         living.addEffect(new MobEffectInstance(ModEffects.WASHED, 200, 0));
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         SimpleParticleData data = new SimpleParticleData(ModParticleTypes.AWA.get());
         data.setSize(1.3F);
         ((net.minecraft.server.level.ServerLevel)this.level()).sendParticles(data, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);

         SimpleParticleData foamData = new SimpleParticleData(ModParticleTypes.AWA_FOAM.get());
         foamData.setSize(1.3F);
         ((net.minecraft.server.level.ServerLevel)this.level()).sendParticles(foamData, this.getX(), this.getY(), this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
      }
   }
}
