package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DiableJambeParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      if (entity instanceof LivingEntity living) {
         float f = living.f_20884_ + (living.f_20883_ - living.f_20884_) + 30.0F;
         double x = (double)Mth.m_14031_(f * (float)Math.PI / -180.0F) * 0.2;
         double z = (double)Mth.m_14089_(f * (float)Math.PI / -180.0F) * 0.2;
         double offsetX = x + WyHelper.randomDouble() / (double)5.0F;
         double offsetY = 0.6 + WyHelper.randomDouble() / (double)2.5F;
         double offsetZ = z + WyHelper.randomDouble() / (double)5.0F;
         int age = (int)((double)3.0F + WyHelper.randomWithRange(0, 2));
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
         data.setLife(age);
         data.setSize((float)age / 2.5F);
         data.setMotion((double)0.0F, 0.002, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
