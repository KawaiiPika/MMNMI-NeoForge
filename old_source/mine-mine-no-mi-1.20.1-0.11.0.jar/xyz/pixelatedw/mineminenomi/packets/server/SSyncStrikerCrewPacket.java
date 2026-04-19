package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;

public class SSyncStrikerCrewPacket {
   private int entityId;
   private Crew crew;

   public SSyncStrikerCrewPacket() {
   }

   public SSyncStrikerCrewPacket(StrikerEntity entity, Crew crew) {
      this.entityId = entity.m_19879_();
      this.crew = crew;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130079_(this.crew.write());
   }

   public static SSyncStrikerCrewPacket decode(FriendlyByteBuf buffer) {
      SSyncStrikerCrewPacket msg = new SSyncStrikerCrewPacket();
      msg.entityId = buffer.readInt();
      msg.crew = Crew.from(buffer.m_130261_());
      return msg;
   }

   public static void handle(SSyncStrikerCrewPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSyncStrikerCrewPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSyncStrikerCrewPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         if (player != null) {
            Entity entity = mc.f_91073_.m_6815_(message.entityId);
            if (entity != null) {
               if (entity instanceof StrikerEntity) {
                  StrikerEntity striker = (StrikerEntity)entity;
                  striker.setCrew(message.crew);
               }

            }
         }
      }
   }
}
