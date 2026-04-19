package xyz.pixelatedw.mineminenomi.entities;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
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

public class ThrownSpearEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
   private static final EntityDataAccessor<Byte> ID_LOYALTY;
   private static final EntityDataAccessor<Boolean> ID_FOIL;
   private ItemStack itemStack;
   private float attackDamage;
   private boolean dealtDamage;
   public int clientSideReturnTickCount;

   public ThrownSpearEntity(EntityType<?> type, Level world) {
      super(type, world);
   }

   public ThrownSpearEntity(Player thrower, ItemStack stack) {
      super((EntityType)ModEntities.THROWN_SPEAR.get(), thrower, thrower.m_9236_());
      this.itemStack = stack.m_41777_();
      this.f_19804_.m_135381_(ID_LOYALTY, (byte)EnchantmentHelper.m_44928_(stack));
      this.f_19804_.m_135381_(ID_FOIL, stack.m_41790_());
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ID_LOYALTY, (byte)0);
      this.f_19804_.m_135372_(ID_FOIL, false);
   }

   public void m_8119_() {
      if (this.f_36704_ > 4) {
         this.dealtDamage = true;
      }

      Entity entity = this.m_19749_();
      if ((this.dealtDamage || this.m_36797_()) && entity != null) {
         int i = (Byte)this.f_19804_.m_135370_(ID_LOYALTY);
         if (i > 0 && !this.isAcceptibleReturnOwner()) {
            if (!this.m_9236_().f_46443_ && this.f_36705_ == Pickup.ALLOWED) {
               this.m_5552_(this.m_7941_(), 0.1F);
            }

            this.m_142687_(RemovalReason.DISCARDED);
         } else if (i > 0) {
            this.m_36790_(true);
            Vec3 vec = new Vec3(entity.m_20185_() - this.m_20185_(), entity.m_20188_() - this.m_20186_(), entity.m_20189_() - this.m_20189_());
            this.m_20343_(this.m_20185_(), this.m_20186_() + vec.f_82480_ * 0.015 * (double)i, this.m_20189_());
            if (this.m_9236_().f_46443_) {
               this.f_19791_ = this.m_20186_();
            }

            double d0 = 0.05 * (double)i;
            AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82490_(0.95).m_82549_(vec.m_82541_().m_82490_(d0)));
            if (this.clientSideReturnTickCount == 0) {
               this.m_5496_(SoundEvents.f_12516_, 10.0F, 1.0F);
            }

            ++this.clientSideReturnTickCount;
         }
      }

      super.m_8119_();
   }

   private boolean isAcceptibleReturnOwner() {
      Entity entity = this.m_19749_();
      if (entity != null && entity.m_6084_()) {
         return !(entity instanceof ServerPlayer) || !entity.m_5833_();
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

   @OnlyIn(Dist.CLIENT)
   public boolean isFoil() {
      return (Boolean)this.f_19804_.m_135370_(ID_FOIL);
   }

   @Nullable
   protected EntityHitResult m_6351_(Vec3 pStartVec, Vec3 pEndVec) {
      return this.dealtDamage ? null : super.m_6351_(pStartVec, pEndVec);
   }

   protected void m_5790_(EntityHitResult result) {
      Entity entity = result.m_82443_();
      float f = this.attackDamage;
      if (entity instanceof LivingEntity livingentity && !entity.m_9236_().f_46443_) {
         f += EnchantmentHelper.m_44833_(this.itemStack, livingentity.m_6336_());
      }

      Entity entity1 = this.m_19749_();
      DamageSource damagesource = this.m_9236_().m_269111_().m_269525_(this, (Entity)(entity1 == null ? this : entity1));
      this.dealtDamage = true;
      SoundEvent soundevent = SoundEvents.f_12514_;
      if (entity.m_6469_(damagesource, f)) {
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
      float f1 = 1.0F;
      Level var9 = this.m_9236_();
      if (var9 instanceof ServerLevel serverLevel) {
         if (this.m_9236_().m_46470_() && EnchantmentHelper.m_44936_(this.itemStack)) {
            BlockPos blockpos = entity.m_20183_();
            if (serverLevel.m_45527_(blockpos)) {
               LightningBolt bolt = (LightningBolt)EntityType.f_20465_.m_20615_(serverLevel);
               bolt.m_20219_(Vec3.m_82539_(blockpos));
               ServerPlayer var10001;
               if (entity1 instanceof ServerPlayer) {
                  ServerPlayer serverPlayer = (ServerPlayer)entity1;
                  var10001 = serverPlayer;
               } else {
                  var10001 = null;
               }

               bolt.m_20879_(var10001);
               serverLevel.m_7967_(bolt);
               soundevent = SoundEvents.f_12521_;
               f1 = 5.0F;
            }
         }
      }

      this.m_5496_(soundevent, f1, 1.0F);
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
      this.f_19804_.m_135381_(ID_LOYALTY, (byte)EnchantmentHelper.m_44928_(this.itemStack));
   }

   public void m_7380_(CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128365_("Item", this.itemStack.m_41739_(new CompoundTag()));
      pCompound.m_128379_("DealtDamage", this.dealtDamage);
   }

   public void m_6901_() {
      int i = (Byte)this.f_19804_.m_135370_(ID_LOYALTY);
      if (this.f_36705_ != Pickup.ALLOWED || i <= 0) {
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

   static {
      ID_LOYALTY = SynchedEntityData.m_135353_(ThrownSpearEntity.class, EntityDataSerializers.f_135027_);
      ID_FOIL = SynchedEntityData.m_135353_(ThrownSpearEntity.class, EntityDataSerializers.f_135035_);
   }
}
