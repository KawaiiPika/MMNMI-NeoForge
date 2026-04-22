package xyz.pixelatedw.mineminenomi.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;

public class ModRaces {
    private static int idx = 1;
    private static final int HUMAN_ORDER = idx++;
    private static final int FISHMAN_ORDER = idx++;
    private static final int CYBORG_ORDER = idx++;
    private static final int MINK_ORDER = idx++;
    private static final int SKYPIEAN_ORDER = idx++;

    public static final DeferredHolder<Race, Race> HUMAN = ModRegistries.RACES_REGISTRY.register("human", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.HUMAN, HUMAN_ORDER);
        info.addTopAbilities(ModAbilities.SORU, ModAbilities.TEKKAI, ModAbilities.GEPPO, ModAbilities.KAMIE, ModAbilities.RANKYAKU);
        return new Race().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> FISHMAN_SAWSHARK = ModRegistries.RACES_REGISTRY.register("fishman_sawshark", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, 1);
        info.addBottomAbilities(ModAbilities.FISHMAN_SWIM_SPEED_PERK, ModAbilities.FISHMAN_DAMAGE_PERK);
        return Race.subRace().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> FISHMAN_RAY = ModRegistries.RACES_REGISTRY.register("fishman_ray", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, 2);
        info.addBottomAbilities(ModAbilities.FISHMAN_SWIM_SPEED_PERK, ModAbilities.FISHMAN_DAMAGE_PERK);
        return Race.subRace().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> FISHMAN_GARFISH = ModRegistries.RACES_REGISTRY.register("fishman_garfish", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, 3);
        info.addBottomAbilities(ModAbilities.FISHMAN_SWIM_SPEED_PERK, ModAbilities.FISHMAN_DAMAGE_PERK);
        return Race.subRace().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> FISHMAN = ModRegistries.RACES_REGISTRY.register("fishman", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.FISHMAN, FISHMAN_ORDER);
        info.addBottomAbilities(ModAbilities.FISHMAN_SWIM_SPEED_PERK, ModAbilities.FISHMAN_DAMAGE_PERK);
        List<Supplier<Race>> subRaces = new ArrayList<>();
        subRaces.add(FISHMAN_SAWSHARK);
        subRaces.add(FISHMAN_RAY);
        subRaces.add(FISHMAN_GARFISH);
        return new Race().setBookDetails(info).setRenderFeatures().setSubRaces(subRaces);
    });
    public static final DeferredHolder<Race, Race> CYBORG = ModRegistries.RACES_REGISTRY.register("cyborg", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.CYBORG, CYBORG_ORDER);
        info.addBottomAbilities(ModAbilities.CYBORG_ARMOR_PERK);
        return new Race().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> MINK_DOG = ModRegistries.RACES_REGISTRY.register("mink_dog", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK1, 1);
        info.addTopAbilities(ModAbilities.ELECTRO);
        info.addBottomAbilities(ModAbilities.MINK_SPEED_PERK, ModAbilities.MINK_JUMP_PERK);
        return Race.subRace().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> MINK_LION = ModRegistries.RACES_REGISTRY.register("mink_lion", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK2, 2);
        info.addTopAbilities(ModAbilities.ELECTRO);
        info.addBottomAbilities(ModAbilities.MINK_SPEED_PERK, ModAbilities.MINK_JUMP_PERK);
        return Race.subRace().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> MINK_BUNNY = ModRegistries.RACES_REGISTRY.register("mink_bunny", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK3, 3);
        info.addTopAbilities(ModAbilities.ELECTRO);
        info.addBottomAbilities(ModAbilities.MINK_SPEED_PERK, ModAbilities.MINK_JUMP_PERK);
        return Race.subRace().setBookDetails(info);
    });
    public static final DeferredHolder<Race, Race> MINK = ModRegistries.RACES_REGISTRY.register("mink", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MINK1, MINK_ORDER);
        info.addTopAbilities(ModAbilities.ELECTRO);
        info.addBottomAbilities(ModAbilities.MINK_SPEED_PERK, ModAbilities.MINK_JUMP_PERK);
        List<Supplier<Race>> subRaces = new ArrayList<>();
        subRaces.add(MINK_DOG);
        subRaces.add(MINK_LION);
        subRaces.add(MINK_BUNNY);
        return new Race().setBookDetails(info).setRenderFeatures().setSubRaces(subRaces);
    });
    public static final DeferredHolder<Race, Race> SKYPIEAN = ModRegistries.RACES_REGISTRY.register("skypiean", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.SKYPIEAN, SKYPIEAN_ORDER);
        info.addTopAbilities(ModAbilities.MANTRA);
        return new Race().setBookDetails(info).setRenderFeatures();
    });

    public static void init() {}
}
