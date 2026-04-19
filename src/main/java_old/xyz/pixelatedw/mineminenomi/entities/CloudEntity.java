package xyz.pixelatedw.mineminenomi.entities;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class CloudEntity extends Entity implements IEntityAdditionalSpawnData, TraceableEntity {
   private int life = 100;
   private UUID ownerUUID;
   private LivingEntity owner;
   private EntityDimensions size = null;

   public CloudEntity(EntityType<? extends CloudEntity> type, Level world) {
      super(type, world);
   }

   public CloudEntity(EntityType<? extends CloudEntity> type, Level world, LivingEntity owner) {
      super(type, world);
      this.setOwner(owner);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.owner == null) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         if (this.life <= 0) {
            this.m_142687_(RemovalReason.DISCARDED);
         }

         --this.life;
      }

   }

   public void setOwner(LivingEntity owner) {
      this.owner = owner;
      if (this.owner != null) {
         this.ownerUUID = owner.m_20148_();
      }

   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null) {
         Level var2 = this.m_9236_();
         if (var2 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var2;
            Entity entity = serverLevel.m_8791_(this.ownerUUID);
            if (entity instanceof LivingEntity) {
               this.owner = (LivingEntity)entity;
            }
         }
      }

      return this.owner;
   }

   public int getLife() {
      return this.life;
   }

   public void setLife(int life) {
      this.life = life;
   }

   public void setSize(EntityDimensions size) {
      this.size = size;
      this.m_6210_();
   }

   public EntityDimensions m_6972_(Pose pose) {
      return this.size == null ? super.m_6972_(pose) : this.size;
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag compound) {
   }

   protected void m_7380_(CompoundTag compound) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = this.getOwner() != null;
      buffer.writeBoolean(hasOwner);
      if (hasOwner) {
         buffer.writeInt(this.getOwner().m_19879_());
      }

      buffer.writeFloat(this.size != null ? this.size.f_20377_ : this.m_6095_().m_20680_().f_20377_);
      buffer.writeFloat(this.size != null ? this.size.f_20378_ : this.m_6095_().m_20680_().f_20378_);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = buffer.readBoolean();
      if (hasOwner) {
         int ownerId = buffer.readInt();
         Entity var5 = this.m_9236_().m_6815_(ownerId);
         if (var5 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity)var5;
            this.setOwner(owner);
         }
      }

      float width = buffer.readFloat();
      float height = buffer.readFloat();
      EntityDimensions size = EntityDimensions.m_20398_(width, height);
      this.setSize(size);
   }
}
