package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BaraFestivalEntity extends Entity implements TraceableEntity, IEntityAdditionalSpawnData {
   private LivingEntity owner;
   private LivingEntity target;

   public BaraFestivalEntity(EntityType<? extends BaraFestivalEntity> type, Level world) {
      super(type, world);
   }

   public BaraFestivalEntity(Level world, LivingEntity owner) {
      super((EntityType)ModProjectiles.BARA_FESTIVAL.get(), world);
      this.owner = owner;
      this.m_20242_(true);
   }

   protected void m_8097_() {
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_) {
         if (this.getOwner() != null && !this.getOwner().m_6084_()) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         if (this.target == null || !this.target.m_6084_()) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         this.m_6021_(this.target.m_20185_(), this.target.m_20186_(), this.target.m_20189_());
      }

      super.m_8119_();
   }

   public void setTarget(LivingEntity target) {
      this.target = target;
   }

   @Nullable
   public LivingEntity getOwner() {
      return this.owner;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6000_(double pX, double pY, double pZ) {
      return true;
   }

   protected void m_7378_(CompoundTag nbt) {
   }

   protected void m_7380_(CompoundTag nbt) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = this.owner != null;
      buffer.writeBoolean(hasOwner);
      if (hasOwner) {
         buffer.writeInt(this.owner.m_19879_());
      }

   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = buffer.readBoolean();
      if (hasOwner) {
         int ownerId = buffer.readInt();
         Entity var5 = this.m_9236_().m_6815_(ownerId);
         if (var5 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity)var5;
            this.owner = owner;
         }
      }

   }
}
