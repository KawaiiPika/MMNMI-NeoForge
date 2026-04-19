package xyz.pixelatedw.mineminenomi.particles.effects.zou;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GreatStompParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double phi = (double)0.0F;

      while(phi < (double)10.0F) {
         phi += (Math.PI / 10D);

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = phi * Math.cos(theta);
            double y = WyHelper.randomDouble();
            double z = phi * Math.sin(theta);
            BlockState blockState = world.m_8055_((new BlockPos((int)posX, (int)posY, (int)posZ)).m_7495_());
            if (blockState.m_60795_()) {
               blockState = Blocks.f_50493_.m_49966_();
            }

            BlockParticleOption particleData = new BlockParticleOption(ParticleTypes.f_123794_, blockState);
            world.m_6493_(particleData, true, posX + WyHelper.randomWithRange(-3, 3) + x, posY + y, posZ + WyHelper.randomWithRange(-3, 3) + z, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
