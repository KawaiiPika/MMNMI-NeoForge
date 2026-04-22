package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.networking.ModNetworking;

public record COpenCrewScreenPacket() implements CustomPacketPayload {

    public static final Type<COpenCrewScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_crew_screen_request"));

    public static final StreamCodec<RegistryFriendlyByteBuf, COpenCrewScreenPacket> STREAM_CODEC = StreamCodec.unit(new COpenCrewScreenPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final COpenCrewScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                FactionsWorldData worldData = FactionsWorldData.get();
                if (worldData == null) return;
                Crew crew = worldData.getCrewWithMember(player.getUUID());
                if (crew != null) {
                    ModNetworking.sendTo(new SOpenCrewScreenPacket(crew), player);
                }
            }
        });
    }
}
