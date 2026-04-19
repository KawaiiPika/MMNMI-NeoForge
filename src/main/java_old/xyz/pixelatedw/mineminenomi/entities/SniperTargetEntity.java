package xyz.pixelatedw.mineminenomi.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SniperTargetEntity extends Entity {
   public SniperTargetEntity(EntityType<? extends SniperTargetEntity> type, Level world) {
      super(type, world);
   }

   public SniperTargetEntity(Level world) {
      this((EntityType)ModEntities.SNIPER_TARGET.get(), world);
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (this.m_6673_(source)) {
         return false;
      } else {
         if (!this.m_213877_() && !this.m_9236_().f_46443_) {
            this.m_6074_();
            this.m_5834_();
         }

         return true;
      }
   }

   public boolean m_6087_() {
      return this.m_6084_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_9236_().f_46443_ || !this.m_20096_() && !this.m_20069_() && !this.m_20077_()) {
         if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_((double)0.0F, -0.001, (double)0.0F));
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.98));
      } else {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag tag) {
   }

   protected void m_7380_(CompoundTag tag) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
