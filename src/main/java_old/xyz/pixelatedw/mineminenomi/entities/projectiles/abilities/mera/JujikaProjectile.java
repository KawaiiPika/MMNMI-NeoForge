package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class JujikaProjectile extends NuProjectileEntity {
   public JujikaProjectile(EntityType<? extends JujikaProjectile> type, Level world) {
      super(type, world);
   }

   public JujikaProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.JUJIKA.get(), world, player, ability);
      this.setDamage(25.0F);
      this.setArmorPiercing(0.75F);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void tickEvent() {
      if (super.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get())) {
         super.m_142687_(RemovalReason.KILLED);
         super.m_20193_().m_7106_(ParticleTypes.f_123762_, super.m_20185_(), super.m_20186_() + 1.1, super.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F);
      }

      if (!super.m_9236_().f_46443_ && super.f_19797_ > 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.JUJIKA.get(), this, super.m_20185_(), super.m_20186_(), super.m_20189_());
      }

   }

   private void blockHitEvent(BlockHitResult result) {
      BlockPos hit = result.m_82425_();

      for(int j = -2; j <= 2; ++j) {
         for(int i = -5; i <= 5; ++i) {
            if (super.m_9236_().m_46859_(new BlockPos(hit.m_123341_() + i, hit.m_123342_() + j, hit.m_123343_()))) {
               NuWorld.setBlockState((Entity)this.getOwner(), new BlockPos(hit.m_123341_() + i, hit.m_123342_() + j, hit.m_123343_() - i), Blocks.f_50083_.m_49966_(), 2, (BlockProtectionRule)null);
            }

            if (super.m_9236_().m_46859_(new BlockPos(hit.m_123341_(), hit.m_123342_() + j, hit.m_123343_() + i))) {
               NuWorld.setBlockState((Entity)this.getOwner(), new BlockPos(hit.m_123341_(), hit.m_123342_() + j, hit.m_123343_() + i), Blocks.f_50083_.m_49966_(), 2, (BlockProtectionRule)null);
            }
         }
      }

   }
}
