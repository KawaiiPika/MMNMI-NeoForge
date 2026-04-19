package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.BuildMode;

public class PatreonHandler {
   private static final Component NO_ACCESS_MESSAGE = Component.m_237113_("§c§lWARNING!§r \n\nYou don't have access to this version yet!");
   private static final Component PUBLIC_TESTING_ENDED_MESSAGE = Component.m_237113_("§c§lWARNING!§r \n\nPublic testing access for this build has ended, please download the actual release!");
   private static final HashMap<UUID, List<AccountType>> PATREONS = new HashMap();

   public static boolean isDevBuild() {
      return BuildMode.MODE == BuildMode.DEV;
   }

   public static boolean isEarlyAccessBuild() {
      return BuildMode.MODE == BuildMode.EARLY_ACCESS;
   }

   public static boolean isPublicQABuild() {
      return BuildMode.MODE == BuildMode.PUBLIC_QA;
   }

   public static boolean isReleaseBuild() {
      return BuildMode.MODE == BuildMode.RELEASE;
   }

   public static boolean isQABuild() {
      return BuildMode.MODE == BuildMode.QA;
   }

   public static boolean isPromoBuild() {
      return BuildMode.MODE == BuildMode.PROMO;
   }

   private static @Nullable List<AccountType> getPatreonLevel(Player player) throws IOException {
      String apiURL = "/patreon/" + player.m_20148_().toString();
      String[] result = (String[])WyHelper.sendGET(apiURL, String[].class);
      if (result != null && result.length > 0) {
         List<AccountType> types = new ArrayList();

         for(String group : result) {
            String formattedGroupName = WyHelper.getResourceName(group);
            if (formattedGroupName.equalsIgnoreCase("patreon_rookie")) {
               types.add(PatreonHandler.AccountType.ROOKIE);
            }

            if (formattedGroupName.equalsIgnoreCase("patreon_supernova")) {
               types.add(PatreonHandler.AccountType.SUPERNOVA);
            }

            if (formattedGroupName.equalsIgnoreCase("patreon_celestial_dragon")) {
               types.add(PatreonHandler.AccountType.CELESTIAL_DRAGON);
            }

            if (formattedGroupName.equalsIgnoreCase("mine_mine_no_mi_qateam")) {
               types.add(PatreonHandler.AccountType.TESTER);
            }
         }

         return types;
      } else {
         return null;
      }
   }

   public static boolean isCelestialDragon(Player player) {
      if (!PATREONS.containsKey(player.m_20148_())) {
         return false;
      } else {
         List<AccountType> accountInfo = (List)PATREONS.get(player.m_20148_());
         boolean hasType = accountInfo != null && accountInfo.contains(PatreonHandler.AccountType.CELESTIAL_DRAGON);
         return hasType;
      }
   }

   public static boolean isSupernova(Player player) {
      if (!PATREONS.containsKey(player.m_20148_())) {
         return false;
      } else {
         List<AccountType> accountInfo = (List)PATREONS.get(player.m_20148_());
         boolean hasType = accountInfo != null && accountInfo.contains(PatreonHandler.AccountType.SUPERNOVA);
         return hasType || isCelestialDragon(player);
      }
   }

   public static boolean isTester(Player player) {
      if (!PATREONS.containsKey(player.m_20148_())) {
         return false;
      } else {
         List<AccountType> accountInfo = (List)PATREONS.get(player.m_20148_());
         boolean hasType = accountInfo != null && accountInfo.contains(PatreonHandler.AccountType.TESTER);
         return hasType;
      }
   }

   public static boolean isRookie(Player player) {
      if (!PATREONS.containsKey(player.m_20148_())) {
         return false;
      } else {
         List<AccountType> accountInfo = (List)PATREONS.get(player.m_20148_());
         boolean hasType = accountInfo != null && accountInfo.contains(PatreonHandler.AccountType.ROOKIE);
         return hasType || isSupernova(player) || isCelestialDragon(player);
      }
   }

   public static boolean hasPatreonAccess(Player player) {
      if (isDevBuild() && isCelestialDragon(player)) {
         return true;
      } else if (isQABuild() && isTester(player)) {
         return true;
      } else {
         return isEarlyAccessBuild() && isSupernova(player);
      }
   }

   public static boolean isPublicQAPeriod() {
      Calendar cal = Calendar.getInstance();
      int month = cal.get(2) + 1;
      int day = cal.get(5);
      return month == 3;
   }

   public static boolean handlePatreonAccess(ServerPlayer player) {
      if (isPublicQABuild()) {
         if (!isPublicQAPeriod()) {
            player.f_8906_.m_9942_(PUBLIC_TESTING_ENDED_MESSAGE);
            return false;
         } else {
            return true;
         }
      } else if (!isReleaseBuild() && (!isDevBuild() || !WyDebug.isDebug())) {
         if (isPromoBuild() && player.m_20194_().m_6982_()) {
            return true;
         } else {
            if (PATREONS.containsKey(player.m_20148_())) {
               List<AccountType> accountInfo = (List)PATREONS.get(player.m_20148_());
               if (accountInfo == null || accountInfo.isEmpty()) {
                  player.f_8906_.m_9942_(NO_ACCESS_MESSAGE);
                  return false;
               }

               if (hasPatreonAccess(player)) {
                  return true;
               }
            }

            List<AccountType> patreonLevels = null;

            try {
               patreonLevels = getPatreonLevel(player);
            } catch (IOException e) {
               e.printStackTrace();
               player.f_8906_.m_9942_(NO_ACCESS_MESSAGE);
               return false;
            }

            PATREONS.put(player.m_20148_(), patreonLevels);
            if (patreonLevels != null && !patreonLevels.isEmpty() && hasPatreonAccess(player)) {
               return true;
            } else {
               player.f_8906_.m_9942_(NO_ACCESS_MESSAGE);
               return false;
            }
         }
      } else {
         return true;
      }
   }

   private static enum AccountType {
      NORMAL,
      ROOKIE,
      SUPERNOVA,
      CELESTIAL_DRAGON,
      TESTER;

      // $FF: synthetic method
      private static AccountType[] $values() {
         return new AccountType[]{NORMAL, ROOKIE, SUPERNOVA, CELESTIAL_DRAGON, TESTER};
      }
   }
}
