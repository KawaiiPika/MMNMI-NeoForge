package xyz.pixelatedw.mineminenomi.init;

import java.awt.Color;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.abilities.marineloyalty.MusterAbility;
import xyz.pixelatedw.mineminenomi.abilities.marineloyalty.SmallMusterAbility;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.factions.RevolutionaryRank;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class ModFactions {
   private static int idx = 1;
   private static final int PIRATE_ORDER;
   private static final int MARINE_ORDER;
   private static final int BOUNTY_HUNTER_ORDER;
   private static final int REVOLUTIONARY_ORDER;
   private static final BiPredicate<LivingEntity, LivingEntity> MARINE_HURT_CHECK;
   public static final RegistryObject<Faction> CIVILIAN;
   public static final RegistryObject<Faction> PIRATE;
   public static final RegistryObject<Faction> MARINE;
   public static final RegistryObject<Faction> BOUNTY_HUNTER;
   public static final RegistryObject<Faction> REVOLUTIONARY_ARMY;
   public static final RegistryObject<Faction> BANDIT;
   public static final RegistryObject<Faction> WORLD_GOVERNMENT;

   public static void init() {
   }

   static {
      PIRATE_ORDER = idx++;
      MARINE_ORDER = idx++;
      BOUNTY_HUNTER_ORDER = idx++;
      REVOLUTIONARY_ORDER = idx++;
      MARINE_HURT_CHECK = (attacker, target) -> {
         FactionsWorldData worldData = FactionsWorldData.get();
         IEntityStats attackerProps = (IEntityStats)EntityStatsCapability.get(attacker).orElse((Object)null);
         IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
         if (worldData != null && attackerProps != null && targetProps != null) {
            boolean isCivilian = target instanceof AbstractVillager || target instanceof IronGolem || targetProps.isCivilian();
            boolean isAttackerMarineAligned = attackerProps.isMarine() || attackerProps.isBountyHunter() || attackerProps.isWorldGovernment();
            boolean isTargetMarineAligned = targetProps.isMarine() || targetProps.isBountyHunter() || targetProps.isWorldGovernment();
            return !isAttackerMarineAligned || !isTargetMarineAligned && !isCivilian;
         } else {
            return true;
         }
      };
      CIVILIAN = ModRegistry.<Faction>registerFaction("civilian", "Civilian", Faction::new);
      PIRATE = ModRegistry.<Faction>registerFaction("pirate", "Pirate", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.PIRATE_ICON, PIRATE_ORDER);
         Predicate<LivingEntity> bountyCheck = (player) -> true;
         BiPredicate<LivingEntity, LivingEntity> hurtCheck = (attacker, target) -> {
            FactionsWorldData worldData = FactionsWorldData.get();
            IEntityStats attackerProps = (IEntityStats)EntityStatsCapability.get(attacker).orElse((Object)null);
            IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
            if (worldData != null && attackerProps != null && targetProps != null) {
               if (attackerProps.isPirate() && targetProps.isPirate()) {
                  if (attacker instanceof Mob && target instanceof Mob) {
                     return false;
                  } else {
                     Crew livingCrew = worldData.getCrewWithMember(attacker.m_20148_());
                     if (livingCrew == null) {
                        return true;
                     } else {
                        Crew targetCrew = worldData.getCrewWithMember(target.m_20148_());
                        if (targetCrew == null) {
                           return true;
                        } else {
                           return !livingCrew.equals(targetCrew);
                        }
                     }
                  }
               } else {
                  return true;
               }
            } else {
               return true;
            }
         };
         return (new Faction()).setBookDetails(info).setCanReceiveBountyCheck(bountyCheck).addCanHurtCheck(hurtCheck).setFlagBackgroundColor(Color.BLACK.getRGB());
      });
      MARINE = ModRegistry.<Faction>registerFaction("marine", "Marine", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MARINE_ICON, MARINE_ORDER);
         info.addTopAbilities(SmallMusterAbility.INSTANCE, MusterAbility.INSTANCE, CommandAbility.INSTANCE);
         return (new Faction()).setBookDetails(info).setCanReceiveLoyaltyCheck((player) -> true).addCanHurtCheck(MARINE_HURT_CHECK).setRanks(MarineRank.class).setFlagBackgroundColor(15398380);
      });
      BOUNTY_HUNTER = ModRegistry.<Faction>registerFaction("bounty_hunter", "Bounty Hunter", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BOUNTY_HUNTER_ICON, BOUNTY_HUNTER_ORDER);
         return (new Faction()).setBookDetails(info).setCanReceiveLoyaltyCheck((player) -> true).addCanHurtCheck(MARINE_HURT_CHECK);
      });
      REVOLUTIONARY_ARMY = ModRegistry.<Faction>registerFaction("revolutionary_army", "Revolutionary Army", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.REVOLUTIONARY_ARMY_ICON, REVOLUTIONARY_ORDER);
         BiPredicate<LivingEntity, LivingEntity> hurtCheck = (attacker, target) -> {
            FactionsWorldData worldData = FactionsWorldData.get();
            IEntityStats attackerProps = (IEntityStats)EntityStatsCapability.get(attacker).orElse((Object)null);
            IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
            if (worldData != null && attackerProps != null && targetProps != null) {
               boolean isCivilian = target instanceof AbstractVillager || targetProps.isCivilian();
               return !attackerProps.isRevolutionary() || !targetProps.isRevolutionary() && !isCivilian;
            } else {
               return true;
            }
         };
         return (new Faction()).setBookDetails(info).setCanReceiveBountyCheck((player) -> true).setCanReceiveLoyaltyCheck((player) -> true).addCanHurtCheck(hurtCheck).setRanks(RevolutionaryRank.class).setFlagBackgroundColor(11403264);
      });
      BANDIT = ModRegistry.<Faction>registerFaction("bandit", "Bandit", () -> {
         BiPredicate<LivingEntity, LivingEntity> hurtCheck = (attacker, target) -> {
            IEntityStats attackerProps = (IEntityStats)EntityStatsCapability.get(attacker).orElse((Object)null);
            IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
            if (attackerProps != null && targetProps != null) {
               return !attackerProps.isBandit() || !targetProps.isBandit();
            } else {
               return true;
            }
         };
         return (new Faction()).addCanHurtCheck(hurtCheck).setCanReceiveBountyCheck((player) -> true);
      });
      WORLD_GOVERNMENT = ModRegistry.<Faction>registerFaction("world_government", "World Government", () -> (new Faction()).setFlagBackgroundColor(15398380));
   }
}
