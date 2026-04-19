package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler;

public class SViewProtectionPacket {
   private boolean state;
   private boolean update;
   private Map<String, ProtectedArea> areas = new HashMap();

   public SViewProtectionPacket() {
   }

   public SViewProtectionPacket(ServerLevel level, boolean state) {
      this.state = state;
      ProtectedAreasData worldData = ProtectedAreasData.get(level);
      this.areas = worldData.getAllRestrictions();
   }

   public static SViewProtectionPacket update(ServerLevel level) {
      SViewProtectionPacket packet = new SViewProtectionPacket(level, false);
      packet.update = true;
      ProtectedAreasData worldData = ProtectedAreasData.get(level);
      packet.areas = worldData.getAllRestrictions();
      return packet;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.update);
      buffer.writeBoolean(this.state);
      buffer.writeInt(this.areas.size());

      for(ProtectedArea area : this.areas.values()) {
         buffer.m_130064_(area.getCenter());
         buffer.writeInt(area.getSize());
         buffer.m_130070_(area.getLabel());
      }

   }

   public static SViewProtectionPacket decode(FriendlyByteBuf buffer) {
      SViewProtectionPacket msg = new SViewProtectionPacket();
      msg.update = buffer.readBoolean();
      msg.state = buffer.readBoolean();
      int areas = buffer.readInt();
      msg.areas.clear();

      for(int i = 0; i < areas; ++i) {
         BlockPos pos = buffer.m_130135_();
         int size = buffer.readInt();
         String label = buffer.m_130277_();
         ProtectedArea area = new ProtectedArea(pos, size, label);
         msg.areas.put(label, area);
      }

      return msg;
   }

   public static void handle(SViewProtectionPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SViewProtectionPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SViewProtectionPacket message) {
         boolean shouldUpdate = message.update && AbilityProtectionHandler.CLIENT_AREAS.size() > 0;
         AbilityProtectionHandler.CLIENT_AREAS.clear();
         if (message.state || shouldUpdate) {
            AbilityProtectionHandler.CLIENT_AREAS.putAll(message.areas);
         }

      }
   }
}
