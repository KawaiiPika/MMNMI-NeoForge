package xyz.pixelatedw.mineminenomi.init;

import com.google.common.collect.Lists;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaOverdriveAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeBooAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeVentAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.FreshFireAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.RadicalBeamAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.StrongRightAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalLunaAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalMissileAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalShowerAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.SulongAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.TwoFishEngineAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.YarinamiAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.ui.CharacterCreatorSelectionMap;

public class ModRaces {
   private static int idx = 1;
   private static final int HUMAN_ORDER;
   private static final int FISHMAN_ORDER;
   private static final int FISHMAN_SAWSHARK_ORDER = 1;
   private static final int FISHMAN_RAY_ORDER = 2;
   private static final int FISHMAN_GARFISH_ORDER = 3;
   private static final int CYBORG_ORDER;
   private static final int MINK_ORDER;
   private static final int MINK_DOG_ORDER = 1;
   private static final int MINK_LION_ORDER = 2;
   private static final int MINK_BUNNY_ORDER = 3;
   private static final int SKYPIEAN_ORDER;
   public static final RegistryObject<Race> HUMAN;
   public static final RegistryObject<Race> FISHMAN_SAWSHARK;
   public static final RegistryObject<Race> FISHMAN_RAY;
   public static final RegistryObject<Race> FISHMAN_GARFISH;
   public static final RegistryObject<Race> FISHMAN;
   public static final RegistryObject<Race> CYBORG;
   public static final RegistryObject<Race> MINK_DOG;
   public static final RegistryObject<Race> MINK_LION;
   public static final RegistryObject<Race> MINK_BUNNY;
   public static final RegistryObject<Race> MINK;
   public static final RegistryObject<Race> SKYPIEAN;

   public static void init() {
   }

   static {
      HUMAN_ORDER = idx++;
      FISHMAN_ORDER = idx++;
      CYBORG_ORDER = idx++;
      MINK_ORDER = idx++;
      SKYPIEAN_ORDER = idx++;
      HUMAN = ModRegistry.<Race>registerRace("human", "Human", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.HUMAN, HUMAN_ORDER);
         info.addTopAbilities(SoruAbility.INSTANCE, TekkaiAbility.INSTANCE, GeppoAbility.INSTANCE, KamieAbility.INSTANCE, RankyakuAbility.INSTANCE);
         return (new Race()).setBookDetails(info);
      });
      FISHMAN_SAWSHARK = ModRegistry.<Race>registerRace("fishman_sawshark", "Fishman Saw-shark", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, 1);
         return Race.subRace().setBookDetails(info);
      });
      FISHMAN_RAY = ModRegistry.<Race>registerRace("fishman_ray", "Fishman Ray", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, 2);
         return Race.subRace().setBookDetails(info);
      });
      FISHMAN_GARFISH = ModRegistry.<Race>registerRace("fishman_garfish", "Fishman Garfish", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, 3);
         return Race.subRace().setBookDetails(info);
      });
      FISHMAN = ModRegistry.<Race>registerRace("fishman", "Fishman", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, FISHMAN_ORDER);
         info.addTopAbilities(UchimizuAbility.INSTANCE, SamehadaShoteiAbility.INSTANCE, KachiageHaisokuAbility.INSTANCE, TwoFishEngineAbility.INSTANCE, YarinamiAbility.INSTANCE, KarakusagawaraSeikenAbility.INSTANCE);
         info.addBottomAbilities(() -> CharacterCreatorSelectionMap.FISHMAN_SWIM_SPEED_PERK, () -> CharacterCreatorSelectionMap.FISHMAN_DAMAGE_PERK);
         return (new Race()).setBookDetails(info).setRenderFeatures().setSubRaces(Lists.newArrayList(new RegistryObject[]{FISHMAN_SAWSHARK, FISHMAN_RAY, FISHMAN_GARFISH}));
      });
      CYBORG = ModRegistry.<Race>registerRace("cyborg", "Cyborg", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.CYBORG, CYBORG_ORDER);
         info.addTopAbilities(StrongRightAbility.INSTANCE, RadicalBeamAbility.INSTANCE, CoupDeVentAbility.INSTANCE, FreshFireAbility.INSTANCE, CoupDeBooAbility.INSTANCE, ColaOverdriveAbility.INSTANCE);
         info.addBottomAbilities(() -> CharacterCreatorSelectionMap.CYBORG_ARMOR_PERK);
         return (new Race()).setBookDetails(info);
      });
      MINK_DOG = ModRegistry.<Race>registerRace("mink_dog", "Mink Dog", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK1, 1);
         return Race.subRace().setBookDetails(info);
      });
      MINK_LION = ModRegistry.<Race>registerRace("mink_lion", "Mink Lion", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK2, 2);
         return Race.subRace().setBookDetails(info);
      });
      MINK_BUNNY = ModRegistry.<Race>registerRace("mink_bunny", "Mink Bunny", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK3, 3);
         return Race.subRace().setBookDetails(info);
      });
      MINK = ModRegistry.<Race>registerRace("mink", "Mink", () -> {
         CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK1, MINK_ORDER);
         info.addTopAbilities(EleclawAbility.INSTANCE, ElectricalShowerAbility.INSTANCE, ElectricalLunaAbility.INSTANCE, ElectricalMissileAbility.INSTANCE, SulongAbility.INSTANCE);
         info.addBottomAbilities(() -> CharacterCreatorSelectionMap.MINK_SPEED_PERK, () -> CharacterCreatorSelectionMap.MINK_JUMP_PERK);
         return (new Race()).setBookDetails(info).setRenderFeatures().setSubRaces(Lists.newArrayList(new RegistryObject[]{MINK_DOG, MINK_LION, MINK_BUNNY}));
      });
      SKYPIEAN = ModRegistry.<Race>registerRace("skypiean", "Skypiean", () -> {
         new CharacterCreatorSelectionInfo(ModResources.SKYPIEAN, SKYPIEAN_ORDER);
         return (new Race()).setRenderFeatures();
      });
   }
}
