package xyz.pixelatedw.mineminenomi.particles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class SimpleParticleType extends ParticleType<SimpleParticleData> {
    public SimpleParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter);
    }

    @Override
    public MapCodec<SimpleParticleData> codec() {
        return SimpleParticleData.codec(this);
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, SimpleParticleData> streamCodec() {
        return SimpleParticleData.streamCodec(this);
    }
}
