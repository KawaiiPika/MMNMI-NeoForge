package xyz.pixelatedw.mineminenomi.entities.clouds;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KemuriBoshiCloudEntity extends CloudEntity {
   public KemuriBoshiCloudEntity(EntityType<? extends KemuriBoshiCloudEntity> type, Level world) {
      super(type, world);
   }

   public KemuriBoshiCloudEntity(Level world, LivingEntity owner) {
      super((EntityType)ModEntities.KEMURI_BOSHI_CLOUD.get(), world, owner);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         for(LivingEntity target : WyHelper.getNearbyLiving(this.m_20182_(), this.m_9236_(), (double)6.0F, ModEntityPredicates.getEnemyFactions(this.getOwner()))) {
            if (this.getOwner() != target && !target.m_21023_(MobEffects.f_19614_)) {
               target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 80, 1));
            }
         }

         if (this.f_19797_ % 2 == 0) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.KEMURI_BOSHI.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
         }
      }

   }
}
