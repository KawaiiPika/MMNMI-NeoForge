package xyz.pixelatedw.mineminenomi.client.particles.effects.haki;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import java.awt.Color;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;

public class HaoshokuHakiParticleEffect extends Particle {

    protected HaoshokuHakiParticleEffect(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.lifetime = 15;
        this.bbWidth = 3.0F; // Matches legacy
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
        int size = 3;
        int color = 16711680; // Red

        BlockPos ogPos = new BlockPos((int)posX, (int)posY, (int)posZ);

        for (double x = -size; x < size; ++x) {
            for (double z = -size; z < size; ++z) {
                BlockPos pos = new BlockPos((int) (posX + x), (int) posY, (int) (posZ + z));
                if (ogPos.closerToCenterThan(pos.getCenter(), size) && ogPos.hashCode() % 20 >= 5) {
                    BlockState blockState = level.getBlockState(pos.below());
                    if (!blockState.isAir()) {
                        for (int i = 0; i < 10; ++i) {
                            level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                                posX + (level.random.nextInt(7) - 3) + x,
                                posY,
                                posZ + (level.random.nextInt(7) - 3) + z,
                                0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
        }

        Vec3 playerPos = new Vec3(posX, posY, posZ);
        double r = 2.0D;

        for (double phi = 0.0D; phi <= (Math.PI * 2D); phi += 0.04908738521234052D) {
            double x = r * Math.cos(phi) + level.random.nextDouble() / 5.0D;
            double z = r * Math.sin(phi) + level.random.nextDouble() / 5.0D;
            Vec3 pos = playerPos.add(new Vec3(x, posY, z));
            Vec3 dirVec = playerPos.subtract(pos).normalize().multiply(3.5D, 1.0D, 3.5D);
            Color clientRGB = new Color(color);

            // Adding sub-particles to simulate the effect
            level.addParticle(ParticleTypes.CRIT,
                posX + x, posY + 0.3D, posZ + z,
                -dirVec.x, 0.0D, -dirVec.z);
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleData> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleData type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new HaoshokuHakiParticleEffect(level, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
