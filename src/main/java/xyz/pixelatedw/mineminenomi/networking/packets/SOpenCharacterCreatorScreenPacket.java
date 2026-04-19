package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.ui.screens.CharacterCreatorScreen;

public record SOpenCharacterCreatorScreenPacket(boolean hasRandomizedRace, boolean allowSubRaceSelect) implements CustomPacketPayload {
    public static final Type<SOpenCharacterCreatorScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "open_character_creator"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenCharacterCreatorScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SOpenCharacterCreatorScreenPacket::hasRandomizedRace,
            ByteBufCodecs.BOOL, SOpenCharacterCreatorScreenPacket::allowSubRaceSelect,
            SOpenCharacterCreatorScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenCharacterCreatorScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientHandler.handle(payload);
            }
        });
    }

    private static class ClientHandler {
        public static void handle(SOpenCharacterCreatorScreenPacket payload) {
            Minecraft.getInstance().setScreen(new CharacterCreatorScreen(payload.hasRandomizedRace(), payload.allowSubRaceSelect()));
        }
    }
}
