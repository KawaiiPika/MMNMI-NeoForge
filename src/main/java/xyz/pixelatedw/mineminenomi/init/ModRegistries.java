package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;

@EventBusSubscriber(modid = "mineminenomi", bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

    public static final ResourceKey<Registry<Faction>> FACTIONS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "factions"));
    public static final ResourceKey<Registry<Race>> RACES_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "races"));
    public static final ResourceKey<Registry<FightingStyle>> FIGHTING_STYLES_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fighting_styles"));
    public static final ResourceKey<Registry<xyz.pixelatedw.mineminenomi.api.quests.QuestId<?>>> QUESTS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "quests"));
    public static final ResourceKey<Registry<xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement>> JOLLY_ROGER_ELEMENTS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jolly_roger_elements"));

    public static Registry<Faction> FACTIONS;
    public static Registry<Race> RACES;
    public static Registry<FightingStyle> FIGHTING_STYLES;
    public static Registry<xyz.pixelatedw.mineminenomi.api.quests.QuestId<?>> QUESTS;
    public static Registry<xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement> JOLLY_ROGER_ELEMENTS;

    public static final net.neoforged.neoforge.registries.DeferredRegister<Faction> FACTIONS_REGISTRY = net.neoforged.neoforge.registries.DeferredRegister.create(FACTIONS_KEY, "mineminenomi");
    public static final net.neoforged.neoforge.registries.DeferredRegister<Race> RACES_REGISTRY = net.neoforged.neoforge.registries.DeferredRegister.create(RACES_KEY, "mineminenomi");
    public static final net.neoforged.neoforge.registries.DeferredRegister<FightingStyle> FIGHTING_STYLES_REGISTRY = net.neoforged.neoforge.registries.DeferredRegister.create(FIGHTING_STYLES_KEY, "mineminenomi");
    public static final net.neoforged.neoforge.registries.DeferredRegister<xyz.pixelatedw.mineminenomi.api.quests.QuestId<?>> QUESTS_REGISTRY = net.neoforged.neoforge.registries.DeferredRegister.create(QUESTS_KEY, "mineminenomi");
    public static final net.neoforged.neoforge.registries.DeferredRegister<xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement> JOLLY_ROGER_ELEMENTS_REGISTRY = net.neoforged.neoforge.registries.DeferredRegister.create(JOLLY_ROGER_ELEMENTS_KEY, "mineminenomi");

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        FACTIONS = event.create(new RegistryBuilder<>(FACTIONS_KEY));
        RACES = event.create(new RegistryBuilder<>(RACES_KEY));
        FIGHTING_STYLES = event.create(new RegistryBuilder<>(FIGHTING_STYLES_KEY));
        QUESTS = event.create(new RegistryBuilder<>(QUESTS_KEY));
        JOLLY_ROGER_ELEMENTS = event.create(new RegistryBuilder<>(JOLLY_ROGER_ELEMENTS_KEY));
    }

    public static void init(net.neoforged.bus.api.IEventBus eventBus) {
        FACTIONS_REGISTRY.register(eventBus);
        RACES_REGISTRY.register(eventBus);
        FIGHTING_STYLES_REGISTRY.register(eventBus);
        QUESTS_REGISTRY.register(eventBus);
        JOLLY_ROGER_ELEMENTS_REGISTRY.register(eventBus);
    }
}
