package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBlockAvalancheProjectile;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class RaigoProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
   private static final EntityDataAccessor<Float> SIZE;
   private static final float EXPLOSION_RADIUS = 0.7F;
   private static final float SHOCKWAVE_RADIUS = 0.85F;
   private boolean dealtAOE = false;
   private boolean closeToFloor = false;

   public RaigoProjectile(EntityType<? extends RaigoProjectile> type, Level world) {
      super(type, world);
   }

   public RaigoProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.RAIGO.get(), world, player, ability);
      this.setDamage(100.0F);
      this.setMaxLife(256);
      this.setEntityCollisionSize((double)4.0F);
      this.setPassThroughEntities();
      this.setPassThroughBlocks();
      this.setArmorPiercing(0.75F);
      this.setUnavoidable();
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   public void onBlockImpactEvent(BlockHitResult result) {
      int explosionRadius = (int)(0.7F * this.getSize());
      int shockwaveRadius = (int)(0.85F * this.getSize());
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID).setSize(explosionRadius);
      placer.generate(this.m_9236_(), this.m_20183_().m_6625_(explosionRadius / 8), BlockGenerators.SPHERE);
      if (!this.dealtAOE) {
         List<Entity> list = WyHelper.<Entity>getNearbyEntities(this.m_20182_(), this.m_9236_(), (double)shockwaveRadius, (Predicate)null, Entity.class);
         list.remove(this.getOwner());

         for(Entity target : list) {
            if (target instanceof Projectile || target instanceof AbstractArrow) {
               target.m_146870_();
            }

            if (target instanceof LivingEntity) {
               target.m_6469_(ModDamageSources.getInstance().ability(this.getOwner(), ((IAbility)this.getParent().get()).getCore()), this.getDamage());
               Vec3 speed = target.m_20154_().m_82490_((double)-1.0F).m_82542_((double)5.0F, (double)0.0F, (double)5.0F);
               AbilityHelper.setDeltaMovement(target, speed.f_82479_, (double)1.0F, speed.f_82481_);
            }
         }

         this.dealtAOE = true;
      }

   }

   private void onTickEvent() {
      this.setDamage(2.0F * this.getSize());
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 25; ++i) {
            ParticleType<SimpleParticleData> goroPrticle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO.get() : (ParticleType)ModParticleTypes.GORO_YELLOW.get();
            ParticleType<SimpleParticleData> goro2Particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO2.get() : (ParticleType)ModParticleTypes.GORO2_YELLOW.get();
            ParticleType<SimpleParticleData> particleToUse = this.f_19797_ % 2 == 0 ? goro2Particle : goroPrticle;
            double offsetX = WyHelper.randomDouble() * (double)5.0F;
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble() * (double)5.0F;
            SimpleParticleData data = new SimpleParticleData(particleToUse);
            data.setLife(20);
            data.setSize(7.0F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }

         this.m_19915_(0.0F, 90.0F);
         if (!this.closeToFloor) {
            HitResult mop = WyHelper.rayTraceBlocks(this, (double)30.0F);
            if (mop.m_6662_().equals(Type.BLOCK)) {
               this.setMaxLife(16);
               this.closeToFloor = true;
            }
         }

         if (this.f_19797_ % 5 == 0) {
            for(int j = 0; j < 10; ++j) {
               float boltLength = (float)WyHelper.randomWithRange(36, 50);
               RaigoLightningProjectile bolt = new RaigoLightningProjectile(this.m_9236_(), this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)WyHelper.randomWithRange(0, 360), (float)WyHelper.randomWithRange(-90, 90), boltLength, (IAbility)this.getParent().get());
               this.m_9236_().m_7967_(bolt);
            }
         }
      }

   }

   public void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SIZE, 1.0F);
   }

   public float getSize() {
      return (Float)this.m_20088_().m_135370_(SIZE);
   }

   public void setSize(float size) {
      this.m_20088_().m_135381_(SIZE, Math.min(size, 80.0F));
   }

   public void increaseSize(float increaseAmount) {
      float size = this.getSize() + increaseAmount * 10.0F;
      this.setSize(size);
      this.setEntityCollisionSize((double)size);
      this.setBlockCollisionSize((double)size);
   }

   static {
      SIZE = SynchedEntityData.m_135353_(IceBlockAvalancheProjectile.class, EntityDataSerializers.f_135029_);
   }
}
