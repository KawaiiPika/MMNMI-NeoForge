package xyz.pixelatedw.mineminenomi.entities;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class ChakramEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
   private ItemStack itemStack;
   private float attackDamage;
   private boolean dealtDamage;
   public int clientSideReturnTickCount;

   public ChakramEntity(EntityType<?> type, Level world) {
      super(type, world);
   }

   public ChakramEntity(LivingEntity thrower, ItemStack stack) {
      super((EntityType)ModEntities.CHAKRAM.get(), thrower, thrower.m_9236_());
      this.itemStack = stack.m_41777_();
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_8119_() {
      if (this.f_36704_ > 4) {
         this.dealtDamage = true;
      }

      Entity owner = this.m_19749_();
      if ((this.dealtDamage || this.m_36797_()) && owner != null) {
         if (!this.isAcceptibleReturnOwner()) {
            if (this.m_9236_() != null && !this.m_9236_().f_46443_ && this.f_36705_ == Pickup.ALLOWED) {
               this.m_5552_(this.m_7941_(), 0.1F);
            }

            this.m_142687_(RemovalReason.DISCARDED);
         } else {
            if (owner instanceof Mob && this.m_20280_(owner) < (double)3.5F && this.f_19797_ > 10) {
               this.m_142687_(RemovalReason.DISCARDED);
            }

            this.m_36790_(true);
            Vec3 vector3d = new Vec3(owner.m_20185_() - this.m_20185_(), owner.m_20188_() - this.m_20186_(), owner.m_20189_() - this.m_20189_());
            this.m_20343_(this.m_20185_(), this.m_20186_() + vector3d.f_82480_ * 0.015, this.m_20189_());
            if (this.m_9236_() != null && this.m_9236_().f_46443_) {
               this.f_19791_ = this.m_20186_();
            }

            double d0 = 0.1;
            AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82490_(0.95).m_82549_(vector3d.m_82541_().m_82490_(d0)));
            ++this.clientSideReturnTickCount;
         }
      }

      if (!this.m_20068_() && !this.m_36797_()) {
         Vec3 vector3d4 = this.m_20184_();
         this.m_20334_(vector3d4.f_82479_, vector3d4.f_82480_ + (double)0.04F, vector3d4.f_82481_);
      }

      super.m_8119_();
   }

   private boolean isAcceptibleReturnOwner() {
      Entity entity = this.m_19749_();
      if (entity != null && entity.m_6084_()) {
         if (entity instanceof ServerPlayer && !entity.m_5833_()) {
            return true;
         } else {
            return !entity.m_5833_();
         }
      } else {
         return false;
      }
   }

   public ItemStack m_7941_() {
      return this.itemStack.m_41777_();
   }

   public ItemStack getItemUsed() {
      return this.itemStack;
   }

   public void setAttackDamage(float damage) {
      this.attackDamage = damage;
   }

   @Nullable
   protected EntityHitResult m_6351_(Vec3 pStartVec, Vec3 pEndVec) {
      return this.dealtDamage ? null : super.m_6351_(pStartVec, pEndVec);
   }

   protected void m_5790_(EntityHitResult pResult) {
      Entity entity = pResult.m_82443_();
      float damage = this.attackDamage;
      if (entity instanceof LivingEntity livingentity && !entity.m_9236_().f_46443_) {
         damage += EnchantmentHelper.m_44833_(this.itemStack, livingentity.m_6336_());
      }

      Entity entity1 = this.m_19749_();
      DamageSource damagesource = this.m_269291_().m_269525_(this, (Entity)(entity1 == null ? this : entity1));
      this.dealtDamage = true;
      SoundEvent soundevent = SoundEvents.f_12514_;
      if (entity.m_6469_(damagesource, damage)) {
         if (entity.m_6095_() == EntityType.f_20566_) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity1 = (LivingEntity)entity;
            if (entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity1, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity1);
            }

            this.m_7761_(livingentity1);
         }
      }

      AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82542_(-0.01, -0.1, -0.01));
      this.m_5496_(soundevent, 1.0F, 1.5F);
   }

   protected SoundEvent m_7239_() {
      return SoundEvents.f_12515_;
   }

   public void m_6123_(Player pEntity) {
      Entity entity = this.m_19749_();
      if (entity == null || entity.m_20148_() == pEntity.m_20148_()) {
         super.m_6123_(pEntity);
      }

   }

   public void m_7378_(CompoundTag pCompound) {
      super.m_7378_(pCompound);
      if (pCompound.m_128425_("Item", 10)) {
         this.itemStack = ItemStack.m_41712_(pCompound.m_128469_("Item"));
      }

      this.dealtDamage = pCompound.m_128471_("DealtDamage");
   }

   public void m_7380_(CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128365_("Item", this.itemStack.m_41739_(new CompoundTag()));
      pCompound.m_128379_("DealtDamage", this.dealtDamage);
   }

   public void m_6901_() {
      if (this.f_36705_ != Pickup.ALLOWED) {
         super.m_6901_();
      }

   }

   protected float m_6882_() {
      return 0.99F;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6000_(double pX, double pY, double pZ) {
      return true;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeItemStack(this.itemStack, false);
      buffer.writeInt(this.m_19749_() != null ? this.m_19749_().m_19879_() : -1);
   }

   public void readSpawnData(FriendlyByteBuf data) {
      this.itemStack = data.m_130267_();
      int ownerId = data.readInt();
      if (ownerId >= 0) {
         this.m_5602_(this.m_9236_().m_6815_(ownerId));
      }

   }
}
