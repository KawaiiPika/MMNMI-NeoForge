package xyz.pixelatedw.mineminenomi.entities;

import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.renderers.ArenaSkybox;

public class SphereEntity extends Entity implements IEntityAdditionalSpawnData {
   private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/skyboxes/default.png");
   private static final EntityDataAccessor<Float> RADIUS;
   @Nullable
   private LivingEntity spawner;
   private Vec3 oldPos;
   private ResourceLocation[] textures;
   private Color color;
   private int animationSpeed;
   private int detailLevel;
   private boolean hasSpawner;
   private boolean isFullWrapping;
   private boolean move;
   @OnlyIn(Dist.CLIENT)
   private ArenaSkybox cachedSkybox;
   private final PriorityEventPool<ITickEvent> tickEvents;

   public SphereEntity(EntityType<? extends SphereEntity> type, Level world) {
      super(type, world);
      this.textures = new ResourceLocation[]{DEFAULT_TEXTURE};
      this.color = Color.WHITE;
      this.animationSpeed = 1;
      this.detailLevel = 8;
      this.move = false;
      this.tickEvents = new PriorityEventPool<ITickEvent>();
   }

   public SphereEntity(Level world) {
      this((EntityType)ModEntities.SPHERE.get(), world);
   }

   public SphereEntity(Level world, LivingEntity spawner) {
      this((EntityType)ModEntities.SPHERE.get(), world);
      this.spawner = spawner;
      this.hasSpawner = true;
      this.m_6034_(spawner.m_20185_(), spawner.m_20186_(), spawner.m_20189_());
   }

   public void setTexture(boolean isFullWrapping, ResourceLocation... textures) {
      this.textures = textures;
      this.isFullWrapping = isFullWrapping;
   }

   public ResourceLocation[] getTexture() {
      return this.textures;
   }

   public boolean isFullWrapping() {
      return this.isFullWrapping;
   }

   public void setAnimationSpeed(int animationSpeed) {
      this.animationSpeed = animationSpeed;
   }

   public int getAnimationSpeed() {
      return this.animationSpeed;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public Color getColor() {
      return this.color;
   }

   public void setRadius(float radius) {
      this.m_20088_().m_135381_(RADIUS, radius);
   }

   public float getRadius() {
      return (Float)this.m_20088_().m_135370_(RADIUS);
   }

   public void setDetailLevel(int detail) {
      this.detailLevel = detail;
   }

   public int getDetailLevel() {
      return this.detailLevel;
   }

   public void addTickEvent(ITickEvent event) {
      this.tickEvents.addEvent(event);
   }

   @OnlyIn(Dist.CLIENT)
   public ArenaSkybox getSkybox() {
      if (this.cachedSkybox == null) {
         this.cachedSkybox = new ArenaSkybox();
         this.cachedSkybox.setTexture(this.isFullWrapping, this.textures);
         this.cachedSkybox.setAnimationSpeed(this.animationSpeed);
         this.cachedSkybox.setColor(this.color);
         this.cachedSkybox.setDetailLevel(this.detailLevel);
      }

      this.cachedSkybox.setRadius(this.getRadius());
      return this.cachedSkybox;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(RADIUS, 1.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.oldPos == null) {
         this.oldPos = this.m_20182_();
      }

      if (!this.move) {
         if (!this.m_20182_().equals(this.oldPos)) {
            this.m_6034_(this.oldPos.f_82479_, this.oldPos.f_82480_, this.oldPos.f_82481_);
         }
      } else {
         this.oldPos = this.m_20182_();
         this.move = false;
      }

      if (this.f_19797_ % 20 == 0) {
         if (this.hasSpawner && (this.spawner == null || !this.spawner.m_6084_())) {
            this.m_146870_();
            return;
         }

         this.tickEvents.dispatch((event) -> event.tick(this));
      }

   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6000_(double pX, double pY, double pZ) {
      return true;
   }

   protected void m_7380_(CompoundTag tag) {
      tag.m_128379_("hasSpawner", this.hasSpawner);
      tag.m_128405_("textures", this.textures.length);

      for(int i = 0; i < this.textures.length; ++i) {
         tag.m_128359_("texture" + i, this.textures[i].toString());
      }

      tag.m_128379_("isFullWrapping", this.isFullWrapping);
      tag.m_128405_("animationSpeed", this.animationSpeed);
      tag.m_128405_("rgb", this.color.getRGB());
      tag.m_128405_("alpha", this.color.getAlpha());
      tag.m_128350_("radius", (Float)this.m_20088_().m_135370_(RADIUS));
      tag.m_128405_("detailLevel", this.detailLevel);
      tag.m_128379_("move", this.move);
   }

   protected void m_7378_(CompoundTag tag) {
      this.hasSpawner = tag.m_128471_("hasSpawner");
      int texs = tag.m_128451_("textures");
      this.textures = new ResourceLocation[texs];

      for(int i = 0; i < texs; ++i) {
         ResourceLocation tex = ResourceLocation.m_135820_(tag.m_128461_("texture" + i));
         this.textures[i] = tex != null ? tex : DEFAULT_TEXTURE;
      }

      this.isFullWrapping = tag.m_128471_("isFullWrapping");
      this.animationSpeed = tag.m_128451_("animationSpeed");
      this.color = WyHelper.intToRGB(tag.m_128451_("rgb"), tag.m_128451_("alpha"));
      this.m_20088_().m_135381_(RADIUS, tag.m_128457_("radius"));
      this.detailLevel = tag.m_128451_("detailLevel");
      this.move = tag.m_128471_("move");
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.textures.length);

      for(int i = 0; i < this.textures.length; ++i) {
         buffer.m_130085_(this.textures[i]);
      }

      buffer.writeBoolean(this.isFullWrapping);
      buffer.writeInt(this.animationSpeed);
      buffer.writeInt(this.color.getRGB());
      buffer.writeInt(this.color.getAlpha());
      buffer.writeInt(this.detailLevel);
      buffer.writeBoolean(this.move);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      int textures = buffer.readInt();
      this.textures = new ResourceLocation[textures];

      for(int i = 0; i < textures; ++i) {
         this.textures[i] = buffer.m_130281_();
      }

      this.isFullWrapping = buffer.readBoolean();
      this.animationSpeed = buffer.readInt();
      int rgb = buffer.readInt();
      int alpha = buffer.readInt();
      this.color = WyHelper.intToRGB(rgb, alpha);
      this.detailLevel = buffer.readInt();
      this.move = buffer.readBoolean();
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   static {
      RADIUS = SynchedEntityData.m_135353_(SphereEntity.class, EntityDataSerializers.f_135029_);
   }

   @FunctionalInterface
   public interface ITickEvent {
      void tick(SphereEntity var1);
   }
}
