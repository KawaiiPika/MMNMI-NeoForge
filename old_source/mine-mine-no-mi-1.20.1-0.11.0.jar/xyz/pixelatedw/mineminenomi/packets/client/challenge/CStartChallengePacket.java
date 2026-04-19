package xyz.pixelatedw.mineminenomi.packets.client.challenge;

import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;

public class CStartChallengePacket {
   private ResourceLocation id;
   private boolean isFree;

   public CStartChallengePacket() {
   }

   public CStartChallengePacket(ResourceLocation resourceLocation, boolean isFree) {
      this.id = resourceLocation;
      this.isFree = isFree;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.id);
      buffer.writeBoolean(this.isFree);
   }

   public static CStartChallengePacket decode(FriendlyByteBuf buffer) {
      CStartChallengePacket msg = new CStartChallengePacket();
      msg.id = buffer.m_130281_();
      msg.isFree = buffer.readBoolean();
      return msg;
   }

   public static void handle(CStartChallengePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            if (ServerConfig.isChallengesEnabled()) {
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               ServerLevel world = player.m_284548_();
               IChallengeData props = (IChallengeData)ChallengeCapability.get(player).orElse((Object)null);
               if (props != null) {
                  ChallengeCore<?> core = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(message.id);
                  if (core != null) {
                     List<LivingEntity> list = new ArrayList();
                     UnmodifiableIterator var7 = props.getGroupMembersIds().iterator();

                     while(var7.hasNext()) {
                        UUID id = (UUID)var7.next();
                        Player groupMember = world.m_46003_(id);
                        if (groupMember != null) {
                           list.add(groupMember);
                        }
                     }

                     ChallengesWorldData.get().startChallenge(player, list, core, message.isFree);
                  }
               }
            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
