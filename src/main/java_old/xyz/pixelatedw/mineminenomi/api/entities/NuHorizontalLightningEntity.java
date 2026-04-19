package xyz.pixelatedw.mineminenomi.api.entities;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;

public abstract class NuHorizontalLightningEntity extends NuLightningEntity {
   private Vec3 offset;
   private boolean followOwner;

   public NuHorizontalLightningEntity(EntityType<? extends NuHorizontalLightningEntity> type, Level world) {
      super(type, world);
      this.offset = Vec3.f_82478_;
   }

   public NuHorizontalLightningEntity(EntityType<? extends NuHorizontalLightningEntity> type, LivingEntity thrower, float maxTravelDistance, float travelSpeed, IAbility ability) {
      this(type, thrower, thrower.m_20185_(), thrower.m_20186_(), thrower.m_20189_(), maxTravelDistance, travelSpeed, ability, ability.getCore().getSourceElement(), ability.getCore().getSourceHakiNature(), ability.getCore().getSourceTypesArray());
   }

   public NuHorizontalLightningEntity(EntityType<? extends NuHorizontalLightningEntity> type, LivingEntity thrower, double posX, double posY, double posZ, float maxTravelDistance, float travelSpeed, @Nullable IAbility ability, SourceElement element, SourceHakiNature hakiNature, SourceType... types) {
      super(type, thrower, posX, posY, posZ, thrower.m_146908_(), thrower.m_146909_(), maxTravelDistance, travelSpeed, ability, element, hakiNature, types);
      this.offset = Vec3.f_82478_;
      this.offset(thrower, Vec3.f_82478_);
   }

   protected boolean shouldResetForLookChange(Vec3 newLook) {
      if (this.canRetract()) {
         return false;
      } else if (super.lookVec == Vec3.f_82478_) {
         return false;
      } else {
         Vec3 oldLook = super.lookVec.m_82541_();
         double cosTheta = Mth.m_14008_(oldLook.m_82526_(newLook.m_82541_()), (double)-1.0F, (double)1.0F);
         double sinTheta = Math.sqrt((double)1.0F - cosTheta * cosTheta);
         double beamLength = (double)super.getLength();
         double effectiveRadius = Math.max((double)super.getSize(), 0.1) * (double)7.0F;
         return beamLength * sinTheta >= effectiveRadius;
      }
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_) {
         LivingEntity owner = super.getOwner();
         if (this.getOwner() != null && this.followOwner) {
            double x = Mth.m_14139_((double)0.0F, owner.f_19854_, owner.m_20185_());
            double y = Mth.m_14139_((double)0.0F, owner.f_19855_, owner.m_20186_()) + (double)owner.m_20192_();
            double z = Mth.m_14139_((double)0.0F, owner.f_19856_, owner.m_20189_());
            float yRot = owner.m_5675_(0.0F) % 360.0F;
            float xRot = owner.m_5686_(0.0F) % 360.0F;
            Vec3 ownerPos = new Vec3(x, y, z);
            Vec3 pos = VectorHelper.getRelativeOffset(ownerPos, yRot, xRot, this.offset);
            this.m_7678_(pos.f_82479_, pos.f_82480_, pos.f_82481_, yRot, xRot);
            this.lookVec = owner.m_20154_().m_82541_();
         }
      }

      super.m_8119_();
   }

   public void offsetToHand(LivingEntity entity) {
      float dir = entity.m_5737_() == HumanoidArm.RIGHT ? -0.75F : 0.75F;
      Vec3 offset = new Vec3((double)dir, (double)-0.25F, (double)0.0F);
      this.offset(entity, offset);
   }

   public void offset(LivingEntity entity, Vec3 offset) {
      this.offset = offset;
      Vec3 pos = VectorHelper.getRelativeOffset(entity.m_20182_(), entity.m_6080_(), this.offset);
      this.m_7678_(pos.f_82479_, pos.f_82480_ + (double)entity.m_20192_(), pos.f_82481_, entity.m_146908_(), entity.m_146909_());
   }

   public void setFollowOwner() {
      this.followOwner = true;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeDouble(this.offset.f_82479_);
      buffer.writeDouble(this.offset.f_82480_);
      buffer.writeDouble(this.offset.f_82481_);
      buffer.writeBoolean(this.followOwner);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      double offsetX = buffer.readDouble();
      double offsetY = buffer.readDouble();
      double offsetZ = buffer.readDouble();
      this.offset = new Vec3(offsetX, offsetY, offsetZ);
      this.followOwner = buffer.readBoolean();
   }
}
