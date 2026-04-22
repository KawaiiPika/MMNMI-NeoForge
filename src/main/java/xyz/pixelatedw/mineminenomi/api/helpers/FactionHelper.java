package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;

public class FactionHelper {
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
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.ITamableEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class FactionHelper {
   public static final Color CIVILIAN_COLOR = new Color(0x55, 0xFF, 0x55);
   public static final Color MARINE_COLOR = new Color(0x55, 0xDD, 0xFF);
   public static final Color PIRATE_COLOR = new Color(0xFF, 0x22, 0x00);
   public static final Color REVO_COLOR = PIRATE_COLOR;
   public static final Color BOUNTY_HUNTER_COLOR = new Color(0xBB, 0xFF, 0x88);
   public static final Color BANDIT_COLOR = new Color(0x92, 0x59, 0x59);

   public static @Nullable ResourceLocation getFactionIcon(PlayerStats stats) {
      if (stats == null || stats.getBasic().identity().faction().isEmpty()) return null;
      String factionPath = stats.getBasic().identity().faction().get().getPath();

      return switch (factionPath) {
         case "pirate" -> ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/pirate.png");
         case "marine" -> ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/marine.png");
         case "bounty_hunter" -> ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/bounty_hunter.png");
         case "revolutionary_army" -> ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/revolutionary_army.png");
         default -> null;
      };
   }

   public static Color getFactionColor(PlayerStats stats) {
      if (stats == null || stats.getBasic().identity().faction().isEmpty()) return CIVILIAN_COLOR;
      String factionPath = stats.getBasic().identity().faction().get().getPath();

      return switch (factionPath) {
         case "pirate" -> PIRATE_COLOR;
         case "marine" -> MARINE_COLOR;
         case "bounty_hunter" -> BOUNTY_HUNTER_COLOR;
         case "revolutionary_army" -> REVO_COLOR;
         case "bandit" -> BANDIT_COLOR;
         default -> CIVILIAN_COLOR;
      };
   }

   public static int getFactionRGBColor(PlayerStats stats) {
      return getFactionColor(stats).getRGB();
   }

   public static void sendMessageToCrew(Level world, Crew crew, Component message) {
      for(Crew.Member member : crew.getMembers()) {
         UUID uuid = member.getUUID();
         Player memberPlayer = world.getPlayerByUUID(uuid);
         if (memberPlayer != null && memberPlayer.isAlive()) {
            memberPlayer.displayClientMessage(message, false);
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
         boolean isSpectating = !EntitySelector.NO_SPECTATORS.test(target);
         if (isSpectating) {
            return false;
         } else if (target.isPassenger() && target.getVehicle() != null) {
             // Skipping vehicle faction predicates until fully ported
             return false;
         } else {
            PlayerStats livingProps = entity.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);
            PlayerStats targetProps = target.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);
            if (livingProps != null && targetProps != null) {
               if (!livingProps.getBasic().isRogue() && !targetProps.getBasic().isRogue()) {
                  // Re-added the check logic from java_old/..
                  if (entity instanceof ITamableEntity tameEntity && tameEntity.getOwnerIfAlive() != null) {
                       return isEnemyFactions(tameEntity.getOwnerIfAlive(), target);
                  }
                  if (target instanceof ITamableEntity tameTarget && tameTarget.getOwnerIfAlive() != null) {
                       return isEnemyFactions(entity, tameTarget.getOwnerIfAlive());
                  }

                     if (livingProps.getBasic().identity().faction().isPresent()) {
                         ResourceLocation livingFactionLoc = livingProps.getBasic().identity().faction().get();
                         Faction faction = xyz.pixelatedw.mineminenomi.init.ModRegistries.FACTIONS_REGISTRY.getEntries().stream().filter(e -> e.getId().equals(livingFactionLoc)).map(net.neoforged.neoforge.registries.DeferredHolder::get).findFirst().orElse(null);
                         return faction == null ? true : faction.canHurt(entity, target);
                     }
                     return true;
               } else {
                  return true;
               }
            } else {
               return true;
            }
         }
      }
   }
}
