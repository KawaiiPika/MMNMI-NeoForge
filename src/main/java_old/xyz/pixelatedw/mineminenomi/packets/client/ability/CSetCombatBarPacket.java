package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class CSetCombatBarPacket {
   private int bar;

   public CSetCombatBarPacket() {
   }

   public CSetCombatBarPacket(int bar) {
      this.bar = bar;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.bar);
   }

   public static CSetCombatBarPacket decode(FriendlyByteBuf buffer) {
      CSetCombatBarPacket msg = new CSetCombatBarPacket();
      msg.bar = buffer.readInt();
      return msg;
   }

   public static void handle(CSetCombatBarPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            int maxBars = ServerConfig.getAbilityBars();
            if (maxBars >= message.bar + 1) {
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
               if (abilityProps != null) {
                  abilityProps.setCombatBarSet(message.bar);
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
