package xyz.pixelatedw.mineminenomi.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.rendertypes.SimpleParticleRenderType;

public class SimpleParticle extends SingleQuadParticle {
   private boolean hasMotionDecay = true;
   private boolean hasScaleDecay = true;
   private Vector3f rotation;
   private float rotationSpeed;
   protected ParticleRenderType type;
   protected final float initialSize;
   private boolean faceCamera;
   private float yaw;
   private float pitch;
   private int animIdx = 0;
   private int animSpeed = 2;
   private int maxFrames = 8;
   private EasingFunction function;
   private EasingDirection easeDir;
   private float easeStrength = 1.0F;

   public SimpleParticle(SimpleParticleData data, ParticleRenderType type, ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
      super(world, x, y, z, (double)0.0F, (double)0.0F, (double)0.0F);
      this.f_107225_ = 30 + this.f_107223_.m_188503_(10);
      this.f_107224_ = 0;
      this.f_107663_ = 0.2F;
      this.f_107226_ = 0.0F;
      this.m_107253_(1.0F, 1.0F, 1.0F);
      this.f_107219_ = false;
      this.type = type;
      this.f_107215_ = xd;
      this.f_107216_ = yd;
      this.f_107217_ = zd;
      this.m_107253_(data.getRed(), data.getGreen(), data.getBlue());
      this.setRotation(data.getRotation());
      this.m_107271_(data.getAlpha());
      this.setParticleSize(data.getSize() / 10.0F);
      this.setParticleRotation(data.getRotationSpeed());
      this.setParticleAge(data.getLife());
      this.setHasMotionDecay(data.hasMotionDecay());
      this.setHasScaleDecay(data.hasScaleDecay());
      this.faceCamera = data.faceCamera();
      this.yaw = data.getYaw();
      this.pitch = data.getPitch();
      this.animSpeed = data.getAnimationSpeed();
      this.function = data.getFunction();
      this.easeDir = data.getEaseDirecetion();
      this.easeStrength = data.getEaseStrength();
      this.initialSize = this.f_107663_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107226_ != 0.0F) {
         this.f_107216_ = -0.04 * (double)this.f_107226_;
      }

      if (this.f_107224_ % this.animSpeed == 0) {
         ++this.animIdx;
      }

      this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
      if (this.hasMotionDecay) {
         this.f_107215_ *= 0.99;
         this.f_107216_ *= 0.99;
         this.f_107217_ *= 0.99;
      }

      if (this.hasScaleDecay) {
         if (this.f_107224_ >= this.f_107225_ / 2 && this.f_107663_ > 0.0F) {
            this.f_107663_ /= 1.1F;
         }

         if (this.f_107224_ + 5 >= this.f_107225_ && this.f_107230_ > 0.0F) {
            this.f_107230_ = (float)((double)this.f_107230_ / 1.15);
         }
      } else {
         if (this.function != null) {
            float speedModifier = 0.0F;
            float progress = Math.min((float)this.f_107224_ / ((float)this.f_107225_ * (1.0F - speedModifier)), 1.0F);
            float result = this.function.apply(progress) * this.easeStrength;
            float quadSize = this.initialSize;
            switch (this.easeDir) {
               case POSITIVE -> quadSize = this.initialSize + this.initialSize * result;
               case NEGATIVE -> quadSize = this.initialSize - this.initialSize * result;
               case HALF_HALF -> quadSize = (double)progress < (double)0.5F ? this.initialSize + this.initialSize * result : this.initialSize - this.initialSize * result;
            }

            this.f_107663_ = quadSize;
         }

         if (this.f_107224_ + 5 >= this.f_107225_ && this.f_107230_ > 0.0F) {
            this.f_107230_ = (float)((double)this.f_107230_ / 1.15);
         }
      }

      if (this.rotationSpeed != 0.0F) {
         this.f_107231_ -= this.rotationSpeed;
      }

