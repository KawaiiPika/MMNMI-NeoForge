package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.ICombatData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenPlayerScreenPacket;

public class COpenPlayerScreenPacket {
   public void encode(FriendlyByteBuf buffer) {
   }

   public static COpenPlayerScreenPacket decode(FriendlyByteBuf buffer) {
      COpenPlayerScreenPacket msg = new COpenPlayerScreenPacket();
      return msg;
   }

   public static void handle(COpenPlayerScreenPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
            IEntityStats entityProps = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (entityProps != null) {
               if (entityProps.hasRace() && entityProps.hasFaction() && entityProps.hasFightingStyle()) {
                  Optional<IQuestData> questProps = QuestCapability.get(player);
                  Optional<IChallengeData> challengeProps = ChallengeCapability.get(player);
                  Optional<ICombatData> combatProps = CombatCapability.get(player);
                  double doriki = entityProps.getDoriki();
                  boolean hasQuests = ServerConfig.isQuestsEnabled();
                  int questAmount = (Integer)questProps.map((props) -> props.countInProgressQuests()).orElse(0);
                  boolean hasChallenges = ServerConfig.isChallengesEnabled();
                  int challengeAmount = (Integer)challengeProps.map((props) -> props.countChallenges()).orElse(0);
                  boolean isInCombat = (Boolean)combatProps.map((props) -> props.isInCombatCache()).orElse(false);
                  boolean isInChallengeDimension = NuWorld.isChallengeDimension(player.m_9236_());
                  int invites = (Integer)challengeProps.map((props) -> props.getInvitations().size()).orElse(0);
                  boolean hasCrew = FactionsWorldData.get().getCrewWithMember(player.m_20148_()) != null;
                  ModNetwork.sendTo(new SOpenPlayerScreenPacket(doriki, hasQuests, questAmount, hasChallenges, challengeAmount, isInCombat, isInChallengeDimension, invites, hasCrew), player);
               } else {
                  boolean hasRandomizedRace = ServerConfig.getRaceRandomizer();
                  boolean allowMinkRaceSelect = ServerConfig.getAllowSubRaceSelect();
                  ModNetwork.sendTo(new SOpenCharacterCreatorScreenPacket(hasRandomizedRace, allowMinkRaceSelect), player);
               }

            }
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
