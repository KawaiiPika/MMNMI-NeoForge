package xyz.pixelatedw.mineminenomi.init;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.packets.client.CFinishCCPacket;
import xyz.pixelatedw.mineminenomi.packets.client.CRequestSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetCombatBarPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CToggleCombatModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CTogglePassiveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CUseAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.component.CChangeAbilityAltModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.component.CSwingTriggerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.node.CUnlockAbilityNodePacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CAcceptChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CDisbandChallengeGroupPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CFetchPossibleInvitesPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CSendChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CStartChallengePacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CCreateCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CKickFromCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CLeaveCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CUpdateJollyRogerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSwitchVehicleModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSyncPlayerInputPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CUpdateInventoryPagePacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperBuyRumPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListNewsPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListRumorsPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CDoctorHealPacket;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CAbandonQuestPacket;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CUpdateQuestStatePacket;
import xyz.pixelatedw.mineminenomi.packets.client.trade.CBuyFromTraderPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilitiesListScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenJollyRogerEditorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenPlayerScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.CSetDyeLayerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SAddScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SRemoveScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSetFlagOnFirePacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSetRogueModePacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSpawnParticleEffectPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncChallengeDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDynDimensionsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncGCDDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncHakiDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncProjectileExtrasPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncStrikerCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SUpdateCombatStatePacket;
import xyz.pixelatedw.mineminenomi.packets.server.SViewProtectionPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SFlightValuePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRecalculateEyeHeightPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveShadowEventsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SSetCombatBarPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SSetHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SStartGCDPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleCombatModePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleDrumsOfLiberationSoundPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.STriggerAbilityUseEventsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUnlockAbilityNodePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateEquippedAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SBonusManagerUpdatePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAbilityAltModePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAnimationStatePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeMorphPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SDisableAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SForceStopChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetContinuityThresholdPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetOverlayLayersPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPauseTickPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPoolInUsePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetStacksPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartContinuityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartCooldownPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartDisablePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopContinuityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopCooldownPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopDisablePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSwingTriggerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SUpdateAbilityComponentPacket;
import xyz.pixelatedw.mineminenomi.packets.server.challenge.SUpdateChallengeGroupPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SCarryEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SForceDismountEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SKairosekiCoatingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SLeashPlayerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateLightningEntity;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjBlockCollisionBox;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjEntityCollisionBox;
import xyz.pixelatedw.mineminenomi.packets.server.quest.SFinishObjectivePacket;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetFruitSeedsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetRandomizedFruitsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.trade.SUpdateTraderOffersPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenAbilitySelectionScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCreateCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenEncyclopediaScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenJollyRogerEditorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenPlayerScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenQuestTrackerScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenTraderScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenWantedPosterScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SSetServerMaxBarsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SToggleDyeLayersPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateOPBossEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateRogueStateEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SScreenDataEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SSimpleMessageScreenEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenBarkeeperDialogueScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenTrainerDialogueScreenPacket;

public class ModNetwork {
   private static int packet = 0;
   private static final String PROTOCOL_VERSION = Integer.toString(1);
   private static final SimpleChannel INSTANCE;

   public static <MSG> void registerPacket(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
      INSTANCE.registerMessage(packet++, messageType, encoder, decoder, messageConsumer);
   }

   public static <MSG> void sendToServer(MSG msg) {
      if (FGCommand.SHOW_PACKET_LOG) {
         WyDebug.debug("Sent packet to server " + String.valueOf(msg));
      }

      INSTANCE.sendToServer(msg);
   }

