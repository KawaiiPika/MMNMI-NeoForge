package xyz.pixelatedw.mineminenomi.particles.effects.supa;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AtomicSpurtParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 2; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         BlockState BlockState = world.m_8055_((new BlockPos((int)posX, (int)posY, (int)posZ)).m_7495_());
         world.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, BlockState), posX + offsetX, posY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
