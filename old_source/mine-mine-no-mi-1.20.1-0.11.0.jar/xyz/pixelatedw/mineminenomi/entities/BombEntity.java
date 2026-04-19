package xyz.pixelatedw.mineminenomi.entities;

import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BombEntity extends Entity implements TraceableEntity {
   private final TargetPredicate targetPredicate;
   private final LivingEntity owner;
   private int fuseTime = 300;
   private boolean canDestroyBlocks = false;
   private boolean explodeOnImpact = false;

   public BombEntity(EntityType<? extends BombEntity> type, Level world) {
      super(type, world);
      this.owner = null;
      this.targetPredicate = (new TargetPredicate()).selector(Predicates.alwaysTrue());
   }

   public BombEntity(Level world, LivingEntity owner) {
      super((EntityType)ModEntities.BOMB.get(), world);
      this.owner = owner;
      this.targetPredicate = (new TargetPredicate()).selector((entity) -> {
         if (entity.equals(owner)) {
            return false;
         } else if (entity instanceof BombEntity) {
            return false;
         } else {
            return !ModEntityPredicates.getFriendlyFactions(owner).test(entity);
         }
      });
   }

   public boolean m_7337_(Entity entity) {
      return !(entity instanceof BombEntity);
   }

   public void setFuseTime(int fuseTime) {
      this.fuseTime = fuseTime;
   }

   public void setDestroyBlocks() {
      this.canDestroyBlocks = true;
   }

   public void setExplodeOnImpact() {
      this.explodeOnImpact = true;
   }

   public void m_8119_() {
      Level level = super.m_9236_();
      if (level != null) {
         this.f_19854_ = this.m_20185_();
         this.f_19855_ = this.m_20186_();
         this.f_19856_ = this.m_20189_();
         if (this.f_19797_ % 3 == 0) {
            level.m_7106_(ParticleTypes.f_123744_, this.m_20185_(), this.m_20186_() + (double)1.0F, this.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F);
         }

         if (!level.f_46443_) {
            this.m_6478_(MoverType.SELF, this.m_20184_());
            if (!this.m_20068_()) {
               AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82520_((double)0.0F, (double)-0.098F, (double)0.0F));
               AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82542_(0.98, 0.98, 0.98));
               if (this.f_19863_ || this.f_19862_) {
                  AbilityHelper.setDeltaMovement(this, Vec3.f_82478_);
                  if (this.explodeOnImpact) {
                     this.m_6074_();
                  }
               }
            }

            if (this.f_19797_ % 10 == 0) {
               List<LivingEntity> targets = TargetHelper.<LivingEntity>getEntitiesInArea(level, (LivingEntity)null, this.m_20183_(), (double)2.0F, this.targetPredicate, LivingEntity.class);
               if (targets.size() > 0) {
                  this.m_6074_();
               }
            }

            if (this.f_19797_ >= this.fuseTime) {
               this.m_6074_();
            }
         }

      }
   }

   public void m_6074_() {
      Level level = super.m_9236_();
      if (level != null) {
         if (!level.f_46443_) {
            AbilityExplosion explosion = new AbilityExplosion(this, (IAbility)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), 4.0F);
            explosion.setExplosionSound(true);
            explosion.setDamageOwner(false);
            explosion.setDestroyBlocks(this.canDestroyBlocks);
            explosion.setFireAfterExplosion(false);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
            explosion.setDamageEntities(true);
            explosion.m_46061_();
         }

         super.m_6074_();
      }
   }

   protected void m_8097_() {
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_5829_() {
      return true;
   }

   protected void m_7378_(CompoundTag compound) {
   }

   protected void m_7380_(CompoundTag compound) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @Nullable
   public Entity m_19749_() {
      return this.owner;
   }
}
