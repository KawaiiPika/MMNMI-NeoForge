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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import xyz.pixelatedw.mineminenomi.api.WyHelper;

import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;


import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModSounds;


public class CannonEntity extends Entity {

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      builder.define(MODE, (byte)0);
   }

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
      this((net.minecraft.world.entity.EntityType<? extends Entity>)xyz.pixelatedw.mineminenomi.init.ModEntities.CANNON.get(), level);
   }

   public CannonEntity(EntityType<?> type, Level level) {
      super(type, level);
      // this.setMaxUpStep(0.6F);
      this.blocksBuilding = true;
   }

   public float getEyeHeightForge(Pose pose, EntityDimensions size) {
      return size.width();
   }

   public boolean isPushable(Entity entity) {
      return canVehicleCollide(this, entity);
   }

   public static boolean canVehicleCollide(Entity vehicle, Entity entity) {
      if (entity.getY() < vehicle.getY()) {
         return false;
      } else {
         return (entity.isPushable() || entity.canBeCollidedWith()) && !vehicle.hasPassenger(entity);
      }
   }

   public boolean isPushable() {
      return true;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isPickable() {
      return this.isAlive();
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public double getMyRidingOffset() {
      return 0.35;
   }

   public Direction getMotionDirection() {
      return this.getDirection().getClockWise();
   }

   private void tickLerp() {
      if (this.isControlledByLocalInstance()) {
         this.lerpSteps = 0;
         this.setPos(this.getX(), this.getY(), this.getZ());
      }

      if (this.lerpSteps > 0) {
         double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
         double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
         double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
         double d3 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
         this.setYRot((float)((double)this.getYRot() + d3 / (double)this.lerpSteps));
         this.setXRot((float)((double)this.getXRot() + (this.lerpXRot - (double)this.getXRot()) / (double)this.lerpSteps));
         --this.lerpSteps;
         this.setPos(d0, d1, d2);
         this.setRot(this.getYRot(), this.getXRot());
      }

   }

   public void tick() {
      super.tick();
      Level level = super.level();
      if (level != null) {
         this.tickLerp();
         if (this.cooldown > 0.0F) {
            --this.cooldown;
         }

         Entity passanger = this.getControllingPassenger();
         if (!level.isClientSide && passanger != null && passanger instanceof LivingEntity) {
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

         double gravity = this.isNoGravity() ? (double)0.0F : (double)-0.4F;
         Vec3 motion = this.getDeltaMovement();
         this.setDeltaMovement(motion.x * 0.1D, motion.y + gravity, motion.z * 0.1D);
         if (this.isControlledByLocalInstance()) {
            this.deltaYaw *= 0.1F;
            this.deltaPitch *= 0.1F;
            if (this.getControllingPassenger() != null) {
               this.controlVehicle();
            }
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
         if (!this.onGround()) {
            for(Entity entity : Lists.newArrayList(level.getEntities(this, this.getBoundingBox().expandTowards((double)0.0F, (double)-1.0F, (double)0.0F)))) {
               entity.hurt(this.damageSources().generic(), 20.0F);
            }
         }

      }
   }

   private void controlVehicle() {
      if (this.isVehicle()) {
         Entity passanger = this.getControllingPassenger();
         if (passanger == null || !(passanger instanceof LivingEntity)) {
            return;
         }

         LivingEntity player = (LivingEntity)passanger;
         if (player.yya > 0.0F) {
            --this.deltaYaw;
         } else if (player.yya < 0.0F) {
            ++this.deltaYaw;
         }

         float speed = 0.0F;
         if (this.isMoveMode()) {
            if (player.xxa > 0.0F) {
               speed = 0.05F;
            } else if (player.xxa < 0.0F) {
               speed = -0.03F;
            }
         } else if (this.isAimMode()) {
            if (player.xxa > 0.0F && this.getXRot() > -50.0F) {
               --this.deltaPitch;
            } else if (player.xxa < 0.0F && this.getXRot() < 0.0F) {
               ++this.deltaPitch;
            }
         }

         this.setYRot(this.getYRot() + this.deltaYaw);
         this.setXRot(this.getXRot() + this.deltaPitch);
         this.setDeltaMovement(this.getDeltaMovement().add((double)(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * speed), 0.0D, (double)(Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * speed)));
      }

   }

   public boolean canShoot(LivingEntity rider) {
      if (this.isVehicle()) {
         boolean canShoot = false;
         boolean hasInfiniteAmmo = false;
         ItemStack cannonballStack = ItemStack.EMPTY;
         int cannonballCount = 0;
         ItemStack gunpowderStack = ItemStack.EMPTY;
         int gunpowderCount = 0;
         if (rider instanceof Player) {
            Player player = (Player)rider;
            hasInfiniteAmmo = player.getAbilities().instabuild;
         } else {
            hasInfiniteAmmo = true;
         }

         if (rider.getUseItemRemainingTicks() == 1 && this.cooldown <= 0.0F) {
            if (hasInfiniteAmmo) {
               canShoot = true;
            } else {
               java.util.List<ItemStack> inventory = new java.util.ArrayList<>();
               if (rider instanceof Player player1) {
                  for (int i = 0; i < player1.getInventory().getContainerSize(); i++) {
                     inventory.add(player1.getInventory().getItem(i));
                  }
               }

               for(int j = 0; j < inventory.size(); ++j) {
                  ItemStack itemstack = (ItemStack)inventory.get(j);
                  if (itemstack.getItem().equals(net.minecraft.world.item.Items.FIRE_CHARGE)) {
                     cannonballStack = itemstack;
                     cannonballCount += itemstack.getCount();
                  }
               }

               if (cannonballCount <= 0 || cannonballStack.isEmpty()) {

                  return false;
               }

               for(int j = 0; j < inventory.size(); ++j) {
                  ItemStack itemstack = (ItemStack)inventory.get(j);
                  if (itemstack.getItem().equals(Items.GUNPOWDER)) {
                     gunpowderStack = itemstack;
                     gunpowderCount += itemstack.getCount();
                  }
               }

               if (gunpowderCount <= 0 || gunpowderStack.isEmpty()) {

                  return false;
               }

               if (cannonballCount > 0 && gunpowderCount > 0 && !cannonballStack.isEmpty() && !gunpowderStack.isEmpty()) {
                  canShoot = true;
               }
            }
         }

         if (canShoot) {
            if (!hasInfiniteAmmo) {
               cannonballStack.shrink(1);
               gunpowderStack.shrink(1);
            }

            return true;
         }
      }

      return false;
   }

   public void shootCannonball(LivingEntity rider) {
      net.minecraft.world.entity.projectile.Snowball proj = new net.minecraft.world.entity.projectile.Snowball(this.level(), rider);
      proj.setPos(this.position().x, this.position().y + 0.25F, this.position().z);



      this.level().addFreshEntity(proj);
      proj.shootFromRotation(this, this.getXRot(), this.getYRot(), 0.0F, 3.0F, 0.0F);
      rider.level().playSound((Player)null, this.blockPosition(), (SoundEvent)ModSounds.GENERIC_EXPLOSION.get(), SoundSource.PLAYERS, 2.5F, 0.75F + rider.getRandom().nextFloat() / 4.0F);
      this.cooldown = 10.0F;
      Vec3 lookVec = this.getLookAngle().scale((double)4.0F);

      for(int i = 0; i < 10; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F + lookVec.x;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F + lookVec.z;
         ((ServerLevel)this.level()).sendParticles(ParticleTypes.SMOKE, (double)this.blockPosition().getX() + offsetX, (double)(this.blockPosition().getY() + 1) + offsetY, (double)this.blockPosition().getZ() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public void positionRider(Entity passenger, Entity.MoveFunction moveFunc) {
      if (this.hasPassenger(passenger)) {
         float f = -1.0F;
         float f1 = (float)((this.isCrouching() ? (double)0.01F : this.getMyRidingOffset()) + (passenger.getDimensions(passenger.getPose()).height() * 0.85F));
         if (this.getPassengers().size() > 1) {
            int i = this.getPassengers().indexOf(passenger);
            if (i == 0) {
               f = 0.2F;
            } else {
               f = -0.6F;
            }

            if (passenger instanceof Animal) {
               f = (float)((double)f + 0.2);
            }
         }

         Vec3 vec3 = (new Vec3((double)f, (double)0.0F, (double)0.0F)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
         moveFunc.accept(passenger, this.getX() + vec3.x, this.getY() + (double)f1, this.getZ() + vec3.z);
         passenger.setPos(this.getX() + vec3.x, this.getY() + (double)f1, this.getZ() + vec3.z);
         passenger.setYRot(passenger.getYRot() + this.deltaYaw);
         passenger.setYBodyRot(((net.minecraft.world.entity.LivingEntity)passenger).yBodyRot + this.deltaYaw);
         this.clampRotation(passenger);
         if (passenger instanceof Animal) {
            Animal animal = (Animal)passenger;
            if (this.getPassengers().size() > 1) {
               int j = passenger.getId() % 2 == 0 ? 90 : 270;
               passenger.setYHeadRot(animal.yBodyRot + (float)j);
               passenger.setYBodyRot(((net.minecraft.world.entity.LivingEntity)passenger).yBodyRot + (float)j);
            }
         }
      }

   }

   public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
      Vec3 lookVec = this.getLookAngle().normalize();
      return entity.position().add((double)0.0F, (double)0.75F, (double)0.0F).cross(lookVec);
   }

   public void changeVehicleMode() {
      if (this.isAimMode()) {
         this.setMoveMode();
      } else {
         this.setAimMode();
      }

   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      Entity entity = this.getFirstPassenger();
      if (entity instanceof LivingEntity livingentity) {
         return livingentity;
      } else {
         return null;
      }
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (hand.equals(InteractionHand.OFF_HAND)) {
         return InteractionResult.PASS;
      } else if (!this.level().isClientSide) {
         if (player.startRiding(this)) {
            this.cooldown = 10.0F;
            return InteractionResult.CONSUME;
         } else {
            return InteractionResult.PASS;
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (!this.level().isClientSide && !this.isCrouching()) {
         boolean var10000;
         label32: {
            this.level().broadcastEntityEvent(this, (byte)100);
            this.setDamage(this.getDamage() + amount * 10.0F);
            this.markHurt();
            Entity var5 = source.getEntity();
            if (var5 instanceof Player) {
               Player player = (Player)var5;
               if (player.getAbilities().instabuild) {
                  var10000 = true;
                  break label32;
               }
            }

            var10000 = false;
         }

         boolean flag = var10000;
         if (flag || this.getDamage() > 50.0F) {
            if (!flag && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
               this.spawnAtLocation(((Item)ModItems.CANNON.get()).getDefaultInstance());
            }

            this.discard();
         }

         return true;
      } else {
         return true;
      }
   }

   public void checkFallDamage(boolean isDownwards) {
   }

   public void push(Entity entity) {
      if (entity instanceof CannonEntity) {
         if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
            super.push(entity);
         }
      } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
         super.push(entity);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void handleEntityEvent(byte id) {
      switch (id) {
         case 100:
            this.hurtTime = 10;
         default:
            super.handleEntityEvent(id);
      }
   }

   protected void clampRotation(Entity entity) {
      entity.setYHeadRot(this.getYRot());
      float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
      float f1 = Mth.clamp(f, -105.0F, 105.0F);
      entity.yRotO += f1 - f;
      entity.setYRot(entity.getYRot() + f1 - f);
      entity.setYBodyRot(entity.getYRot());
   }

   public ItemStack getPickedResult(HitResult target) {
      return ((Item)ModItems.CANNON.get()).getDefaultInstance();
   }

   @OnlyIn(Dist.CLIENT)
   public void onPassengerTurned(Entity pEntityToUpdate) {
      this.clampRotation(pEntityToUpdate);
   }

   protected void readAdditionalSaveData(CompoundTag nbt) {
      this.setModeByte(nbt.getByte("mode"));
   }

   protected void addAdditionalSaveData(CompoundTag nbt) {
      nbt.putByte("mode", this.getModeByte());
   }

   public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getAddEntityPacket() {
      return null;
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
      this.entityData.set(MODE, mode);
   }

   public boolean isAimMode() {
      return this.getMode().equals(CannonEntity.Mode.AIM);
   }

   public boolean isMoveMode() {
      return this.getMode().equals(CannonEntity.Mode.MOVE);
   }

   private byte getModeByte() {
      return this.entityData.get(MODE);
   }

   public Mode getMode() {
      return CannonEntity.Mode.values()[this.entityData.get(MODE)];
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
      MODE = SynchedEntityData.defineId(CannonEntity.class, EntityDataSerializers.BYTE);
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
