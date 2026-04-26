package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class ChargingPikaParticleEvent {
   public static void spawn(Entity entity, Level world, double posX, double posY, double posZ) {
      for(int i = 0; i < 2; ++i) {
         double offsetX = world.getRandom().nextDouble() * 1.55;
         double offsetY = world.getRandom().nextDouble();
         double offsetZ = world.getRandom().nextDouble() * 1.55;
         SimpleParticleData data = new SimpleParticleData(ModParticleTypes.PIKA.get());
         data.setLife(5);
         data.setSize(3.0F);
         data.setMotion(0.0F, 0.15F, 0.0F);
         world.addAlwaysVisibleParticle(data, true, posX + offsetX, posY + (double)0.5F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }
   }
}
