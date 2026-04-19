package xyz.pixelatedw.mineminenomi.handlers.entity;

import com.google.common.base.Predicates;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.abilities.FlyAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataBase;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeDataBase;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitBase;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiDataBase;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestDataBase;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsBase;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class StatsRestoreHandler {
   public static void restoreHakiPassively(Player player) {
      IHakiData hakiProps = (IHakiData)HakiCapability.get(player).orElse((Object)null);
      if (hakiProps != null) {
         float maxOveruse = (float)hakiProps.getMaxOveruse();
         float hakiOveruse = (float)hakiProps.getHakiOveruse();
         if (!(maxOveruse <= 0.0F)) {
            if (hakiOveruse >= maxOveruse) {
               player.m_7292_(new MobEffectInstance((MobEffect)ModEffects.HAKI_OVERUSE.get(), 100, 2));
            } else if ((double)hakiOveruse >= (double)maxOveruse * 0.95) {
               player.m_7292_(new MobEffectInstance((MobEffect)ModEffects.HAKI_OVERUSE.get(), 80, 1));
            } else if ((double)hakiOveruse >= (double)maxOveruse * 0.9) {
               player.m_7292_(new MobEffectInstance((MobEffect)ModEffects.HAKI_OVERUSE.get(), 40, 0));
            }

            IAbilityData ablData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (ablData != null) {
               boolean hasAnyHakiInUse = false;

               for(IAbility abl : ablData.getEquippedAbilities(AbilityCategory.HAKI.isAbilityPartofCategory())) {
                  if (abl != null && !(abl instanceof KenbunshokuHakiAuraAbility)) {
                     hasAnyHakiInUse = (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((c) -> c.isContinuous()).orElse(false);
                     if (hasAnyHakiInUse) {
                        break;
                     }
                  }
               }

               if (!hasAnyHakiInUse) {
                  int overuseHeal = (int)(-Math.max(5.0F, hakiProps.getTotalHakiExp() / 30.0F) * 6.0F);
                  hakiProps.alterHakiOveruse(overuseHeal);
               }
            }
         }
      }
   }

   public static void restoreHakiFromSleep(Player player) {
      if (!WyHelper.isInCombat(player) && player.m_5803_()) {
         IHakiData hakiData = (IHakiData)HakiCapability.get(player).orElse((Object)null);
         if (hakiData == null) {
            return;
         }

         int restoreAmount = (int)((double)(hakiData.getMaxHakiExp() * 140.0F) / (double)100.0F);
         hakiData.setHakiOveruse(hakiData.getHakiOveruse() - restoreAmount);
      }

   }

   public static void restoreStats(Player original, Player player) {
      IDevilFruit oldDevilFruitProps = (IDevilFruit)DevilFruitCapability.get(original).orElse(new DevilFruitBase(original));
      IDevilFruit newDevilFruitProps = (IDevilFruit)DevilFruitCapability.get(player).orElse(new DevilFruitBase(player));
      IAbilityData newAbilityData = (IAbilityData)AbilityCapability.get(player).orElse(new AbilityDataBase(player));
      IEntityStats oldEntityStats = (IEntityStats)EntityStatsCapability.get(original).orElse(new EntityStatsBase(original));
      CompoundTag oldEntityStatsNBT = (CompoundTag)oldEntityStats.serializeNBT();
      IEntityStats newEntityStats = (IEntityStats)EntityStatsCapability.get(player).orElse(new EntityStatsBase(player));
      newEntityStats.deserializeNBT(oldEntityStatsNBT);
      if ((Boolean)GeneralConfig.RACE_KEEP.get()) {
         newEntityStats.setRace((Race)oldEntityStats.getRace().orElse((Object)null));
         newEntityStats.setSubRace((Race)oldEntityStats.getSubRace().orElse((Object)null));
      }

      if ((Boolean)GeneralConfig.FACTION_KEEP.get()) {
         newEntityStats.setFaction((Faction)oldEntityStats.getFaction().orElse((Object)null));
      }

      if ((Boolean)GeneralConfig.FIGHTING_STYLE_KEEP.get()) {
         newEntityStats.setFightingStyle((FightingStyle)oldEntityStats.getFightingStyle().orElse((Object)null));
      }

      if ((Boolean)GeneralConfig.DEVIL_FRUIT_KEEP.get()) {
         CompoundTag oldFruitNBT = (CompoundTag)oldDevilFruitProps.serializeNBT();
         newDevilFruitProps.deserializeNBT(oldFruitNBT);
      }

      double dorikiLeft = WyHelper.percentage((double)ServerConfig.getDorikiKeepPercentage(), oldEntityStats.getDoriki());
      newEntityStats.alterDoriki(dorikiLeft - oldEntityStats.getDoriki(), StatChangeSource.DEATH);
      long bountyLeft = (long)WyHelper.percentage((double)ServerConfig.getBountyKeepPercentage(), (double)oldEntityStats.getBounty());
      newEntityStats.alterBounty(bountyLeft - oldEntityStats.getBounty(), StatChangeSource.DEATH);
      long bellyLeft = (long)WyHelper.percentage((double)ServerConfig.getBellyKeepPercentage(), (double)oldEntityStats.getBelly());
      newEntityStats.alterBelly(bellyLeft - oldEntityStats.getBelly(), StatChangeSource.DEATH);
      long extolLeft = (long)WyHelper.percentage((double)ServerConfig.getBellyKeepPercentage(), (double)oldEntityStats.getExtol());
      newEntityStats.alterExtol(extolLeft - oldEntityStats.getExtol(), StatChangeSource.DEATH);
      HakiCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IHakiData newProps = (IHakiData)HakiCapability.get(player).orElse(new HakiDataBase(player));
         newProps.deserializeNBT(tag);
         float hardeningBusoExpLeft = (float)WyHelper.percentage((double)ServerConfig.getHakiExpKeepPercentage(), (double)oldProps.getBusoshokuHakiExp());
         newProps.alterBusoshokuHakiExp(hardeningBusoExpLeft - oldProps.getBusoshokuHakiExp(), StatChangeSource.DEATH);
         float observationExpLeft = (float)WyHelper.percentage((double)ServerConfig.getHakiExpKeepPercentage(), (double)oldProps.getKenbunshokuHakiExp());
         newProps.alterKenbunshokuHakiExp(observationExpLeft - oldProps.getKenbunshokuHakiExp(), StatChangeSource.DEATH);
      });
      int loyaltyLeft = (int)WyHelper.percentage((double)(Integer)GeneralConfig.LOYALTY_KEEP_PERCENTAGE.get(), oldEntityStats.getLoyalty());
      newEntityStats.alterLoyalty((double)loyaltyLeft - oldEntityStats.getLoyalty(), StatChangeSource.DEATH);
      restorePermaData(original, player);
      AbilityHelper.enableAbilities(player, Predicates.alwaysTrue());
      ProgressionHandler.checkAllForNewUnlocks(player, false);
      ModNetwork.sendTo(new SSyncDevilFruitPacket(player), player);
      ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
      boolean hasUnlockedFlying = newAbilityData.getPassiveAbilities((abl) -> abl instanceof FlyAbility && !((FlyAbility)abl).isPaused()).size() > 0;
      if (hasUnlockedFlying && !player.m_7500_() && !player.m_5833_()) {
         player.m_150110_().f_35936_ = false;
         player.m_150110_().f_35935_ = false;
         ((ServerPlayer)player).f_8906_.m_9829_(new ServerboundPlayerAbilitiesPacket(player.m_150110_()));
      }

   }

   public static void restoreFullData(Player original, Player player) {
      EntityStatsCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IEntityStats newProps = (IEntityStats)EntityStatsCapability.get(player).orElse(new EntityStatsBase(player));
         newProps.deserializeNBT(tag);
         newProps.setCola(Math.max(Math.min(oldProps.getCola(), newProps.getMaxCola()), 0));
      });
      DevilFruitCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IDevilFruit newProps = (IDevilFruit)DevilFruitCapability.get(player).orElse(new DevilFruitBase(player));
         newProps.deserializeNBT(tag);
         newProps.clearMorphs();
      });
      AbilityCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IAbilityData newProps = (IAbilityData)AbilityCapability.get(player).orElse(new AbilityDataBase(player));
         newProps.deserializeNBT(tag);
      });
      HakiCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IHakiData newProps = (IHakiData)HakiCapability.get(player).orElse(new HakiDataBase(player));
         newProps.deserializeNBT(tag);
      });
      restorePermaData(original, player);
   }

   private static void restorePermaData(Player original, Player player) {
      QuestCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IQuestData newProps = (IQuestData)QuestCapability.get(player).orElse(new QuestDataBase(player));
         newProps.deserializeNBT(tag);
      });
      ChallengeCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IChallengeData newProps = (IChallengeData)ChallengeCapability.get(player).orElse(new ChallengeDataBase(player));
         newProps.deserializeNBT(tag);
      });
      AbilityCapability.get(original).ifPresent((oldProps) -> {
         CompoundTag tag = (CompoundTag)oldProps.serializeNBT();
         IAbilityData newProps = (IAbilityData)AbilityCapability.get(player).orElse(new AbilityDataBase(player));
         newProps.deserializeNBT(tag);
      });
      ProgressionHandler.checkAllForNewUnlocks(player, false);
      AttributeHelper.updateHPAttribute(player);
      player.m_21153_(player.m_21233_());
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse(new EntityStatsBase(player));
      props.setShadow(true);
      props.setStrawDoll(true);
      props.setHeart(true);
   }
}
