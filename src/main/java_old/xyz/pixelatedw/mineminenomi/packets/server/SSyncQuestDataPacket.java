package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;

public class SSyncQuestDataPacket {
   private int entityId;
   private CompoundTag data;

   public SSyncQuestDataPacket() {
   }

   public SSyncQuestDataPacket(Player player) {
      this(player, QuestCapability.get(player));
   }

   public SSyncQuestDataPacket(Player player, Optional<IQuestData> lazyOptional) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)lazyOptional.map((data) -> (CompoundTag)data.serializeNBT()).orElse(new CompoundTag());
      this.entityId = player.m_19879_();
   }

   public SSyncQuestDataPacket(LivingEntity entity, IQuestData props) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)props.serializeNBT();
      this.entityId = entity.m_19879_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130079_(this.data);
   }

   public static SSyncQuestDataPacket decode(FriendlyByteBuf buffer) {
      SSyncQuestDataPacket msg = new SSyncQuestDataPacket();
      msg.entityId = buffer.readInt();
      msg.data = buffer.m_130260_();
      return msg;
   }

   public static void handle(SSyncQuestDataPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSyncQuestDataPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSyncQuestDataPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof Player player) {
            if (!message.data.m_128456_()) {
               QuestCapability.get(player).ifPresent((props) -> props.deserializeNBT(message.data));
            }
         }
      }
   }
}
