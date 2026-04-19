package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class YukiRabiProjectile extends NuProjectileEntity {
   public YukiRabiProjectile(EntityType<? extends YukiRabiProjectile> type, Level world) {
      super(type, world);
   }

   public YukiRabiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.YUKI_RABI.get(), world, player, ability);
      this.setDamage(3.5F);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         FrostbiteEffect.addFrostbiteStacks(target, 1);
      }

   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)5.0F;
            double offsetY = WyHelper.randomDouble() / (double)5.0F;
            double offsetZ = WyHelper.randomDouble() / (double)5.0F;
            ParticleType<SimpleParticleData> particle;
            if (i % 2 == 0) {
               particle = (ParticleType)ModParticleTypes.YUKI2.get();
            } else {
               particle = (ParticleType)ModParticleTypes.YUKI.get();
            }

            SimpleParticleData data = new SimpleParticleData(particle);
            data.setLife(20);
            data.setSize(1.3F);
            data.setMotion((double)0.0F, -0.02, (double)0.0F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + (double)0.25F + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
