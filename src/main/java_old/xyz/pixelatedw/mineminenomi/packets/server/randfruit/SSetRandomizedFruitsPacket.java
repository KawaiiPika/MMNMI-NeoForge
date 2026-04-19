package xyz.pixelatedw.mineminenomi.packets.server.randfruit;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;

public class SSetRandomizedFruitsPacket {
   private boolean flag;

   public SSetRandomizedFruitsPacket() {
   }

   public SSetRandomizedFruitsPacket(boolean flag) {
      this.flag = flag;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.flag);
   }

   public static SSetRandomizedFruitsPacket decode(FriendlyByteBuf buffer) {
      SSetRandomizedFruitsPacket msg = new SSetRandomizedFruitsPacket();
      msg.flag = buffer.readBoolean();
      return msg;
   }

   public static void handle(SSetRandomizedFruitsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetRandomizedFruitsPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetRandomizedFruitsPacket message) {
         RandomizedFruitsHandler.Client.HAS_RANDOMIZED_FRUIT = message.flag;
      }
   }
}
