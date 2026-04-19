package xyz.pixelatedw.mineminenomi.api.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateLightningEntity;

public abstract class NuLightningEntity extends NuProjectileEntity {
   private static final int DEFAULT_FADE_TIME = 20;
   private final LightningBolt bolt;
   public long seed;
   protected ArrayList<Integer> targets;
   protected ArrayList<Entity> explosionTargets;
   protected Set<Long> firstContactBlocks;
   protected Vec3 hitPosition;
   protected Vec3 lookVec;
   protected RandomSource spazzingRandom;
   private float length;
   private float size;
   private float maxTravelDistance;
   private float travelSpeed;
   private float rotation;
   private int branches;
   private int segments;
   private int segmentLength;
   private int angle;
   private int targetTimeToReset;
   private int startFadeTime;
   private int fadeTime;
   private int depth;
   private boolean visualOnly;
   private boolean lightningMovement;
   private boolean hasToCheckForTarget;
   private boolean canCauseKnockback;
   private boolean fadeTrail;
   private boolean canRetract;
   private boolean isRetracting;
   private boolean hasLengthChanged;
   private int red;
   private int green;
   private int blue;
   private int alpha;
   private Optional<ExplosionComponent> explosionComponent;

   public NuLightningEntity(EntityType<? extends NuLightningEntity> entityType, Level world) {
      super(entityType, world);
      this.bolt = new LightningBolt(EntityType.f_20465_, this.m_9236_());
      this.targets = new ArrayList();
      this.explosionTargets = new ArrayList();
      this.firstContactBlocks = new LinkedHashSet();
      this.hitPosition = Vec3.f_82478_;
      this.lookVec = Vec3.f_82478_;
      this.spazzingRandom = RandomSource.m_216327_();
      this.length = 0.0F;
      this.size = 1.0F;
      this.maxTravelDistance = 0.0F;
      this.travelSpeed = 12.0F;
      this.rotation = 0.0F;
      this.branches = 1;
      this.segments = 7;
      this.segmentLength = 0;
      this.angle = 0;
      this.targetTimeToReset = 20;
      this.startFadeTime = 20;
      this.fadeTime = -1;
      this.depth = 4;
      this.visualOnly = false;
      this.lightningMovement = true;
      this.hasToCheckForTarget = true;
      this.canCauseKnockback = true;
      this.fadeTrail = false;
      this.canRetract = false;
      this.isRetracting = false;
      this.hasLengthChanged = false;
      this.red = 255;
      this.green = 255;
      this.blue = 255;
      this.alpha = 255;
      this.explosionComponent = Optional.empty();
      this.f_19811_ = true;
      this.seed = this.f_19796_.m_188505_();
   }

   public NuLightningEntity(EntityType<? extends NuLightningEntity> type, LivingEntity thrower, float maxTravelDistance, float travelSpeed, IAbility ability) {
      this(type, thrower, thrower.m_20185_(), thrower.m_20186_(), thrower.m_20189_(), thrower.m_146908_(), thrower.m_146909_(), maxTravelDistance, travelSpeed, ability, ability.getCore().getSourceElement(), ability.getCore().getSourceHakiNature(), ability.getCore().getSourceTypesArray());
   }

   public NuLightningEntity(EntityType<? extends NuLightningEntity> type, LivingEntity thrower, double posX, double posY, double posZ, float rotationYaw, float rotationPitch, float maxTravelDistance, float travelSpeed, IAbility ability) {
      this(type, thrower, posX, posY, posZ, rotationYaw, rotationPitch, maxTravelDistance, travelSpeed, ability, ability.getCore().getSourceElement(), ability.getCore().getSourceHakiNature(), ability.getCore().getSourceTypesArray());
   }

