package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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

public class IceBlockPheasantProjectile extends NuProjectileEntity {
   public IceBlockPheasantProjectile(EntityType<? extends IceBlockPheasantProjectile> type, Level world) {
      super(type, world);
   }

   public IceBlockPheasantProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.ICE_BLOCK_PHEASANT.get(), world, player, ability);
      this.setDamage(45.0F);
      this.setArmorPiercing(1.0F);
      this.setPassThroughEntities();
      this.setMaxLife(60);
      super.setPhysical();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         FrostbiteEffect.addFrostbiteStacks(target, 6);
      }

   }

   private void onTickEvent() {
      BlockPos pos = null;

      for(int j = 1; pos == null; ++j) {
         BlockState state = this.m_9236_().m_8055_(this.m_20183_().m_6625_(j));
         if (state.m_60815_()) {
            pos = this.m_20183_().m_6625_(j);
            break;
         }

         if (j > 2) {
            break;
         }
      }

      if (pos != null) {
         int size = 2 + 4 * (this.getMaxLife() - this.getLife()) / this.getMaxLife();
         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50568_.m_49966_()).setSize(size).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID);
         placer.generate(this.m_9236_(), pos, BlockGenerators.SPHERE);
      }

      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 5; ++i) {
            double offsetX = WyHelper.randomDouble();
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble();
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
