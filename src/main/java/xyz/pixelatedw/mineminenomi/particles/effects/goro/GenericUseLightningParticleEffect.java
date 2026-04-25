package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class GenericUseLightningParticleEffect {
   public static void spawn(Entity entity, Level world, double posX, double posY, double posZ) {
      for(int i = 0; i < 4; ++i) {
         double offsetX = world.getRandom().nextDouble() * 2.0 - 1.0;
         double offsetY = world.getRandom().nextDouble() * 2.0 - 1.0;
         double offsetZ = world.getRandom().nextDouble() * 2.0 - 1.0;
         SimpleParticleData data = new SimpleParticleData(ModParticleTypes.GORO2.get());
         data.setLife(10);
         data.setSize(10.0F);
         world.addAlwaysVisibleParticle(data, true, posX + offsetX, posY + 1.0 + offsetY, posZ + offsetZ, 0.0, 0.0, 0.0);
      }
   }
}
