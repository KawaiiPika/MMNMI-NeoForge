package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import net.minecraft.client.Minecraft;

public record SSyncStrikerCrewPacket(int entityId, Crew crew) implements CustomPacketPayload {

    public static final Type<SSyncStrikerCrewPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sync_striker_crew"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SSyncStrikerCrewPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SSyncStrikerCrewPacket::entityId,
            ByteBufCodecs.COMPOUND_TAG.map(Crew::from, Crew::write),
            SSyncStrikerCrewPacket::crew,
            SSyncStrikerCrewPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SSyncStrikerCrewPacket payload, final IPayloadContext context) {
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> ClientHandler.handle(payload));
        }
    }

    public static class ClientHandler {
        public static void handle(final SSyncStrikerCrewPacket payload) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.level != null) {
                Entity entity = mc.level.getEntity(payload.entityId());
                // TODO: Update StrikerEntity
                // if (entity instanceof xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity striker) {
                //    striker.setCrew(payload.crew());
                // }
            }
        }
    }
}
