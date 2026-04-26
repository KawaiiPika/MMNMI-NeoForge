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
        info.addTopAbilities(ModAbilities.CHARGED_PUNCH, ModAbilities.TACKLE, ModAbilities.HAKAI_HO, ModAbilities.JISHIN_HO, ModAbilities.KING_PUNCH, ModAbilities.SUPLEX, ModAbilities.SPINNING_BRAWL, ModAbilities.DAMAGE_ABSORPTION);
        info.addBottomAbilities(ModAbilities.BRAWLER_PASSIVE_BONUSES);
        return new FightingStyle().setBookDetails(info);
    });
    public static final DeferredHolder<FightingStyle, FightingStyle> BLACK_LEG = ModRegistries.FIGHTING_STYLES_REGISTRY.register("black_leg", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BLACK_LEG, BLACK_LEG_ORDER);
        info.addTopAbilities(ModAbilities.GEPPO); // Skywalk is Geppo
        info.addBottomAbilities(ModAbilities.BLACK_LEG_DAMAGE_PERK, ModAbilities.BLACK_LEG_SPEED_PERK);
        return new FightingStyle().setBookDetails(info);
    });


    private static final int FISHMAN_KARATE_ORDER = idx++;
    private static final int HASSHOKEN_ORDER = idx++;
    private static final int RYUSOKEN_ORDER = idx++;

    public static final DeferredHolder<FightingStyle, FightingStyle> FISHMAN_KARATE = ModRegistries.FIGHTING_STYLES_REGISTRY.register("fishman_karate", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/fishman-karate.png"), FISHMAN_KARATE_ORDER);
        info.addTopAbilities(ModAbilities.KACHIAGE_HAISOKU, ModAbilities.KARAKUSAGAWARA_SEIKEN, ModAbilities.MIZU_OSU, ModAbilities.MIZU_SHURYUDAN, ModAbilities.MIZU_TAIHO, ModAbilities.PACK_OF_SHARKS, ModAbilities.SAMEHADA_SHOTEI, ModAbilities.SHARK_ON_TOOTH, ModAbilities.TWO_FISH_ENGINE, ModAbilities.UCHIMIZU, ModAbilities.YARINAMI);
        info.addBottomAbilities(ModAbilities.FISHMAN_PASSIVE_BONUSES);
        return new FightingStyle().setBookDetails(info);
    });

    public static final DeferredHolder<FightingStyle, FightingStyle> HASSHOKEN = ModRegistries.FIGHTING_STYLES_REGISTRY.register("hasshoken", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/hasshoken.png"), HASSHOKEN_ORDER);
        info.addTopAbilities(ModAbilities.BUJAOGEN, ModAbilities.BUTO, ModAbilities.BUTO_KAITEN, ModAbilities.KIRYU_KIRIKUGI, ModAbilities.MUKIRYU_MUKIRIKUGI);
        info.addBottomAbilities(ModAbilities.HASSHOKEN_PASSIVE_BONUSES);
        return new FightingStyle().setBookDetails(info);
    });

    public static final DeferredHolder<FightingStyle, FightingStyle> RYUSOKEN = ModRegistries.FIGHTING_STYLES_REGISTRY.register("ryusoken", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/icons/ryusoken.png"), RYUSOKEN_ORDER);
        info.addTopAbilities(ModAbilities.RYU_NO_IBUKI, ModAbilities.RYU_NO_KAGIZUME);
        return new FightingStyle().setBookDetails(info);
    });

    public static void init() {}
}
