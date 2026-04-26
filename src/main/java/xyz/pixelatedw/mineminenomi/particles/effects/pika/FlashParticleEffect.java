package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class FlashParticleEffect {
   public static void spawn(Entity entity, Level world, double posX, double posY, double posZ) {
      for(int i = 0; i < 10; ++i) {
         double offsetX = world.getRandom().nextDouble() / (double)1.25F;
         double offsetY = world.getRandom().nextDouble() * (double)1.25F;
         double offsetZ = world.getRandom().nextDouble() / (double)1.25F;
         SimpleParticleData data = new SimpleParticleData(ModParticleTypes.PIKA.get());
         data.setLife(5);
         data.setSize(4.0F);
         world.addAlwaysVisibleParticle(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }
   }
}
