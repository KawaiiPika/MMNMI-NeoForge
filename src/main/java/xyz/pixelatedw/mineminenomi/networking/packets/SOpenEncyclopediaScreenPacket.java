package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.world.item.ItemStack;

public record SOpenEncyclopediaScreenPacket(ItemStack book) implements CustomPacketPayload {

    public static final Type<SOpenEncyclopediaScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_encyclopedia_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenEncyclopediaScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, SOpenEncyclopediaScreenPacket::book,
            SOpenEncyclopediaScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenEncyclopediaScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            // EncyclopediaScreen is not yet ported
            // EncyclopediaScreen.open(payload.book());
        });
    }
}
