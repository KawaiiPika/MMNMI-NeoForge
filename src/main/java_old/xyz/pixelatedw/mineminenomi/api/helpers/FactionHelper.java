package xyz.pixelatedw.mineminenomi.api.helpers;

import java.awt.Color;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.ITamableEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class FactionHelper {
   public static final Color CIVILIAN_COLOR = WyHelper.hexToRGB("#55FF55");
   public static final Color MARINE_COLOR = WyHelper.hexToRGB("#55DDFF");
   public static final Color PIRATE_COLOR = WyHelper.hexToRGB("#FF2200");
   public static final Color REVO_COLOR;
   public static final Color BOUNTY_HUNTER_COLOR;
   public static final Color BANDIT_COLOR;

   public static @Nullable ResourceLocation getFactionIcon(IEntityStats props) {
      ResourceLocation icon = null;
      if (props.isPirate()) {
         icon = ModResources.PIRATE_ICON;
      } else if (props.isMarine()) {
         icon = ModResources.MARINE_ICON_GREYSCALE;
      } else if (props.isBountyHunter()) {
         icon = ModResources.BOUNTY_HUNTER_ICON_GREYSCALE;
      } else if (props.isRevolutionary()) {
         icon = ModResources.REVOLUTIONARY_ARMY_ICON_GREYSCALE;
      }

      return icon;
   }

   public static Color getFactionColor(IEntityStats props) {
      if (props == null) {
         return CIVILIAN_COLOR;
      } else if (props.isPirate()) {
         return PIRATE_COLOR;
      } else if (props.isRevolutionary()) {
         return REVO_COLOR;
      } else if (props.isMarine()) {
         return MARINE_COLOR;
      } else if (props.isBountyHunter()) {
         return BOUNTY_HUNTER_COLOR;
      } else {
         return props.isBandit() ? BANDIT_COLOR : CIVILIAN_COLOR;
      }
   }

   public static int getFactionRGBColor(IEntityStats props) {
      return getFactionColor(props).getRGB();
   }

   public static void sendMessageToCrew(Level world, Crew crew, Component message) {
      for(Crew.Member member : crew.getMembers()) {
         UUID uuid = member.getUUID();
         Player memberPlayer = world.m_46003_(uuid);
         if (memberPlayer != null && memberPlayer.m_6084_()) {
            memberPlayer.m_213846_(message);
         }
      }

   }

   public static boolean isFriendlyFactions(LivingEntity entity, LivingEntity target) {
      return !isEnemyFactions(entity, target);
   }

   public static boolean isEnemyFactions(LivingEntity entity, LivingEntity target) {
      if (entity == null) {
         return true;
      } else if (entity == target) {
         return false;
      } else {
         boolean isSpectating = !EntitySelector.f_20408_.test(target);
         if (isSpectating) {
            return false;
         } else if (entity.m_9236_() instanceof ServerLevel && NuWorld.isChallengeDimension(entity.m_9236_())) {
            InProgressChallenge challenge = ChallengesWorldData.get().getInProgressChallengeFor((ServerLevel)entity.m_9236_());
            if (challenge != null) {
               if (entity instanceof ITamableEntity) {
                  ITamableEntity tameable = (ITamableEntity)entity;
                  if (tameable.getOwner() != null) {
                     if (challenge.getGroup().contains(tameable.getOwner()) && !challenge.getGroup().contains(target)) {
                        return true;
                     }

                     if (challenge.getEnemies().contains(tameable.getOwner()) && !challenge.getEnemies().contains(target)) {
                        return true;
                     }
                  }
               }

               if (challenge.getGroup().contains(entity) && !challenge.getGroup().contains(target)) {
                  return true;
               }

               if (challenge.getEnemies().contains(entity) && !challenge.getEnemies().contains(target)) {
                  return true;
               }
            }

            return false;
         } else if (target.m_20160_() && target.m_6688_() != null && ModEntityPredicates.getFriendlyFactions(entity).test(target.m_6688_())) {
            return false;
         } else {
            IEntityStats livingProps = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
            IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
            if (livingProps != null && targetProps != null) {
               if (!livingProps.isRogue() && !targetProps.isRogue()) {
                  if (!MobsHelper.isTamedBy(entity, target) && !MobsHelper.isTamedBy(target, entity)) {
                     if (MobsHelper.canBeTamed(entity) && MobsHelper.isTamedBy(target, MobsHelper.getTamer(entity))) {
                        return false;
                     } else {
                        Faction faction = (Faction)livingProps.getFaction().orElse((Object)null);
                        return faction == null ? true : faction.canHurt(entity, target);
                     }
                  } else {
                     return false;
                  }
               } else {
                  return true;
               }
            } else {
               return true;
            }
         }
      }
   }

   static {
      REVO_COLOR = PIRATE_COLOR;
      BOUNTY_HUNTER_COLOR = WyHelper.hexToRGB("#BBFF88");
      BANDIT_COLOR = WyHelper.hexToRGB("#925959");
   }
}