   public static <MSG> void sendTo(MSG msg, Player player) {
      if (FGCommand.SHOW_PACKET_LOG) {
         String var10000 = String.valueOf(msg);
         WyDebug.debug("Sent packet " + var10000 + " to " + player.m_7755_().getString());
      }

      if (player instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.f_8906_ != null) {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
         }
      }

   }

   public static <MSG> void sendToAll(MSG msg) {
      if (FGCommand.SHOW_PACKET_LOG) {
         WyDebug.debug("Sent packet to all " + String.valueOf(msg));
      }

      INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
   }

   public static <MSG> void sendToAllTracking(MSG msg, Entity tracked) {
      if (FGCommand.SHOW_PACKET_LOG) {
         String var10000 = String.valueOf(msg);
         WyDebug.debug("Sent packet " + var10000 + " to all tracking " + tracked.m_7755_().getString());
      }

      INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> tracked), msg);
   }

   public static <MSG> void sendToAllTrackingAndSelf(MSG msg, LivingEntity tracked) {
      sendToAllTracking(msg, tracked);
      if (tracked instanceof Player player) {
         sendTo(msg, player);
      }

   }

   public static <MSG> void sendToAllAround(MSG msg, Entity sender) {
      sendToAllAroundDistance(msg, sender.m_9236_(), sender.m_20182_(), 256);
   }

   public static <MSG> void sendToAllAroundDistance(MSG msg, Level world, Vec3 pivot, int distance) {
      if (FGCommand.SHOW_PACKET_LOG) {
         String var10000 = String.valueOf(msg);
         WyDebug.debug("Sent packet " + var10000 + " to all around " + String.valueOf(pivot));
      }

      INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(pivot.m_7096_(), pivot.m_7098_(), pivot.m_7094_(), (double)distance, world.m_46472_())), msg);
   }

   public static void init() {
      registerPacket(CUpdateQuestStatePacket.class, CUpdateQuestStatePacket::encode, CUpdateQuestStatePacket::decode, CUpdateQuestStatePacket::handle);
      registerPacket(CToggleCombatModePacket.class, CToggleCombatModePacket::encode, CToggleCombatModePacket::decode, CToggleCombatModePacket::handle);
      registerPacket(CUseAbilityPacket.class, CUseAbilityPacket::encode, CUseAbilityPacket::decode, CUseAbilityPacket::handle);
      registerPacket(CFinishCCPacket.class, CFinishCCPacket::encode, CFinishCCPacket::decode, CFinishCCPacket::handle);
      registerPacket(CRequestSyncQuestDataPacket.class, CRequestSyncQuestDataPacket::encode, CRequestSyncQuestDataPacket::decode, CRequestSyncQuestDataPacket::handle);
      registerPacket(CRemoveAbilityPacket.class, CRemoveAbilityPacket::encode, CRemoveAbilityPacket::decode, CRemoveAbilityPacket::handle);
      registerPacket(CCreateCrewPacket.class, CCreateCrewPacket::encode, CCreateCrewPacket::decode, CCreateCrewPacket::handle);
      registerPacket(CLeaveCrewPacket.class, CLeaveCrewPacket::encode, CLeaveCrewPacket::decode, CLeaveCrewPacket::handle);
      registerPacket(CUpdateJollyRogerPacket.class, CUpdateJollyRogerPacket::encode, CUpdateJollyRogerPacket::decode, CUpdateJollyRogerPacket::handle);
      registerPacket(CSetCombatBarPacket.class, CSetCombatBarPacket::encode, CSetCombatBarPacket::decode, CSetCombatBarPacket::handle);
      registerPacket(CStartChallengePacket.class, CStartChallengePacket::encode, CStartChallengePacket::decode, CStartChallengePacket::handle);
      registerPacket(CKickFromCrewPacket.class, CKickFromCrewPacket::encode, CKickFromCrewPacket::decode, CKickFromCrewPacket::handle);
      registerPacket(CAbandonQuestPacket.class, CAbandonQuestPacket::encode, CAbandonQuestPacket::decode, CAbandonQuestPacket::handle);
      registerPacket(CBuyFromTraderPacket.class, CBuyFromTraderPacket::encode, CBuyFromTraderPacket::decode, CBuyFromTraderPacket::handle);
      registerPacket(CEquipAbilityPacket.class, CEquipAbilityPacket::encode, CEquipAbilityPacket::decode, CEquipAbilityPacket::handle);
      registerPacket(CTogglePassiveAbilityPacket.class, CTogglePassiveAbilityPacket::encode, CTogglePassiveAbilityPacket::decode, CTogglePassiveAbilityPacket::handle);
      registerPacket(COpenPlayerScreenPacket.class, COpenPlayerScreenPacket::encode, COpenPlayerScreenPacket::decode, COpenPlayerScreenPacket::handle);
      registerPacket(COpenChallengesScreenPacket.class, COpenChallengesScreenPacket::encode, COpenChallengesScreenPacket::decode, COpenChallengesScreenPacket::handle);
      registerPacket(COpenAbilitiesListScreenPacket.class, COpenAbilitiesListScreenPacket::encode, COpenAbilitiesListScreenPacket::decode, COpenAbilitiesListScreenPacket::handle);
      registerPacket(CSetHakiColorPacket.class, CSetHakiColorPacket::encode, CSetHakiColorPacket::decode, CSetHakiColorPacket::handle);
      registerPacket(CUpdateInventoryPagePacket.class, CUpdateInventoryPagePacket::encode, CUpdateInventoryPagePacket::decode, CUpdateInventoryPagePacket::handle);
      registerPacket(CDoctorHealPacket.class, CDoctorHealPacket::encode, CDoctorHealPacket::decode, CDoctorHealPacket::handle);
      registerPacket(CSwitchVehicleModePacket.class, CSwitchVehicleModePacket::encode, CSwitchVehicleModePacket::decode, CSwitchVehicleModePacket::handle);
      registerPacket(CSendChallengeInvitationPacket.class, CSendChallengeInvitationPacket::encode, CSendChallengeInvitationPacket::decode, CSendChallengeInvitationPacket::handle);
      registerPacket(CAcceptChallengeInvitationPacket.class, CAcceptChallengeInvitationPacket::encode, CAcceptChallengeInvitationPacket::decode, CAcceptChallengeInvitationPacket::handle);
      registerPacket(CDisbandChallengeGroupPacket.class, CDisbandChallengeGroupPacket::encode, CDisbandChallengeGroupPacket::decode, CDisbandChallengeGroupPacket::handle);
      registerPacket(CFetchPossibleInvitesPacket.class, CFetchPossibleInvitesPacket::encode, CFetchPossibleInvitesPacket::decode, CFetchPossibleInvitesPacket::handle);
      registerPacket(COpenCrewScreenPacket.class, COpenCrewScreenPacket::encode, COpenCrewScreenPacket::decode, COpenCrewScreenPacket::handle);
      registerPacket(CSyncPlayerInputPacket.class, CSyncPlayerInputPacket::encode, CSyncPlayerInputPacket::decode, CSyncPlayerInputPacket::handle);
      registerPacket(COpenJollyRogerEditorScreenPacket.class, COpenJollyRogerEditorScreenPacket::encode, COpenJollyRogerEditorScreenPacket::decode, COpenJollyRogerEditorScreenPacket::handle);
      registerPacket(CSetDyeLayerPacket.class, CSetDyeLayerPacket::encode, CSetDyeLayerPacket::decode, CSetDyeLayerPacket::handle);
      registerPacket(COpenAbilityTreeScreenPacket.class, COpenAbilityTreeScreenPacket::encode, COpenAbilityTreeScreenPacket::decode, COpenAbilityTreeScreenPacket::handle);
      registerPacket(CUnlockAbilityNodePacket.class, CUnlockAbilityNodePacket::encode, CUnlockAbilityNodePacket::decode, CUnlockAbilityNodePacket::handle);
      registerPacket(CBarkeeperBuyRumPacket.class, CBarkeeperBuyRumPacket::encode, CBarkeeperBuyRumPacket::decode, CBarkeeperBuyRumPacket::handle);
      registerPacket(CBarkeeperListNewsPacket.class, CBarkeeperListNewsPacket::encode, CBarkeeperListNewsPacket::decode, CBarkeeperListNewsPacket::handle);
      registerPacket(CBarkeeperListRumorsPacket.class, CBarkeeperListRumorsPacket::encode, CBarkeeperListRumorsPacket::decode, CBarkeeperListRumorsPacket::handle);
      registerPacket(CChangeAbilityAltModePacket.class, CChangeAbilityAltModePacket::encode, CChangeAbilityAltModePacket::decode, CChangeAbilityAltModePacket::handle);
      registerPacket(CSwingTriggerPacket.class, CSwingTriggerPacket::encode, CSwingTriggerPacket::decode, CSwingTriggerPacket::handle);
      registerPacket(SUpdateAbilityComponentPacket.class, SUpdateAbilityComponentPacket::encode, SUpdateAbilityComponentPacket::decode, SUpdateAbilityComponentPacket::handle);
      registerPacket(SSyncAbilityDataPacket.class, SSyncAbilityDataPacket::encode, SSyncAbilityDataPacket::decode, SSyncAbilityDataPacket::handle);
      registerPacket(SSyncQuestDataPacket.class, SSyncQuestDataPacket::encode, SSyncQuestDataPacket::decode, SSyncQuestDataPacket::handle);
      registerPacket(SSyncDevilFruitPacket.class, SSyncDevilFruitPacket::encode, SSyncDevilFruitPacket::decode, SSyncDevilFruitPacket::handle);
      registerPacket(SSyncEntityStatsPacket.class, SSyncEntityStatsPacket::encode, SSyncEntityStatsPacket::decode, SSyncEntityStatsPacket::handle);
      registerPacket(SRecalculateEyeHeightPacket.class, SRecalculateEyeHeightPacket::encode, SRecalculateEyeHeightPacket::decode, SRecalculateEyeHeightPacket::handle);
      registerPacket(SViewProtectionPacket.class, SViewProtectionPacket::encode, SViewProtectionPacket::decode, SViewProtectionPacket::handle);
      registerPacket(SOpenCharacterCreatorScreenPacket.class, SOpenCharacterCreatorScreenPacket::encode, SOpenCharacterCreatorScreenPacket::decode, SOpenCharacterCreatorScreenPacket::handle);
      registerPacket(SOpenWantedPosterScreenPacket.class, SOpenWantedPosterScreenPacket::encode, SOpenWantedPosterScreenPacket::decode, SOpenWantedPosterScreenPacket::handle);
      registerPacket(SUpdateTraderOffersPacket.class, SUpdateTraderOffersPacket::encode, SUpdateTraderOffersPacket::decode, SUpdateTraderOffersPacket::handle);
      registerPacket(SOpenTraderScreenPacket.class, SOpenTraderScreenPacket::encode, SOpenTraderScreenPacket::decode, SOpenTraderScreenPacket::handle);
      registerPacket(SOpenJollyRogerEditorScreenPacket.class, SOpenJollyRogerEditorScreenPacket::encode, SOpenJollyRogerEditorScreenPacket::decode, SOpenJollyRogerEditorScreenPacket::handle);
      registerPacket(SOpenTrainerDialogueScreenPacket.class, SOpenTrainerDialogueScreenPacket::encode, SOpenTrainerDialogueScreenPacket::decode, SOpenTrainerDialogueScreenPacket::handle);
      registerPacket(SOpenCreateCrewScreenPacket.class, SOpenCreateCrewScreenPacket::encode, SOpenCreateCrewScreenPacket::decode, SOpenCreateCrewScreenPacket::handle);
      registerPacket(SSyncHakiDataPacket.class, SSyncHakiDataPacket::encode, SSyncHakiDataPacket::decode, SSyncHakiDataPacket::handle);
      registerPacket(SFlightValuePacket.class, SFlightValuePacket::encode, SFlightValuePacket::decode, SFlightValuePacket::handle);
      registerPacket(SSyncChallengeDataPacket.class, SSyncChallengeDataPacket::encode, SSyncChallengeDataPacket::decode, SSyncChallengeDataPacket::handle);
      registerPacket(SUpdateEquippedAbilityPacket.class, SUpdateEquippedAbilityPacket::encode, SUpdateEquippedAbilityPacket::decode, SUpdateEquippedAbilityPacket::handle);
      registerPacket(SOpenPlayerScreenPacket.class, SOpenPlayerScreenPacket::encode, SOpenPlayerScreenPacket::decode, SOpenPlayerScreenPacket::handle);
      registerPacket(SSetCombatBarPacket.class, SSetCombatBarPacket::encode, SSetCombatBarPacket::decode, SSetCombatBarPacket::handle);
      registerPacket(SOpenChallengesScreenPacket.class, SOpenChallengesScreenPacket::encode, SOpenChallengesScreenPacket::decode, SOpenChallengesScreenPacket::handle);
      registerPacket(SSetRandomizedFruitsPacket.class, SSetRandomizedFruitsPacket::encode, SSetRandomizedFruitsPacket::decode, SSetRandomizedFruitsPacket::handle);
      registerPacket(SSetFruitSeedsPacket.class, SSetFruitSeedsPacket::encode, SSetFruitSeedsPacket::decode, SSetFruitSeedsPacket::handle);
      registerPacket(SOpenEncyclopediaScreenPacket.class, SOpenEncyclopediaScreenPacket::encode, SOpenEncyclopediaScreenPacket::decode, SOpenEncyclopediaScreenPacket::handle);
      registerPacket(SOpenQuestTrackerScreenPacket.class, SOpenQuestTrackerScreenPacket::encode, SOpenQuestTrackerScreenPacket::decode, SOpenQuestTrackerScreenPacket::handle);
      registerPacket(SOpenAbilitySelectionScreenPacket.class, SOpenAbilitySelectionScreenPacket::encode, SOpenAbilitySelectionScreenPacket::decode, SOpenAbilitySelectionScreenPacket::handle);
      registerPacket(SSyncDynDimensionsPacket.class, SSyncDynDimensionsPacket::encode, SSyncDynDimensionsPacket::decode, SSyncDynDimensionsPacket::handle);
      registerPacket(SSetHakiColorPacket.class, SSetHakiColorPacket::encode, SSetHakiColorPacket::decode, SSetHakiColorPacket::handle);
      registerPacket(SToggleCombatModePacket.class, SToggleCombatModePacket::encode, SToggleCombatModePacket::decode, SToggleCombatModePacket::handle);
      registerPacket(SDisableAbilityPacket.class, SDisableAbilityPacket::encode, SDisableAbilityPacket::decode, SDisableAbilityPacket::handle);
      registerPacket(SSpawnParticleEffectPacket.class, SSpawnParticleEffectPacket::encode, SSpawnParticleEffectPacket::decode, SSpawnParticleEffectPacket::handle);
      registerPacket(SUpdateOPBossEventPacket.class, SUpdateOPBossEventPacket::encode, SUpdateOPBossEventPacket::decode, SUpdateOPBossEventPacket::handle);
      registerPacket(SUpdateEquippedAbilityPacket.class, SUpdateEquippedAbilityPacket::encode, SUpdateEquippedAbilityPacket::decode, SUpdateEquippedAbilityPacket::handle);
      registerPacket(SEquipAbilityPacket.class, SEquipAbilityPacket::encode, SEquipAbilityPacket::decode, SEquipAbilityPacket::handle);
      registerPacket(SStartGCDPacket.class, SStartGCDPacket::encode, SStartGCDPacket::decode, SStartGCDPacket::handle);
      registerPacket(SSetFlagOnFirePacket.class, SSetFlagOnFirePacket::encode, SSetFlagOnFirePacket::decode, SSetFlagOnFirePacket::handle);
      registerPacket(SUpdateColaAmountPacket.class, SUpdateColaAmountPacket::encode, SUpdateColaAmountPacket::decode, SUpdateColaAmountPacket::handle);
      registerPacket(SUpdateCombatStatePacket.class, SUpdateCombatStatePacket::encode, SUpdateCombatStatePacket::decode, SUpdateCombatStatePacket::handle);
      registerPacket(SCarryEntityPacket.class, SCarryEntityPacket::encode, SCarryEntityPacket::decode, SCarryEntityPacket::handle);
      registerPacket(SLeashPlayerPacket.class, SLeashPlayerPacket::encode, SLeashPlayerPacket::decode, SLeashPlayerPacket::handle);
      registerPacket(SUpdateChallengeGroupPacket.class, SUpdateChallengeGroupPacket::encode, SUpdateChallengeGroupPacket::decode, SUpdateChallengeGroupPacket::handle);
      registerPacket(SUpdateProjEntityCollisionBox.class, SUpdateProjEntityCollisionBox::encode, SUpdateProjEntityCollisionBox::decode, SUpdateProjEntityCollisionBox::handle);
      registerPacket(SUpdateProjBlockCollisionBox.class, SUpdateProjBlockCollisionBox::encode, SUpdateProjBlockCollisionBox::decode, SUpdateProjBlockCollisionBox::handle);
      registerPacket(SUpdateLightningEntity.class, SUpdateLightningEntity::encode, SUpdateLightningEntity::decode, SUpdateLightningEntity::handle);
      registerPacket(SOpenCrewScreenPacket.class, SOpenCrewScreenPacket::encode, SOpenCrewScreenPacket::decode, SOpenCrewScreenPacket::handle);
      registerPacket(SUpdateHakiColorPacket.class, SUpdateHakiColorPacket::encode, SUpdateHakiColorPacket::decode, SUpdateHakiColorPacket::handle);
      registerPacket(SToggleAnimationPacket.class, SToggleAnimationPacket::encode, SToggleAnimationPacket::decode, SToggleAnimationPacket::handle);
      registerPacket(SSyncGCDDataPacket.class, SSyncGCDDataPacket::encode, SSyncGCDDataPacket::decode, SSyncGCDDataPacket::handle);
      registerPacket(SAddScreenShaderPacket.class, SAddScreenShaderPacket::encode, SAddScreenShaderPacket::decode, SAddScreenShaderPacket::handle);
      registerPacket(SRemoveScreenShaderPacket.class, SRemoveScreenShaderPacket::encode, SRemoveScreenShaderPacket::decode, SRemoveScreenShaderPacket::handle);
      registerPacket(SFinishObjectivePacket.class, SFinishObjectivePacket::encode, SFinishObjectivePacket::decode, SFinishObjectivePacket::handle);
      registerPacket(SSyncStrikerCrewPacket.class, SSyncStrikerCrewPacket::encode, SSyncStrikerCrewPacket::decode, SSyncStrikerCrewPacket::handle);
      registerPacket(SSimpleMessageScreenEventPacket.class, SSimpleMessageScreenEventPacket::encode, SSimpleMessageScreenEventPacket::decode, SSimpleMessageScreenEventPacket::handle);
      registerPacket(SScreenDataEventPacket.class, SScreenDataEventPacket::encode, SScreenDataEventPacket::decode, SScreenDataEventPacket::handle);
      registerPacket(SKairosekiCoatingPacket.class, SKairosekiCoatingPacket::encode, SKairosekiCoatingPacket::decode, SKairosekiCoatingPacket::handle);
      registerPacket(SSetServerMaxBarsPacket.class, SSetServerMaxBarsPacket::encode, SSetServerMaxBarsPacket::decode, SSetServerMaxBarsPacket::handle);
      registerPacket(SToggleDyeLayersPacket.class, SToggleDyeLayersPacket::encode, SToggleDyeLayersPacket::decode, SToggleDyeLayersPacket::handle);
      registerPacket(SSetRogueModePacket.class, SSetRogueModePacket::encode, SSetRogueModePacket::decode, SSetRogueModePacket::handle);
      registerPacket(STriggerAbilityUseEventsPacket.class, STriggerAbilityUseEventsPacket::encode, STriggerAbilityUseEventsPacket::decode, STriggerAbilityUseEventsPacket::handle);
      registerPacket(SPinCameraPacket.class, SPinCameraPacket::encode, SPinCameraPacket::decode, SPinCameraPacket::handle);
      registerPacket(SUnpinCameraPacket.class, SUnpinCameraPacket::encode, SUnpinCameraPacket::decode, SUnpinCameraPacket::handle);
      registerPacket(SToggleDrumsOfLiberationSoundPacket.class, SToggleDrumsOfLiberationSoundPacket::encode, SToggleDrumsOfLiberationSoundPacket::decode, SToggleDrumsOfLiberationSoundPacket::handle);
      registerPacket(SRemoveAbilityPacket.class, SRemoveAbilityPacket::encode, SRemoveAbilityPacket::decode, SRemoveAbilityPacket::handle);
      registerPacket(SOpenAbilityTreeScreenPacket.class, SOpenAbilityTreeScreenPacket::encode, SOpenAbilityTreeScreenPacket::decode, SOpenAbilityTreeScreenPacket::handle);
      registerPacket(SUnlockAbilityNodePacket.class, SUnlockAbilityNodePacket::encode, SUnlockAbilityNodePacket::decode, SUnlockAbilityNodePacket::handle);
      registerPacket(SUpdateAbilityNBTPacket.class, SUpdateAbilityNBTPacket::encode, SUpdateAbilityNBTPacket::decode, SUpdateAbilityNBTPacket::handle);
      registerPacket(SUpdateRogueStateEventPacket.class, SUpdateRogueStateEventPacket::encode, SUpdateRogueStateEventPacket::decode, SUpdateRogueStateEventPacket::handle);
      registerPacket(SRemoveShadowEventsPacket.class, SRemoveShadowEventsPacket::encode, SRemoveShadowEventsPacket::decode, SRemoveShadowEventsPacket::handle);
      registerPacket(SSyncProjectileExtrasPacket.class, SSyncProjectileExtrasPacket::encode, SSyncProjectileExtrasPacket::decode, SSyncProjectileExtrasPacket::handle);
      registerPacket(SOpenBarkeeperDialogueScreenPacket.class, SOpenBarkeeperDialogueScreenPacket::encode, SOpenBarkeeperDialogueScreenPacket::decode, SOpenBarkeeperDialogueScreenPacket::handle);
      registerPacket(SDialogueResponsePacket.class, SDialogueResponsePacket::encode, SDialogueResponsePacket::decode, SDialogueResponsePacket::handle);
      registerPacket(SForceDismountEntityPacket.class, SForceDismountEntityPacket::encode, SForceDismountEntityPacket::decode, SForceDismountEntityPacket::handle);
      registerPacket(SStartCooldownPacket.class, SStartCooldownPacket::encode, SStartCooldownPacket::decode, SStartCooldownPacket::handle);
      registerPacket(SStopCooldownPacket.class, SStopCooldownPacket::encode, SStopCooldownPacket::decode, SStopCooldownPacket::handle);
      registerPacket(SStartContinuityPacket.class, SStartContinuityPacket::encode, SStartContinuityPacket::decode, SStartContinuityPacket::handle);
      registerPacket(SStopContinuityPacket.class, SStopContinuityPacket::encode, SStopContinuityPacket::decode, SStopContinuityPacket::handle);
      registerPacket(SStartChargingPacket.class, SStartChargingPacket::encode, SStartChargingPacket::decode, SStartChargingPacket::handle);
      registerPacket(SStopChargingPacket.class, SStopChargingPacket::encode, SStopChargingPacket::decode, SStopChargingPacket::handle);
      registerPacket(SStartDisablePacket.class, SStartDisablePacket::encode, SStartDisablePacket::decode, SStartDisablePacket::handle);
      registerPacket(SStopDisablePacket.class, SStopDisablePacket::encode, SStopDisablePacket::decode, SStopDisablePacket::handle);
      registerPacket(SSetPoolInUsePacket.class, SSetPoolInUsePacket::encode, SSetPoolInUsePacket::decode, SSetPoolInUsePacket::handle);
      registerPacket(SSetStacksPacket.class, SSetStacksPacket::encode, SSetStacksPacket::decode, SSetStacksPacket::handle);
      registerPacket(SSetOverlayLayersPacket.class, SSetOverlayLayersPacket::encode, SSetOverlayLayersPacket::decode, SSetOverlayLayersPacket::handle);
      registerPacket(SSetPauseTickPacket.class, SSetPauseTickPacket::encode, SSetPauseTickPacket::decode, SSetPauseTickPacket::handle);
      registerPacket(SChangeAbilityAltModePacket.class, SChangeAbilityAltModePacket::encode, SChangeAbilityAltModePacket::decode, SChangeAbilityAltModePacket::handle);
      registerPacket(SSwingTriggerPacket.class, SSwingTriggerPacket::encode, SSwingTriggerPacket::decode, SSwingTriggerPacket::handle);
      registerPacket(SChangeMorphPacket.class, SChangeMorphPacket::encode, SChangeMorphPacket::decode, SChangeMorphPacket::handle);
      registerPacket(SBonusManagerUpdatePacket.class, SBonusManagerUpdatePacket::encode, SBonusManagerUpdatePacket::decode, SBonusManagerUpdatePacket::handle);
      registerPacket(SChangeAnimationStatePacket.class, SChangeAnimationStatePacket::encode, SChangeAnimationStatePacket::decode, SChangeAnimationStatePacket::handle);
      registerPacket(SForceStopChargingPacket.class, SForceStopChargingPacket::encode, SForceStopChargingPacket::decode, SForceStopChargingPacket::handle);
      registerPacket(SSetContinuityThresholdPacket.class, SSetContinuityThresholdPacket::encode, SSetContinuityThresholdPacket::decode, SSetContinuityThresholdPacket::handle);
   }

   static {
      NetworkRegistry.ChannelBuilder var10000 = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath("mineminenomi", "main_channel"));
      String var10001 = PROTOCOL_VERSION;
      Objects.requireNonNull(var10001);
      var10000 = var10000.clientAcceptedVersions(var10001::equals);
      var10001 = PROTOCOL_VERSION;
      Objects.requireNonNull(var10001);
      INSTANCE = var10000.serverAcceptedVersions(var10001::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
   }
}
