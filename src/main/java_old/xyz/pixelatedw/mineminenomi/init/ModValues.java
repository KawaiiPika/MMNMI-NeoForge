package xyz.pixelatedw.mineminenomi.init;

import com.google.common.collect.ImmutableMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModValues {
   public static final ArrayList<AkumaNoMiItem> DEVIL_FRUITS = new ArrayList();
   public static final ArrayList<AkumaNoMiItem> LOGIA_FRUITS = new ArrayList();
   public static ImmutableMultimap<AbilityCategory, AbilityCore<?>> abilityCategoryMap;
   public static final String API_URL = "https://pixelatedw.xyz/api/v1";
   public static final Gson GSON = (new GsonBuilder()).disableHtmlEscaping().setPrettyPrinting().create();
   public static final UUID NIL_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
   public static final ResourceLocation NIL_LOCATION = ResourceLocation.parse("empty");
   public static final int MAX_QUESTS = 4;
   public static final int MAX_ULTRACOLA = 20;
   public static final int MAX_COLA = 1000;
   public static final int MAX_GENERAL = 999999999;
   public static final int MAX_CREW = 50;
   public static final long MAX_CURRENCY = 999999999L;
   public static final int MAX_LOYALTY = 100;
   public static final int MIN_LOYALTY = -5;
   public static final int COMBAT_TIME_CACHE = 300;
   public static final int MAX_SELECTED_ABILITIES = 8;
   public static final int MAX_IN_PROGRESS_QUESTS = 4;
   public static final int CHARACTER_CREATOR = 0;
   public static final int WANTED_POSTER = 1;
   public static final int MAX_DIFFICULTY_STARS = 10;
   public static final int MAX_DIFFICULTY = 10 * ChallengeDifficulty.values().length;
   public static final long MAX_BOUNTY = 100000000000L;
   public static final int HAOSHOKU_HAKI_COLOR = 16711680;
}
