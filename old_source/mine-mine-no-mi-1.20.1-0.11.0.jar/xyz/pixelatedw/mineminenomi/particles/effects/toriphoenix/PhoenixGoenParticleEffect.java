package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PhoenixGoenParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      float mult = 1.0F;
      if (entity instanceof NuProjectileEntity projectile) {
         mult = (float)(projectile.getLife() / projectile.getMaxLife()) * 1.25F;
      }

      for(int i = 0; (float)i < 25.0F * mult; ++i) {
         double offsetX = WyHelper.randomDouble() * (double)mult;
         double offsetY = WyHelper.randomDouble() * (double)mult;
         double offsetZ = WyHelper.randomDouble() * (double)mult;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.BLUE_FLAME.get());
         data.setLife(8);
         data.setSize(3.0F * mult);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
