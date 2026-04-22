package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenDTO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ComponentSerialization;

public record SSimpleMessageScreenEventPacket(SimpleMessageScreenDTO event) implements CustomPacketPayload {

    public static final Type<SSimpleMessageScreenEventPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "simple_message_screen_event"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SSimpleMessageScreenEventPacket> STREAM_CODEC = StreamCodec.composite(
            StreamCodec.composite(
                    ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional),
                    dto -> java.util.Optional.ofNullable(dto.getMessage()),
                    ByteBufCodecs.INT,
                    SimpleMessageScreenDTO::getTimeVisible,
                    (messageOpt, timeVisible) -> {
                        SimpleMessageScreenDTO dto = new SimpleMessageScreenDTO();
                        messageOpt.ifPresent(dto::setMessage);
                        dto.setTimeVisible(timeVisible);
                        return dto;
                    }
            ),
            SSimpleMessageScreenEventPacket::event,
            SSimpleMessageScreenEventPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SSimpleMessageScreenEventPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Screen screen = mc.screen;
            if (screen instanceof IEventReceiverScreen eventReceiver) {
                eventReceiver.handleEvent(payload.event());
            }
        });
    }
}
