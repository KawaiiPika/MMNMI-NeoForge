package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;

public class ModFightingStyles {
    private static int idx = 1;
    private static final int SWORDSMAN_ORDER = idx++;
    private static final int SNIPER_ORDER = idx++;
    private static final int DOCTOR_ORDER = idx++;
    private static final int ART_OF_WEATHER_ORDER = idx++;
    private static final int BRAWLER_ORDER = idx++;
    private static final int BLACK_LEG_ORDER = idx++;

    public static final DeferredHolder<FightingStyle, FightingStyle> SWORDSMAN = ModRegistries.FIGHTING_STYLES_REGISTRY.register("swordsman", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.SWORDSMAN, SWORDSMAN_ORDER);
        info.addTopAbilities(ModAbilities.SHISHI_SONSON, ModAbilities.YAKKODORI, ModAbilities.SANBYAKUROKUJU_POUND_HO, ModAbilities.HIRYU_KAEN, ModAbilities.O_TATSUMAKI);
        info.addBottomAbilities(ModAbilities.SWORDSMAN_DAMAGE_PERK);
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> SNIPER = ModRegistries.FIGHTING_STYLES_REGISTRY.register("sniper", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.SNIPER, SNIPER_ORDER);
        info.addTopAbilities(ModAbilities.KAEN_BOSHI, ModAbilities.KEMURI_BOSHI, ModAbilities.NEMURI_BOSHI, ModAbilities.TETSU_BOSHI, ModAbilities.HI_NO_TORI_BOSHI);
        info.addBottomAbilities(ModAbilities.SNIPER_ACCURACY_PERK, ModAbilities.ZOOM_PERK);
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> DOCTOR = ModRegistries.FIGHTING_STYLES_REGISTRY.register("doctor", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.DOCTOR, DOCTOR_ORDER);
        info.addTopAbilities(ModAbilities.FIRST_AID, ModAbilities.ANTIDOTE_SHOT, ModAbilities.DOPING, ModAbilities.VIRUS_ZONE, ModAbilities.MEDIC_BAG_EXPLOSION);
        info.addBottomAbilities(ModAbilities.MEDICAL_EXPERTISE_PERK);
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> ART_OF_WEATHER = ModRegistries.FIGHTING_STYLES_REGISTRY.register("art_of_weather", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.ART_OF_WEATHER, ART_OF_WEATHER_ORDER);
        info.addTopAbilities(ModAbilities.THUNDERBOLT_TEMPO, ModAbilities.CYCLONE_TEMPO, ModAbilities.THUNDER_LANCE_TEMPO);
        info.addBottomAbilities(ModAbilities.WEATHER_KNOWLEDGE_PERK);
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> BRAWLER = ModRegistries.FIGHTING_STYLES_REGISTRY.register("brawler", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BRAWLER, BRAWLER_ORDER);
        info.addTopAbilities(ModAbilities.CHARGED_PUNCH, ModAbilities.TACKLE, ModAbilities.HAKAI_HO, ModAbilities.JISHIN_HO, ModAbilities.KING_PUNCH);
        info.addBottomAbilities(ModAbilities.BRAWLER_DAMAGE_PERK);
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> BLACK_LEG = ModRegistries.FIGHTING_STYLES_REGISTRY.register("black_leg", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BLACK_LEG, BLACK_LEG_ORDER);
        info.addTopAbilities(ModAbilities.GEPPO); // Skywalk is Geppo
        info.addBottomAbilities(ModAbilities.BLACK_LEG_DAMAGE_PERK, ModAbilities.BLACK_LEG_SPEED_PERK);
        return new FightingStyle().setBookDetails(info);
    });

    public static void init() {}
}
