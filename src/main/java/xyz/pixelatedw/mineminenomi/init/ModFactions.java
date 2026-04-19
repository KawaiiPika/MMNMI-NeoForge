package xyz.pixelatedw.mineminenomi.init;

import java.awt.Color;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;

public class ModFactions {
    private static int idx = 1;
    private static final int PIRATE_ORDER = idx++;
    private static final int MARINE_ORDER = idx++;
    private static final int BOUNTY_HUNTER_ORDER = idx++;
    private static final int REVOLUTIONARY_ORDER = idx++;

    public static final DeferredHolder<Faction, Faction> CIVILIAN = ModRegistries.FACTIONS_REGISTRY.register("civilian", () -> new Faction());
    public static final DeferredHolder<Faction, Faction> PIRATE = ModRegistries.FACTIONS_REGISTRY.register("pirate", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.PIRATE_ICON, PIRATE_ORDER);
        return new Faction().setBookDetails(info).setCanReceiveBountyCheck((player) -> true).setFlagBackgroundColor(Color.BLACK.getRGB());
    });
    public static final DeferredHolder<Faction, Faction> MARINE = ModRegistries.FACTIONS_REGISTRY.register("marine", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.MARINE_ICON, MARINE_ORDER);
        return new Faction().setBookDetails(info).setCanReceiveLoyaltyCheck((player) -> true).setFlagBackgroundColor(15398380);
    });
    public static final DeferredHolder<Faction, Faction> BOUNTY_HUNTER = ModRegistries.FACTIONS_REGISTRY.register("bounty_hunter", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.BOUNTY_HUNTER_ICON, BOUNTY_HUNTER_ORDER);
        return new Faction().setBookDetails(info).setCanReceiveLoyaltyCheck((player) -> true);
    });
    public static final DeferredHolder<Faction, Faction> REVOLUTIONARY_ARMY = ModRegistries.FACTIONS_REGISTRY.register("revolutionary_army", () -> {
        CharacterCreatorSelectionInfo info = new CharacterCreatorSelectionInfo(ModResources.REVOLUTIONARY_ARMY_ICON, REVOLUTIONARY_ORDER);
        return new Faction().setBookDetails(info).setCanReceiveBountyCheck((player) -> true).setCanReceiveLoyaltyCheck((player) -> true).setFlagBackgroundColor(11403264);
    });
    public static final DeferredHolder<Faction, Faction> BANDIT = ModRegistries.FACTIONS_REGISTRY.register("bandit", () -> {
        return new Faction().setCanReceiveBountyCheck((player) -> true);
    });
    public static final DeferredHolder<Faction, Faction> WORLD_GOVERNMENT = ModRegistries.FACTIONS_REGISTRY.register("world_government", () -> new Faction().setFlagBackgroundColor(15398380));

    public static void init() {}
}
