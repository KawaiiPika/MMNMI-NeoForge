package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.ParticleStatus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ParticleEffect<A extends ParticleEffect.Details> {
   public static final NoDetails EMPTY_DETAILS = new NoDetails();
   private IFactory<A> factory;

   public ParticleEffect() {
   }

   public ParticleEffect(IFactory<A> factory) {
      this.factory = factory;
   }

   public abstract void spawn(Entity var1, Level var2, double var3, double var5, double var7, A var9);

   @OnlyIn(Dist.CLIENT)
   public boolean canParticlesSpawn(Level world) {
      OptionInstance<ParticleStatus> particles = Minecraft.m_91087_().f_91066_.m_231929_();
      return (particles.m_231551_() != ParticleStatus.DECREASED || world.m_213780_().m_188503_(10) >= 2) && (particles.m_231551_() != ParticleStatus.MINIMAL || world.m_213780_().m_188503_(10) >= 8);
   }

   public A createDetails() {
      return (A)(this.factory != null ? this.factory.create() : EMPTY_DETAILS);
   }

   public abstract static class Details {
      public abstract void save(CompoundTag var1);

      public abstract void load(CompoundTag var1);
   }

   public static class NoDetails extends Details {
      public void save(CompoundTag nbt) {
      }

      public void load(CompoundTag nbt) {
      }
   }

   public interface IFactory<A extends Details> {
      A create();
   }
}
