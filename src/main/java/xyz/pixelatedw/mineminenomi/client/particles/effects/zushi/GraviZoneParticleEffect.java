package xyz.pixelatedw.mineminenomi.client.particles.effects.zushi;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;

public class GraviZoneParticleEffect extends Particle {

    private final int range;
    private final int yOffset;

    protected GraviZoneParticleEffect(ClientLevel level, double x, double y, double z, int range, int yOffset) {
        super(level, x, y, z);
        this.range = range;
        this.yOffset = yOffset;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.NO_RENDER;
    }

    @Override
    public void tick() {
        if (this.age++ > 0) { this.remove(); return; }

        for(double z = 0.0D; z < 7.283185307179586; z += 0.09817477042468103) {
            double offsetX = Math.cos(z) * this.range;
            double offsetZ = Math.sin(z) * this.range;
            level.addParticle(new SimpleParticleData(ModParticleTypes.GASU.get()), true,
                this.x + offsetX, this.y + 1.0D + this.yOffset, this.z + offsetZ,
                offsetX / 20.0D, 0.0D, offsetZ / 20.0D);
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) { }

    public static class Provider implements ParticleProvider<SimpleParticleData> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) { this.sprites = sprites; }
        @Override
        public Particle createParticle(SimpleParticleData type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new GraviZoneParticleEffect(level, x, y, z, (int)dx, (int)dy);
        }
    }
}
