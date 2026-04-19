package xyz.pixelatedw.mineminenomi.entities.vehicles;

import com.google.common.collect.Lists;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class UnicycleEntity extends Entity {
   private static final float MOVE_SPEED = 0.25F;
   private static final float BACKWARDS_MOVE_SPEED = -0.25F;
   private static final float YAW_AIM_SPEED = 1.0F;
   private static final float MOVEMENT_FRICTION = 0.55F;
   private static final float ROTATION_FRICTION = 0.55F;
   private static final float GRAVITY = -0.098F;
   private static final float DEFAULT_COOLDOWN = 10.0F;
   private static final byte HURT_EVENT = 100;
   private float deltaYaw;
   private float deltaPitch;
   private int lerpSteps;
   private double lerpX;
   private double lerpY;
   private double lerpZ;
   private double lerpYRot;
   private double lerpXRot;
   private float cooldown;
   private float damage;
   private int hurtTime;

   public UnicycleEntity(Level level) {
      this((EntityType)ModEntities.UNICYCLE.get(), level);
   }

   public UnicycleEntity(EntityType<?> type, Level level) {
      super(type, level);
      this.f_19850_ = true;
      this.m_274367_(1.0F);
   }

   protected void m_8097_() {
   }

   public float getEyeHeightForge(Pose pose, EntityDimensions size) {
      return size.f_20378_;
   }

   public boolean m_7337_(Entity entity) {
      return canVehicleCollide(this, entity);
   }

   public static boolean canVehicleCollide(Entity vehicle, Entity entity) {
      if (entity.m_20186_() < vehicle.m_20186_()) {
         return false;
      } else {
         return (entity.m_5829_() || entity.m_6094_()) && !vehicle.m_20365_(entity);
      }
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_6087_() {
      return this.m_6084_();
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public double m_6048_() {
      return 0.55;
   }

   public Direction m_6374_() {
      return this.m_6350_().m_122427_();
   }

   private void tickLerp() {
      if (this.m_6109_()) {
         this.lerpSteps = 0;
         this.m_217006_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

      if (this.lerpSteps > 0) {
         double d0 = this.m_20185_() + (this.lerpX - this.m_20185_()) / (double)this.lerpSteps;
         double d1 = this.m_20186_() + (this.lerpY - this.m_20186_()) / (double)this.lerpSteps;
         double d2 = this.m_20189_() + (this.lerpZ - this.m_20189_()) / (double)this.lerpSteps;
         double d3 = Mth.m_14175_(this.lerpYRot - (double)this.m_146908_());
         this.m_146922_((float)((double)this.m_146908_() + d3 / (double)this.lerpSteps));
         this.m_146926_((float)((double)this.m_146909_() + (this.lerpXRot - (double)this.m_146909_()) / (double)this.lerpSteps));
         --this.lerpSteps;
         this.m_6034_(d0, d1, d2);
         this.m_19915_(this.m_146908_(), this.m_146909_());
      }

   }

   public void m_8119_() {
      super.m_8119_();
      this.tickLerp();
      if (this.cooldown > 0.0F) {
         --this.cooldown;
      }

      if (this.getHurtTime() > 0) {
         this.setHurtTime(this.getHurtTime() - 1);
      }

      if (this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      double gravity = this.m_20068_() ? (double)0.0F : (double)-0.098F;
      Vec3 motion = this.m_20184_();
      AbilityHelper.setDeltaMovement(this, motion.f_82479_ * (double)0.55F, motion.f_82480_ + gravity, motion.f_82481_ * (double)0.55F);
      if (this.m_6109_()) {
         this.deltaYaw *= 0.55F;
         this.deltaPitch *= 0.55F;
         if (this.m_6688_() != null) {
            this.controlVehicle();
         }
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
      if (!this.m_20096_()) {
         for(Entity entity : Lists.newArrayList(this.m_9236_().m_45933_(this, this.m_20191_().m_82386_((double)0.0F, (double)-1.0F, (double)0.0F)))) {
            entity.m_6469_(this.m_269291_().m_269230_(entity), 20.0F);
         }
      }

   }

   private void controlVehicle() {
      if (this.m_20160_()) {
         Entity passanger = this.m_6688_();
         if (passanger == null || !(passanger instanceof LivingEntity)) {
            return;
         }

         LivingEntity player = (LivingEntity)passanger;
         if (player.f_20900_ > 0.0F) {
            --this.deltaYaw;
         } else if (player.f_20900_ < 0.0F) {
            ++this.deltaYaw;
         }

         float speed = 0.0F;
         if (player.f_20902_ > 0.0F) {
            speed = 0.25F;
         } else if (player.f_20902_ < 0.0F) {
            speed = -0.25F;
         }

         this.m_146922_(this.m_146908_() + this.deltaYaw);
         this.m_146926_(this.m_146909_() + this.deltaPitch);
         AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82520_((double)(Mth.m_14031_(-this.m_146908_() * ((float)Math.PI / 180F)) * speed), (double)0.0F, (double)(Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)) * speed)));
      }

   }

   public void m_19956_(Entity passenger, Entity.MoveFunction moveFunc) {
      if (this.m_20363_(passenger)) {
         float f1 = (float)((this.m_213877_() ? (double)0.01F : this.m_6048_()) + passenger.m_6049_());
         passenger.m_6034_(this.m_20185_(), this.m_20186_() + (double)f1, this.m_20189_());
      }

   }

   public Vec3 m_7688_(LivingEntity entity) {
      Vec3 lookVec = this.m_20154_().m_82548_();
      return entity.m_20182_().m_82520_((double)0.0F, (double)0.5F, (double)0.0F).m_82549_(lookVec);
   }

   @Nullable
   public LivingEntity m_6688_() {
      Entity entity = this.m_146895_();
      if (entity instanceof LivingEntity livingentity) {
         return livingentity;
      } else {
         return null;
      }
   }

   public InteractionResult m_6096_(Player player, InteractionHand hand) {
      if (hand.equals(InteractionHand.OFF_HAND)) {
         return InteractionResult.PASS;
      } else if (!this.m_9236_().f_46443_) {
         if (player.m_20329_(this)) {
            this.cooldown = 10.0F;
            return InteractionResult.CONSUME;
         } else {
            return InteractionResult.PASS;
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (this.m_6673_(source)) {
         return false;
      } else if (!this.m_9236_().f_46443_ && !this.m_213877_()) {
         boolean var10000;
         label32: {
            this.m_9236_().m_7605_(this, (byte)100);
            this.setDamage(this.getDamage() + amount * 10.0F);
            this.m_5834_();
            Entity var5 = source.m_7639_();
            if (var5 instanceof Player) {
               Player player = (Player)var5;
               if (player.m_150110_().f_35937_) {
                  var10000 = true;
                  break label32;
               }
            }

            var10000 = false;
         }

         boolean flag = var10000;
         if (flag || this.getDamage() > 20.0F) {
            if (!flag && this.m_9236_().m_46469_().m_46207_(GameRules.f_46137_)) {
               this.m_19983_(((Item)ModItems.UNICYCLE.get()).m_5456_().m_7968_());
            }

            this.m_142687_(RemovalReason.KILLED);
         }

         return true;
      } else {
         return true;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 100:
            this.hurtTime = 10;
         default:
            super.m_7822_(id);
      }
   }

   protected void clampRotation(Entity entity) {
      entity.m_5618_(this.m_146908_());
      float f = Mth.m_14177_(entity.m_146908_() - this.m_146908_());
      float f1 = Mth.m_14036_(f, -105.0F, 105.0F);
      entity.f_19859_ += f1 - f;
      entity.m_146922_(entity.m_146908_() + f1 - f);
      entity.m_5616_(entity.m_146908_());
   }

   public ItemStack getPickedResult(HitResult target) {
      return ((Item)ModItems.UNICYCLE.get()).m_7968_();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7340_(Entity pEntityToUpdate) {
      this.clampRotation(pEntityToUpdate);
   }

   public float getDamage() {
      return this.damage;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public int getHurtTime() {
      return this.hurtTime;
   }

   public void setHurtTime(int hurtTime) {
      this.hurtTime = hurtTime;
   }

   protected void m_7378_(CompoundTag nbt) {
   }

   protected void m_7380_(CompoundTag nbt) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
