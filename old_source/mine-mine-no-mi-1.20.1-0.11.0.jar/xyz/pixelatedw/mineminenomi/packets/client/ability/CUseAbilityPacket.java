package xyz.pixelatedw.mineminenomi.packets.client.ability;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityCanUseEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class CUseAbilityPacket {
   private int slot;

   public CUseAbilityPacket() {
   }

   public CUseAbilityPacket(int slot) {
      this.slot = slot;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.slot);
   }

   public static CUseAbilityPacket decode(FriendlyByteBuf buffer) {
      CUseAbilityPacket msg = new CUseAbilityPacket();
      msg.slot = buffer.readInt();
      return msg;
   }

   public static void handle(CUseAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            player.m_20193_().m_46473_().m_6180_("abilityUse");
            IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (abilityData != null) {
               Ability abl = (Ability)abilityData.getEquippedAbility(message.slot);
               if (abl != null && !player.m_5833_()) {
                  AbilityCanUseEvent pre = new AbilityCanUseEvent(player, abl);
                  if (!MinecraftForge.EVENT_BUS.post(pre)) {
                     if (message.slot <= 8 * ServerConfig.getAbilityBars()) {
                        abl.use(player);
                        player.m_20193_().m_46473_().m_7238_();
                     }
                  }
               }
            }
         }).exceptionally((e) -> {
            e.printStackTrace();
            ((NetworkEvent.Context)ctx.get()).getNetworkManager().m_129507_(Component.m_237110_("mineminenomi.networking.failed", new Object[]{e.getMessage()}));
            return null;
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
