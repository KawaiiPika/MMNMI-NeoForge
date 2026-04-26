package xyz.pixelatedw.mineminenomi.api.entities;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.RegistryFriendlyByteBuf;

public abstract class NuLightningEntity extends ThrowableItemProjectile implements IEntityWithComplexSpawn {

   private static final EntityDataAccessor<Float> LENGTH = SynchedEntityData.defineId(NuLightningEntity.class, EntityDataSerializers.FLOAT);

   protected Vec3 lookVec;
   protected float travelSpeed;
   protected float maxTravelDistance;
   protected float length;
   protected float size;
   protected int red;
   protected int green;
   protected int blue;
   protected int alpha;
   protected float rotation;
   protected int startFadeTime;
   protected int fadeTime;
   protected int targetTimeToReset;
   protected int branches;
   protected int segments;
   protected int segmentLength;
   protected int angle;
   protected int depth;
   protected boolean hasLengthChanged;
   protected boolean canCauseKnockback;
   protected boolean canRetract;
   protected boolean isRetracting;
   protected boolean visualOnly;
   protected boolean lightningMovement;
   protected boolean fadeTrail;
   protected RandomSource spazzingRandom;
   private Vec3 hitPosition;
   private Set<Long> firstContactBlocks;

   public NuLightningEntity(EntityType<? extends NuLightningEntity> type, Level world) {
      super(type, world);
      this.lookVec = Vec3.ZERO;
      this.travelSpeed = 1.0F;
      this.maxTravelDistance = 20.0F;
      this.length = 0.0F;
      this.size = 0.25F;
      this.red = 255;
      this.green = 255;
      this.blue = 255;
      this.alpha = 255;
      this.rotation = 0.0F;
      this.startFadeTime = 10;
      this.fadeTime = -1;
      this.targetTimeToReset = 20;
      this.branches = 3;
      this.segments = 5;
      this.segmentLength = 3;
      this.angle = 90;
      this.depth = 8;
      this.hasLengthChanged = false;
      this.canCauseKnockback = true;
      this.canRetract = false;
      this.isRetracting = false;
      this.visualOnly = false;
      this.lightningMovement = false;
      this.fadeTrail = false;
      this.spazzingRandom = RandomSource.create();
      this.hitPosition = Vec3.ZERO;
      this.firstContactBlocks = new LinkedHashSet<>();
   }

   public NuLightningEntity(EntityType<? extends NuLightningEntity> type, LivingEntity thrower, double posX, double posY, double posZ, float rotY, float rotX, float maxTravelDistance, float travelSpeed, @Nullable Object dummy) {
      super(type, thrower, thrower.level());
      this.setPos(posX, posY, posZ);
      this.setYRot(rotY);
      this.setXRot(rotX);
      this.lookVec = Vec3.ZERO;
      this.travelSpeed = 1.0F;
      this.maxTravelDistance = 20.0F;
      this.length = 0.0F;
      this.size = 0.25F;
      this.red = 255;
      this.green = 255;
      this.blue = 255;
      this.alpha = 255;
      this.rotation = 0.0F;
      this.startFadeTime = 10;
      this.fadeTime = -1;
      this.targetTimeToReset = 20;
      this.branches = 3;
      this.segments = 5;
      this.segmentLength = 3;
      this.angle = 90;
      this.depth = 8;
      this.hasLengthChanged = false;
      this.canCauseKnockback = true;
      this.canRetract = false;
      this.isRetracting = false;
      this.visualOnly = false;
      this.lightningMovement = false;
      this.fadeTrail = false;
      this.spazzingRandom = RandomSource.create();
      this.hitPosition = Vec3.ZERO;
      this.firstContactBlocks = new LinkedHashSet<>();
      this.maxTravelDistance = maxTravelDistance;
      this.travelSpeed = travelSpeed;
      if (thrower != null) {
         this.lookVec = thrower.getViewVector(1.0F).normalize();
      }
   }

