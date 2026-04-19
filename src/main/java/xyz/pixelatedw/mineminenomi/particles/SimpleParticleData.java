package xyz.pixelatedw.mineminenomi.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;

import java.util.Optional;

public class SimpleParticleData implements ParticleOptions {
    private final ParticleType<SimpleParticleData> type;
    private float red = 1.0F;
    private float green = 1.0F;
    private float blue = 1.0F;
    private float alpha = 1.0F;
    private float size = 1.0F;
    private float rotSpeed = 0.0F;
    private int life = 10;
    private float yaw;
    private float pitch;
    private boolean hasMotionDecay = true;
    private boolean hasScaleDecay = true;
    private boolean faceCamera = false;
    private double motionX;
    private double motionY;
    private double motionZ;
    private Vector3f rotation = new Vector3f(0.0F, 0.0F, 0.0F);
    private int animSpeed = 2;
    private EasingFunction function;
    private EasingDirection easeDirection = EasingDirection.POSITIVE;
    private float easeStrength = 1.0F;

    public SimpleParticleData(ParticleType<SimpleParticleData> type) {
        this.type = type;
    }

    @Override
    public ParticleType<SimpleParticleData> getType() {
        return this.type;
    }

    public static MapCodec<SimpleParticleData> codec(ParticleType<SimpleParticleData> type) {
        return RecordCodecBuilder.mapCodec((RecordCodecBuilder.Instance<SimpleParticleData> instance) -> instance.group(
                net.minecraft.util.ExtraCodecs.VECTOR4F.optionalFieldOf("color", new org.joml.Vector4f(1.0F, 1.0F, 1.0F, 1.0F)).forGetter((SimpleParticleData d) -> new org.joml.Vector4f(d.getRed(), d.getGreen(), d.getBlue(), d.getAlpha())),
                Codec.FLOAT.optionalFieldOf("size", 1.0F).forGetter((SimpleParticleData d) -> d.getSize()),
                Codec.FLOAT.optionalFieldOf("rotSpeed", 0.0F).forGetter((SimpleParticleData d) -> d.getRotationSpeed()),
                Codec.INT.optionalFieldOf("life", 10).forGetter((SimpleParticleData d) -> d.getLife()),
                Codec.FLOAT.listOf().xmap(l -> new org.joml.Vector2f(l.get(0), l.get(1)), v -> java.util.List.of(v.x(), v.y())).optionalFieldOf("look", new org.joml.Vector2f(0.0F, 0.0F)).forGetter((SimpleParticleData d) -> new org.joml.Vector2f(d.getYaw(), d.getPitch())),
                Codec.BOOL.optionalFieldOf("hasMotionDecay", true).forGetter((SimpleParticleData d) -> d.hasMotionDecay()),
                Codec.BOOL.optionalFieldOf("hasScaleDecay", true).forGetter((SimpleParticleData d) -> d.hasScaleDecay()),
                Codec.BOOL.optionalFieldOf("faceCamera", false).forGetter((SimpleParticleData d) -> d.faceCamera()),
                net.minecraft.world.phys.Vec3.CODEC.optionalFieldOf("motion", net.minecraft.world.phys.Vec3.ZERO).forGetter((SimpleParticleData d) -> new net.minecraft.world.phys.Vec3(d.getDeltaMovementX(), d.getDeltaMovementY(), d.getDeltaMovementZ())),
                net.minecraft.util.ExtraCodecs.VECTOR3F.optionalFieldOf("rotation", new Vector3f(0.0F, 0.0F, 0.0F)).forGetter((SimpleParticleData d) -> d.getRotation()),
                Codec.INT.optionalFieldOf("animSpeed", 2).forGetter((SimpleParticleData d) -> d.getAnimationSpeed()),
                EasingFunction.CODEC.optionalFieldOf("function").forGetter((SimpleParticleData d) -> Optional.ofNullable(d.getFunction())),
                EasingDirection.CODEC.optionalFieldOf("easeDirection", EasingDirection.POSITIVE).forGetter((SimpleParticleData d) -> d.getEaseDirection()),
                Codec.FLOAT.optionalFieldOf("easeStrength", 1.0F).forGetter((SimpleParticleData d) -> d.getEaseStrength())
        ).apply(instance, (color, size, rotSpeed, life, look, hasMotionDecay, hasScaleDecay, faceCamera, motion, rotation, animSpeed, function, easeDirection, easeStrength) -> {
            SimpleParticleData data = new SimpleParticleData(type);
            data.red = color.x();
            data.green = color.y();
            data.blue = color.z();
            data.alpha = color.w();
            data.size = size;
            data.rotSpeed = rotSpeed;
            data.life = life;
            data.yaw = look.x();
            data.pitch = look.y();
            data.hasMotionDecay = hasMotionDecay;
            data.hasScaleDecay = hasScaleDecay;
            data.faceCamera = faceCamera;
            data.motionX = motion.x;
            data.motionY = motion.y;
            data.motionZ = motion.z;
            data.rotation = rotation;
            data.animSpeed = animSpeed;
            data.function = function.orElse(null);
            data.easeDirection = easeDirection;
            data.easeStrength = easeStrength;
            return data;
        }));
    }

