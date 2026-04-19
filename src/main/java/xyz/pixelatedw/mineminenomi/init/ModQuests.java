package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.swordsman.*;
import xyz.pixelatedw.mineminenomi.quests.blackleg.*;
import xyz.pixelatedw.mineminenomi.quests.sniper.*;
import xyz.pixelatedw.mineminenomi.quests.brawler.*;
import xyz.pixelatedw.mineminenomi.quests.doctor.*;
import xyz.pixelatedw.mineminenomi.quests.artofweather.*;

public class ModQuests {

    public static final DeferredHolder<QuestId<?>, QuestId<SwordsmanTrial01Quest>> SWORDSMAN_TRIAL_01 = ModRegistries.QUESTS_REGISTRY.register("swordsman_trial_01", () -> {
        QuestId<SwordsmanTrial01Quest> id = SwordsmanTrial01Quest.INSTANCE;
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "swordsman_trial_01"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SwordsmanTrial02Quest>> SWORDSMAN_TRIAL_02 = ModRegistries.QUESTS_REGISTRY.register("swordsman_trial_02", () -> {
        QuestId<SwordsmanTrial02Quest> id = SwordsmanTrial02Quest.INSTANCE;
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "swordsman_trial_02"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SwordsmanTrial03Quest>> SWORDSMAN_TRIAL_03 = ModRegistries.QUESTS_REGISTRY.register("swordsman_trial_03", () -> {
        QuestId<SwordsmanTrial03Quest> id = SwordsmanTrial03Quest.INSTANCE;
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "swordsman_trial_03"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SwordsmanTrial04Quest>> SWORDSMAN_TRIAL_04 = ModRegistries.QUESTS_REGISTRY.register("swordsman_trial_04", () -> {
        QuestId<SwordsmanTrial04Quest> id = SwordsmanTrial04Quest.INSTANCE;
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "swordsman_trial_04"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SwordsmanTrial05Quest>> SWORDSMAN_TRIAL_05 = ModRegistries.QUESTS_REGISTRY.register("swordsman_trial_05", () -> {
        QuestId<SwordsmanTrial05Quest> id = SwordsmanTrial05Quest.INSTANCE;
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "swordsman_trial_05"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BlackLegTrial01Quest>> BLACK_LEG_TRIAL_01 = ModRegistries.QUESTS_REGISTRY.register("black_leg_trial_01", () -> {
        QuestId<BlackLegTrial01Quest> id = new QuestId.Builder<>(BlackLegTrial01Quest::new).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_leg_trial_01"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BlackLegTrial02Quest>> BLACK_LEG_TRIAL_02 = ModRegistries.QUESTS_REGISTRY.register("black_leg_trial_02", () -> {
        QuestId<BlackLegTrial02Quest> id = new QuestId.Builder<>(BlackLegTrial02Quest::new).addRequirements(() -> BLACK_LEG_TRIAL_01.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_leg_trial_02"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BlackLegTrial03Quest>> BLACK_LEG_TRIAL_03 = ModRegistries.QUESTS_REGISTRY.register("black_leg_trial_03", () -> {
        QuestId<BlackLegTrial03Quest> id = new QuestId.Builder<>(BlackLegTrial03Quest::new).addRequirements(() -> BLACK_LEG_TRIAL_02.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_leg_trial_03"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BlackLegTrial04Quest>> BLACK_LEG_TRIAL_04 = ModRegistries.QUESTS_REGISTRY.register("black_leg_trial_04", () -> {
        QuestId<BlackLegTrial04Quest> id = new QuestId.Builder<>(BlackLegTrial04Quest::new).addRequirements(() -> BLACK_LEG_TRIAL_03.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_leg_trial_04"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BlackLegTrial05Quest>> BLACK_LEG_TRIAL_05 = ModRegistries.QUESTS_REGISTRY.register("black_leg_trial_05", () -> {
        QuestId<BlackLegTrial05Quest> id = new QuestId.Builder<>(BlackLegTrial05Quest::new).addRequirements(() -> BLACK_LEG_TRIAL_04.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "black_leg_trial_05"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SniperTrial01Quest>> SNIPER_TRIAL_01 = ModRegistries.QUESTS_REGISTRY.register("sniper_trial_01", () -> {
        QuestId<SniperTrial01Quest> id = new QuestId.Builder<>(SniperTrial01Quest::new).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_trial_01"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SniperTrial02Quest>> SNIPER_TRIAL_02 = ModRegistries.QUESTS_REGISTRY.register("sniper_trial_02", () -> {
        QuestId<SniperTrial02Quest> id = new QuestId.Builder<>(SniperTrial02Quest::new).addRequirements(() -> SNIPER_TRIAL_01.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_trial_02"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SniperTrial03Quest>> SNIPER_TRIAL_03 = ModRegistries.QUESTS_REGISTRY.register("sniper_trial_03", () -> {
        QuestId<SniperTrial03Quest> id = new QuestId.Builder<>(SniperTrial03Quest::new).addRequirements(() -> SNIPER_TRIAL_02.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_trial_03"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SniperTrial04Quest>> SNIPER_TRIAL_04 = ModRegistries.QUESTS_REGISTRY.register("sniper_trial_04", () -> {
        QuestId<SniperTrial04Quest> id = new QuestId.Builder<>(SniperTrial04Quest::new).addRequirements(() -> SNIPER_TRIAL_03.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_trial_04"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<SniperTrial05Quest>> SNIPER_TRIAL_05 = ModRegistries.QUESTS_REGISTRY.register("sniper_trial_05", () -> {
        QuestId<SniperTrial05Quest> id = new QuestId.Builder<>(SniperTrial05Quest::new).addRequirements(() -> SNIPER_TRIAL_04.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_trial_05"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BrawlerTrial01Quest>> BRAWLER_TRIAL_01 = ModRegistries.QUESTS_REGISTRY.register("brawler_trial_01", () -> {
        QuestId<BrawlerTrial01Quest> id = new QuestId.Builder<>(BrawlerTrial01Quest::new).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_trial_01"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BrawlerTrial02Quest>> BRAWLER_TRIAL_02 = ModRegistries.QUESTS_REGISTRY.register("brawler_trial_02", () -> {
        QuestId<BrawlerTrial02Quest> id = new QuestId.Builder<>(BrawlerTrial02Quest::new).addRequirements(() -> BRAWLER_TRIAL_01.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_trial_02"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BrawlerTrial03Quest>> BRAWLER_TRIAL_03 = ModRegistries.QUESTS_REGISTRY.register("brawler_trial_03", () -> {
        QuestId<BrawlerTrial03Quest> id = new QuestId.Builder<>(BrawlerTrial03Quest::new).addRequirements(() -> BRAWLER_TRIAL_02.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_trial_03"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BrawlerTrial04Quest>> BRAWLER_TRIAL_04 = ModRegistries.QUESTS_REGISTRY.register("brawler_trial_04", () -> {
        QuestId<BrawlerTrial04Quest> id = new QuestId.Builder<>(BrawlerTrial04Quest::new).addRequirements(() -> BRAWLER_TRIAL_03.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_trial_04"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<BrawlerTrial05Quest>> BRAWLER_TRIAL_05 = ModRegistries.QUESTS_REGISTRY.register("brawler_trial_05", () -> {
        QuestId<BrawlerTrial05Quest> id = new QuestId.Builder<>(BrawlerTrial05Quest::new).addRequirements(() -> BRAWLER_TRIAL_04.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "brawler_trial_05"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<DoctorTrial01Quest>> DOCTOR_TRIAL_01 = ModRegistries.QUESTS_REGISTRY.register("doctor_trial_01", () -> {
        QuestId<DoctorTrial01Quest> id = new QuestId.Builder<>(DoctorTrial01Quest::new).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "doctor_trial_01"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<DoctorTrial02Quest>> DOCTOR_TRIAL_02 = ModRegistries.QUESTS_REGISTRY.register("doctor_trial_02", () -> {
        QuestId<DoctorTrial02Quest> id = new QuestId.Builder<>(DoctorTrial02Quest::new).addRequirements(() -> DOCTOR_TRIAL_01.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "doctor_trial_02"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<DoctorTrial03Quest>> DOCTOR_TRIAL_03 = ModRegistries.QUESTS_REGISTRY.register("doctor_trial_03", () -> {
        QuestId<DoctorTrial03Quest> id = new QuestId.Builder<>(DoctorTrial03Quest::new).addRequirements(() -> DOCTOR_TRIAL_02.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "doctor_trial_03"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<DoctorTrial04Quest>> DOCTOR_TRIAL_04 = ModRegistries.QUESTS_REGISTRY.register("doctor_trial_04", () -> {
        QuestId<DoctorTrial04Quest> id = new QuestId.Builder<>(DoctorTrial04Quest::new).addRequirements(() -> DOCTOR_TRIAL_03.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "doctor_trial_04"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<DoctorTrial05Quest>> DOCTOR_TRIAL_05 = ModRegistries.QUESTS_REGISTRY.register("doctor_trial_05", () -> {
        QuestId<DoctorTrial05Quest> id = new QuestId.Builder<>(DoctorTrial05Quest::new).addRequirements(() -> DOCTOR_TRIAL_04.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "doctor_trial_05"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<WeatherTrial01Quest>> WEATHER_TRIAL_01 = ModRegistries.QUESTS_REGISTRY.register("weather_trial_01", () -> {
        QuestId<WeatherTrial01Quest> id = new QuestId.Builder<>(WeatherTrial01Quest::new).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "weather_trial_01"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<WeatherTrial02Quest>> WEATHER_TRIAL_02 = ModRegistries.QUESTS_REGISTRY.register("weather_trial_02", () -> {
        QuestId<WeatherTrial02Quest> id = new QuestId.Builder<>(WeatherTrial02Quest::new).addRequirements(() -> WEATHER_TRIAL_01.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "weather_trial_02"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<WeatherTrial03Quest>> WEATHER_TRIAL_03 = ModRegistries.QUESTS_REGISTRY.register("weather_trial_03", () -> {
        QuestId<WeatherTrial03Quest> id = new QuestId.Builder<>(WeatherTrial03Quest::new).addRequirements(() -> WEATHER_TRIAL_02.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "weather_trial_03"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<WeatherTrial04Quest>> WEATHER_TRIAL_04 = ModRegistries.QUESTS_REGISTRY.register("weather_trial_04", () -> {
        QuestId<WeatherTrial04Quest> id = new QuestId.Builder<>(WeatherTrial04Quest::new).addRequirements(() -> WEATHER_TRIAL_03.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "weather_trial_04"));
        return id;
    });

    public static final DeferredHolder<QuestId<?>, QuestId<WeatherTrial05Quest>> WEATHER_TRIAL_05 = ModRegistries.QUESTS_REGISTRY.register("weather_trial_05", () -> {
        QuestId<WeatherTrial05Quest> id = new QuestId.Builder<>(WeatherTrial05Quest::new).addRequirements(() -> WEATHER_TRIAL_04.get()).build();
        id.setKey(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "weather_trial_05"));
        return id;
    });

    public static void init(IEventBus bus) {
        ModRegistries.QUESTS_REGISTRY.register(bus);
    }
}
