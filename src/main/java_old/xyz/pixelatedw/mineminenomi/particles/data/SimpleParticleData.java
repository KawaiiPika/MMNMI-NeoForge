package xyz.pixelatedw.mineminenomi.particles.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;

public class SimpleParticleData extends ParticleType<SimpleParticleData> implements ParticleOptions {
   public static final ParticleOptions.Deserializer<SimpleParticleData> DESERIALIZER = new ParticleOptions.Deserializer<SimpleParticleData>() {
      public SimpleParticleData fromCommand(ParticleType<SimpleParticleData> particleType, StringReader stringReader) throws CommandSyntaxException {
         SimpleParticleData part = (SimpleParticleData)particleType;
         part.setSize(2.0F);
         return part;
      }

      public SimpleParticleData fromNetwork(ParticleType<SimpleParticleData> particleType, FriendlyByteBuf packetBuffer) {
         float red = packetBuffer.readFloat();
         float green = packetBuffer.readFloat();
         float blue = packetBuffer.readFloat();
         float alpha = packetBuffer.readFloat();
         float size = packetBuffer.readFloat();
         float rot = packetBuffer.readFloat();
         int life = packetBuffer.readInt();
         float yaw = packetBuffer.readFloat();
         float pitch = packetBuffer.readFloat();
         boolean hasMotionDecay = packetBuffer.readBoolean();
         boolean hasScaleDecay = packetBuffer.readBoolean();
         boolean faceCamera = packetBuffer.readBoolean();
         double motionX = packetBuffer.readDouble();
         double motionY = packetBuffer.readDouble();
         double motionZ = packetBuffer.readDouble();
         float rotX = packetBuffer.readFloat();
         float rotY = packetBuffer.readFloat();
         float rotZ = packetBuffer.readFloat();
         Vector3f rotation = new Vector3f(rotX, rotY, rotZ);
         int animSpeed = packetBuffer.readInt();
         int easeFunc = packetBuffer.readInt();
         int easeDir = packetBuffer.readInt();
         float easeStr = packetBuffer.readFloat();
         SimpleParticleData data = new SimpleParticleData(particleType);
         data.setColor(red, green, blue, alpha);
         data.setMotion(motionX, motionY, motionZ);
         data.setRotation(rotation);
         data.setSize(size);
         data.setRotationSpeed(rot);
         data.setLife(life);
         data.setLookVec(yaw, pitch);
         data.setHasMotionDecay(hasMotionDecay);
         data.setHasScaleDecay(hasScaleDecay);
         data.setAnimationSpeed(animSpeed);
         data.setFunction(easeFunc);
         data.setEaseDirection(easeDir);
         data.setEaseStrength(easeStr);
         return data;
      }
   };
   private final Codec<SimpleParticleData> codec = Codec.unit(this);
   private float red = 1.0F;
   private float green = 1.0F;
   private float blue = 1.0F;
   private double motionX;
   private double motionY;
   private double motionZ;
   private Vector3f rotation = new Vector3f(0.0F, 0.0F, 0.0F);
   private float alpha = 1.0F;
   private float size = 1.0F;
   private float rotSpeed = 0.0F;
   private int life = 10;
   private boolean hasMotionDecay = true;
   private boolean hasScaleDecay = true;
   private boolean faceCamera = false;
   private float yaw;
   private float pitch;
   private int animSpeed = 2;
   private int function = -1;
   private int easeDirection;
   private float easeStrength = 1.0F;
   protected ParticleType<?> type;

   public SimpleParticleData() {
      super(true, DESERIALIZER);
      this.type = this;
   }

   public SimpleParticleData(ParticleType<?> type) {
      super(true, DESERIALIZER);
      this.type = type;
   }

   public SimpleParticleData(ParticleType<?> type, ParticleOptions.Deserializer<SimpleParticleData> des) {
      super(true, des);
      this.type = type;
   }

   public SimpleParticleData(boolean limiter, ParticleOptions.Deserializer<SimpleParticleData> des) {
      super(limiter, des);
      this.type = this;
   }

