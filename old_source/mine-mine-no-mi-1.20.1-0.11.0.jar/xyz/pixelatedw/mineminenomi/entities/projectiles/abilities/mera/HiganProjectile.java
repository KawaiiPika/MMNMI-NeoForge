package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class HiganProjectile extends NuProjectileEntity {
   public HiganProjectile(EntityType<? extends HiganProjectile> type, Level world) {
      super(type, world);
   }

   public HiganProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.HIGAN.get(), world, thrower, ability);
      this.setDamage(10.0F);
      this.setArmorPiercing(0.75F);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      BlockState state = super.m_9236_().m_8055_(result.m_82425_());
      if (state.m_60734_() == Blocks.f_50683_) {
         super.m_9236_().m_7731_(result.m_82425_(), (BlockState)state.m_61124_(BlockStateProperties.f_61443_, true), 11);
      } else {
         NuWorld.setBlockState((Entity)this.getOwner(), result.m_82425_().m_7918_(0, 1, 0), Blocks.f_50083_.m_49966_(), 2, DefaultProtectionRules.AIR);
      }

   }

   private void entityHitEvent(EntityHitResult result) {
      if (result.m_82443_() != null && result.m_82443_().m_6084_()) {
         Entity var3 = result.m_82443_();
         if (var3 instanceof LivingEntity) {
            LivingEntity target = (LivingEntity)var3;
            target.m_20254_(4);
         }
      }

   }

   private void tickEvent() {
      if (super.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get())) {
         super.m_142687_(RemovalReason.KILLED);
         super.m_20193_().m_7106_(ParticleTypes.f_123762_, super.m_20185_(), super.m_20186_() + 1.1, super.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F);
      }

      if (!super.m_9236_().f_46443_) {
         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)5.0F;
            double offsetY = WyHelper.randomDouble() / (double)5.0F;
            double offsetZ = WyHelper.randomDouble() / (double)5.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            data.setLife(10);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)super.m_9236_(), super.m_20185_() + offsetX, super.m_20186_() + (double)0.25F + offsetY, super.m_20189_() + offsetZ);
         }
      }

   }
}