   @Override
   protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LENGTH, 0.0f);
   }

   @Override
   protected Item getDefaultItem() {
        return Items.AIR;
   }

   protected boolean shouldResetForLookChange(Vec3 newLook) {
      return false;
   }

   public void tick() {
      if (this.tickCount > 1000) {
         this.discard();
         return;
      }
      if (this.getOwner() == null || this.getOwner().isAlive()) {
         if (this.lookVec == null) {
            this.lookVec = Vec3.ZERO;
         }

         if (this.getOwner() != null) {
            Vec3 newLook = this.getOwner().getViewVector(1.0F).normalize();
            if (this.shouldResetForLookChange(newLook)) {
               this.setLength(0.0F);
               this.firstContactBlocks.clear();
            }

            this.lookVec = newLook;
         }

         if (this.tickCount > 1) {
            if (this.isRetracting) {
               this.setLength(Math.max(this.getLength() - this.travelSpeed, 0.0F));
            } else if (this.fadeTime <= 0) {
               this.setLength(Math.min(this.getLength() + this.travelSpeed, this.maxTravelDistance));
            }

            this.hitPosition = this.position();

            for(int i = 0; (float)i < this.getLength(); ++i) {
               double currentX = this.getSegmentX(i);
               double currentY = this.getSegmentY(i);
               double currentZ = this.getSegmentZ(i);
               BlockPos pos = BlockPos.containing(currentX, currentY, currentZ);

               if (!this.isVisualOnly()) {
                  AABB aabb = this.getCollisionBox(currentX, currentY, currentZ);
                  if (this.fadeTime < 0 || (double)this.fadeTime > (double)this.startFadeTime * 0.5D) {
                     List<Entity> entityList = this.level().getEntitiesOfClass(Entity.class, aabb, e -> e != this && e != this.getOwner());
                     for (Entity e : entityList) {
                        this.onEntityImpact(e);
                     }
                  }

                  if (this.hasLengthChanged) {
                     long packedPos = pos.asLong();
                     if (!this.level().getBlockState(pos).isAir() && !this.firstContactBlocks.contains(packedPos)) {
                        this.firstContactBlocks.add(packedPos);
                     }
                  }
               }
            }

            if (this.hasLengthChanged) {
               this.updateClient();

               for(long packedPos : this.firstContactBlocks) {
                  this.onBlockImpact(BlockPos.of(packedPos));
               }
            }
         }

         if (this.fadeTime > 0) {
            --this.fadeTime;
         }

         if (this.hasLengthChanged) {
            if (!this.isRetracting && this.getLength() == this.maxTravelDistance) {
               if (this.canRetract) {
                  this.isRetracting = true;
               } else {
                  this.startFade(this.startFadeTime);
               }

               this.hasLengthChanged = false;
               this.updateClient();
            } else if (this.isRetracting && this.getLength() <= 0.0F) {
               this.discard();
            }
         }

         super.tick();

         this.entityData.set(LENGTH, this.length);
      } else {
         this.discard();
      }
   }

   protected void onEntityImpact(Entity target) {
        if (target instanceof LivingEntity living && this.getOwner() instanceof LivingEntity owner) {
            target.hurt(this.damageSources().mobAttack(owner), 5.0f);
        }
   }

   protected void onBlockImpact(BlockPos result) {

   }

   public double getPreciseCurrentX() {
      return this.getPreciseSegmentX(this.length);
   }

   public double getPreciseCurrentY() {
      return this.getPreciseSegmentY(this.length);
   }

   public double getPreciseCurrentZ() {
      return this.getPreciseSegmentZ(this.length);
   }

   public double getCurrentX() {
      return this.getSegmentX(Math.round(this.length));
   }

   public double getCurrentY() {
      return this.getSegmentY(Math.round(this.length));
   }

   public double getCurrentZ() {
      return this.getSegmentZ(Math.round(this.length));
   }

   public double getPreciseSegmentX(float length) {
      return this.getX() + this.lookVec.x * (double)length;
   }

   public double getPreciseSegmentY(float length) {
      return this.getY() + this.lookVec.y * (double)length;
   }

   public double getPreciseSegmentZ(float length) {
      return this.getZ() + this.lookVec.z * (double)length;
   }

   public double getSegmentX(int length) {
      return Math.floor(this.getX() + this.lookVec.x * (double)length);
   }

   public double getSegmentY(int length) {
      return Math.floor(this.getY() + this.lookVec.y * (double)length);
   }

   public double getSegmentZ(int length) {
      return Math.floor(this.getZ() + this.lookVec.z * (double)length);
   }

   public AABB getCollisionBox(double x, double y, double z) {
      double boxSize = Math.max((double)this.getSize(), 0.1) * 7.0D;
      return new AABB(x - boxSize, y - boxSize, z - boxSize, x + boxSize, y + boxSize, z + boxSize);
   }

   public boolean hasLineOfSight(Entity target) {
      if (target.level() != this.level()) {
         return false;
      } else {
         Vec3 startVec = this.hitPosition;
         Vec3 targetVec = new Vec3(target.getX(), target.getY(), target.getZ());
         if (targetVec.distanceTo(startVec) > 128.0D) {
            return false;
         } else {
            return this.level().clip(new ClipContext(startVec, targetVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == net.minecraft.world.phys.HitResult.Type.MISS;
         }
      }
   }

   public void setTargetTimeToReset(int value) {
      this.targetTimeToReset = value;
   }

   public void setOneTimeHit() {
      this.targetTimeToReset = 9999;
   }

   public void disableExplosionKnockback() {
      this.canCauseKnockback = false;
   }

   public void setLength(float length) {
      this.length = length;
      this.hasLengthChanged = true;
   }

   public float getLength() {
      return this.entityData.get(LENGTH);
   }

   public void setSize(float size) {
      this.size = size;
   }

   public float getSize() {
      return this.size;
   }

   public void setDepth(int depth) {
      this.depth = depth;
   }

   public int getDepth() {
      return this.depth;
   }

   public void setBranches(int branches) {
      this.branches = branches;
   }

   public int getBranches() {
      return this.branches;
   }

   public void setSegments(int segments) {
      this.segments = segments;
   }

   public int getSegments() {
      return this.segments;
   }

   public void setSegmentLength(int len) {
      this.segmentLength = len;
   }

   public int getSegmentLength() {
      return this.segmentLength;
   }


   public void setColor(int red, int green, int blue, int alpha) {
      this.red = Mth.clamp(red, 0, 255);
      this.green = Mth.clamp(green, 0, 255);
      this.blue = Mth.clamp(blue, 0, 255);
      this.alpha = Mth.clamp(alpha, 0, 255);
   }

   public int getRed() {
      return this.red;
   }

   public int getGreen() {
      return this.green;
   }

   public int getBlue() {
      return this.blue;
   }

   public int getAlpha() {
      return this.alpha;
   }

   public void setAngle(int angle) {
      this.angle = Math.round((float)Mth.clamp(angle, 0, 180));
   }

   public int getAngle() {
      return this.angle;
   }

   public void setVisualOnly() {
      this.visualOnly = true;
   }

   public boolean isVisualOnly() {
      return this.visualOnly;
   }

   public void setFadeTime(int fadeTime) {
      this.startFadeTime = fadeTime;
   }

   public void startFade(int fadeTime) {
      this.startFadeTime = fadeTime;
      this.fadeTime = fadeTime;
   }

   public int getStartFadeTime() {
      return this.startFadeTime;
   }

   public int getFadeTime() {
      return this.fadeTime;
   }

   public boolean canRetract() {
      return this.canRetract;
   }

   public void setRetracting() {
      this.canRetract = true;
   }

   public void setTravelSpeed(float speed) {
      this.travelSpeed = speed;
   }

   public void setMaxTravelDistance(float dist) {
      this.maxTravelDistance = dist;
   }

   public void setLightningMovement(boolean hasLightningMovement) {
      this.lightningMovement = hasLightningMovement;
   }

   public boolean getLightningMovement() {
      return this.lightningMovement;
   }

   public void setBeamLookVector(Vec3 vec) {
      this.lookVec = vec;
   }

   public void setRotationSpeed(float rot) {
      this.rotation = rot;
   }

   public float getRotation() {
      return this.rotation;
   }

   public void setTrailFade() {
      this.fadeTrail = true;
   }

   public boolean isTrailFading() {
      return this.fadeTrail;
   }

   public void updateClient() {
   }

   public RandomSource getRandom() {
      return this.spazzingRandom;
   }

   @Override
   public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
      buffer.writeFloat(this.length);
      buffer.writeFloat(this.size);
      buffer.writeFloat(this.rotation);
      buffer.writeInt(this.red);
      buffer.writeInt(this.green);
      buffer.writeInt(this.blue);
      buffer.writeInt(this.alpha);
      buffer.writeInt(this.branches);
      buffer.writeInt(this.segments);
      buffer.writeInt(this.angle);
      buffer.writeInt(this.depth);
      buffer.writeInt(this.segmentLength);
      buffer.writeBoolean(this.visualOnly);
      buffer.writeBoolean(this.lightningMovement);
      buffer.writeBoolean(this.fadeTrail);
   }

   @Override
   public void readSpawnData(RegistryFriendlyByteBuf buffer) {
      this.length = buffer.readFloat();
      this.size = buffer.readFloat();
      this.rotation = buffer.readFloat();
      this.red = buffer.readInt();
      this.green = buffer.readInt();
      this.blue = buffer.readInt();
      this.alpha = buffer.readInt();
      this.branches = buffer.readInt();
      this.segments = buffer.readInt();
      this.angle = buffer.readInt();
      this.depth = buffer.readInt();
      this.segmentLength = buffer.readInt();
      this.visualOnly = buffer.readBoolean();
      this.lightningMovement = buffer.readBoolean();
      this.fadeTrail = buffer.readBoolean();
   }
}
