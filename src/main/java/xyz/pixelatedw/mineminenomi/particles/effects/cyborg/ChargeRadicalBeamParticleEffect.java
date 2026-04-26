package xyz.pixelatedw.mineminenomi.particles.effects.cyborg;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class ChargeRadicalBeamParticleEffect {
   public static void spawn(Entity entity, Level world, double posX, double posY, double posZ) {
      Vec3 spawnPoint = entity.getViewVector(1.0F).multiply(1.0, 1.0, 1.0).add(0.0, entity.getEyeHeight(), 0.0);
      double r = 0.8;

      for(double phi = 0.0; phi <= (Math.PI * 2D); phi += (Math.PI / 8D)) {
         if (entity.level().getRandom().nextInt(20) == 0) {
            double x = r * Math.sin(phi) + world.getRandom().nextDouble() / 2.0;
            double y = r * Math.cos(phi) + world.getRandom().nextDouble() / 2.0;
            double z = 0.0;
            Vec3 vec = new Vec3(x, y, z);
            vec = VectorHelper.rotateAroundX(vec, Math.toRadians(entity.getXRot()));
            vec = VectorHelper.rotateAroundY(vec, Math.toRadians(-entity.getYRot()));
            Vec3 spawnPos = entity.position().add(spawnPoint.add(0.0, -entity.getEyeHeight(), 0.0)).add(vec);
            Vec3 moveVec = entity.position().vectorTo(spawnPos);
            double motionX = moveVec.x * 0.03;
            double motionY = moveVec.y * 0.03;
            double motionZ = moveVec.z * 0.03;
            SimpleParticleData part = new SimpleParticleData(ModParticleTypes.PIKA.get());
            part.setLife(20);
            part.setSize(1.0F);
            part.setMotion((float)motionX, (float)motionY, (float)motionZ);
            world.addAlwaysVisibleParticle(part, true, posX + spawnPoint.x + vec.x, posY + spawnPoint.y + vec.y, posZ + spawnPoint.z + vec.z, 0.0, 0.0, 0.0);
         }
      }
   }
}
