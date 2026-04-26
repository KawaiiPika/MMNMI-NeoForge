package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class LightningExplosionParticleEffect {
   public static void spawn(Entity entity, Level world, double posX, double posY, double posZ) {
      for(int i = 0; i < 30; ++i) {
         double offsetX = world.getRandom().nextDouble() * 32.0 - 16.0;
         double offsetY = world.getRandom().nextDouble() * 32.0 - 16.0;
         double offsetZ = world.getRandom().nextDouble() * 32.0 - 16.0;
         SimpleParticleData data = new SimpleParticleData(ModParticleTypes.GORO3.get());
         data.setLife(140);
         data.setSize(50.0F);
         world.addAlwaysVisibleParticle(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, 0.0, 0.0, 0.0);
      }
   }
}
