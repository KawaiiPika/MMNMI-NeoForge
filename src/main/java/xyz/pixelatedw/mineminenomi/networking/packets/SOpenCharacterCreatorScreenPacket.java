package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SOpenCharacterCreatorScreenPacket(boolean hasRandomizedRace, boolean allowSubRaceSelect) implements CustomPacketPayload {

    public static final Type<SOpenCharacterCreatorScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_character_creator_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenCharacterCreatorScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SOpenCharacterCreatorScreenPacket::hasRandomizedRace,
            ByteBufCodecs.BOOL,
            SOpenCharacterCreatorScreenPacket::allowSubRaceSelect,
            SOpenCharacterCreatorScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenCharacterCreatorScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> ClientHandler.handle(payload));
    }

    public static class ClientHandler {
        @OnlyIn(Dist.CLIENT)
        public static void handle(final SOpenCharacterCreatorScreenPacket payload) {
            net.minecraft.client.Minecraft.getInstance().setScreen(new xyz.pixelatedw.mineminenomi.ui.screens.CharacterCreatorScreen(payload.hasRandomizedRace(), payload.allowSubRaceSelect()));
        }
    }
}
