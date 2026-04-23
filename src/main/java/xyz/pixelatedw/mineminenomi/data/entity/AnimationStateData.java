package xyz.pixelatedw.mineminenomi.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record AnimationStateData(Optional<ResourceLocation> activeAnimation, long startTimeTicks) {

    public static final Codec<AnimationStateData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("activeAnimation").forGetter(AnimationStateData::activeAnimation),
            Codec.LONG.fieldOf("startTimeTicks").forGetter(AnimationStateData::startTimeTicks)
    ).apply(instance, AnimationStateData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AnimationStateData> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public AnimationStateData decode(RegistryFriendlyByteBuf buffer) {
            return new AnimationStateData(
                    ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer),
                    buffer.readLong()
            );
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, AnimationStateData value) {
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.activeAnimation());
            buffer.writeLong(value.startTimeTicks());
        }
    };

    public AnimationStateData() {
        this(Optional.empty(), 0L);
    }
}
