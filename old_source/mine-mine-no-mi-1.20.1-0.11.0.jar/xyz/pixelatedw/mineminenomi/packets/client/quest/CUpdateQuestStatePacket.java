package xyz.pixelatedw.mineminenomi.packets.client.quest;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.ui.events.TrialsScreenEvent;

public class CUpdateQuestStatePacket {
   private ResourceLocation questId;

   public CUpdateQuestStatePacket() {
   }

   public CUpdateQuestStatePacket(QuestId<?> quest) {
      this.questId = quest.getRegistryKey();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.questId);
   }

   public static CUpdateQuestStatePacket decode(FriendlyByteBuf buffer) {
      CUpdateQuestStatePacket msg = new CUpdateQuestStatePacket();
      msg.questId = buffer.m_130281_();
      return msg;
   }

   public static void handle(CUpdateQuestStatePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            if (message.questId != null) {
               TrialsScreenEvent event = new TrialsScreenEvent();
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               if (player != null) {
                  IQuestData props = (IQuestData)QuestCapability.get(player).orElse((Object)null);
                  if (props != null) {
                     Stream var10000 = WyHelper.getNearbyLiving(player.m_20182_(), player.m_9236_(), (double)10.0F, (Predicate)null).stream();
                     Objects.requireNonNull(ITrainer.class);
                     var10000 = var10000.filter(ITrainer.class::isInstance);
                     Objects.requireNonNull(ITrainer.class);
                     Optional<ITrainer> trainer = var10000.map(ITrainer.class::cast).findFirst();
                     if (trainer.isPresent()) {
                        QuestId<?> questId = (QuestId)((IForgeRegistry)WyRegistry.QUESTS.get()).getValue(message.questId);
                        if (questId != null && !questId.isLocked(player)) {
                           if (((ITrainer)trainer.get()).getAvailableQuests(player).stream().anyMatch((q) -> q.equals(questId))) {
                              boolean updateClient = false;
                              Quest inprogressQuest = props.getInProgressQuest(questId);
                              if (props.hasInProgressQuest(questId) && inprogressQuest != null && inprogressQuest.isComplete()) {
                                 inprogressQuest.triggerTurnInEvents(player);
                                 props.addQuestOnCooldown(questId);
                                 props.addFinishedQuest(questId);
                                 props.removeInProgressQuest(questId);
                                 updateClient = true;
                              } else if (!props.hasInProgressQuest(questId) && !props.isQuestOnCooldown(questId)) {
                                 Quest quest = questId.createQuest();
                                 quest.triggerStartEvents(player);
                                 props.addInProgressQuest(quest);
                                 updateClient = true;
                              }

                              if (updateClient) {
                                 event.setQuestId(questId);
                                 event.sendEvent(player);
                                 ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
                              }

                           }
                        }
                     }
                  }
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
