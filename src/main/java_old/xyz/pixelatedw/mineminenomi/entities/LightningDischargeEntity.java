package xyz.pixelatedw.mineminenomi.entities;

import java.awt.Color;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class LightningDischargeEntity extends Entity implements IEntityAdditionalSpawnData, TraceableEntity {
   private static final Color DEFAULT_COLOR = WyHelper.hexToRGB("#F0EC7155");
   private UUID ownerUUID;
   private LivingEntity owner;
   private float size;
   private int aliveTicks;
   private float lightningLength;
   private int lightningDensity;
   private int lightningDetail;
   private int color;
   private int alpha;
   private int outlineColor;
   private int outlineAlpha;
   private boolean isSplit;
   private int updateRate;
   private int skipSegments;
   private int renderMode;

   public LightningDischargeEntity(Level world) {
      super((EntityType)ModProjectiles.LIGHTNING_DISCHARGE.get(), world);
      this.size = 1.0F;
      this.aliveTicks = 60;
      this.lightningLength = 1.0F;
      this.lightningDensity = 8;
      this.lightningDetail = 6;
      this.color = DEFAULT_COLOR.getRGB();
      this.alpha = DEFAULT_COLOR.getAlpha();
      this.outlineColor = 0;
      this.outlineAlpha = 0;
      this.isSplit = false;
      this.updateRate = 5;
      this.skipSegments = 0;
      this.renderMode = 0;
   }

   public LightningDischargeEntity(EntityType<? extends LightningDischargeEntity> entityType, Level world) {
      super(entityType, world);
      this.size = 1.0F;
      this.aliveTicks = 60;
      this.lightningLength = 1.0F;
      this.lightningDensity = 8;
      this.lightningDetail = 6;
      this.color = DEFAULT_COLOR.getRGB();
      this.alpha = DEFAULT_COLOR.getAlpha();
      this.outlineColor = 0;
      this.outlineAlpha = 0;
      this.isSplit = false;
      this.updateRate = 5;
      this.skipSegments = 0;
      this.renderMode = 0;
      this.f_19811_ = true;
   }

   public LightningDischargeEntity(Entity entity, double posX, double posY, double posZ, float rotationYaw, float rotationPitch) {
      this((EntityType)ModProjectiles.LIGHTNING_DISCHARGE.get(), entity.m_9236_());
      this.setOwner(entity.m_20148_());
      this.m_7678_(posX, posY, posZ, rotationYaw, rotationPitch);
   }

   public long getSeed() {
      return this.f_19796_.m_188505_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.getOwnerId() == null) {
            this.m_146870_();
            return;
         }

         if (this.getAliveTicks() > 0 && this.f_19797_ >= this.getAliveTicks()) {
            this.m_146870_();
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6783_(double distance) {
      double d0 = (double)64.0F * Entity.m_20150_();
      return distance < d0 * d0;
   }

   public void m_7380_(CompoundTag nbt) {
   }

   public void m_7378_(CompoundTag nbt) {
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.ownerUUID != null);
      if (this.ownerUUID != null) {
         buffer.m_130077_(this.ownerUUID);
      }

      buffer.writeFloat(this.lightningLength);
      buffer.writeInt(this.lightningDensity);
      buffer.writeInt(this.lightningDetail);
      buffer.writeFloat(this.size);
      buffer.writeInt(this.updateRate);
      buffer.writeInt(this.color);
      buffer.writeInt(this.alpha);
      buffer.writeInt(this.outlineColor);
      buffer.writeInt(this.outlineAlpha);
      buffer.writeBoolean(this.isSplit);
      buffer.writeInt(this.skipSegments);
      buffer.writeInt(this.renderMode);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = buffer.readBoolean();
      if (hasOwner) {
         this.ownerUUID = buffer.m_130259_();
      }

      this.lightningLength = buffer.readFloat();
      this.lightningDensity = buffer.readInt();
      this.lightningDetail = buffer.readInt();
      this.size = buffer.readFloat();
      this.updateRate = buffer.readInt();
      this.color = buffer.readInt();
      this.alpha = buffer.readInt();
      this.outlineColor = buffer.readInt();
      this.outlineAlpha = buffer.readInt();
      this.isSplit = buffer.readBoolean();
      this.skipSegments = buffer.readInt();
      this.renderMode = buffer.readInt();
   }

   public void setSkipSegments(int skip) {
      this.skipSegments = skip;
   }

   public void setOwner(@Nullable UUID uuid) {
      this.ownerUUID = uuid;
   }

   public void setUpdateRate(int updateRate) {
      this.updateRate = updateRate;
   }

   public void setSplit() {
      this.isSplit = true;
   }

   public void setDetails(int detail) {
      this.lightningDetail = detail;
   }

   public void setDensity(int density) {
      this.lightningDensity = density;
   }

   public void setSize(float size) {
      this.size = size;
   }

   public void setColor(Color color) {
      this.color = color.getRGB();
      this.alpha = color.getAlpha();
   }

   public void setOutlineColor(Color color) {
      this.outlineColor = color.getRGB();
      this.outlineAlpha = color.getAlpha();
   }

   public void setLightningLength(float len) {
      this.lightningLength = len;
   }

   public int getSkipSegmnets() {
      return this.skipSegments;
   }

   @Nullable
   public UUID getOwnerId() {
      return this.ownerUUID;
   }

   @Nullable
   public Entity m_19749_() {
      if (this.owner == null && this.ownerUUID != null) {
         Level var2 = this.m_9236_();
         if (var2 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var2;
            Entity entity = serverLevel.m_8791_(this.ownerUUID);
            if (entity instanceof LivingEntity) {
               this.owner = (LivingEntity)entity;
            }
         }
      }

      return this.owner;
   }

   public int getUpdateRate() {
      return this.updateRate;
   }

   public boolean isSplit() {
      return this.isSplit;
   }

   public boolean isRenderingTransparent() {
      return this.renderMode == 1;
   }

   public int getRenderMode() {
      return 1;
   }

   public int getDetails() {
      return this.lightningDetail;
   }

   public int getDensity() {
      return this.lightningDensity;
   }

   public float getSize() {
      return this.size;
   }

   public int getColor() {
      return this.color;
   }

   public int getOutlineColor() {
      return this.outlineColor;
   }

   public int getAlpha() {
      return this.alpha;
   }

   public int getOutlineAlpha() {
      return this.outlineAlpha;
   }

   public float getLightningLength() {
      return this.lightningLength;
   }

   public int getAliveTicks() {
      return this.aliveTicks;
   }

   public void setAliveTicks(int ticks) {
      this.aliveTicks = ticks;
   }

   protected void m_8097_() {
   }

   public static record LightningPropieties(Entity entity, long random, float xRot, float yRot, float length) {
   }
}