      if (this.f_107224_++ >= this.f_107225_ || this.f_107218_) {
         this.m_107274_();
      }

   }

   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      Vec3 Vector3d = renderInfo.m_90583_();
      float f = (float)(Mth.m_14139_((double)partialTicks, this.f_107209_, this.f_107212_) - Vector3d.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTicks, this.f_107210_, this.f_107213_) - Vector3d.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTicks, this.f_107211_, this.f_107214_) - Vector3d.m_7094_());
      Quaternionf quaternion = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
      if (this.faceCamera) {
         quaternion = quaternion.rotateY((float)Math.toRadians((double)(-this.pitch)));
         quaternion = quaternion.rotateX((float)Math.toRadians((double)this.yaw));
      } else if (this.f_107231_ == 0.0F) {
         quaternion = renderInfo.m_253121_();
      } else {
         quaternion = new Quaternionf(renderInfo.m_253121_());
         quaternion = quaternion.rotateAxis(Mth.m_14179_(partialTicks, this.f_107204_, this.f_107231_), this.rotation);
      }

      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      vector3f1.rotate(quaternion);
      Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
      float scale = this.m_5902_(partialTicks);

      for(int i = 0; i < 4; ++i) {
         Vector3f vector3f = avector3f[i];
         vector3f.rotate(quaternion);
         vector3f.mul(scale);
         vector3f.add(f, f1, f2);
      }

      float minU = this.m_5970_();
      float maxU = this.m_5952_();
      float minV = this.m_5951_();
      float maxV = this.m_5950_();
      int brightness = this.m_6355_(partialTicks);
      buffer.m_5483_((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).m_7421_(maxU, maxV).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(brightness).m_5752_();
      buffer.m_5483_((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).m_7421_(maxU, minV).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(brightness).m_5752_();
      buffer.m_5483_((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).m_7421_(minU, minV).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(brightness).m_5752_();
      buffer.m_5483_((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).m_7421_(minU, maxV).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(brightness).m_5752_();
   }

   public void setMaxFrames(int frames) {
      this.maxFrames = frames;
   }

   public SimpleParticle setAlphaF(float f) {
      this.m_107271_(f);
      return this;
   }

   public SimpleParticle setParticleSize(float f) {
      this.f_107663_ = f;
      return this;
   }

   public SimpleParticle setParticleRotation(float f) {
      this.rotationSpeed = f;
      return this;
   }

   public SimpleParticle setParticleGravity(float f) {
      this.f_107226_ = f;
      return this;
   }

   public SimpleParticle setParticleAge(int i) {
      this.f_107225_ = i;
      return this;
   }

   public SimpleParticle setHasMotionDecay(boolean flag) {
      this.hasMotionDecay = flag;
      return this;
   }

   public Vector3f getRotationVec() {
      return this.rotation;
   }

   public SimpleParticle setRotation(Vector3f rotation) {
      this.rotation = rotation;
      return this;
   }

   public SimpleParticle setHasScaleDecay(boolean flag) {
      this.hasScaleDecay = flag;
      return this;
   }

   public Vec3 getPos() {
      return new Vec3(this.f_107212_, this.f_107213_, this.f_107214_);
   }

   public ParticleRenderType m_7556_() {
      return this.type;
   }

   protected float m_5970_() {
      return 0.0F;
   }

   protected float m_5951_() {
      return (float)this.animIdx / (float)this.maxFrames;
   }

   protected float m_5952_() {
      return 1.0F;
   }

   protected float m_5950_() {
      return (float)(this.animIdx + 1) / (float)this.maxFrames;
   }

   public static class Factory implements ParticleProvider<SimpleParticleData> {
      private ParticleRenderType type;
      private int maxFrames;

      public Factory(ResourceLocation res) {
         this(res, 1);
      }

      public Factory(ResourceLocation res, int maxFrames) {
         this.maxFrames = 0;
         this.type = new SimpleParticleRenderType(res);
         this.maxFrames = maxFrames;
      }

      public Particle createParticle(SimpleParticleData data, ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
         SimpleParticle part = new SimpleParticle(data, this.type, world, x, y, z, data.getDeltaMovementX(), data.getDeltaMovementY(), data.getDeltaMovementZ());
         part.setMaxFrames(this.maxFrames);
         return part;
      }
   }
}
