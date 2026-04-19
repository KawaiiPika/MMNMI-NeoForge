package xyz.pixelatedw.mineminenomi.entities.clouds;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChloroBallCloudEntity extends CloudEntity {
   private ChloroBallProjectile proj;

   public ChloroBallCloudEntity(EntityType<? extends ChloroBallCloudEntity> type, Level world) {
      super(type, world);
   }

   public ChloroBallCloudEntity(Level world, ChloroBallProjectile proj) {
      super((EntityType)ModEntities.CHLORO_BALL_CLOUD.get(), world, proj.getOwner());
      this.proj = proj;
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.proj == null) {
            this.m_146870_();
            return;
         }

         for(LivingEntity target : WyHelper.getNearbyLiving(this.m_20182_(), this.m_9236_(), (double)this.proj.getPoisonRange(), ModEntityPredicates.getEnemyFactions(this.getOwner()))) {
            if (this.getOwner() != target && !target.m_21023_((MobEffect)ModEffects.DOKU_POISON.get())) {
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOKU_POISON.get(), 200, this.proj.getPoisonAmplifier()));
            }
         }

         if (this.f_19797_ % 2 == 0) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CHLORO_BALL_CLOUD.get(), this, this.m_20185_(), this.m_20186_() + (double)1.0F, this.m_20189_(), this.proj.getPoisonParticles());
         }
      }

   }
}
