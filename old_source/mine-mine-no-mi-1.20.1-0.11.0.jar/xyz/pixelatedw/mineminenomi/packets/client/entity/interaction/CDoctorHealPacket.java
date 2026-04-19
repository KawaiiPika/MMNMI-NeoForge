package xyz.pixelatedw.mineminenomi.packets.client.entity.interaction;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;

public class CDoctorHealPacket {
   private int entityId;

   public CDoctorHealPacket() {
   }

   public CDoctorHealPacket(int entityId) {
      this.entityId = entityId;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
   }

   public static CDoctorHealPacket decode(FriendlyByteBuf buffer) {
      CDoctorHealPacket msg = new CDoctorHealPacket();
      msg.entityId = buffer.readInt();
      return msg;
   }

   public static void handle(CDoctorHealPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props != null) {
               int payment = (int)(player.m_21233_() - player.m_21223_()) * 10;
               if (props.getBelly() < (long)payment) {
                  ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.getRandomNoBellyMessage()), player);
               } else if (WyHelper.isInCombat(player)) {
                  ModNetwork.sendTo(SDialogueResponsePacket.setMessage(ModI18nInteractions.NO_COMBAT_ALLOWED_MESSAGE), player);
               } else {
                  props.alterBelly((long)(-payment), StatChangeSource.STORE);
                  player.m_5634_(player.m_21233_());
                  player.m_21195_(MobEffects.f_19614_);
                  ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
                  ModNetwork.sendTo(SDialogueResponsePacket.closeDialogue(), player);
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
