package xyz.pixelatedw.mineminenomi.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.client.render.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class CustomBeamParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    private final Particle.LifetimeAlpha alphaFader;

    protected CustomBeamParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, SpriteSet sprites) {
        super(level, x, y, z, dx, dy, dz);
        this.sprites = sprites;
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;
        this.lifetime = 10 + this.random.nextInt(10);
        this.setSpriteFromAge(sprites);
        this.hasPhysics = false;
        this.alphaFader = new Particle.LifetimeAlpha(1.0F, 0.0F, 0.0F, 1.0F);
    }


    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }


    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }


    public void renderCustom(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera, float partialTick) {
        this.alpha = this.alphaFader.currentAlphaForAge(this.age, this.lifetime, partialTick);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(ModRenderTypes.LIGHTNING);
        super.render(vertexConsumer, camera, partialTick);
    }

    public static class Provider implements ParticleProvider<SimpleParticleData> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }


        public Particle createParticle(SimpleParticleData type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            CustomBeamParticle particle = new CustomBeamParticle(level, x, y, z, dx, dy, dz, this.sprites);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}
