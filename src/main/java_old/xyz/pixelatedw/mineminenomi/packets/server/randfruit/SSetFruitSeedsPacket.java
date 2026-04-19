package xyz.pixelatedw.mineminenomi.packets.server.randfruit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;

public class SSetFruitSeedsPacket {
   private HashMap<Integer, Long> seeds = new HashMap();
   private long worldSeed;

   public SSetFruitSeedsPacket() {
   }

   public SSetFruitSeedsPacket(HashMap<Integer, Long> seeds, long worldSeed) {
      this.seeds = seeds;
      this.worldSeed = worldSeed;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeLong(this.worldSeed);
      buffer.writeInt(this.seeds.entrySet().size());

      for(Map.Entry<Integer, Long> entry : this.seeds.entrySet()) {
         buffer.writeInt((Integer)entry.getKey());
         buffer.writeLong((Long)entry.getValue());
      }

   }

   public static SSetFruitSeedsPacket decode(FriendlyByteBuf buffer) {
      SSetFruitSeedsPacket msg = new SSetFruitSeedsPacket();
      msg.worldSeed = buffer.readLong();
      HashMap<Integer, Long> seeds = new HashMap();
      int size = buffer.readInt();

      for(int i = 0; i < size; ++i) {
         int key = buffer.readInt();
         long seed = buffer.readLong();
         seeds.put(key, seed);
      }

      msg.seeds = seeds;
      return msg;
   }

   public static void handle(SSetFruitSeedsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetFruitSeedsPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetFruitSeedsPacket message) {
         RandomizedFruitsHandler.Client.FRUIT_SEEDS = message.seeds;
         RandomizedFruitsHandler.Client.DIRTY = true;
         RandomizedFruitsHandler.Common.SEED = message.worldSeed;
      }
   }
}
