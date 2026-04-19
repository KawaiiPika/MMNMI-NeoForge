package xyz.pixelatedw.mineminenomi.packets.client.entity;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;

public class CUpdateInventoryPagePacket {
   private int entityId;
   private int pageId;

   public CUpdateInventoryPagePacket() {
   }

   public CUpdateInventoryPagePacket(int entityId, int pageId) {
      this.entityId = entityId;
      this.pageId = pageId;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.pageId);
   }

   public static CUpdateInventoryPagePacket decode(FriendlyByteBuf buffer) {
      CUpdateInventoryPagePacket msg = new CUpdateInventoryPagePacket();
      msg.entityId = buffer.readInt();
      msg.pageId = buffer.readInt();
      return msg;
   }

   public static void handle(CUpdateInventoryPagePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            Level world = player.m_9236_();
            Entity entity = world.m_6815_(message.entityId);
            if (entity != null) {
               if (entity instanceof WhiteWalkieEntity) {
                  WhiteWalkieEntity walkie = (WhiteWalkieEntity)entity;
                  walkie.setInventoryPage(message.pageId);
                  if (walkie.m_269323_() != null) {
                     LivingEntity patt1766$temp = walkie.m_269323_();
                     if (patt1766$temp instanceof Player) {
                        Player ownerPlayer = (Player)patt1766$temp;
                        walkie.openInventory(ownerPlayer);
                        return;
                     }
                  }

                  walkie.openInventory(player);
               }

            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
