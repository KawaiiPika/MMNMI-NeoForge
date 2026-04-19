package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SSetCombatBarPacket {
   private int bar;

   public SSetCombatBarPacket() {
   }

   public SSetCombatBarPacket(int bar) {
      this.bar = bar;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.bar);
   }

   public static SSetCombatBarPacket decode(FriendlyByteBuf buffer) {
      SSetCombatBarPacket msg = new SSetCombatBarPacket();
      msg.bar = buffer.readInt();
      return msg;
   }

   public static void handle(SSetCombatBarPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetCombatBarPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetCombatBarPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (abilityProps != null) {
            abilityProps.setCombatBarSet(message.bar);
         }
      }
   }
}
