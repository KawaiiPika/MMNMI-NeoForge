package xyz.pixelatedw.mineminenomi.entities.vehicles;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncStrikerCrewPacket;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class StrikerEntity extends Entity {
   private static final EntityDataAccessor<Float> FUEL;
   private static final float MAX_FUEL = 100.0F;
   private static final float SPEED = 0.07F;
   private static final float BACKWARDS_SPEED = -0.014F;
   private static final float TURN_SPEED = 0.5F;
   private static final byte HURT_EVENT = 100;
   private static final AABB AABB;
   private float deltaRotation;
   private int lerpSteps;
   private double lerpX;
   private double lerpY;
   private double lerpZ;
   private double lerpYRot;
   private double lerpXRot;
   private double waterLevel;
   private Status status;
   private Status oldStatus;
   private double lastYd;
   private float invFriction;
   private float landFriction;
   @Nullable
   private UUID lastRiderUUID;
   @Nullable
   private Player lastRider;
   private float damage;
   private int hurtTime;
   @Nullable
   private Crew crew;

   public StrikerEntity(Level level) {
      this((EntityType)ModEntities.STRIKER.get(), level);
   }

   public StrikerEntity(EntityType<?> type, Level level) {
      super(type, level);
      this.f_19850_ = true;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(FUEL, 0.0F);
   }

   public float getEyeHeightForge(Pose pose, EntityDimensions size) {
      return size.f_20378_;
   }

   public boolean m_7337_(Entity entity) {
      return canVehicleCollide(this, entity);
   }

   public static boolean canVehicleCollide(Entity vehicle, Entity entity) {
      return (entity.m_5829_() || entity.m_6094_()) && !vehicle.m_20365_(entity);
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6094_() {
      return true;
   }

   public boolean m_6087_() {
      return this.m_6084_();
   }

   public boolean shouldRiderSit() {
      return this.m_6688_() == null || !this.isMeraUser(this.m_6688_());
   }

   public double m_6048_() {
      return this.m_6688_() != null && this.isMeraUser(this.m_6688_()) ? 0.7 : 0.15;
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
      this.oldStatus = this.status;
      this.status = this.getStatus();
      super.m_8119_();
      this.tickLerp();
      if (this.m_6688_() != null && (this.lastRiderUUID == null || !this.lastRiderUUID.equals(this.m_6688_().m_20148_()))) {
         this.lastRiderUUID = this.m_6688_().m_20148_();
      }

      if (!this.m_9236_().f_46443_) {
         this.consumeFuel();
      }

      if (this.getHurtTime() > 0) {
         this.setHurtTime(this.getHurtTime() - 1);
      }

      if (this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      if (this.m_6109_()) {
         this.invFriction = 0.05F;
         double gravity = this.m_20068_() ? (double)0.0F : (double)-0.04F;
         double d2 = (double)0.0F;
         if (this.oldStatus == StrikerEntity.Status.IN_AIR && this.status != StrikerEntity.Status.IN_AIR && this.status != StrikerEntity.Status.ON_LAND) {
            this.waterLevel = this.m_20227_((double)1.0F);
            this.m_6034_(this.m_20185_(), (double)(this.getWaterLevelAbove() - this.m_20206_()) + 0.101, this.m_20189_());
            AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82542_((double)1.0F, (double)0.0F, (double)1.0F));
            this.lastYd = (double)0.0F;
            this.status = StrikerEntity.Status.IN_WATER;
         } else if (this.status == StrikerEntity.Status.IN_WATER) {
            d2 = (this.waterLevel - this.m_20186_()) / (double)this.m_20206_();
            this.invFriction = 0.9F;
         } else if (this.status == StrikerEntity.Status.UNDER_FLOWING_WATER) {
            gravity = -7.0E-4;
            this.invFriction = 0.9F;
         } else if (this.status == StrikerEntity.Status.UNDER_WATER) {
            d2 = (double)0.01F;
            this.invFriction = 0.45F;
         } else if (this.status == StrikerEntity.Status.IN_AIR) {
            this.invFriction = 0.9F;
         } else if (this.status == StrikerEntity.Status.ON_LAND) {
            this.invFriction = this.landFriction;
            if (this.m_6688_() instanceof Player) {
               this.landFriction /= 2.0F;
            }
         }

         Vec3 motion = this.m_20184_();
         AbilityHelper.setDeltaMovement(this, motion.f_82479_ * (double)this.invFriction, motion.f_82480_ + gravity, motion.f_82481_ * (double)this.invFriction);
         this.deltaRotation *= this.invFriction;
         if (d2 > (double)0.0F) {
            motion = this.m_20184_();
            AbilityHelper.setDeltaMovement(this, motion.f_82479_, (motion.f_82480_ + d2 * 0.061) * (double)0.75F, motion.f_82481_);
         }

         if (this.m_6688_() != null && this.hasFuel(this.m_6688_())) {
            this.controlVehicle();
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
      } else {
         AbilityHelper.setDeltaMovement(this, Vec3.f_82478_);
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

   private void consumeFuel() {
      Entity passanger = this.m_6688_();
      if (passanger != null && passanger instanceof Player player) {
         if (this.isMeraUser(player)) {
            return;
         }

         float speed = 0.0F;
         if (player.f_20902_ > 0.0F) {
            speed = 0.07F;
         } else if (player.f_20902_ < 0.0F) {
            speed = -0.014F;
         }

         float fuel = this.getFuel() - Math.abs(speed / 4.0F);
         fuel = Mth.m_14036_(fuel, 0.0F, 100.0F);
         this.setFuel(fuel);
      }

   }

   public void m_19956_(Entity passenger, Entity.MoveFunction moveFunc) {
      if (this.m_20363_(passenger)) {
         float f = 0.0F;
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
         passenger.m_146922_(passenger.m_146908_() + this.deltaRotation);
         passenger.m_5616_(passenger.m_6080_() + this.deltaRotation);
         this.clampRotation(passenger);
         if (passenger instanceof Animal) {
            Animal animalEntity = (Animal)passenger;
            if (this.m_20197_().size() > 1) {
               int j = passenger.m_19879_() % 2 == 0 ? 90 : 270;
               passenger.m_5618_(animalEntity.f_20883_ + (float)j);
               passenger.m_5616_(passenger.m_6080_() + (float)j);
            }
         }
      }

   }

   private Status getStatus() {
      Status status = this.isUnderwater();
      if (status != null) {
         this.waterLevel = this.m_20191_().f_82292_;
         return status;
      } else if (this.checkInWater()) {
         return StrikerEntity.Status.IN_WATER;
      } else {
         float f = this.getGroundFriction();
         if (f > 0.0F) {
            this.landFriction = f;
            return StrikerEntity.Status.ON_LAND;
         } else {
            return StrikerEntity.Status.IN_AIR;
         }
      }
   }

   private void controlVehicle() {
      if (this.m_20160_()) {
         Entity passanger = this.m_6688_();
         if (passanger == null || !(passanger instanceof Player)) {
            return;
         }

         Player player = (Player)passanger;
         if (player.f_20900_ > 0.0F) {
            this.deltaRotation -= 0.5F;
         } else if (player.f_20900_ < 0.0F) {
            this.deltaRotation += 0.5F;
         }

         this.m_146922_(this.m_146908_() + this.deltaRotation);
         float speed = 0.0F;
         if (player.f_20902_ > 0.0F) {
            speed = 0.07F;
         } else if (player.f_20902_ < 0.0F) {
            speed = -0.014F;
         }

         AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82520_((double)(Mth.m_14031_(-this.m_146908_() * ((float)Math.PI / 180F)) * speed), (double)0.0F, (double)(Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)) * speed)));
         if (this.isMeraUser(player)) {
            for(int i = 0; i < 5; ++i) {
               double offsetX = WyHelper.randomDouble() / (double)2.5F;
               double offsetY = WyHelper.randomDouble() / (double)4.0F;
               double offsetZ = WyHelper.randomDouble() / (double)2.5F;
               SimpleParticleData proj = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
               proj.setLife(5);
               proj.setSize(3.0F);
               player.m_9236_().m_7106_(proj, this.m_20185_() + offsetX, this.m_20186_() + (double)0.5F + offsetY, this.m_20189_() + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }

         if (speed > 0.0F) {
            double yRot = Mth.m_14175_((double)this.m_146908_() * (Math.PI / 180D)) + 0.8;
            double dist = (double)1.5F;
            double xp = dist * Math.cos(yRot) - dist * Math.sin(yRot);
            double zp = dist * Math.cos(yRot) + dist * Math.sin(yRot);

            for(int i = 0; i < 20; ++i) {
               double offsetX = WyHelper.randomDouble() / (double)2.5F - xp;
               double offsetY = WyHelper.randomDouble() / (double)2.5F;
               double offsetZ = WyHelper.randomDouble() / (double)2.5F - zp;
               SimpleParticleData proj = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
               proj.setLife(10);
               proj.setSize(4.0F);
               proj.setMotion(-this.m_20184_().f_82479_, -this.m_20184_().f_82480_, -this.m_20184_().f_82481_);
               player.m_9236_().m_7106_(proj, this.m_20185_() + offsetX, this.m_20186_() + (double)0.5F + offsetY, this.m_20189_() + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }
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
      ItemStack heldItem = player.m_21205_();
      if (hand.equals(InteractionHand.OFF_HAND)) {
         return InteractionResult.PASS;
      } else if (player.m_6047_() && !player.m_9236_().f_46443_) {
         String var10001 = ModI18n.INFO_FUEL_LEFT;
         Object[] var10002 = new Object[1];
         Object[] var10006 = new Object[]{this.getFuel()};
         var10002[0] = String.format("%.2f", var10006) + "%";
         player.m_213846_(Component.m_237110_(var10001, var10002));
         return InteractionResult.CONSUME;
      } else if (heldItem != null && !heldItem.m_41619_() && heldItem.m_41720_().equals(((Block)ModBlocks.FLAME_DIAL.get()).m_5456_()) && this.getFuel() < 100.0F) {
         for(; this.getFuel() < 100.0F && !heldItem.m_41619_(); this.setFuel(this.getFuel() + 25.0F)) {
            if (!player.m_150110_().f_35937_) {
               heldItem.m_41774_(1);
            }
         }

         return InteractionResult.CONSUME;
      } else if (!this.m_9236_().f_46443_) {
         if (player.m_20329_(this)) {
            Crew crew = FactionsWorldData.get().getCrewWithMember(player.m_20148_());
            if (crew != null) {
               this.crew = crew;
               ModNetwork.sendToAllTracking(new SSyncStrikerCrewPacket(this, crew), this);
            }

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
         if (flag || this.getDamage() > 30.0F) {
            if (!flag && this.m_9236_().m_46469_().m_46207_(GameRules.f_46137_)) {
               this.m_19983_(((Item)ModItems.STRIKER.get()).m_7968_());
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

   private boolean isMeraUser(LivingEntity entity) {
      boolean hasMera = DevilFruitCapability.hasDevilFruit(entity, (AkumaNoMiItem)ModFruits.MERA_MERA_NO_MI.get());
      return hasMera;
   }

   private boolean hasFuel(LivingEntity entity) {
      return this.isMeraUser(entity) ? true : this.getFuel() > 0.0F;
   }

   public float getGroundFriction() {
      AABB aabb = this.m_20191_();
      AABB aabb1 = new AABB(aabb.f_82288_, aabb.f_82289_ - 0.001, aabb.f_82290_, aabb.f_82291_, aabb.f_82289_, aabb.f_82293_);
      int i = Mth.m_14107_(aabb1.f_82288_) - 1;
      int j = Mth.m_14165_(aabb1.f_82291_) + 1;
      int k = Mth.m_14107_(aabb1.f_82289_) - 1;
      int l = Mth.m_14165_(aabb1.f_82292_) + 1;
      int i1 = Mth.m_14107_(aabb1.f_82290_) - 1;
      int j1 = Mth.m_14165_(aabb1.f_82293_) + 1;
      VoxelShape voxelshape = Shapes.m_83064_(aabb1);
      float f = 0.0F;
      int k1 = 0;
      BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

      for(int l1 = i; l1 < j; ++l1) {
         for(int i2 = i1; i2 < j1; ++i2) {
            int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
            if (j2 != 2) {
               for(int k2 = k; k2 < l; ++k2) {
                  if (j2 <= 0 || k2 != k && k2 != l - 1) {
                     blockpos$mutable.m_122178_(l1, k2, i2);
                     BlockState blockstate = this.m_9236_().m_8055_(blockpos$mutable);
                     if (!(blockstate.m_60734_() instanceof WaterlilyBlock) && Shapes.m_83157_(blockstate.m_60812_(this.m_9236_(), blockpos$mutable).m_83216_((double)l1, (double)k2, (double)i2), voxelshape, BooleanOp.f_82689_)) {
                        f += blockstate.getFriction(this.m_9236_(), blockpos$mutable, this);
                        ++k1;
                     }
                  }
               }
            }
         }
      }

      return f / (float)k1;
   }

   public float getWaterLevelAbove() {
      AABB axisalignedbb = this.m_20191_();
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82292_);
      int l = Mth.m_14165_(axisalignedbb.f_82292_ - this.lastYd);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

      label39:
      for(int k1 = k; k1 < l; ++k1) {
         float f = 0.0F;

         for(int l1 = i; l1 < j; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutable.m_122178_(l1, k1, i2);
               FluidState fluidstate = this.m_9236_().m_6425_(blockpos$mutable);
               if (fluidstate.m_205070_(FluidTags.f_13131_)) {
                  f = Math.max(f, fluidstate.m_76155_(this.m_9236_(), blockpos$mutable));
               }

               if (f >= 1.0F) {
                  continue label39;
               }
            }
         }

         if (f < 1.0F) {
            return (float)blockpos$mutable.m_123342_() + f;
         }
      }

      return (float)(l + 1);
   }

   private boolean checkInWater() {
      AABB aabb = this.m_20191_();
      int i = Mth.m_14107_(aabb.f_82288_);
      int j = Mth.m_14165_(aabb.f_82291_);
      int k = Mth.m_14107_(aabb.f_82289_);
      int l = Mth.m_14165_(aabb.f_82289_ + 0.001);
      int i1 = Mth.m_14107_(aabb.f_82290_);
      int j1 = Mth.m_14165_(aabb.f_82293_);
      boolean flag = false;
      this.waterLevel = Double.MIN_VALUE;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               mutpos.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.m_9236_().m_6425_(mutpos);
               if (fluidstate.m_205070_(FluidTags.f_13131_)) {
                  float f = (float)l1 + fluidstate.m_76155_(this.m_9236_(), mutpos);
                  this.waterLevel = Math.max((double)f, this.waterLevel);
                  flag |= aabb.f_82289_ < (double)f;
               }
            }
         }
      }

      return flag;
   }

   @Nullable
   private Status isUnderwater() {
      AABB aabb = this.m_20191_();
      double d0 = aabb.f_82292_ + 0.001;
      int i = Mth.m_14107_(aabb.f_82288_);
      int j = Mth.m_14165_(aabb.f_82291_);
      int k = Mth.m_14107_(aabb.f_82292_);
      int l = Mth.m_14165_(d0);
      int i1 = Mth.m_14107_(aabb.f_82290_);
      int j1 = Mth.m_14165_(aabb.f_82293_);
      boolean flag = false;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               mutpos.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.m_9236_().m_6425_(mutpos);
               if (fluidstate.m_205070_(FluidTags.f_13131_) && d0 < (double)((float)mutpos.m_123342_() + fluidstate.m_76155_(this.m_9236_(), mutpos))) {
                  if (!fluidstate.m_76170_()) {
                     return StrikerEntity.Status.UNDER_FLOWING_WATER;
                  }

                  flag = true;
               }
            }
         }
      }

      return flag ? StrikerEntity.Status.UNDER_WATER : null;
   }

   public void m_7334_(Entity entity) {
      if (entity instanceof StrikerEntity) {
         if (entity.m_20191_().f_82289_ < this.m_20191_().f_82292_) {
            super.m_7334_(entity);
         }
      } else if (entity.m_20191_().f_82289_ <= this.m_20191_().f_82289_) {
         super.m_7334_(entity);
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

   protected void m_20101_() {
      AABB aabb = this.m_20191_();
      BlockPos blockpos = BlockPos.m_274561_(aabb.f_82288_ + 0.001, aabb.f_82289_ + 0.001, aabb.f_82290_ + 0.001);
      BlockPos blockpos1 = BlockPos.m_274561_(aabb.f_82291_ - 0.001, aabb.f_82292_ - 0.001, aabb.f_82293_ - 0.001);
      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
      if (this.m_9236_().m_46832_(blockpos, blockpos1)) {
         for(int i = blockpos.m_123341_(); i <= blockpos1.m_123341_(); ++i) {
            for(int j = blockpos.m_123342_(); j <= blockpos1.m_123342_(); ++j) {
               for(int k = blockpos.m_123343_(); k <= blockpos1.m_123343_(); ++k) {
                  pos.m_122178_(i, j, k);
                  BlockState state = this.m_9236_().m_8055_(pos);
                  if (state.m_60734_() == Blocks.f_50196_) {
                     this.m_9236_().m_46953_(pos, true, this);
                  }
               }
            }
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void m_7340_(Entity entity) {
      this.clampRotation(entity);
   }

   public ItemStack getPickedResult(HitResult target) {
      return ((Item)ModItems.STRIKER.get()).m_5456_().m_7968_();
   }

   @OnlyIn(Dist.CLIENT)
   public AABB m_6921_() {
      AABB aabb = super.m_6921_();
      aabb = aabb.m_82377_((double)0.0F, (double)3.0F, (double)0.0F);
      return aabb;
   }

   protected void m_7378_(CompoundTag nbt) {
      this.setFuel(nbt.m_128457_("fuel"));
      if (nbt.m_128403_("lastRiderId")) {
         this.lastRiderUUID = nbt.m_128342_("lastRiderId");
      }

      if (nbt.m_128425_("crew", 10)) {
         Crew crew = Crew.from(nbt.m_128469_("crew"));
         if (crew != null) {
            this.crew = crew;
         }
      }

   }

   protected void m_7380_(CompoundTag nbt) {
      nbt.m_128350_("fuel", this.getFuel());
      if (this.getLastRiderId() != null) {
         nbt.m_128362_("lastRiderId", this.getLastRiderId());
      }

      if (this.crew != null) {
         nbt.m_128365_("crew", this.crew.write());
      }

   }

   @Nullable
   public UUID getLastRiderId() {
      return this.lastRiderUUID;
   }

   @Nullable
   public Player getLastRider() {
      if (this.lastRider == null && this.lastRiderUUID != null) {
         this.lastRider = this.m_9236_().m_46003_(this.lastRiderUUID);
      }

      return this.lastRider;
   }

   public void setFuel(float fuel) {
      this.f_19804_.m_135381_(FUEL, fuel);
   }

   public float getFuel() {
      return (Float)this.f_19804_.m_135370_(FUEL);
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

   @Nullable
   public Crew getCrew() {
      return this.crew;
   }

   public void setCrew(Crew crew) {
      this.crew = crew;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   static {
      FUEL = SynchedEntityData.m_135353_(StrikerEntity.class, EntityDataSerializers.f_135029_);
      AABB = new AABB((double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;

      // $FF: synthetic method
      private static Status[] $values() {
         return new Status[]{IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER, ON_LAND, IN_AIR};
      }
   }
}
