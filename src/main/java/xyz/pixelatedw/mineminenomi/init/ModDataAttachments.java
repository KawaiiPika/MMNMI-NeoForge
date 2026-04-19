package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class ModDataAttachments {

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerStats>> PLAYER_STATS = ModRegistry.ATTACHMENT_TYPES.register(
            "player_stats", () -> AttachmentType.builder(() -> new PlayerStats()).serialize(PlayerStats.CODEC).copyOnDeath().build()
    );

    public static void init() {
        // Trigger class loading
    }
}
