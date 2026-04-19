package xyz.pixelatedw.mineminenomi.particles.effects.mera;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class JujikaParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      Direction dir = Direction.m_122364_((double)entity.m_146908_());
      RandomSource rand = entity.m_9236_().f_46441_;

      for(float i = 0.0F; i <= 5.0F; i += 0.5F) {
         int x = 0;
         int z = 0;
         z = (int)((float)z + i * (float)dir.m_122429_());
         x = (int)((float)x + i * (float)dir.m_122431_());
         SimpleParticleData part;
         if (rand.m_188503_(10) % 2 == 0) {
            part = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
         } else {
            part = new SimpleParticleData((ParticleType)ModParticleTypes.MERA2.get());
         }

         part.setLife((int)WyHelper.randomWithRange(1, 3));
         part.setSize((float)WyHelper.randomWithRange(0, 2));
         part.setColor(1.0F, 1.0F, 1.0F, 1.0F);
         part.setMotion(entity.m_20184_().f_82479_, entity.m_20184_().f_82480_, entity.m_20184_().f_82481_);
         entity.m_9236_().m_6493_(part, true, posX - (double)x, posY, posZ - (double)z, (double)0.0F, (double)0.0F, (double)0.0F);
         entity.m_9236_().m_6493_(part, true, posX + (double)x, posY, posZ + (double)z, (double)0.0F, (double)0.0F, (double)0.0F);
         entity.m_9236_().m_6493_(part, true, posX, posY + (double)i, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
         entity.m_9236_().m_6493_(part, true, posX, posY - (double)i, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
