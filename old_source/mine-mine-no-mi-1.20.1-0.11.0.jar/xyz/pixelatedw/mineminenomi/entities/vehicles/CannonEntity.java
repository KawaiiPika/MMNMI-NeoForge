package xyz.pixelatedw.mineminenomi.entities.vehicles;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.IVehicleAltMode;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class CannonEntity extends Entity implements IVehicleAltMode {
   private static final EntityDataAccessor<Byte> MODE;
   private static final float MOVE_SPEED = 0.05F;
   private static final float BACKWARDS_MOVE_SPEED = -0.03F;
   private static final float YAW_AIM_SPEED = 1.0F;
   private static final float PITCH_AIM_SPEED = 1.0F;
   private static final float MOVEMENT_FRICTION = 0.1F;
   private static final float ROTATION_FRICTION = 0.1F;
   private static final float GRAVITY = -0.4F;
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

   public CannonEntity(Level level) {
      this((EntityType)ModEntities.CANNON.get(), level);
   }

   public CannonEntity(EntityType<?> type, Level level) {
      super(type, level);
      this.m_274367_(0.6F);
      this.f_19850_ = true;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(MODE, (byte)0);
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
      return 0.35;
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
      Level level = super.m_9236_();
      if (level != null) {
         this.tickLerp();
         if (this.cooldown > 0.0F) {
            --this.cooldown;
         }

         Entity passanger = this.m_6688_();
         if (!level.f_46443_ && passanger != null && passanger instanceof LivingEntity) {
            LivingEntity rider = (LivingEntity)passanger;
            if (this.canShoot(rider)) {
               this.shootCannonball(rider);
            }
         }

         if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
         }

         if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
         }

         double gravity = this.m_20068_() ? (double)0.0F : (double)-0.4F;
         Vec3 motion = this.m_20184_();
         AbilityHelper.setDeltaMovement(this, motion.f_82479_ * (double)0.1F, motion.f_82480_ + gravity, motion.f_82481_ * (double)0.1F);
         if (this.m_6109_()) {
            this.deltaYaw *= 0.1F;
            this.deltaPitch *= 0.1F;
            if (this.m_6688_() != null) {
               this.controlVehicle();
            }
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
         if (!this.m_20096_()) {
            for(Entity entity : Lists.newArrayList(level.m_45933_(this, this.m_20191_().m_82386_((double)0.0F, (double)-1.0F, (double)0.0F)))) {
               entity.m_6469_(this.m_269291_().m_269230_(entity), 20.0F);
            }
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
         if (this.isMoveMode()) {
            if (player.f_20902_ > 0.0F) {
               speed = 0.05F;
            } else if (player.f_20902_ < 0.0F) {
               speed = -0.03F;
            }
         } else if (this.isAimMode()) {
            if (player.f_20902_ > 0.0F && this.m_146909_() > -50.0F) {
               --this.deltaPitch;
            } else if (player.f_20902_ < 0.0F && this.m_146909_() < 0.0F) {
               ++this.deltaPitch;
            }
         }

         this.m_146922_(this.m_146908_() + this.deltaYaw);
         this.m_146926_(this.m_146909_() + this.deltaPitch);
         AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82520_((double)(Mth.m_14031_(-this.m_146908_() * ((float)Math.PI / 180F)) * speed), (double)0.0F, (double)(Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)) * speed)));
      }

   }

   public boolean canShoot(LivingEntity rider) {
      if (this.m_20160_()) {
         boolean canShoot = false;
         boolean hasInfiniteAmmo = false;
         ItemStack cannonballStack = ItemStack.f_41583_;
         int cannonballCount = 0;
         ItemStack gunpowderStack = ItemStack.f_41583_;
         int gunpowderCount = 0;
         if (rider instanceof Player) {
            Player player = (Player)rider;
            hasInfiniteAmmo = player.m_150110_().f_35937_;
         } else {
            hasInfiniteAmmo = true;
         }

         if (rider.f_20913_ == 1 && this.cooldown <= 0.0F) {
            if (hasInfiniteAmmo) {
               canShoot = true;
            } else {
               List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(rider);

               for(int j = 0; j < inventory.size(); ++j) {
                  ItemStack itemstack = (ItemStack)inventory.get(j);
                  if (itemstack.m_41720_().equals(ModItems.CANNON_BALL.get())) {
                     cannonballStack = itemstack;
                     cannonballCount += itemstack.m_41613_();
                  }
               }

               if (cannonballCount <= 0 || cannonballStack.m_41619_()) {
                  rider.m_213846_(ModI18n.INFO_NEEDS_CANNONBALL_LOADED);
                  return false;
               }

               for(int j = 0; j < inventory.size(); ++j) {
                  ItemStack itemstack = (ItemStack)inventory.get(j);
                  if (itemstack.m_41720_().equals(Items.f_42403_)) {
                     gunpowderStack = itemstack;
                     gunpowderCount += itemstack.m_41613_();
                  }
               }

               if (gunpowderCount <= 0 || gunpowderStack.m_41619_()) {
                  rider.m_213846_(ModI18n.INFO_NEEDS_GUNPOWDER_LOADED);
                  return false;
               }

               if (cannonballCount > 0 && gunpowderCount > 0 && !cannonballStack.m_41619_() && !gunpowderStack.m_41619_()) {
                  canShoot = true;
               }
            }
         }

         if (canShoot) {
            if (!hasInfiniteAmmo) {
               cannonballStack.m_41774_(1);
               gunpowderStack.m_41774_(1);
            }

            return true;
         }
      }

      return false;
   }

   public void shootCannonball(LivingEntity rider) {
      CannonBallProjectile proj = new CannonBallProjectile(this.m_9236_(), rider);
      proj.m_6034_(this.m_20182_().f_82479_, this.m_20182_().f_82480_ + (double)0.25F, this.m_20182_().f_82481_);
      proj.setDamage(30.0F);
      proj.setMaxLife(180);
      proj.setGravity(0.04F);
      this.m_9236_().m_7967_(proj);
      proj.m_37251_(this, this.m_146909_(), this.m_146908_(), 0.0F, 3.0F, 0.0F);
      rider.m_9236_().m_5594_((Player)null, this.m_20183_(), (SoundEvent)ModSounds.GENERIC_EXPLOSION.get(), SoundSource.PLAYERS, 2.5F, 0.75F + rider.m_217043_().m_188501_() / 4.0F);
      this.cooldown = 10.0F;
      Vec3 lookVec = this.m_20154_().m_82490_((double)4.0F);

      for(int i = 0; i < 10; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F + lookVec.f_82479_;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F + lookVec.f_82481_;
         ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123759_, (double)this.m_20183_().m_123341_() + offsetX, (double)(this.m_20183_().m_123342_() + 1) + offsetY, (double)this.m_20183_().m_123343_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public void m_19956_(Entity passenger, Entity.MoveFunction moveFunc) {
      if (this.m_20363_(passenger)) {
         float f = -1.0F;
         float f1 = (float)((this.m_213877_() ? (double)0.01F : this.m_6048_()) + passenger.m_6049_());
         if (this.m_20197_().size() > 1) {
            int i = this.m_20197_().indexOf(passenger);
            if (i == 0) {
               f = 0.2F;
            } else {
               f = -0.6F;
            }

            if (passenger instanceof Animal) {
               f = (float)((double)f + 0.2);
            }
         }

         Vec3 vec3 = (new Vec3((double)f, (double)0.0F, (double)0.0F)).m_82524_(-this.m_146908_() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
         moveFunc.m_20372_(passenger, this.m_20185_() + vec3.f_82479_, this.m_20186_() + (double)f1, this.m_20189_() + vec3.f_82481_);
         passenger.m_6034_(this.m_20185_() + vec3.f_82479_, this.m_20186_() + (double)f1, this.m_20189_() + vec3.f_82481_);
         passenger.m_146922_(passenger.m_146908_() + this.deltaYaw);
         passenger.m_5616_(passenger.m_6080_() + this.deltaYaw);
         this.clampRotation(passenger);
         if (passenger instanceof Animal) {
            Animal animal = (Animal)passenger;
            if (this.m_20197_().size() > 1) {
               int j = passenger.m_19879_() % 2 == 0 ? 90 : 270;
               passenger.m_5618_(animal.f_20883_ + (float)j);
               passenger.m_5616_(passenger.m_6080_() + (float)j);
            }
         }
      }

   }

   public Vec3 m_7688_(LivingEntity entity) {
      Vec3 lookVec = this.m_20154_().m_82548_();
      return entity.m_20182_().m_82520_((double)0.0F, (double)0.75F, (double)0.0F).m_82549_(lookVec);
   }

   public void changeVehicleMode() {
      if (this.isAimMode()) {
         this.setMoveMode();
      } else {
         this.setAimMode();
      }

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
         if (flag || this.getDamage() > 50.0F) {
            if (!flag && this.m_9236_().m_46469_().m_46207_(GameRules.f_46137_)) {
               this.m_19983_(((Item)ModItems.CANNON.get()).m_7968_());
            }

            this.m_142687_(RemovalReason.KILLED);
         }

         return true;
      } else {
         return true;
      }
   }

   public void m_6845_(boolean isDownwards) {
   }

   public void m_7334_(Entity entity) {
      if (entity instanceof CannonEntity) {
         if (entity.m_20191_().f_82289_ < this.m_20191_().f_82292_) {
            super.m_7334_(entity);
         }
      } else if (entity.m_20191_().f_82289_ <= this.m_20191_().f_82289_) {
         super.m_7334_(entity);
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
      return ((Item)ModItems.CANNON.get()).m_7968_();
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7340_(Entity pEntityToUpdate) {
      this.clampRotation(pEntityToUpdate);
   }

   protected void m_7378_(CompoundTag nbt) {
      this.setModeByte(nbt.m_128445_("mode"));
   }

   protected void m_7380_(CompoundTag nbt) {
      nbt.m_128344_("mode", this.getModeByte());
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void setAimMode() {
      this.setMode(CannonEntity.Mode.AIM);
   }

   public void setMoveMode() {
      this.setMode(CannonEntity.Mode.MOVE);
   }

   private void setMode(Mode mode) {
      this.setModeByte((byte)mode.ordinal());
   }

   private void setModeByte(byte mode) {
      this.f_19804_.m_135381_(MODE, mode);
   }

   public boolean isAimMode() {
      return this.getMode().equals(CannonEntity.Mode.AIM);
   }

   public boolean isMoveMode() {
      return this.getMode().equals(CannonEntity.Mode.MOVE);
   }

   private byte getModeByte() {
      return (Byte)this.f_19804_.m_135370_(MODE);
   }

   public Mode getMode() {
      return CannonEntity.Mode.values()[(Byte)this.f_19804_.m_135370_(MODE)];
   }

   public float getCooldown() {
      return this.cooldown;
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

   static {
      MODE = SynchedEntityData.m_135353_(CannonEntity.class, EntityDataSerializers.f_135027_);
   }

   public static enum Mode {
      AIM,
      MOVE;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{AIM, MOVE};
      }
   }
}
