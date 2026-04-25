package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationStateData;
import xyz.pixelatedw.mineminenomi.data.entity.MorphData;

public class ModDataAttachments {

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerStats>> PLAYER_STATS = ModRegistry.ATTACHMENT_TYPES.register(
            "player_stats", () -> AttachmentType.builder(() -> new PlayerStats()).serialize(PlayerStats.CODEC).sync(PlayerStats.STREAM_CODEC).copyOnDeath().build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> KARMA_VALUE = ModRegistry.ATTACHMENT_TYPES.register(
            "karma_value", () -> AttachmentType.builder(() -> 0).serialize(com.mojang.serialization.Codec.INT).sync(net.minecraft.network.codec.ByteBufCodecs.INT).copyOnDeath().build()
    );
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AnimationStateData>> ANIMATION_STATE = ModRegistry.ATTACHMENT_TYPES.register(
            "animation_state", () -> AttachmentType.builder(() -> new AnimationStateData()).serialize(AnimationStateData.CODEC).sync(AnimationStateData.STREAM_CODEC).build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MorphData>> MORPH_DATA = ModRegistry.ATTACHMENT_TYPES.register(
            "morph_data", () -> AttachmentType.builder(() -> new MorphData()).serialize(MorphData.CODEC).sync(MorphData.STREAM_CODEC).build()
    );

    public static void init() {
        // Trigger class loading
    }
}
