package xyz.pixelatedw.mineminenomi.packets.client.quest;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;

public class CAbandonQuestPacket {
   private ResourceLocation questId;

   public CAbandonQuestPacket() {
   }

   public CAbandonQuestPacket(QuestId<?> questId) {
      this.questId = ((IForgeRegistry)WyRegistry.QUESTS.get()).getKey(questId);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.questId);
   }

   public static CAbandonQuestPacket decode(FriendlyByteBuf buffer) {
      CAbandonQuestPacket msg = new CAbandonQuestPacket();
      msg.questId = buffer.m_130281_();
      return msg;
   }

   public static void handle(CAbandonQuestPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            QuestId<?> questId = (QuestId)((IForgeRegistry)WyRegistry.QUESTS.get()).getValue(message.questId);
            if (questId != null) {
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               IQuestData props = (IQuestData)QuestCapability.get(player).orElse((Object)null);
               if (props != null) {
                  props.removeInProgressQuest(questId);
                  ModNetwork.sendTo(new SSyncQuestDataPacket(player, props), player);
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
