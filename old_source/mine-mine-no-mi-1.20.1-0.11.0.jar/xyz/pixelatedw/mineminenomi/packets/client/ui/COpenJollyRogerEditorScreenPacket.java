package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenJollyRogerEditorScreenPacket;

public class COpenJollyRogerEditorScreenPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static COpenJollyRogerEditorScreenPacket decode(FriendlyByteBuf buffer) {
      COpenJollyRogerEditorScreenPacket msg = new COpenJollyRogerEditorScreenPacket();
      return msg;
   }

   public static void handle(COpenJollyRogerEditorScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            Player player = ((NetworkEvent.Context)ctx.get()).getSender();
            FactionsWorldData worldData = FactionsWorldData.get();
            Crew crew = worldData.getCrewWithMember(player.m_20148_());
            if (crew != null) {
               Collection<JollyRogerElement> allElements = ((IForgeRegistry)WyRegistry.JOLLY_ROGER_ELEMENTS.get()).getValues();
               List<JollyRogerElement> elements = allElements.stream().filter((elem) -> elem != null && elem.canUse(player, crew)).toList();
               ModNetwork.sendTo(new SOpenJollyRogerEditorScreenPacket(false, crew, elements), player);
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
