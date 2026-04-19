package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class IceBlockPartisanProjectile extends NuProjectileEntity {
   public IceBlockPartisanProjectile(EntityType<? extends IceBlockPartisanProjectile> type, Level world) {
      super(type, world);
   }

   public IceBlockPartisanProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.ICE_BLOCK_PARTISAN.get(), world, player, ability);
      this.setDamage(9.0F);
      this.setMaxLife(40);
      super.setPhysical();
      this.addTickEvent(100, this::onTickEvent);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         FrostbiteEffect.addFrostbiteStacks(target, 2);
      }

   }

   private void onBlockImpactEvent(BlockHitResult result) {
      NuWorld.setBlockState((Entity)this.getOwner(), result.m_82425_(), Blocks.f_50568_.m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID);
   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.HIE.get());
            part.setLife(32);
            part.setAnimationSpeed(5);
            part.setRotation(new Vector3f(0.0F, 0.0F, 1.0F));
            part.setRotationSpeed(i % 2 == 0 ? 0.07F : -0.07F);
            part.setSize(1.5F);
            WyHelper.spawnParticles(part, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
