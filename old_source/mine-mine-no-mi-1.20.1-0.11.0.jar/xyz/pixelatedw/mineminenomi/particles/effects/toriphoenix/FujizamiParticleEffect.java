package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FujizamiParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      LocalPlayer clientPlayer = Minecraft.m_91087_().f_91074_;
      float visibility = entity == clientPlayer ? 0.15F : 0.5F;

      for(int i = 0; i < 64; ++i) {
         double offsetX = WyHelper.randomDouble() / 1.2;
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble() / 1.2;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.BLUE_FLAME.get());
         data.setLife(20);
         data.setColor(1.0F, 1.0F, 1.0F, visibility);
         data.setSize(2.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)0.5F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
