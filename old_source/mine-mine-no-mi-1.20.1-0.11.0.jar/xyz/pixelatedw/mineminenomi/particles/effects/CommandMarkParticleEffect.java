package xyz.pixelatedw.mineminenomi.particles.effects;

import java.awt.Color;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class CommandMarkParticleEffect extends ParticleEffect<Details> {
   public CommandMarkParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      Color color = details.getColor();
      SimpleParticleData particle = new SimpleParticleData((ParticleType)ModParticleTypes.COMMAND_MARK.get());
      particle.setLife(5);
      particle.setSize(details.isMainMark() ? 5.0F : 4.0F);
      particle.setColor((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 1.0F);
      particle.setFunction(EasingFunction.BOUNCE_IN);
      particle.setMotion((double)0.0F, 0.01, (double)0.0F);
      world.m_6493_(particle, true, posX, posY + (double)0.75F, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }

   public static class Details extends ParticleEffect.Details {
      private boolean isMain;
      private int rgb;

      public Details() {
      }

      public Details(int rgb, boolean isMain) {
         this.rgb = rgb;
         this.isMain = true;
      }

      public Details(String hex, boolean isMain) {
         this(WyHelper.hexToRGB(hex).getRGB(), isMain);
      }

      public void save(CompoundTag nbt) {
         nbt.m_128405_("rgb", this.rgb);
      }

      public void load(CompoundTag nbt) {
         this.rgb = nbt.m_128451_("rgb");
      }

      public int getRGB() {
         return this.rgb;
      }

      public Color getColor() {
         return new Color(this.rgb);
      }

      public void setRGB(int rgb) {
         this.rgb = rgb;
      }

      public boolean isMainMark() {
         return this.isMain;
      }

      public void setMainMark() {
         this.isMain = true;
      }
   }
}
