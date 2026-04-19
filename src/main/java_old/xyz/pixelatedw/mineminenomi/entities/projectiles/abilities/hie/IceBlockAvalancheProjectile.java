package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.IFlexibleSizeProjectile;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class IceBlockAvalancheProjectile extends NuProjectileEntity implements IFlexibleSizeProjectile {
   private static final EntityDataAccessor<Float> SIZE;
   public boolean finalized = false;

   public IceBlockAvalancheProjectile(EntityType<? extends IceBlockAvalancheProjectile> type, Level world) {
      super(type, world);
   }

   public IceBlockAvalancheProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.ICE_BLOCK_AVALANCHE.get(), world, player, ability);
      this.setDamage(50.0F);
      this.setMaxLife(150);
      this.setPassThroughEntities();
      super.setPhysical();
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      float mult = this.getSize() / 6.0F;
      this.m_20011_(this.m_20191_().m_82400_((double)mult));
      this.setEntityCollisionSize((double)mult);
      if (!this.finalized) {
         this.setSize(this.getSize() + 0.4F);
      }

   }

   public void onBlockImpactEvent(BlockHitResult result) {
      if (!this.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ICE_BLOCK_AVALANCHE.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }

   public void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SIZE, 1.0F);
   }

   public void setSize(float size) {
      this.m_20088_().m_135381_(SIZE, Math.min(size, 50.0F));
   }

   public float getSize() {
      return (Float)this.f_19804_.m_135370_(SIZE);
   }

   static {
      SIZE = SynchedEntityData.m_135353_(IceBlockAvalancheProjectile.class, EntityDataSerializers.f_135029_);
   }
}
