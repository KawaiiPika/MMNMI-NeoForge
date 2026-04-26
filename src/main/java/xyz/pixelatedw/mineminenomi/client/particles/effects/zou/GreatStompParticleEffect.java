package xyz.pixelatedw.mineminenomi.client.particles.effects.zou;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;

public class GreatStompParticleEffect extends Particle {

    protected GreatStompParticleEffect(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.lifetime = 15;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.NO_RENDER;
    }

    @Override
    public void tick() {
        if (this.age++ > 0) { this.remove(); return; }

        double phi = 0.0D;
        while(phi < 10.0D) {
            phi += (Math.PI / 10D);
            for(double theta = 0.0D; theta <= 12.566370614359172; theta += 0.19634954084936207) {
                double dx = phi * Math.cos(theta);
                double dy = level.random.nextDouble();
                double dz = phi * Math.sin(theta);
                BlockPos bPos = new BlockPos((int)this.x, (int)this.y, (int)this.z).below();
                BlockState blockState = level.getBlockState(bPos);
                if (blockState.isAir()) {
                    blockState = Blocks.DIRT.defaultBlockState();
                }

                BlockParticleOption particleData = new BlockParticleOption(ParticleTypes.BLOCK, blockState);
                level.addParticle(particleData, true,
                    this.x + (level.random.nextInt(7) - 3) + dx,
                    this.y + dy,
                    this.z + (level.random.nextInt(7) - 3) + dz,
                    0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) { }

    public static class Provider implements ParticleProvider<SimpleParticleData> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) { this.sprites = sprites; }
        @Override
        public Particle createParticle(SimpleParticleData type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new GreatStompParticleEffect(level, x, y, z, dx, dy, dz);
        }
    }
}
