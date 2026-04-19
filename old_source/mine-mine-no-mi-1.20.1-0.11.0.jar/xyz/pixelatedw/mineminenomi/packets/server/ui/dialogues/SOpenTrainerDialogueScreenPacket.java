package xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.TrainerDialogueScreen;

public class SOpenTrainerDialogueScreenPacket {
   private int questGiverEntity;
   private boolean isInCombat;
   private List<QuestId<?>> availableQuests = new ArrayList();
   private List<QuestId<?>> completedQuests = new ArrayList();
   private List<QuestId<?>> lockedQuests = new ArrayList();

   public SOpenTrainerDialogueScreenPacket() {
   }

   public <T extends LivingEntity & ITrainer> SOpenTrainerDialogueScreenPacket(Player player, T trainer) {
      IQuestData props = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (props != null) {
         List<QuestId<?>> quests = (trainer).getAvailableQuests(player);
         this.availableQuests = quests.stream().filter((quest) -> {
            Quest inprogressQuest = props.getInProgressQuest(quest);
            if (inprogressQuest != null && inprogressQuest.isComplete()) {
               return true;
            } else {
               boolean hasQuestInProg = inprogressQuest != null;
               return !hasQuestInProg && (!props.hasFinishedQuest(quest) || quest.isRepeatable());
            }
         }).toList();
         this.completedQuests = quests.stream().filter((quest) -> props.hasInProgressQuest(quest) && props.getInProgressQuest(quest).isComplete()).toList();
         this.lockedQuests = quests.stream().filter((quest) -> quest.isLocked(player)).toList();
      }

      this.questGiverEntity = trainer.m_19879_();
      this.isInCombat = WyHelper.isInCombat(player);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.questGiverEntity);
      buffer.writeBoolean(this.isInCombat);
      buffer.writeInt(this.availableQuests.size());

      for(QuestId<?> quest : this.availableQuests) {
         buffer.m_130085_(quest.getRegistryKey());
      }

      buffer.writeInt(this.completedQuests.size());

      for(QuestId<?> quest : this.completedQuests) {
         buffer.m_130085_(quest.getRegistryKey());
      }

      buffer.writeInt(this.lockedQuests.size());

      for(QuestId<?> quest : this.lockedQuests) {
         buffer.m_130085_(quest.getRegistryKey());
      }

   }

   public static SOpenTrainerDialogueScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenTrainerDialogueScreenPacket msg = new SOpenTrainerDialogueScreenPacket();
      msg.questGiverEntity = buffer.readInt();
      msg.isInCombat = buffer.readBoolean();
      int availableQuestsSize = buffer.readInt();
      if (availableQuestsSize > 0) {
         for(int i = 0; i < availableQuestsSize; ++i) {
            QuestId<?> quest = QuestId.get(buffer.m_130281_());
            msg.availableQuests.add(quest);
         }
      }

      int completedQuestsSize = buffer.readInt();
      if (completedQuestsSize > 0) {
         for(int i = 0; i < completedQuestsSize; ++i) {
            QuestId<?> quest = QuestId.get(buffer.m_130281_());
            msg.completedQuests.add(quest);
         }
      }

      int lockedQuestsSize = buffer.readInt();
      if (lockedQuestsSize > 0) {
         for(int i = 0; i < lockedQuestsSize; ++i) {
            QuestId<?> quest = QuestId.get(buffer.m_130281_());
            msg.lockedQuests.add(quest);
         }
      }

      return msg;
   }

   public static void handle(SOpenTrainerDialogueScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenTrainerDialogueScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenTrainerDialogueScreenPacket message) {
         Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(message.questGiverEntity);
         if (entity instanceof ITrainer questGiver) {
            if (questGiver instanceof LivingEntity livingEntity) {
               ArrayList entries = new ArrayList();

               for(QuestId<?> quest : message.availableQuests) {
                  TrainerDialogueScreen.QuestEntry entry = new TrainerDialogueScreen.QuestEntry(quest);
                  if (message.completedQuests.contains(quest)) {
                     entry.setComplete();
                  }

                  if (message.lockedQuests.contains(quest)) {
                     entry.setLocked();
                  }

                  entries.add(entry);
               }

               Minecraft.m_91087_().m_91152_(new TrainerDialogueScreen(livingEntity, entries, message.isInCombat));
            }
         }
      }
   }
}