    public static StreamCodec<RegistryFriendlyByteBuf, SimpleParticleData> streamCodec(ParticleType<SimpleParticleData> type) {
        return StreamCodec.of(
                (buf, val) -> {
                    buf.writeFloat(val.red);
                    buf.writeFloat(val.green);
                    buf.writeFloat(val.blue);
                    buf.writeFloat(val.alpha);
                    buf.writeFloat(val.size);
                    buf.writeFloat(val.rotSpeed);
                    buf.writeInt(val.life);
                    buf.writeFloat(val.yaw);
                    buf.writeFloat(val.pitch);
                    buf.writeBoolean(val.hasMotionDecay);
                    buf.writeBoolean(val.hasScaleDecay);
                    buf.writeBoolean(val.faceCamera);
                    buf.writeDouble(val.motionX);
                    buf.writeDouble(val.motionY);
                    buf.writeDouble(val.motionZ);
                    ByteBufCodecs.VECTOR3F.encode(buf, val.rotation);
                    buf.writeInt(val.animSpeed);
                    EasingFunction.STREAM_CODEC.encode(buf, val.function);
                    EasingDirection.STREAM_CODEC.encode(buf, val.easeDirection);
                    buf.writeFloat(val.easeStrength);
                },
                buf -> {
                    SimpleParticleData data = new SimpleParticleData(type);
                    data.red = buf.readFloat();
                    data.green = buf.readFloat();
                    data.blue = buf.readFloat();
                    data.alpha = buf.readFloat();
                    data.size = buf.readFloat();
                    data.rotSpeed = buf.readFloat();
                    data.life = buf.readInt();
                    data.yaw = buf.readFloat();
                    data.pitch = buf.readFloat();
                    data.hasMotionDecay = buf.readBoolean();
                    data.hasScaleDecay = buf.readBoolean();
                    data.faceCamera = buf.readBoolean();
                    data.motionX = buf.readDouble();
                    data.motionY = buf.readDouble();
                    data.motionZ = buf.readDouble();
                    data.rotation = ByteBufCodecs.VECTOR3F.decode(buf);
                    data.animSpeed = buf.readInt();
                    data.function = EasingFunction.STREAM_CODEC.decode(buf);
                    data.easeDirection = EasingDirection.STREAM_CODEC.decode(buf);
                    data.easeStrength = buf.readFloat();
                    return data;
                }
        );
    }

    // Getters and Setters
    public SimpleParticleData setColor(float red, float green, float blue) { return setColor(red, green, blue, 1.0F); }
    public SimpleParticleData setColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        return this;
    }
    public SimpleParticleData setMotion(double x, double y, double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        return this;
    }
    public SimpleParticleData setSize(float size) { this.size = size; return this; }
    public SimpleParticleData setLife(int life) { this.life = life; return this; }
    public SimpleParticleData setRotationSpeed(float speed) { this.rotSpeed = speed; return this; }
    public SimpleParticleData setRotation(Vector3f rotation) { this.rotation = rotation; return this; }
    public SimpleParticleData setHasMotionDecay(boolean flag) { this.hasMotionDecay = flag; return this; }
    public SimpleParticleData setHasScaleDecay(boolean flag) { this.hasScaleDecay = flag; return this; }
    public SimpleParticleData setFaceCamera(boolean flag) { this.faceCamera = flag; return this; }
    public SimpleParticleData setLookVec(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.faceCamera = true;
        return this;
    }
    public SimpleParticleData setAnimationSpeed(int speed) { this.animSpeed = speed; return this; }
    public SimpleParticleData setFunction(EasingFunction func) { this.function = func; this.setHasScaleDecay(false); return this; }
    public SimpleParticleData setEaseDirection(EasingDirection dir) { this.easeDirection = dir; return this; }
    public SimpleParticleData setEaseStrength(float strength) { this.easeStrength = strength; return this; }

    public float getRed() { return red; }
    public float getGreen() { return green; }
    public float getBlue() { return blue; }
    public float getAlpha() { return alpha; }
    public float getSize() { return size; }
    public float getRotationSpeed() { return rotSpeed; }
    public int getLife() { return life; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public boolean hasMotionDecay() { return hasMotionDecay; }
    public boolean hasScaleDecay() { return hasScaleDecay; }
    public boolean faceCamera() { return faceCamera; }
    public double getDeltaMovementX() { return motionX; }
    public double getDeltaMovementY() { return motionY; }
    public double getDeltaMovementZ() { return motionZ; }
    public Vector3f getRotation() { return rotation; }
    public int getAnimationSpeed() { return animSpeed; }
    public EasingFunction getFunction() { return function; }
    public EasingDirection getEaseDirection() { return easeDirection; }
    public float getEaseStrength() { return easeStrength; }
}
