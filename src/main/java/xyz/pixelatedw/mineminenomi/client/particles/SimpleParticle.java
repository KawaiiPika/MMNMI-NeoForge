package xyz.pixelatedw.mineminenomi.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class SimpleParticle extends Particle {
    private final SimpleParticleData data;
    private final ParticleRenderType type;
    protected float quadSize;
    private int maxFrames = 1;
    private int animIdx = 0;

    public SimpleParticle(ClientLevel level, double x, double y, double z, SimpleParticleData data, ParticleRenderType type) {
        super(level, x, y, z);
        this.data = data;
        this.type = type;
        this.rCol = data.getRed();
        this.gCol = data.getGreen();
        this.bCol = data.getBlue();
        this.alpha = data.getAlpha();
        this.quadSize = data.getSize();
        this.lifetime = data.getLife();
        this.xd = data.getDeltaMovementX();
        this.yd = data.getDeltaMovementY();
        this.zd = data.getDeltaMovementZ();
    }

    public void setMaxFrames(int maxFrames) {
        this.maxFrames = maxFrames;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return this.type;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            if (this.data.getAnimationSpeed() > 0 && this.age % this.data.getAnimationSpeed() == 0) {
                this.animIdx++;
                if (this.animIdx >= this.maxFrames) {
                    this.animIdx = 0;
                }
            }

            if (this.data.hasScaleDecay()) {
                this.quadSize = this.data.getSize() * (1.0F - (float) this.age / (float) this.lifetime);
            }

            if (this.data.getFunction() != null) {
                float progress = (float) this.age / (float) this.lifetime;
                float ease = this.data.getFunction().apply(progress) * this.data.getEaseStrength();
                if (this.data.getEaseDirection() == EasingDirection.NEGATIVE) {
                    ease = -ease;
                } else if (this.data.getEaseDirection() == EasingDirection.HALF_HALF) {
                    ease -= this.data.getEaseStrength() / 2.0F;
                }
                this.quadSize = this.data.getSize() + ease;
            }

            this.move(this.xd, this.yd, this.zd);

            if (this.data.hasMotionDecay()) {
                this.xd *= 0.96;
                this.yd *= 0.96;
                this.zd *= 0.96;
            }

            if (this.onGround) {
                this.xd *= 0.7;
                this.zd *= 0.7;
            }
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        Vector3f[] avec3f = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)
        };
        float f = this.quadSize;

        Quaternionf quaternionf;
        if (this.data.faceCamera()) {
            quaternionf = camera.rotation();
        } else {
            quaternionf = new Quaternionf().rotationXYZ(
                    (float) Math.toRadians(this.data.getRotation().x()),
                    (float) Math.toRadians(this.data.getRotation().y() + (this.data.getRotationSpeed() * ((float) this.age + partialTicks))),
                    (float) Math.toRadians(this.data.getRotation().z())
            );
        }

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = avec3f[i];
            vector3f.rotate(quaternionf);
            vector3f.mul(f);
            vector3f.add((float) Mth.lerp(partialTicks, this.xo, this.x), (float) Mth.lerp(partialTicks, this.yo, this.y), (float) Mth.lerp(partialTicks, this.zo, this.z));
        }

        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = (float) this.animIdx / (float) this.maxFrames;
        float f5 = (float) (this.animIdx + 1) / (float) this.maxFrames;
        
        int j = this.getLightColor(partialTicks);
        buffer.addVertex(avec3f[0].x(), avec3f[0].y(), avec3f[0].z()).setUv(f3, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
        buffer.addVertex(avec3f[1].x(), avec3f[1].y(), avec3f[1].z()).setUv(f3, f4).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
        buffer.addVertex(avec3f[2].x(), avec3f[2].y(), avec3f[2].z()).setUv(f2, f4).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
        buffer.addVertex(avec3f[3].x(), avec3f[3].y(), avec3f[3].z()).setUv(f2, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
    }

    public static class Factory implements ParticleProvider<SimpleParticleData> {
        private final ParticleRenderType type;
        private final int maxFrames;

        public Factory(ResourceLocation texture) {
            this(texture, 1);
        }

        public Factory(ResourceLocation texture, int maxFrames) {
            this.type = new SimpleParticleRenderType(texture);
            this.maxFrames = maxFrames;
        }

        @Override
        public Particle createParticle(SimpleParticleData data, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            SimpleParticle part = new SimpleParticle(level, x, y, z, data, this.type);
            part.setMaxFrames(this.maxFrames);
            return part;
        }
    }
}
