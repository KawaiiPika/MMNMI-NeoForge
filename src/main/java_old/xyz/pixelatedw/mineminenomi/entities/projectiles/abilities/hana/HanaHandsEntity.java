package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HanaHandsEntity extends Entity implements TraceableEntity, IEntityAdditionalSpawnData {
   private int warmupDelayTicks;
   private int lifeTicks;
   private float damage;
   private boolean sentEvent;
   private boolean clientSideAttackStarted;
   private LivingEntity owner;
   private LivingEntity target;
   private UUID casterUuid;
   private IAbility parentAbility;
   private HandsKind kind;

   public HanaHandsEntity(EntityType<? extends HanaHandsEntity> type, Level world) {
      super(type, world);
      this.warmupDelayTicks = 30;
      this.lifeTicks = 22;
   }

   public HanaHandsEntity(Level world, LivingEntity owner, IAbility ability) {
      this((EntityType)ModProjectiles.HANA_HANDS.get(), world);
      this.owner = owner;
      this.parentAbility = ability;
   }

   protected void m_8097_() {
   }

   @Nullable
   public Entity m_19749_() {
      if (this.owner == null && this.casterUuid != null) {
         Level var2 = this.m_9236_();
         if (var2 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var2;
            Entity entity = serverLevel.m_8791_(this.casterUuid);
            if (entity instanceof LivingEntity) {
               this.owner = (LivingEntity)entity;
            }
         }
      }

      return this.owner;
   }

   public void setTarget(LivingEntity target) {
      this.target = target;
      this.m_6034_(target.m_20185_(), target.m_20186_(), target.m_20189_());
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   public void setWarmup(int warmup) {
      this.warmupDelayTicks = warmup;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public void setClutch() {
      this.kind = HanaHandsEntity.HandsKind.CLUTCH;
   }

   public void setSlap() {
      this.kind = HanaHandsEntity.HandsKind.SLAP;
   }

   public void setTwist() {
      this.kind = HanaHandsEntity.HandsKind.TWIST;
   }

   public HandsKind getHandsType() {
      return this.kind;
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Warmup", this.warmupDelayTicks);
      compound.m_128405_("Type", this.kind.ordinal());
      if (this.casterUuid != null) {
         compound.m_128362_("OwnerUUID", this.casterUuid);
      }

   }

   protected void m_7378_(CompoundTag compound) {
      this.warmupDelayTicks = compound.m_128451_("Warmup");
      this.kind = HanaHandsEntity.HandsKind.values()[compound.m_128451_("Type")];
      if (compound.m_128403_("OwnerUUID")) {
         this.casterUuid = compound.m_128342_("OwnerUUID");
      }

   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.kind.ordinal());
      boolean hasOwnerAndTarget = this.owner != null && this.target != null;
      buffer.writeBoolean(hasOwnerAndTarget);
      if (hasOwnerAndTarget) {
         buffer.writeInt(this.owner.m_19879_());
         buffer.writeInt(this.target.m_19879_());
      }

   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.kind = HanaHandsEntity.HandsKind.values()[buffer.readInt()];
      boolean hasOwnerAndTarget = buffer.readBoolean();
      if (hasOwnerAndTarget) {
         int ownerId = buffer.readInt();
         Entity var5 = this.m_9236_().m_6815_(ownerId);
         if (var5 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity)var5;
            this.owner = owner;
         }

         int targetId = buffer.readInt();
         Entity var6 = this.m_9236_().m_6815_(targetId);
         if (var6 instanceof LivingEntity) {
            LivingEntity target = (LivingEntity)var6;
            this.setTarget(target);
         }
      }

   }

   public void m_8119_() {
      if (this.target != null && this.target.m_6084_()) {
         this.m_7678_(this.target.m_20185_(), this.target.m_20186_(), this.target.m_20189_(), this.target.m_146908_(), this.target.m_146909_());
      }

      super.m_8119_();
      if (this.m_9236_().f_46443_) {
         if (this.clientSideAttackStarted) {
            --this.lifeTicks;
            if (this.lifeTicks == 14) {
               for(int i = 0; i < 12; ++i) {
                  double x = this.m_20185_() + (this.f_19796_.m_188500_() * (double)2.0F - (double)1.0F) * (double)this.m_20205_() * (double)0.5F;
                  double y = this.m_20186_() + 0.05 + this.f_19796_.m_188500_();
                  double z = this.m_20189_() + (this.f_19796_.m_188500_() * (double)2.0F - (double)1.0F) * (double)this.m_20205_() * (double)0.5F;
                  double motionX = (this.f_19796_.m_188500_() * (double)2.0F - (double)1.0F) * 0.3;
                  double motionY = 0.3 + this.f_19796_.m_188500_() * 0.3;
                  double motionZ = (this.f_19796_.m_188500_() * (double)2.0F - (double)1.0F) * 0.3;
                  this.m_9236_().m_7106_(ParticleTypes.f_123797_, x, y + (double)1.0F, z, motionX, motionY, motionZ);
               }
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -8 && this.target != null) {
            this.damage(this.target);
         }

         if (!this.sentEvent) {
            this.m_9236_().m_7605_(this, (byte)4);
            this.sentEvent = true;
         }

         if (--this.lifeTicks < 0) {
            this.m_146870_();
         }
      }

   }

   private void damage(LivingEntity target) {
      Entity owner = this.m_19749_();
      if (target.m_6084_() && !target.m_20147_() && owner != null && target != owner && owner instanceof LivingEntity livingOwner) {
         this.parentAbility.getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> comp.hurtTarget(livingOwner, target, this.damage));
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      super.m_7822_(id);
      if (id == 4) {
         this.clientSideAttackStarted = true;
         if (!this.m_20067_()) {
            this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), (SoundEvent)ModSounds.SNAP_SFX.get(), this.m_5720_(), 2.0F, this.f_19796_.m_188501_() * 0.2F + 0.85F, false);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public float getAnimationProgress(float partialTicks) {
      if (!this.clientSideAttackStarted) {
         return 0.0F;
      } else {
         int i = this.lifeTicks - 2;
         return i <= 0 ? 1.0F : 1.0F - ((float)i - partialTicks) / 20.0F;
      }
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public static enum HandsKind {
      CLUTCH,
      SLAP,
      TWIST;

      // $FF: synthetic method
      private static HandsKind[] $values() {
         return new HandsKind[]{CLUTCH, SLAP, TWIST};
      }
   }
}
