package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import xyz.pixelatedw.mineminenomi.entities.mobs.BruteEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.CaptainEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.SniperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.PirateTraderEntity;

import java.util.function.Supplier;

public class ModMobs {

    // --- Marines ---
    public static final Supplier<EntityType<GruntEntity>> MARINE_GRUNT = ModRegistry.ENTITY_TYPES.register("marine_grunt",
            () -> EntityType.Builder.of(GruntEntity::createMarineGrunt, MobCategory.MONSTER).sized(0.6F, 1.8F).build("marine_grunt"));

    public static final Supplier<EntityType<BruteEntity>> MARINE_BRUTE = ModRegistry.ENTITY_TYPES.register("marine_brute",
            () -> EntityType.Builder.of(BruteEntity::createMarineBrute, MobCategory.MONSTER).sized(0.8F, 2.3F).build("marine_brute"));

    public static final Supplier<EntityType<CaptainEntity>> MARINE_CAPTAIN = ModRegistry.ENTITY_TYPES.register("marine_captain",
            () -> EntityType.Builder.of(CaptainEntity::createMarineCaptain, MobCategory.MONSTER).sized(0.6F, 1.8F).build("marine_captain"));

    public static final Supplier<EntityType<SniperEntity>> MARINE_SNIPER = ModRegistry.ENTITY_TYPES.register("marine_sniper",
            () -> EntityType.Builder.of(SniperEntity::createMarineSniper, MobCategory.MONSTER).sized(0.6F, 1.8F).build("marine_sniper"));

    public static final Supplier<EntityType<NotoriousEntity>> MARINE_VICE_ADMIRAL = ModRegistry.ENTITY_TYPES.register("marine_vice_admiral",
            () -> EntityType.Builder.of(NotoriousEntity::createMarine, MobCategory.MONSTER).sized(0.6F, 2.0F).build("marine_vice_admiral"));

    // --- Pirates ---
    public static final Supplier<EntityType<GruntEntity>> PIRATE_GRUNT = ModRegistry.ENTITY_TYPES.register("pirate_grunt",
            () -> EntityType.Builder.of(GruntEntity::createPirateGrunt, MobCategory.MONSTER).sized(0.6F, 1.8F).build("pirate_grunt"));

    public static final Supplier<EntityType<BruteEntity>> PIRATE_BRUTE = ModRegistry.ENTITY_TYPES.register("pirate_brute",
            () -> EntityType.Builder.of(BruteEntity::createPirateBrute, MobCategory.MONSTER).sized(0.8F, 2.3F).build("pirate_brute"));

    public static final Supplier<EntityType<CaptainEntity>> PIRATE_CAPTAIN = ModRegistry.ENTITY_TYPES.register("pirate_captain",
            () -> EntityType.Builder.of(CaptainEntity::createPirateCaptain, MobCategory.MONSTER).sized(0.6F, 1.8F).build("pirate_captain"));

    public static final Supplier<EntityType<SniperEntity>> PIRATE_SNIPER = ModRegistry.ENTITY_TYPES.register("pirate_sniper",
            () -> EntityType.Builder.of(SniperEntity::createPirateSniper, MobCategory.MONSTER).sized(0.6F, 1.8F).build("pirate_sniper"));

    public static final Supplier<EntityType<NotoriousEntity>> PIRATE_NOTORIOUS_CAPTAIN = ModRegistry.ENTITY_TYPES.register("pirate_notorious_captain",
            () -> EntityType.Builder.of(NotoriousEntity::createPirate, MobCategory.MONSTER).sized(0.6F, 2.0F).build("pirate_notorious_captain"));

    public static final Supplier<EntityType<PirateTraderEntity>> PIRATE_TRADER = ModRegistry.ENTITY_TYPES.register("pirate_trader",
            () -> EntityType.Builder.of(PirateTraderEntity::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("pirate_trader"));

    // --- Bandits ---
    public static final Supplier<EntityType<GruntEntity>> BANDIT_GRUNT = ModRegistry.ENTITY_TYPES.register("bandit_grunt",
            () -> EntityType.Builder.of(GruntEntity::createBanditGrunt, MobCategory.MONSTER).sized(0.6F, 1.8F).build("bandit_grunt"));

    public static final Supplier<EntityType<BruteEntity>> BANDIT_BRUTE = ModRegistry.ENTITY_TYPES.register("bandit_brute",
            () -> EntityType.Builder.of(BruteEntity::createBanditBrute, MobCategory.MONSTER).sized(0.8F, 2.3F).build("bandit_brute"));

    public static final Supplier<EntityType<CaptainEntity>> BANDIT_CAPTAIN = ModRegistry.ENTITY_TYPES.register("bandit_captain",
            () -> EntityType.Builder.of(CaptainEntity::createBanditCaptain, MobCategory.MONSTER).sized(0.6F, 1.8F).build("bandit_captain"));

    public static final Supplier<EntityType<SniperEntity>> BANDIT_SNIPER = ModRegistry.ENTITY_TYPES.register("bandit_sniper",
            () -> EntityType.Builder.of(SniperEntity::createBanditSniper, MobCategory.MONSTER).sized(0.6F, 1.8F).build("bandit_sniper"));

    public static void init() {}

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MARINE_GRUNT.get(), GruntEntity.createAttributes().build());
        event.put(MARINE_BRUTE.get(), BruteEntity.createAttributes().build());
        event.put(MARINE_CAPTAIN.get(), CaptainEntity.createAttributes().build());
        event.put(MARINE_SNIPER.get(), SniperEntity.createAttributes().build());
        event.put(MARINE_VICE_ADMIRAL.get(), NotoriousEntity.createAttributes().build());

        event.put(PIRATE_GRUNT.get(), GruntEntity.createAttributes().build());
        event.put(PIRATE_BRUTE.get(), BruteEntity.createAttributes().build());
        event.put(PIRATE_CAPTAIN.get(), CaptainEntity.createAttributes().build());
        event.put(PIRATE_SNIPER.get(), SniperEntity.createAttributes().build());
        event.put(PIRATE_NOTORIOUS_CAPTAIN.get(), NotoriousEntity.createAttributes().build());
        event.put(PIRATE_TRADER.get(), PirateTraderEntity.createAttributes().build());

        event.put(BANDIT_GRUNT.get(), GruntEntity.createAttributes().build());
        event.put(BANDIT_BRUTE.get(), BruteEntity.createAttributes().build());
        event.put(BANDIT_CAPTAIN.get(), CaptainEntity.createAttributes().build());
        event.put(BANDIT_SNIPER.get(), SniperEntity.createAttributes().build());
    }
}