   public void m_7711_(FriendlyByteBuf buffer) {
      buffer.writeFloat(this.red);
      buffer.writeFloat(this.green);
      buffer.writeFloat(this.blue);
      buffer.writeFloat(this.alpha);
      buffer.writeFloat(this.size);
      buffer.writeFloat(this.rotSpeed);
      buffer.writeInt(this.life);
      buffer.writeFloat(this.yaw);
      buffer.writeFloat(this.pitch);
      buffer.writeBoolean(this.hasMotionDecay);
      buffer.writeBoolean(this.hasScaleDecay);
      buffer.writeBoolean(this.faceCamera);
      buffer.writeDouble(this.motionX);
      buffer.writeDouble(this.motionY);
      buffer.writeDouble(this.motionZ);
      buffer.writeFloat(this.rotation.x);
      buffer.writeFloat(this.rotation.y);
      buffer.writeFloat(this.rotation.z);
      buffer.writeInt(this.animSpeed);
      buffer.writeInt(this.function);
      buffer.writeInt(this.easeDirection);
      buffer.writeFloat(this.easeStrength);
   }

   public void setLookVec(float yaw, float pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
      this.faceCamera = true;
   }

   public boolean faceCamera() {
      return this.faceCamera;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public SimpleParticleData setMotion(double motionX, double motionY, double motionZ) {
      this.motionX = motionX;
      this.motionY = motionY;
      this.motionZ = motionZ;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public SimpleParticleData setRotation(float x, float y, float z) {
      return this.setRotation(new Vector3f(x, y, z));
   }

   /** @deprecated */
   @Deprecated
   public SimpleParticleData setRotation(Vector3f axis) {
      this.rotation = axis;
      this.rotSpeed = 0.3F;
      return this;
   }

   public void setAnimationSpeed(int speed) {
      this.animSpeed = speed;
   }

   public int getAnimationSpeed() {
      return this.animSpeed;
   }

   public void setEaseStrength(float val) {
      this.easeStrength = val;
   }

   public float getEaseStrength() {
      return this.easeStrength;
   }

   public void setEaseDirection(int id) {
      this.easeDirection = id;
   }

   public void setEaseDirection(EasingDirection dir) {
      this.easeDirection = dir.ordinal();
   }

   public EasingDirection getEaseDirecetion() {
      return EasingDirection.values()[this.easeDirection];
   }

   public void setFunction(int id) {
      this.function = id;
      this.setHasScaleDecay(false);
   }

   public void setFunction(EasingFunction func) {
      this.setFunction(func.ordinal());
   }

   public EasingFunction getFunction() {
      return this.function < 0 ? null : EasingFunction.values()[this.function];
   }

   public SimpleParticleData setColor(float red, float green, float blue) {
      return this.setColor(red, green, blue, 1.0F);
   }

   public SimpleParticleData setColor(float red, float green, float blue, float alpha) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      return this;
   }

   public SimpleParticleData setRotationSpeed(float rot) {
      this.rotSpeed = rot;
      return this;
   }

   public SimpleParticleData setSize(float size) {
      this.size = size;
      return this;
   }

   public SimpleParticleData setLife(int life) {
      this.life = life;
      return this;
   }

   public SimpleParticleData setHasMotionDecay(boolean flag) {
      this.hasMotionDecay = flag;
      return this;
   }

   public SimpleParticleData setHasScaleDecay(boolean flag) {
      this.hasScaleDecay = flag;
      return this;
   }

   public float getRed() {
      return this.red;
   }

   public float getGreen() {
      return this.green;
   }

   public float getBlue() {
      return this.blue;
   }

   public float getAlpha() {
      return this.alpha;
   }

   public float getSize() {
      return this.size;
   }

   public float getRotationSpeed() {
      return this.rotSpeed;
   }

   public int getLife() {
      return this.life;
   }

   public double getDeltaMovementX() {
      return this.motionX;
   }

   public double getDeltaMovementY() {
      return this.motionY;
   }

   public double getDeltaMovementZ() {
      return this.motionZ;
   }

   public Vector3f getRotation() {
      return this.rotation;
   }

   public boolean hasMotionDecay() {
      return this.hasMotionDecay;
   }

   public boolean hasScaleDecay() {
      return this.hasScaleDecay;
   }

   public String m_5942_() {
      return this.m_6012_().toString();
   }

   public ParticleType<?> m_6012_() {
      return this.type;
   }

   public Codec<SimpleParticleData> m_7652_() {
      return this.codec;
   }
}
