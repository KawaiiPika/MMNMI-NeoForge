package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.common.NeoForge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.events.JollyRogerEvent;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

import java.util.UUID;

public record CUpdateJollyRogerPacket(JollyRoger jollyRoger) implements CustomPacketPayload {

    public static final Type<CUpdateJollyRogerPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "update_jolly_roger"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CUpdateJollyRogerPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG.map(tag -> {
                JollyRoger jr = new JollyRoger();
                jr.read(tag);
                return jr;
            }, JollyRoger::write),
            CUpdateJollyRogerPacket::jollyRoger,
            CUpdateJollyRogerPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CUpdateJollyRogerPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                UUID uuid = player.getUUID();
                FactionsWorldData worldData = FactionsWorldData.get();
                if (worldData == null) return;

                Crew crew = worldData.getCrewWithCaptain(uuid);
                if (crew != null) {
                    JollyRogerEvent.Update event = new JollyRogerEvent.Update(payload.jollyRoger(), crew);
                    NeoForge.EVENT_BUS.post(event);
                    worldData.updateCrewJollyRoger(player, crew, payload.jollyRoger());
                }
            }
        });
    }
}
