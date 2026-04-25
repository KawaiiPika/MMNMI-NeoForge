package xyz.pixelatedw.mineminenomi.client.particles.effects.haki;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;


import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import net.minecraft.core.particles.ParticleTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;

public class InternalDestructionBurstParticleEffect extends Particle {

    protected InternalDestructionBurstParticleEffect(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.lifetime = 15;
        this.bbWidth = 10.0F; // Based on old logic
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        float alpha = net.minecraft.client.particle.Particle.LifetimeAlpha.ALWAYS_OPAQUE.currentAlphaForAge(this.age, this.lifetime, partialTicks);
        if (alpha < 0.01F) return;

        double posX = this.x;
        double posY = this.y;
        double posZ = this.z;

        // Visual simulation of Internal Destruction
        for (int i = 0; i < 8; i++) {
            Vec3 moveVec = new Vec3(level.random.nextDouble() * 0.08, level.random.nextDouble() * 0.08, level.random.nextDouble() * 0.08);
            level.addParticle(ParticleTypes.CRIT,
                posX, posY, posZ,
                moveVec.x, moveVec.y, moveVec.z);
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleData> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleData type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new InternalDestructionBurstParticleEffect(level, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
