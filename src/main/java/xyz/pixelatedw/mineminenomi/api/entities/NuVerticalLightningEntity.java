package xyz.pixelatedw.mineminenomi.api.entities;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class NuVerticalLightningEntity extends NuLightningEntity {
   private BlockPos originPosition;

   public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> type, Level world) {
      super(type, world);
      this.originPosition = BlockPos.ZERO;
   }

   public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> type, LivingEntity thrower, double posX, double posY, double posZ, int height, float maxTravelDistance, float travelSpeed, @Nullable Object dummy) {
      super(type, thrower, posX, posY, posZ, 0.0F, 90.0F, maxTravelDistance, travelSpeed, dummy);
      this.originPosition = new BlockPos(this.blockPosition().getX(), height, this.blockPosition().getZ());
      this.lookVec = new Vec3(0.0, -1.0, 0.0);
   }

   public void tick() {
      super.tick();
   }

   public BlockPos getOriginPoint() {
      return this.originPosition;
   }

   public double getPreciseSegmentX(float length) {
      return this.originPosition.getX() + this.lookVec.x * (double)length;
   }

   public double getPreciseSegmentY(float length) {
      return this.originPosition.getY() + this.lookVec.y * (double)length;
   }

   public double getPreciseSegmentZ(float length) {
      return this.originPosition.getZ() + this.lookVec.z * (double)length;
   }

   public double getSegmentX(int length) {
      return Math.floor((double)this.originPosition.getX() + this.lookVec.x * (double)length);
   }

   public double getSegmentY(int length) {
      return Math.floor((double)this.originPosition.getY() + this.lookVec.y * (double)length);
   }

   public double getSegmentZ(int length) {
      return Math.floor((double)this.originPosition.getZ() + this.lookVec.z * (double)length);
   }

   public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeBlockPos(this.originPosition);
   }

   public void readSpawnData(RegistryFriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.originPosition = buffer.readBlockPos();
   }
}
