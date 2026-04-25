package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;
import net.minecraft.client.Minecraft;

import java.util.List;

public record SOpenJollyRogerEditorScreenPacket(boolean isEditing, Crew crew, List<JollyRogerElement> elements) implements CustomPacketPayload {

    public static final Type<SOpenJollyRogerEditorScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_jolly_roger_editor_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenJollyRogerEditorScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SOpenJollyRogerEditorScreenPacket::isEditing,
            ByteBufCodecs.COMPOUND_TAG.map(Crew::from, Crew::write),
            SOpenJollyRogerEditorScreenPacket::crew,
            ByteBufCodecs.collection(java.util.ArrayList::new, ResourceLocation.STREAM_CODEC.map(
                loc -> ModRegistries.JOLLY_ROGER_ELEMENTS.get(loc),
                JollyRogerElement::getRegistryKey
            )),
            SOpenJollyRogerEditorScreenPacket::elements,
            SOpenJollyRogerEditorScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenJollyRogerEditorScreenPacket payload, final net.neoforged.neoforge.network.handling.IPayloadContext context) {
        xyz.pixelatedw.mineminenomi.client.networking.ClientPacketHandlers.handleOpenJollyRogerEditorScreen(payload, context);
    }
}