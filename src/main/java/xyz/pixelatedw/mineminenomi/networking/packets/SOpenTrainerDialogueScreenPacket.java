package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public record SOpenTrainerDialogueScreenPacket(int questGiverEntityId) implements CustomPacketPayload {

    public static final Type<SOpenTrainerDialogueScreenPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "open_trainer_dialogue_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SOpenTrainerDialogueScreenPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SOpenTrainerDialogueScreenPacket::questGiverEntityId,
            SOpenTrainerDialogueScreenPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final SOpenTrainerDialogueScreenPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            // Screen not ported yet, doing nothing for now but packet parses successfully.
            // Entity entity = Minecraft.getInstance().level.getEntity(payload.questGiverEntityId());
            // if (entity instanceof ITrainer && entity instanceof LivingEntity) {
            //     Minecraft.getInstance().setScreen(new TrainerDialogueScreen((LivingEntity) entity, ...));
            // }
        });
    }
}
