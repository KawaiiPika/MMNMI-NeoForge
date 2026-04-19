package xyz.pixelatedw.mineminenomi.ui;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.abilities.DummyAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class CharacterCreatorSelectionMap {
   public static final AbilityCore<DummyAbility> CYBORG_ARMOR_PERK;
   public static final AbilityCore<DummyAbility> FISHMAN_SWIM_SPEED_PERK;
   public static final AbilityCore<DummyAbility> FISHMAN_DAMAGE_PERK;
   public static final AbilityCore<DummyAbility> MINK_SPEED_PERK;
   public static final AbilityCore<DummyAbility> MINK_JUMP_PERK;
   public static final AbilityCore<DummyAbility> SWORDSMAN_DAMAGE_PERK;
   public static final AbilityCore<DummyAbility> SNIPER_ACCURACY_PERK;
   public static final AbilityCore<DummyAbility> SNIPER_GOGGLES_PERK;
   public static final AbilityCore<DummyAbility> BLACK_LEG_DAMAGE_PERK;
   public static final AbilityCore<DummyAbility> BLACK_LEG_SPEED_PERK;
   public static final AbilityCore<DummyAbility> BRAWLER_DAMAGE_PERK;

   static {
      CYBORG_ARMOR_PERK = (new AbilityCore.Builder("cyborg_armor", "Cyborg Armor", AbilityCategory.RACIAL, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "cyborg_armor")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_CYBORG_ARMOR_BONUS).build("mineminenomi");
      FISHMAN_SWIM_SPEED_PERK = (new AbilityCore.Builder("fishman_swim_speed", "Fishman Swim Speed", AbilityCategory.RACIAL, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_swim_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_FISHMAN_SWIM_SPEED_BONUS).build("mineminenomi");
      FISHMAN_DAMAGE_PERK = (new AbilityCore.Builder("fishman_damage", "Fishman Damage", AbilityCategory.RACIAL, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_FISHMAN_DAMAGE_SPEED_BONUS).build("mineminenomi");
      MINK_SPEED_PERK = (new AbilityCore.Builder("mink_speed", "Mink Speed", AbilityCategory.RACIAL, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_MINK_SPEED_BONUS).build("mineminenomi");
      MINK_JUMP_PERK = (new AbilityCore.Builder("mink_jump", "Mink Jump", AbilityCategory.RACIAL, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_jump")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_MINK_JUMP_BONUS).build("mineminenomi");
      SWORDSMAN_DAMAGE_PERK = (new AbilityCore.Builder("swordsman_damage", "Swordsman Damage", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "swordsman_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_SWORDSMAN_DAMAGE_BONUS).build("mineminenomi");
      SNIPER_ACCURACY_PERK = (new AbilityCore.Builder("sniper_accuracy", "Sniper Accuracy", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_accuracy")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_SNIPER_ACCURACY_BONUS).build("mineminenomi");
      SNIPER_GOGGLES_PERK = (new AbilityCore.Builder("sniper_goggles", "Sniper Goggles", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_goggles")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_SNIPER_GOGGLES_BONUS).build("mineminenomi");
      BLACK_LEG_DAMAGE_PERK = (new AbilityCore.Builder("black_leg_damage", "Black Leg Damage", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_leg_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_BLACK_LEG_DAMAGE_BONUS).build("mineminenomi");
      BLACK_LEG_SPEED_PERK = (new AbilityCore.Builder("black_leg_attack_speed", "Black Leg Attack Speed", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_leg_attack_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_BLACK_LEG_ATTACK_SPEED_BONUS).build("mineminenomi");
      BRAWLER_DAMAGE_PERK = (new AbilityCore.Builder("brawler_damage", "Brawler Damage", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18nAbilities.PERK_BRAWLER_DAMAGE_BONUS).build("mineminenomi");
   }
}
