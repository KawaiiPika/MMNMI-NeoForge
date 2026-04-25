package xyz.pixelatedw.mineminenomi.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.common.NeoForge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent;
import xyz.pixelatedw.mineminenomi.api.ui.SimpleMessageScreenDTO;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public record CCreateCrewPacket(String name) implements CustomPacketPayload {

    public static final Type<CCreateCrewPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "create_crew"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CCreateCrewPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CCreateCrewPacket::name,
            CCreateCrewPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final CCreateCrewPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                PlayerStats props = PlayerStats.get(player);
                if (props != null) {
                    FactionsWorldData worldProps = FactionsWorldData.get();
                    if (worldProps == null) return;

                    boolean hasSakeBottle = !player.getMainHandItem().isEmpty() && player.getMainHandItem().is(ModItems.SAKE_BOTTLE.get());
                    boolean isAlreadyInCrew = worldProps.getCrewWithMember(player.getUUID()) != null;

                    SimpleMessageScreenDTO screenEvent = new SimpleMessageScreenDTO();
                    screenEvent.setTimeVisible(40);

                    boolean isPirate = props.isPirate();

                    if (hasSakeBottle && isPirate) {
                        if (isAlreadyInCrew) {
                            screenEvent.setMessage(Component.translatable("crew.message.already_in_crew"));
                            screenEvent.sendEvent(player);
                        } else {
                            Crew crewCheck = worldProps.getCrewByName(payload.name());
                            if (crewCheck != null) {
                                screenEvent.setMessage(Component.translatable("crew.message.name_exists"));
                                screenEvent.sendEvent(player);
                            } else {
                                Crew crew = new Crew(payload.name(), player);
                                CrewEvent.Create event = new CrewEvent.Create(player, crew);
                                if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
                                    worldProps.addCrew(crew);
                                    crew.create(player.level());
                                    screenEvent.sendEvent(player);

                                    // Dummy for ServerConfig.isCrewWorldMessageEnabled
                                    Component newCrewMsg = Component.translatable("crew.message.new_crew", payload.name());

                                    for (net.minecraft.world.entity.player.Player target : player.level().players()) {
                                        target.displayClientMessage(newCrewMsg, false);
                                    }
                                }
                            }
                        }
                    } else {
                        screenEvent.setMessage(Component.translatable("info.generic_error"));
                        screenEvent.sendEvent(player);
                    }
                }
            }
        });
    }
}
