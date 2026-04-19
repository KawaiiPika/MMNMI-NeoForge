package xyz.pixelatedw.mineminenomi.particles.effects.haki;

import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HaoshokuHakiParticleEffect extends ParticleEffect<Details> {
   public HaoshokuHakiParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      int size = details.size;
      int color = details.color;
      if (color == 0) {
         color = 16711680;
      }

      BlockPos ogPos = new BlockPos((int)posX, (int)posY, (int)posZ);

      for(double x = (double)(-size); x < (double)size; ++x) {
         for(double z = (double)(-size); z < (double)size; ++z) {
            BlockPos pos = new BlockPos((int)(posX + x), (int)posY, (int)(posZ + z));
            if (ogPos.m_123314_(pos, (double)size) && ogPos.hashCode() % 20 >= 5) {
               BlockState blockState = world.m_8055_(pos.m_7495_());
               if (!blockState.m_60795_()) {
                  for(int i = 0; i < 10; ++i) {
                     world.m_6493_(new BlockParticleOption(ParticleTypes.f_123794_, blockState), true, posX + WyHelper.randomWithRange(-3, 3) + x, posY, posZ + WyHelper.randomWithRange(-3, 3) + z, (double)0.0F, (double)0.0F, (double)0.0F);
                  }
               }
            }
         }
      }

      Vec3 playerPos = new Vec3(posX, posY, posZ);
      double r = (double)2.0F;

      for(double phi = (double)0.0F; phi <= (Math.PI * 2D); phi += 0.04908738521234052) {
         double x = r * Math.cos(phi) + WyHelper.randomDouble() / (double)5.0F;
         double z = r * Math.sin(phi) + WyHelper.randomDouble() / (double)5.0F;
         Vec3 pos = playerPos.m_82549_(new Vec3(x, posY, z));
         Vec3 dirVec = playerPos.m_82546_(pos).m_82541_().m_82542_((double)3.5F, (double)1.0F, (double)3.5F);
         Color clientRGB = new Color(color);
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.CHIYU.get());
         data.setLife(15);
         data.setSize(3.0F);
         data.setMotion(-dirVec.f_82479_, (double)0.0F, -dirVec.f_82481_);
         data.setColor((float)clientRGB.getRed() / 255.0F, (float)clientRGB.getGreen() / 255.0F, (float)clientRGB.getBlue() / 255.0F, 0.4F);
         world.m_6493_(data, true, posX + x, posY + 0.3, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         data = new SimpleParticleData((ParticleType)ModParticleTypes.CHIYU.get());
         data.setLife(15);
         data.setSize(2.0F);
         data.setMotion(-dirVec.f_82479_, (double)0.0F, -dirVec.f_82481_);
         data.setColor(0.0F, 0.0F, 0.0F, 0.6F);
         world.m_6493_(data, true, posX + x, posY + 0.3, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public static class Details extends ParticleEffect.Details {
      private int color;
      private int size;

      public void save(CompoundTag nbt) {
         nbt.m_128405_("color", this.color);
         nbt.m_128405_("size", this.size);
      }

      public void load(CompoundTag nbt) {
         this.color = nbt.m_128451_("color");
         this.size = nbt.m_128451_("size");
      }

      public void setColor(int color) {
         this.color = color;
      }

      public int getColor() {
         return this.color;
      }

      public void setSize(int size) {
         this.size = size;
      }

      public int getSize() {
         return this.size;
      }
   }
}
