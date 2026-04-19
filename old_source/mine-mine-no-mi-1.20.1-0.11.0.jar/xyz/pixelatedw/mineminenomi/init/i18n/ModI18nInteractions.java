package xyz.pixelatedw.mineminenomi.init.i18n;

import java.util.Random;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ModI18nInteractions {
   private static final Random RANDOM = new Random();
   private static final Component[] NO_BELLY_MESSAGES;
   public static final Component NO_COMBAT_ALLOWED_MESSAGE;
   public static final Component ALREADY_MAX_HEALTH_MESSAGE;
   private static final Component[] INTRO_MESSAGES;
   public static final Component MARINE_HELP_PIRATE_INVASION_MESSAGE;
   private static final Component[] NOTHING_NEW_MESSAGES;
   private static final String[] NOTORIOUS_PIRATE_RUMORS;
   private static final String[] NOTORIOUS_MARINE_RUMORS;
   private static final String[] CARAVAN_RUMORS_MESSAGES;
   public static final String PIRATE_NEWS_1_MESSAGE;
   public static final String PIRATE_NEWS_2_MESSAGE;
   public static final String PIRATE_NEWS_3_MESSAGE;
   public static final String PIRATE_NEWS_4_MESSAGE;
   public static final String PIRATE_NEWS_5_MESSAGE;
   public static final String PIRATE_NEWS_6_MESSAGE;
   public static final String PIRATE_NEWS_7_MESSAGE;
   public static final String MARINE_NEWS_1_MESSAGE;
   public static final String MARINE_NEWS_2_MESSAGE;
   public static final String MARINE_NEWS_3_MESSAGE;
   public static final String MARINE_NEWS_4_MESSAGE;
   public static final String MARINE_NEWS_5_MESSAGE;
   public static final String MARINE_NEWS_6_MESSAGE;

   public static Component getRandomNoBellyMessage() {
      return NO_BELLY_MESSAGES[RANDOM.nextInt(NO_BELLY_MESSAGES.length - 1)];
   }

   public static Component getRandomIntroMessage() {
      return INTRO_MESSAGES[RANDOM.nextInt(INTRO_MESSAGES.length - 1)];
   }

   public static Component getRandomNothingNewMessage() {
      return NOTHING_NEW_MESSAGES[RANDOM.nextInt(NOTHING_NEW_MESSAGES.length - 1)];
   }

   public static Component getRandomPirateRumorMessage(Vec3 position, String name) {
      int var10000 = (int)position.f_82479_;
      String locationString = var10000 + " " + (int)position.f_82480_ + " " + (int)position.f_82481_;
      return Component.m_237110_(NOTORIOUS_PIRATE_RUMORS[RANDOM.nextInt(NOTORIOUS_PIRATE_RUMORS.length - 1)], new Object[]{locationString, name});
   }

   public static Component getRandomMarineRumorMessage(Vec3 position, String name) {
      int var10000 = (int)position.f_82479_;
      String locationString = var10000 + " " + (int)position.f_82480_ + " " + (int)position.f_82481_;
      return Component.m_237110_(NOTORIOUS_MARINE_RUMORS[RANDOM.nextInt(NOTORIOUS_MARINE_RUMORS.length - 1)], new Object[]{locationString, name});
   }

   public static Component getRandomCaravanRumorMessage(Vec3 position) {
      int var10000 = (int)position.f_82479_;
      String locationString = var10000 + " " + (int)position.f_82480_ + " " + (int)position.f_82481_;
      return Component.m_237110_(CARAVAN_RUMORS_MESSAGES[RANDOM.nextInt(CARAVAN_RUMORS_MESSAGES.length - 1)], new Object[]{locationString});
   }

   public static void init() {
   }

   static {
      NO_BELLY_MESSAGES = new Component[]{Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.no_belly_1", "You think I work for free here ?")), Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.no_belly_2", "No belly no service")), Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.no_belly_3", "Talk with me when you'll be less poor"))};
      NO_COMBAT_ALLOWED_MESSAGE = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.no_combat_allowed", "Come talk to me when you'll be less...busy"));
      ALREADY_MAX_HEALTH_MESSAGE = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.already_max_health", "You're already healthy"));
      INTRO_MESSAGES = new Component[]{Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.intro_message_1", "How can I help you stranger ?")), Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.intro_message_2", "Want anything ?"))};
      MARINE_HELP_PIRATE_INVASION_MESSAGE = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.help_pirate_invasion.message", "We urgently need your help with an incoming pirate invasion, will you help us ?"));
      NOTHING_NEW_MESSAGES = new Component[]{Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.nothing_new_1", "Nothing that I am aware of.")), Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.nothing_new_2", "I've got nothing new to share."))};
      NOTORIOUS_PIRATE_RUMORS = new String[]{ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.notorious_pirate_rumor_1", "There's a rumor about a pirate named %2$s being spotted around %1$s. Might want to avoid that area until they leave if you know whats best for you."), ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.notorious_pirate_rumor_2", "Stay away from %1$s for now, overheard some marines talking about how they plan to ambush %2$s over there.")};
      NOTORIOUS_MARINE_RUMORS = new String[]{ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.notorious_marine_rumor_1", "Heard some marines talk about how %2$s was at %1$s waiting to ambush some pirates, poor lads are done for."), ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.notorious_marine_rumor_2", "There's a rumor about %2$s camping around %1$s. Who knows what they're doing there.")};
      CARAVAN_RUMORS_MESSAGES = new String[]{ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.caravan_rumor_1", "Apparently a caravan of marines was sighted around %s, might even transport some rare goods with them."), ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.caravan_rumor_2", "A reliable source of mine said there's a caravan of marines around %s, but you didn't hear that from me.")};
      PIRATE_NEWS_1_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.pirate_news_1.message", "This pirate named %1$s is quite a popular subject these days. Apparently he's a savage one destroying %2$s towns so far.");
      PIRATE_NEWS_2_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.pirate_news_2.message", "Heard about %1$s ? Today's newspaper got his new wanted poster his bounty went up to %2$s.");
      PIRATE_NEWS_3_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.pirate_news_3.message", "This %1$s fella roaming the seas taking on any Marine battleships they catch. %2$s so far and I don't think he's going to stop.");
      PIRATE_NEWS_4_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.pirate_news_4.message", "%1$s's bounty went up again, currently it sits at %2$s.");
      PIRATE_NEWS_5_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.pirate_news_5.message", "A Marine base got destroyed by %1$s singlehandledly, No new bounty was issued in his name but I doubt it'll stay like that for long.");
      PIRATE_NEWS_6_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.pirate_news_6.message", "%1$s attacked yet another town %2$s killed only in this one.");
      PIRATE_NEWS_7_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.pirate_news_7.message", "%2$s more Marine battleships sunk by %1$s and his crew yesterday.");
      MARINE_NEWS_1_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.marine_news_1.message", "%1$s is on a rampage, sinking %2$s pirate ships this week alone.");
      MARINE_NEWS_2_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.marine_news_2.message", "%2$s more pirates arrested by %1$s.");
      MARINE_NEWS_3_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.marine_news_3.message", "An attack on a pirate town lead by %1$s managed to arrest %2$s of them.");
      MARINE_NEWS_4_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.marine_news_4.message", "The Marines are finally doing some work. %1$s took down %2$s pirate ships yesterday.");
      MARINE_NEWS_5_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.marine_news_5.message", "%1$s just took down another pirate ship, bringing their total number of ships sunk to %2$s.");
      MARINE_NEWS_6_MESSAGE = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "interaction.marine_news_6.message", "Have you heard about %1$s ? Today's newspaper says they've received a commendation for taking down an entire pirate fleet");
   }
}
