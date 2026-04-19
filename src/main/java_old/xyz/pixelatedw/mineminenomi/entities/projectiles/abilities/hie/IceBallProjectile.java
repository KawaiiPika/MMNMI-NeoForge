package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class IceBallProjectile extends NuProjectileEntity {
   public IceBallProjectile(EntityType<? extends IceBallProjectile> type, Level world) {
      super(type, world);
   }

   public IceBallProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.ICE_BALL.get(), world, player, ability);
      this.setDamage(25.0F);
      this.setMaxLife(32);
      super.setPhysical();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         FrostbiteEffect.addFrostbiteStacks(target, 6);
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 0));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 100, 0));
      }

      this.createSphere(result.m_82443_().m_20183_());
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      this.createSphere(result.m_82425_());
   }

   private void createSphere(BlockPos pos) {
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50568_.m_49966_()).setSize(5).setHollow().setThickness(3).setRule(DefaultProtectionRules.AIR_FOLIAGE_LIQUID);
      placer.generate(this.m_9236_(), pos, BlockGenerators.SPHERE);
   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 5; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)1.5F;
            double offsetY = WyHelper.randomDouble() / (double)1.5F;
            double offsetZ = WyHelper.randomDouble() / (double)1.5F;
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.HIE.get());
            part.setLife(14);
            part.setAnimationSpeed(2);
            part.setRotation(new Vector3f(0.0F, 0.0F, 1.0F));
            part.setRotationSpeed(i % 2 == 0 ? 0.07F : -0.07F);
            part.setSize(1.5F);
            WyHelper.spawnParticles(part, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
