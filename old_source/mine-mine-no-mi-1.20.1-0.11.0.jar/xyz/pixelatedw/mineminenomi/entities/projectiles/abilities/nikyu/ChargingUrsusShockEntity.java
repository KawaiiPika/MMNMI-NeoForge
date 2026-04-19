package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class ChargingUrsusShockEntity extends Entity {
   protected static final EntityDataAccessor<Float> CHARGE;
   private LivingEntity owner;

   public ChargingUrsusShockEntity(EntityType<? extends ChargingUrsusShockEntity> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public ChargingUrsusShockEntity(Level worldIn) {
      super((EntityType)ModProjectiles.CHARGING_URSUS_SHOCK.get(), worldIn);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.owner == null) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         if (this.f_19797_ > 600) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         this.m_7678_(this.owner.m_20185_(), this.owner.m_20188_() + (double)(0.2F * this.getCharge()), this.owner.m_20189_(), this.owner.m_146908_(), 0.0F);
      }

   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(CHARGE, 0.0F);
   }

   public void setCharge(float value) {
      this.f_19804_.m_135381_(CHARGE, value);
   }

   public float getCharge() {
      return (Float)this.f_19804_.m_135370_(CHARGE);
   }

   public void setOwner(LivingEntity owner) {
      this.owner = owner;
   }

   protected void m_7378_(CompoundTag compound) {
   }

   protected void m_7380_(CompoundTag compound) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   static {
      CHARGE = SynchedEntityData.m_135353_(ChargingUrsusShockEntity.class, EntityDataSerializers.f_135029_);
   }
}
