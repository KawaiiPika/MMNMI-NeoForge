package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HikenProjectile extends NuProjectileEntity {
   private static final BlockProtectionRule GRIEF_RULE;

   public HikenProjectile(EntityType<? extends HikenProjectile> type, Level world) {
      super(type, world);
   }

   public HikenProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.HIKEN.get(), world, thrower, ability);
      this.setDamage(50.0F);
      this.setPassThroughBlocks();
      this.setPassThroughEntities();
      this.setMaxLife(32);
      this.setArmorPiercing(0.75F);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      this.explode((double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_());
   }

   private void tickEvent() {
      if (this.f_19797_ > 2) {
         this.explode(this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

      if (this.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get()) && ServerConfig.getDestroyWater()) {
         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setRule(GRIEF_RULE).setSize(2);
         placer.generate(this.m_9236_(), this.m_20183_(), BlockGenerators.SPHERE);

         for(BlockPos blockPos : placer.getPlacedPositions()) {
            WyHelper.spawnParticles(ParticleTypes.f_123795_, (ServerLevel)this.m_20193_(), (double)blockPos.m_123341_() + WyHelper.randomDouble() / (double)2.0F, (double)blockPos.m_123342_() + 0.8, (double)blockPos.m_123343_() + WyHelper.randomDouble() / (double)2.0F);
            this.m_20193_().m_7106_(ParticleTypes.f_123762_, (double)blockPos.m_123341_(), (double)blockPos.m_123342_() + 1.1, (double)blockPos.m_123343_(), (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

      if (!super.m_9236_().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double offsetX = WyHelper.randomDouble() * (double)2.0F;
            double offsetY = WyHelper.randomDouble() * (double)2.0F;
            double offsetZ = WyHelper.randomDouble() * (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            data.setLife(30);
            data.setSize(3.0F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), super.m_20185_() + offsetX, super.m_20186_() + offsetY, super.m_20189_() + offsetZ);
         }

         for(int i = 0; i < 10; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
            data.setLife(7);
            data.setSize(1.2F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), super.m_20185_() + offsetX, super.m_20186_() + offsetY, super.m_20189_() + offsetZ);
         }
      }

   }

   private void explode(double x, double y, double z) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), x, y, z, 4.0F);
         explosion.setStaticDamage(25.0F);
         explosion.disableExplosionKnockback();
         explosion.setDamageOwner(false);
         explosion.setFireAfterExplosion(true);
         explosion.setExplosionSound(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.m_46061_();
      });
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedTags(ModTags.Blocks.BLOCK_PROT_LIQUIDS, ModTags.Blocks.BLOCK_PROT_SNOW).build();
   }
}
