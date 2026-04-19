package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SagariNoRyuseiProjectile extends NuProjectileEntity {
   private float size;

   public SagariNoRyuseiProjectile(EntityType<? extends SagariNoRyuseiProjectile> type, Level world) {
      super(type, world);
   }

   public SagariNoRyuseiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.SAGARI_NO_RYUSEI.get(), world, player, ability);
      this.setDamage(200.0F);
      this.setArmorPiercing(0.25F);
      this.setMaxLife(256);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      float mult = this.size / 30.0F;
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 20.0F * mult);
         explosion.setStaticDamage(90.0F * mult);
         explosion.addRemovedBlocksToList();
         explosion.setDamageOwner(true);
         explosion.setFireAfterExplosion(true);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)((int)(20.0F * mult))));
         explosion.m_46061_();
         int size = 0;

         for(AbilityExplosion.RemovedBlock rec : explosion.getBlownStates()) {
            FallingBlockEntity entity = FallingBlockEntity.m_201971_(this.m_9236_(), rec.pos(), rec.state());
            AbilityHelper.setDeltaMovement(entity, WyHelper.randomWithRange(-1, 1) / (double)2.0F * (double)mult, ((double)0.75F + WyHelper.randomDouble()) * (double)mult, WyHelper.randomWithRange(-1, 1) / (double)2.0F * (double)mult);
            entity.f_31943_ = false;
            entity.f_31942_ = 1;
            ++size;
            if (size > 256) {
               break;
            }
         }

      });
   }

   private void onTickEvent() {
      float mult = this.size / 30.0F;
      if (1 > this.f_19797_) {
         this.m_20011_(this.m_20191_().m_82400_((double)mult));
      }

      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 25; ++i) {
            ParticleType<SimpleParticleData> particleToUse = this.f_19797_ % 2 == 0 ? (ParticleType)ModParticleTypes.MOKU.get() : (ParticleType)ModParticleTypes.MERA.get();
            double offsetX = WyHelper.randomDouble() * (double)5.0F * (double)mult;
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble() * (double)5.0F * (double)mult;
            SimpleParticleData data = new SimpleParticleData(particleToUse);
            data.setLife(20);
            data.setSize(7.0F * mult);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }

   public void setSize(float size) {
      this.size = size;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeFloat(this.size);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.size = buffer.readFloat();
   }
}
