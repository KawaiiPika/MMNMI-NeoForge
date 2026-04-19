package xyz.pixelatedw.mineminenomi.init;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.GustSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.WeatherEggAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.CycloneTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.FogTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.HeatEggTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.MirageTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderLanceTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderstormTempo;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.BienCuitGrillShotAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.DiableJambeAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.SkywalkAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.KingPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.AntidoteShotAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.DopingAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.FailedExperimentAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.FirstAidAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.MedicBagExplosionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doctor.VirusZoneAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.HiNoToriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.HissatsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.NemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.ui.CharacterCreatorSelectionMap;

public class ModFightingStyles {
   private static int idx = 1;
   private static final int SWORDSMAN_ORDER;
   private static final int SNIPER_ORDER;
   private static final int DOCTOR_ORDER;
   private static final int ART_OF_WEATHER_ORDER;
   private static final int BRAWLER_ORDER;
   private static final int BLACK_LEG_ORDER;
   public static final RegistryObject<FightingStyle> SWORDSMAN;
   public static final RegistryObject<FightingStyle> SNIPER;
   public static final RegistryObject<FightingStyle> DOCTOR;
   public static final RegistryObject<FightingStyle> ART_OF_WEATHER;
   public static final RegistryObject<FightingStyle> BRAWLER;
   public static final RegistryObject<FightingStyle> BLACK_LEG;

   public static void init() {
   }

   static {
      SWORDSMAN_ORDER = idx++;
      SNIPER_ORDER = idx++;
      DOCTOR_ORDER = idx++;
      ART_OF_WEATHER_ORDER = idx++;
      BRAWLER_ORDER = idx++;
      BLACK_LEG_ORDER = idx++;
      SWORDSMAN = ModRegistry.<FightingStyle>registerStyle("swordsman", "Swordsman", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.SWORDSMAN, SWORDSMAN_ORDER);
         info.addTopAbilities(ShiShishiSonsonAbility.INSTANCE, YakkodoriAbility.INSTANCE, SanbyakurokujuPoundHoAbility.INSTANCE, OTatsumakiAbility.INSTANCE, HiryuKaenAbility.INSTANCE);
         info.addBottomAbilities(() -> CharacterCreatorSelectionMap.SWORDSMAN_DAMAGE_PERK);
         return (new FightingStyle()).setBookDetails(info);
      });
      SNIPER = ModRegistry.<FightingStyle>registerStyle("sniper", "Sniper", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.SNIPER, SNIPER_ORDER);
         info.addTopAbilities(HiNoToriBoshiAbility.INSTANCE, KemuriBoshiAbility.INSTANCE, NemuriBoshiAbility.INSTANCE, TokuyoAburaBoshiAbility.INSTANCE, SakuretsuSabotenBoshiAbility.INSTANCE, HissatsuAbility.INSTANCE);
         info.addBottomAbilities(() -> CharacterCreatorSelectionMap.SNIPER_ACCURACY_PERK, () -> CharacterCreatorSelectionMap.SNIPER_GOGGLES_PERK);
         return (new FightingStyle()).setBookDetails(info);
      });
      DOCTOR = ModRegistry.<FightingStyle>registerStyle("doctor", "Doctor", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.DOCTOR, DOCTOR_ORDER);
         info.addTopAbilities(FirstAidAbility.INSTANCE, FailedExperimentAbility.INSTANCE, DopingAbility.INSTANCE, VirusZoneAbility.INSTANCE, AntidoteShotAbility.INSTANCE, MedicBagExplosionAbility.INSTANCE);
         return (new FightingStyle()).setBookDetails(info);
      });
      ART_OF_WEATHER = ModRegistry.<FightingStyle>registerStyle("art_of_weather", "Art of Weather", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.ART_OF_WEATHER, ART_OF_WEATHER_ORDER);
         info.addTopAbilities(WeatherEggAbility.INSTANCE, GustSwordAbility.INSTANCE);
         info.addBottomAbilities(ThunderstormTempo.INSTANCE, CycloneTempo.INSTANCE, MirageTempo.INSTANCE, ThunderLanceTempo.INSTANCE, HeatEggTempo.INSTANCE, FogTempo.INSTANCE);
         return (new FightingStyle()).setBookDetails(info);
      });
      BRAWLER = ModRegistry.<FightingStyle>registerStyle("brawler", "Brawler", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BRAWLER, BRAWLER_ORDER);
         info.addTopAbilities(SuplexAbility.INSTANCE, GenkotsuMeteorAbility.INSTANCE, SpinningBrawlAbility.INSTANCE, HakaiHoAbility.INSTANCE, JishinHoAbility.INSTANCE, KingPunchAbility.INSTANCE);
         info.addBottomAbilities(() -> CharacterCreatorSelectionMap.BRAWLER_DAMAGE_PERK);
         return (new FightingStyle()).setBookDetails(info);
      });
      BLACK_LEG = ModRegistry.<FightingStyle>registerStyle("black_leg", "Black Leg", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BLACK_LEG, BLACK_LEG_ORDER);
         info.addTopAbilities(ConcasseAbility.INSTANCE, AntiMannerKickCourseAbility.INSTANCE, PartyTableKickCourseAbility.INSTANCE, SkywalkAbility.INSTANCE, DiableJambeAbility.INSTANCE, BienCuitGrillShotAbility.INSTANCE);
         info.addBottomAbilities(() -> CharacterCreatorSelectionMap.BLACK_LEG_DAMAGE_PERK, () -> CharacterCreatorSelectionMap.BLACK_LEG_SPEED_PERK);
         return (new FightingStyle()).setBookDetails(info);
      });
   }
}
