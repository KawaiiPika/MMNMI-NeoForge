package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.UrsusShockProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DaiEnkaiEnteiProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
   private static final EntityDataAccessor<Float> SIZE;
   private static final BlockProtectionRule GRIEF_RULE;

   public DaiEnkaiEnteiProjectile(EntityType<? extends DaiEnkaiEnteiProjectile> type, Level world) {
      super(type, world);
   }

   public DaiEnkaiEnteiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.DAI_ENKAI_ENTEI.get(), world, player, ability);
      this.setArmorPiercing(0.75F);
      this.setUnavoidable();
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 0.6F * this.getSize());
         explosion.setStaticDamage(2.0F * this.getSize());
         explosion.setStaticBlockResistance(0.25F);
         explosion.setFireAfterExplosion(true);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)(0.6F * this.getSize())));
         explosion.m_46061_();
      });
   }

   private void tickEvent() {
      this.setDamage(2.0F * this.getSize());
      if (this.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get()) && ServerConfig.getDestroyWater()) {
         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setRule(GRIEF_RULE).setSize(9);
         placer.generate(this.m_9236_(), this.m_20183_(), BlockGenerators.SPHERE);

         for(BlockPos blockPos : placer.getPlacedPositions()) {
            WyHelper.spawnParticles(ParticleTypes.f_123795_, (ServerLevel)this.m_20193_(), (double)blockPos.m_123341_() + WyHelper.randomDouble() / (double)2.0F, (double)blockPos.m_123342_() + 0.8, (double)blockPos.m_123343_() + WyHelper.randomDouble() / (double)2.0F);
            this.m_20193_().m_7106_(ParticleTypes.f_123762_, (double)blockPos.m_123341_(), (double)blockPos.m_123342_() + 1.1, (double)blockPos.m_123343_(), (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double offsetX = WyHelper.randomDouble();
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble();
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            data.setLife(6);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }

         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble();
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble();
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
            data.setLife(4);
            data.setSize(1.2F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }

   public void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SIZE, 0.0F);
   }

   public void setSize(float size) {
      this.f_19804_.m_135381_(SIZE, size);
   }

   public float getSize() {
      return (Float)this.f_19804_.m_135370_(SIZE);
   }

   public void increaseSize() {
      float size = this.getSize();
      if (this.getSize() <= 0.0F) {
         size = 10.0F;
      }

      size += 0.1875F;
      this.setSize(size);
      this.setEntityCollisionSizeAndUpdate((double)(size / 4.0F));
   }

   static {
      SIZE = SynchedEntityData.m_135353_(UrsusShockProjectile.class, EntityDataSerializers.f_135029_);
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedTags(ModTags.Blocks.BLOCK_PROT_LIQUIDS, ModTags.Blocks.BLOCK_PROT_SNOW).build();
   }
}