   public NuLightningEntity(EntityType<? extends NuLightningEntity> type, LivingEntity thrower, double posX, double posY, double posZ, float rotationYaw, float rotationPitch, float maxTravelDistance, float travelSpeed, @Nullable IAbility ability, SourceElement element, SourceHakiNature hakiNature, SourceType... types) {
      super(type, thrower.m_9236_(), thrower, ability, element, hakiNature, types);
      this.bolt = new LightningBolt(EntityType.f_20465_, this.m_9236_());
      this.targets = new ArrayList();
      this.explosionTargets = new ArrayList();
      this.firstContactBlocks = new LinkedHashSet();
      this.hitPosition = Vec3.f_82478_;
      this.lookVec = Vec3.f_82478_;
      this.spazzingRandom = RandomSource.m_216327_();
      this.length = 0.0F;
      this.size = 1.0F;
      this.maxTravelDistance = 0.0F;
      this.travelSpeed = 12.0F;
      this.rotation = 0.0F;
      this.branches = 1;
      this.segments = 7;
      this.segmentLength = 0;
      this.angle = 0;
      this.targetTimeToReset = 20;
      this.startFadeTime = 20;
      this.fadeTime = -1;
      this.depth = 4;
      this.visualOnly = false;
      this.lightningMovement = true;
      this.hasToCheckForTarget = true;
      this.canCauseKnockback = true;
      this.fadeTrail = false;
      this.canRetract = false;
      this.isRetracting = false;
      this.hasLengthChanged = false;
      this.red = 255;
      this.green = 255;
      this.blue = 255;
      this.alpha = 255;
      this.explosionComponent = Optional.empty();
      this.setMaxLife(-1);
      this.m_7678_(posX, posY, posZ, rotationYaw, rotationPitch);
      if (ability != null) {
         this.explosionComponent = ability.<ExplosionComponent>getComponent((AbilityComponentKey)ModAbilityComponents.EXPLOSION.get());
         this.explosionComponent.ifPresent((comp) -> {
            comp.setKillOnBlockLimit(false);
            comp.addLimitCheckEvent(100, (_entity, _ability) -> {
               if (comp.hasReachedLimit() && !this.isVisualOnly()) {
                  this.setEarlyLength(this.m_20182_());
                  if (!this.canRetract) {
                     this.setVisualOnly();
                     this.setPassThroughBlocks(false);
                  }

                  this.hasLengthChanged = true;
               }

               return true;
            });
         });
      }

      this.setLength(2.0F);
      this.maxTravelDistance = maxTravelDistance;
      this.travelSpeed = travelSpeed;
      this.f_19811_ = true;
      this.f_19794_ = true;
      this.m_20242_(true);
      this.setPassThroughEntities();
      this.lookVec = this.m_20154_().m_82541_();
      this.setArmorPiercing(0.75F);
   }

