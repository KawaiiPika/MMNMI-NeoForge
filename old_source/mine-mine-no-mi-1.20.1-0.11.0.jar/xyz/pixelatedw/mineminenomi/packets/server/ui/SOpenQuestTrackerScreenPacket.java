package xyz.pixelatedw.mineminenomi.packets.server.ui;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.ui.screens.QuestsTrackerScreen;

public class SOpenQuestTrackerScreenPacket {
   private Quest[] quests;

   public SOpenQuestTrackerScreenPacket() {
   }

   public SOpenQuestTrackerScreenPacket(Quest[] quests) {
      this.quests = quests;
   }

   public void encode(FriendlyByteBuf buffer) {
      int available = (int)Arrays.stream(this.quests).filter(Objects::nonNull).count();
      buffer.writeInt(available);

      for(Quest quest : this.quests) {
         if (quest != null) {
            CompoundTag tag = new CompoundTag();
            tag = quest.save(tag);
            buffer.m_130079_(tag);
         }
      }

   }

   public static SOpenQuestTrackerScreenPacket decode(FriendlyByteBuf buffer) {
      SOpenQuestTrackerScreenPacket msg = new SOpenQuestTrackerScreenPacket();
      int len = buffer.readInt();
      msg.quests = new Quest[len];

      for(int i = 0; i < len; ++i) {
         CompoundTag tag = buffer.m_130260_();
         if (!tag.m_128456_()) {
            Quest quest = Quest.from(tag);
            msg.quests[i] = quest;
         }
      }

      return msg;
   }

   public static void handle(SOpenQuestTrackerScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SOpenQuestTrackerScreenPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SOpenQuestTrackerScreenPacket message) {
         Player player = Minecraft.m_91087_().f_91074_;
         Minecraft.m_91087_().m_91152_(new QuestsTrackerScreen(player, message.quests));
      }
   }
}
