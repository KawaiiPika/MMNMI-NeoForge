package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.networking.ModNetworking;

import java.util.Collection;
import java.util.List;

public record COpenJollyRogerEditorScreenPacket() implements CustomPacketPayload {

    public static final Type<COpenJollyRogerEditorScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_jolly_roger_editor"));

    public static final StreamCodec<RegistryFriendlyByteBuf, COpenJollyRogerEditorScreenPacket> STREAM_CODEC = StreamCodec.unit(new COpenJollyRogerEditorScreenPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final COpenJollyRogerEditorScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                FactionsWorldData worldData = FactionsWorldData.get();
                if (worldData == null) return;
                Crew crew = worldData.getCrewWithMember(player.getUUID());
                if (crew != null) {
                    Collection<JollyRogerElement> allElements = ModRegistries.JOLLY_ROGER_ELEMENTS.stream().toList();
                    List<JollyRogerElement> elements = allElements.stream().filter((elem) -> elem != null && elem.canUse(player, crew)).toList();
                    ModNetworking.sendTo(new SOpenJollyRogerEditorScreenPacket(false, crew, elements), player);
                }
            }
        });
    }
}
