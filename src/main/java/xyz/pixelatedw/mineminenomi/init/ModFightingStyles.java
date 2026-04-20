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
        info.addTopAbilities();
        info.addBottomAbilities();
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> SNIPER = ModRegistries.FIGHTING_STYLES_REGISTRY.register("sniper", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.SNIPER, SNIPER_ORDER);
        info.addTopAbilities();
        info.addBottomAbilities();
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> DOCTOR = ModRegistries.FIGHTING_STYLES_REGISTRY.register("doctor", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.DOCTOR, DOCTOR_ORDER);
        info.addTopAbilities();
        info.addBottomAbilities();
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> ART_OF_WEATHER = ModRegistries.FIGHTING_STYLES_REGISTRY.register("art_of_weather", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.ART_OF_WEATHER, ART_OF_WEATHER_ORDER);
        info.addTopAbilities();
        info.addBottomAbilities();
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> BRAWLER = ModRegistries.FIGHTING_STYLES_REGISTRY.register("brawler", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BRAWLER, BRAWLER_ORDER);
        info.addTopAbilities();
        info.addBottomAbilities();
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> BLACK_LEG = ModRegistries.FIGHTING_STYLES_REGISTRY.register("black_leg", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BLACK_LEG, BLACK_LEG_ORDER);
        info.addTopAbilities(); // Skywalk is Geppo
        info.addBottomAbilities();
        return new FightingStyle().setBookDetails(info);
    });

    public static void init() {}
}
