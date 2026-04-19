package xyz.pixelatedw.mineminenomi.particles.effects.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShockwaveParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 120; ++i) {
         double y = WyHelper.randomDouble() / (double)2.0F;
         BlockState blockState = world.m_8055_(new BlockPos((int)posX, (int)posY, (int)posZ));
         world.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, blockState), posX + WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble() / (double)20.0F, posY + y + (double)0.5F, posZ + WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble() / (double)20.0F, (double)0.0F, (double)0.0F, (double)0.0F);
         double offsetX = WyHelper.randomDouble() * (double)2.0F;
         double offsetY = WyHelper.randomDouble() * (double)2.0F;
         double offsetZ = WyHelper.randomDouble() * (double)2.0F;
         SimpleParticleData data;
         if (i % 5 == 0) {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU2.get());
         } else {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
         }

         data.setMotion(-offsetX / (double)20.0F, 0.015, -offsetZ / (double)20.0F);
         data.setLife(10);
         data.setSize(1.0F);
         world.m_7106_(data, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
