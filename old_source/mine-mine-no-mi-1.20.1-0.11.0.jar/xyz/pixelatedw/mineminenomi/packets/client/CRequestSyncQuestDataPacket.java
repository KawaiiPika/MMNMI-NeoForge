package xyz.pixelatedw.mineminenomi.packets.client;

import java.util.ArrayList;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEquipItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenQuestTrackerScreenPacket;

public class CRequestSyncQuestDataPacket {
   private boolean openScreen;

   public CRequestSyncQuestDataPacket() {
   }

   public CRequestSyncQuestDataPacket(boolean openScreen) {
      this.openScreen = openScreen;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.openScreen);
   }

   public static CRequestSyncQuestDataPacket decode(FriendlyByteBuf buffer) {
      CRequestSyncQuestDataPacket msg = new CRequestSyncQuestDataPacket();
      msg.openScreen = buffer.readBoolean();
      return msg;
   }

   public static void handle(CRequestSyncQuestDataPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            Player player = ((NetworkEvent.Context)ctx.get()).getSender();
            IQuestData questData = (IQuestData)QuestCapability.get(player).orElse((Object)null);
            if (questData != null) {
               for(Quest quest : questData.getInProgressQuests()) {
                  if (quest != null) {
                     for(Objective obj : quest.getObjectives()) {
                        if (obj instanceof IReachDorikiObjective) {
                           double doriki = (Double)EntityStatsCapability.get(player).map((props) -> props.getDoriki()).orElse((double)0.0F);
                           obj.setProgress(player, (float)doriki, false);
                        }

                        if (obj instanceof IEquipItemObjective) {
                           IEquipItemObjective equipObjective = (IEquipItemObjective)obj;

                           for(EquipmentSlot slot : EquipmentSlot.values()) {
                              boolean slotCheck = ((IEquipItemObjective)obj).checkSlot(slot);
                              if (slotCheck) {
                                 boolean itemCheck = equipObjective.checkEquippedItem(player, player.m_6844_(slot));
                                 if (itemCheck) {
                                    obj.alterProgress(player, 1.0F);
                                 } else {
                                    obj.alterProgress(player, -1.0F);
                                 }
                              }
                           }
                        }

                        if (obj instanceof ICollectItemObjective) {
                           ICollectItemObjective collectObjective = (ICollectItemObjective)obj;
                           ArrayList<ItemStack> slots = new ArrayList();
                           slots.addAll(player.m_150109_().f_35974_);
                           slots.addAll(player.m_150109_().f_35976_);
                           int items = collectObjective.checkItems(slots);
                           if (items > 0) {
                              obj.setProgress(player, (float)items, false);
                           }
                        }
                     }
                  }
               }

               if (message.openScreen) {
                  ModNetwork.sendTo(new SOpenQuestTrackerScreenPacket(questData.getInProgressQuests()), player);
               }

            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
