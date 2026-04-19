package xyz.pixelatedw.mineminenomi.api.poi;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;

public class NewsEntry {
   private static final String[] PIRATE_NEWS;
   private static final String[] MARINE_NEWS;
   private final TrackedNPC tracked;
   private Component message;

   private NewsEntry(TrackedNPC tracked) {
      this.tracked = tracked;
   }

   public static NewsEntry createNewsFor(TrackedNPC tracked, Level world) {
      NewsEntry entry = new NewsEntry(tracked);
      NotoriousEntity entity = tracked.createTrackedMob(world);
      if (tracked.getFaction().equals(ModFactions.PIRATE.get())) {
         long bounty = tracked.getBounty();
         int kills = 100 + tracked.getRandom().nextInt(255);
         int razed = 2 + tracked.getRandom().nextInt(10);
         int newsId = tracked.getRandom().nextInt(PIRATE_NEWS.length);
         Object[] args = new Object[]{entity.m_5446_().getString()};
         switch (newsId) {
            case 0:
            case 2:
            case 6:
               args = new Object[]{entity.m_5446_().getString(), razed};
               break;
            case 1:
            case 3:
               args = new Object[]{entity.m_5446_().getString(), bounty};
            case 4:
            default:
               break;
            case 5:
               args = new Object[]{entity.m_5446_().getString(), kills};
         }

         entry.message = Component.m_237110_(PIRATE_NEWS[newsId], args);
      } else if (tracked.getFaction().equals(ModFactions.MARINE.get())) {
         int arrests = 50 + tracked.getRandom().nextInt(200);
         int razed = 2 + tracked.getRandom().nextInt(10);
         int newsId = tracked.getRandom().nextInt(MARINE_NEWS.length);
         Object[] args = new Object[]{entity.m_5446_().getString()};
         switch (newsId) {
            case 0:
            case 3:
            case 4:
               args = new Object[]{entity.m_5446_().getString(), razed};
               break;
            case 1:
            case 2:
               args = new Object[]{entity.m_5446_().getString(), arrests};
         }

         entry.message = Component.m_237110_(MARINE_NEWS[newsId], args);
      }

      return entry;
   }

   public Component getMessage() {
      return this.message;
   }

   static {
      PIRATE_NEWS = new String[]{ModI18nInteractions.PIRATE_NEWS_1_MESSAGE, ModI18nInteractions.PIRATE_NEWS_2_MESSAGE, ModI18nInteractions.PIRATE_NEWS_3_MESSAGE, ModI18nInteractions.PIRATE_NEWS_4_MESSAGE, ModI18nInteractions.PIRATE_NEWS_5_MESSAGE, ModI18nInteractions.PIRATE_NEWS_6_MESSAGE, ModI18nInteractions.PIRATE_NEWS_7_MESSAGE};
      MARINE_NEWS = new String[]{ModI18nInteractions.MARINE_NEWS_1_MESSAGE, ModI18nInteractions.MARINE_NEWS_2_MESSAGE, ModI18nInteractions.MARINE_NEWS_3_MESSAGE, ModI18nInteractions.MARINE_NEWS_4_MESSAGE, ModI18nInteractions.MARINE_NEWS_5_MESSAGE, ModI18nInteractions.MARINE_NEWS_6_MESSAGE};
   }
}
