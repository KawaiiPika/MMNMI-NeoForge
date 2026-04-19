package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.saraaxolotl;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class PoisonSpitProjectile extends NuProjectileEntity {
   public PoisonSpitProjectile(EntityType<? extends PoisonSpitProjectile> type, Level world) {
      super(type, world);
   }

   public PoisonSpitProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.POISON_SPIT.get(), world, player, ability);
      this.setDamage(8.0F);
      this.setPhysical();
      this.setMaxLife(10);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 0));
      }

   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 7; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)6.0F;
            double offsetY = WyHelper.randomDouble() / (double)6.0F;
            double offsetZ = WyHelper.randomDouble() / (double)6.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DOKU_PINK.get());
            data.setLife(5);
            data.setSize(0.7F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
