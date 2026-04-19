package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class WhiteOutProjectile extends NuProjectileEntity {
   public WhiteOutProjectile(EntityType<? extends WhiteOutProjectile> type, Level world) {
      super(type, world);
   }

   public WhiteOutProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.WHITE_OUT.get(), world, player, ability);
      super.setDamage(0.1F);
      super.setMaxLife(30);
      super.setEntityCollisionSize((double)1.0F);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      if (!super.m_9236_().f_46443_) {
         for(int i = 0; i < 10; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
            data.setLife(10);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)super.m_9236_(), super.m_20185_() + offsetX, super.m_20186_() + offsetY, super.m_20189_() + offsetZ);
         }
      }

   }
}
