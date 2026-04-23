package xyz.pixelatedw.mineminenomi.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record MorphData(Optional<ResourceLocation> currentMorph, List<ResourceLocation> activeMorphs) {

    public static final Codec<MorphData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("currentMorph").forGetter(MorphData::currentMorph),
            ResourceLocation.CODEC.listOf().fieldOf("activeMorphs").forGetter(MorphData::activeMorphs)
    ).apply(instance, MorphData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MorphData> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public MorphData decode(RegistryFriendlyByteBuf buffer) {
            return new MorphData(
                    ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buffer),
                    ByteBufCodecs.collection(ArrayList::new, ResourceLocation.STREAM_CODEC).decode(buffer)
            );
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, MorphData value) {
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buffer, value.currentMorph());
            ByteBufCodecs.collection(java.util.ArrayList<ResourceLocation>::new, ResourceLocation.STREAM_CODEC).encode(buffer, new ArrayList<>(value.activeMorphs()));
        }
    };

    public MorphData() {
        this(Optional.empty(), new ArrayList<>());
    }
}
