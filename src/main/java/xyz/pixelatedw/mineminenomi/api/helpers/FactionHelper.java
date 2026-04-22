package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;

public class FactionHelper {

   public static void sendMessageToCrew(Level world, Crew crew, Component message) {
      for(Crew.Member member : crew.getMembers()) {
         UUID uuid = member.getUUID();
         Player memberPlayer = world.getPlayerByUUID(uuid);
         if (memberPlayer != null && memberPlayer.isAlive()) {
            memberPlayer.displayClientMessage(message, false);
         }
      }
   }
}