   public void onEntityImpact(Entity target) {
      if (!this.targets.contains(target.m_19879_()) && target.m_19879_() != this.m_19879_()) {
         if (this.hasToCheckForTarget) {
            this.targets.add(target.m_19879_());
         }

         EntityHitResult hitResult = new EntityHitResult(target);
         this.triggerEntityHitEvents(hitResult);
         if (this.getDamage() > 0.0F && target.m_6084_() && target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity)target;
            if (this.isVisualOnly()) {
               livingTarget.m_20254_(2 + (int)((float)(this.m_20094_() / 20) + this.getDamage() / 5.0F));
               livingTarget.m_8038_((ServerLevel)this.m_20193_(), this.bolt);
            }

            target.f_19802_ = 0;
            target.m_6469_(this.getDamageSource(), this.getDamage());
         }

      }
   }

   public void onBlockImpact(BlockPos pos) {
      Direction dir = Direction.m_122372_((float)pos.m_123341_(), (float)pos.m_123342_(), (float)pos.m_123343_());
      BlockHitResult hitResult = new BlockHitResult(Vec3.m_82512_(pos), dir, pos, false);
      BlockState before = this.m_9236_().m_8055_(pos);
      super.m_8060_(hitResult);
      BlockState after = this.m_9236_().m_8055_(pos);
      boolean hasBlockSurvived = !before.m_60795_() && before.equals(after);
      if (hasBlockSurvived && this.getLength() != this.maxTravelDistance) {
         this.setEarlyLength(Vec3.m_82512_(pos));
      }

   }

   protected void setEarlyLength(Vec3 pos) {
      if (!this.canPassThroughBlocks()) {
         this.maxTravelDistance = this.getLength();
         this.hitPosition = pos;
      }
   }

   public void m_8119_() {
      if (this.getOwner() != null && this.getOwner().m_6084_()) {
         if (this.getLightningMovement()) {
            this.seed = this.f_19796_.m_188505_();
         }

         if (!this.m_9236_().f_46443_) {
            if (!this.isRetracting && this.getLength() != this.maxTravelDistance) {
               this.setLength(Math.min(this.getLength() + this.travelSpeed, this.maxTravelDistance));
            } else if (this.canRetract && this.isRetracting && this.getLength() > 0.0F) {
               this.setLength(Math.max(this.getLength() - this.travelSpeed, 0.0F));
            }

            if (this.f_19797_ % this.targetTimeToReset == 0 && this.hasToCheckForTarget) {
               this.targets.clear();
               this.explosionTargets.clear();
            }

            this.hitPosition = this.m_20182_();

            for(int i = 0; (float)i < this.getLength(); ++i) {
               double currentX = this.getSegmentX(i);
               double currentY = this.getSegmentY(i);
               double currentZ = this.getSegmentZ(i);
               BlockPos pos = BlockPos.m_274561_(currentX, currentY, currentZ);
               BlockState state = this.m_9236_().m_8055_(pos);
               boolean isVanillaBarrier = state.m_60734_() == Blocks.f_50375_ && !NuWorld.isChallengeDimension(this.m_9236_());
               if (isVanillaBarrier || state.m_60734_() == ModBlocks.BARRIER.get() || state.m_204336_(ModTags.Blocks.KAIROSEKI)) {
                  this.m_142687_(RemovalReason.DISCARDED);
                  return;
               }

               if (!this.isVisualOnly()) {
                  AABB aabb = this.getCollisionBox(currentX, currentY, currentZ);
                  if (this.fadeTime < 0 || (double)this.fadeTime > (double)this.startFadeTime * (double)0.5F) {
                     List<Entity> entityList = TargetHelper.<Entity>getEntitiesInArea(this.m_9236_(), this.getOwner(), aabb, this.targetsCheck, Entity.class);
                     entityList.forEach(this::onEntityImpact);
                  }

                  if (this.hasLengthChanged) {
                     long packedPos = pos.m_121878_();
                     if (!state.m_60795_() && !this.firstContactBlocks.contains(packedPos)) {
                        this.firstContactBlocks.add(packedPos);
                     }
                  }
               }
            }

            if (this.hasLengthChanged) {
               this.updateClient();

               for(long packedPos : this.firstContactBlocks) {
                  this.onBlockImpact(BlockPos.m_122022_(packedPos));
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
                  this.setMaxLife(this.startFadeTime);
               }

               this.hasLengthChanged = false;
               this.updateClient();
            } else if (this.isRetracting && this.getLength() <= 0.0F) {
               this.m_146870_();
            }
         }

         super.m_8119_();
      } else {
         this.m_146870_();
      }
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
      return this.m_20185_() + this.lookVec.f_82479_ * (double)length;
   }

   public double getPreciseSegmentY(float length) {
      return this.m_20188_() + this.lookVec.f_82480_ * (double)length;
   }

   public double getPreciseSegmentZ(float length) {
      return this.m_20189_() + this.lookVec.f_82481_ * (double)length;
   }

   public double getSegmentX(int length) {
      return Math.floor(this.m_20185_() + this.lookVec.f_82479_ * (double)length);
   }

   public double getSegmentY(int length) {
      return Math.floor(this.m_20188_() + this.lookVec.f_82480_ * (double)length);
   }

   public double getSegmentZ(int length) {
      return Math.floor(this.m_20189_() + this.lookVec.f_82481_ * (double)length);
   }

   public AABB getCollisionBox(double x, double y, double z) {
      double boxSize = Math.max((double)this.getSize(), 0.1) * (double)7.0F;
      return new AABB(x - boxSize, y - boxSize, z - boxSize, x + boxSize, y + boxSize, z + boxSize);
   }

   public boolean hasLineOfSight(Entity target) {
      if (target.m_9236_() != this.m_9236_()) {
         return false;
      } else {
         Vec3 startVec = this.hitPosition;
         Vec3 targetVec = new Vec3(target.m_20185_(), target.m_20188_(), target.m_20189_());
         if (targetVec.m_82554_(startVec) > (double)128.0F) {
            return false;
         } else {
            return this.m_9236_().m_45547_(new ClipContext(startVec, targetVec, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
         }
      }
   }

   public void m_37283_() {
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6783_(double distance) {
      double d0 = (double)512.0F * Entity.m_20150_();
      return distance < d0 * d0;
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
      return this.length;
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

   public void setColor(Color color) {
      this.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
   }

   public void setColor(Color color, int alpha) {
      this.setColor(color.getRed(), color.getGreen(), color.getBlue(), alpha);
   }

   public void setColor(int red, int green, int blue, int alpha) {
      this.red = Mth.m_14045_(red, 0, 255);
      this.green = Mth.m_14045_(green, 0, 255);
      this.blue = Mth.m_14045_(blue, 0, 255);
      this.alpha = Mth.m_14045_(alpha, 0, 255);
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
      this.angle = Math.round((float)Mth.m_14045_(angle, 0, 180));
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
      if (!this.m_9236_().f_46443_ && this.getOwner() != null) {
         ModNetwork.sendToAllAround(new SUpdateLightningEntity(this), this.getOwner());
      }

   }

   public RandomSource getRandom() {
      return this.spazzingRandom;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
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

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
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
