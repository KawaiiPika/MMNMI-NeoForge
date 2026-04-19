package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.magu.MaguHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class MeigoProjectile extends NuHorizontalLightningEntity {
   public MeigoProjectile(EntityType<? extends MeigoProjectile> type, Level world) {
      super(type, world);
   }

   public MeigoProjectile(Level world, LivingEntity entity, Ability ability) {
      super((EntityType)ModProjectiles.MEIGO.get(), entity, 20.0F, 2.0F, ability);
      this.setFadeTime(20);
      this.setSize(0.05F);
      this.setOneTimeHit();
      this.setDamage(100.0F);
      this.setPassThroughEntities();
      this.setArmorPiercing(1.0F);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         AbilityHelper.setSecondsOnFireBy(target, 20, this.getOwner());
      }

   }

   private void blockHitEvent(BlockHitResult result) {
      MaguHelper.generateLavaPool(this.m_9236_(), result.m_82425_(), 2);
   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         double x = this.getCurrentX();
         double y = this.getCurrentY();
         double z = this.getCurrentZ();

         for(int i = 0; i < 3; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            data.setLife(5);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), x + offsetX, y + offsetY, z + offsetZ);
         }

         for(int i = 0; i < 10; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123756_, x + offsetX, y + offsetY, z + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.5F);
         }
      }

   }
}
