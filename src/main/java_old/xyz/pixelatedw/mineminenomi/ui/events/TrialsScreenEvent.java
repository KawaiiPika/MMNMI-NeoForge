package xyz.pixelatedw.mineminenomi.ui.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SScreenDataEventPacket;

public class TrialsScreenEvent implements INBTSerializable<CompoundTag> {
   private QuestId<?> questId = null;

   public QuestId<?> getQuestId() {
      return this.questId;
   }

   public void setQuestId(QuestId<?> quest) {
      this.questId = quest;
   }

   public void sendEvent(ServerPlayer player) {
      ModNetwork.sendTo(new SScreenDataEventPacket(this), player);
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      tag.m_128359_("questId", this.questId.getRegistryKey().toString());
      return tag;
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.questId = QuestId.get(ResourceLocation.parse(nbt.m_128461_("questId")));
   }
}
