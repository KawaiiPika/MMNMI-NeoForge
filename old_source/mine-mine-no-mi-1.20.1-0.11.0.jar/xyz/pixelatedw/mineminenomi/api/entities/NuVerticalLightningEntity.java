package xyz.pixelatedw.mineminenomi.api.entities;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;

public abstract class NuVerticalLightningEntity extends NuLightningEntity {
   private BlockPos originPosition;
   private int height;

   public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> type, Level world) {
      super(type, world);
      this.originPosition = BlockPos.f_121853_;
   }

   public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> type, LivingEntity thrower, double posX, double posY, double posZ, int height, float maxTravelDistance, float travelSpeed, IAbility ability) {
      this(type, thrower, posX, posY, posZ, height, maxTravelDistance, travelSpeed, ability, ability.getCore().getSourceElement(), ability.getCore().getSourceHakiNature(), ability.getCore().getSourceTypesArray());
   }

   public NuVerticalLightningEntity(EntityType<? extends NuVerticalLightningEntity> type, LivingEntity thrower, double posX, double posY, double posZ, int height, float maxTravelDistance, float travelSpeed, @Nullable IAbility ability, SourceElement element, SourceHakiNature hakiNature, SourceType... types) {
      super(type, thrower, posX, posY, posZ, 0.0F, 90.0F, maxTravelDistance, travelSpeed, ability, element, hakiNature, types);
      this.originPosition = BlockPos.f_121853_;
      this.height = height;
      this.originPosition = new BlockPos(this.m_20183_().m_123341_(), height, this.m_20183_().m_123343_());
   }

   public void m_8119_() {
      super.m_8119_();
   }

   public double getSegmentX(int length) {
      return Math.floor((double)this.originPosition.m_123341_() + this.lookVec.f_82479_ * (double)length);
   }

   public double getSegmentY(int length) {
      return Math.floor((double)this.originPosition.m_123342_() + this.lookVec.f_82480_ * (double)length);
   }

   public double getSegmentZ(int length) {
      return Math.floor((double)this.originPosition.m_123343_() + this.lookVec.f_82481_ * (double)length);
   }

   public BlockPos getOriginPoint() {
      return this.originPosition;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.m_130064_(this.originPosition);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      super.readSpawnData(buffer);
      this.originPosition = buffer.m_130135_();
   }
}
