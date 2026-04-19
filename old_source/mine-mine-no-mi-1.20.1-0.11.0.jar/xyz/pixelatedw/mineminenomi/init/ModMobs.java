package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.WorldEventsConfig;
import xyz.pixelatedw.mineminenomi.entities.BottomHalfBodyEntity;
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.BarkeeperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.BruteEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.CaptainEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.SniperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.MirageCloneEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.NightmareSoldierEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.WaxCloneEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BlagoriEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BlugoriEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BoxingDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.DenDenMushiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FightingFishEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FlyingFishEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.KungFuDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LegendaryMasterDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.PandaSharkEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.SeaCowEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WanderingDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WrestlingDugongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.YagaraBullEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.bandits.HigumaEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.ArtOfWeatherTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.BlackLegTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.BrawlerTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.DoctorTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanCivilianEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanTraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SniperTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SwordsmanTrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MarineTraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MorganEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.PirateTraderEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ArlongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ChewEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.MissValentineEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr0Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr1Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr5Entity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.BuchiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.JangoEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.KuroEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.ShamEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.AlvidaEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.DonKriegEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.GinEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.PearlEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.worldgov.CelestialDragonEntity;
import xyz.pixelatedw.mineminenomi.models.armors.HumandrillArmorModel;
import xyz.pixelatedw.mineminenomi.models.entities.BottomHalfModel;
import xyz.pixelatedw.mineminenomi.models.entities.PhysicalBodyModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.CelestialDragonSlaveRideModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.PacifistaModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BananawaniModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BananawaniSaddleModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.BigDuckModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DenDenMushiModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.FightingFishModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.FlyingFishModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.HumandrillModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.LapahnModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.PandaSharkModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.SeaCowModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.WhiteWalkieModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.WhiteWalkieSaddleModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.YagaraBullModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.hair.BuggyHairModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.hair.KuroobiHairModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.hair.Mr3HairModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.hair.WizardBeardModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.BottomHalfBodyRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.PhysicalBodyRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.ArtOfWeatherTrainerRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.CabajiRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.CelestialDragonRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.CloneHumanoidRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.JangoRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.KuroobiRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.Mr3Renderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.OPHumanoidRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.PacifistaRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.abilities.DoppelmanRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.abilities.NightmareSoldierRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.BananawaniRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.BigDuckRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.DenDenMushiRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.DugongRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.FightingFishRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.FlyingFishRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.GorillaRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.HumandrillRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.LapahnRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.PandaSharkRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.SeaCowRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.WanderingDugongRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.WhiteWalkieRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.animals.YagaraBullRenderer;

public class ModMobs {
   public static final MobCategory MARINES = MobCategory.create("marines", "marines", 5, false, false, 128);
   public static final MobCategory PIRATES = MobCategory.create("pirates", "pirates", 5, false, false, 128);
   public static final MobCategory BANDITS = MobCategory.create("bandits", "bandits", 5, false, false, 128);
   public static final RegistryObject<EntityType<GruntEntity>> MARINE_GRUNT = registerMarineWithSpawnEgg("Marine Grunt", () -> ModRegistry.createMobType(GruntEntity::createMarineGrunt, MARINES).m_20712_("mineminenomi:marine_grunt"));
   public static final RegistryObject<EntityType<BruteEntity>> MARINE_BRUTE = registerMarineWithSpawnEgg("Marine Brute", () -> ModRegistry.createMobType(BruteEntity::createMarineBrute, MARINES).m_20699_(0.8F, 2.3F).m_20712_("mineminenomi:marine_brute"));
   public static final RegistryObject<EntityType<SniperEntity>> MARINE_SNIPER = registerMarineWithSpawnEgg("Marine Sniper", () -> ModRegistry.createMobType(SniperEntity::createMarineSniper, MARINES).m_20712_("mineminenomi:marine_sniper"));
   public static final RegistryObject<EntityType<CaptainEntity>> MARINE_CAPTAIN = registerMarineWithSpawnEgg("Marine Captain", () -> ModRegistry.createMobType(CaptainEntity::createMarineCaptain, MARINES).m_20712_("mineminenomi:marine_captain"));
   public static final RegistryObject<EntityType<MarineTraderEntity>> MARINE_TRADER = registerMarineWithSpawnEgg("Marine Trader", () -> ModRegistry.createMobType(MarineTraderEntity::new, MARINES).m_20712_("mineminenomi:marine_trader"));
   public static final RegistryObject<EntityType<PacifistaEntity>> PACIFISTA = registerMarineWithSpawnEgg("Pacifista", () -> ModRegistry.createMobType(PacifistaEntity::new, MARINES).m_20699_(1.5F, 3.5F).m_20712_("mineminenomi:pacifista"));
   public static final RegistryObject<EntityType<NotoriousEntity>> MARINE_VICE_ADMIRAL = registerMarineWithSpawnEgg("Marine Vice Admiral", () -> ModRegistry.createMobType(NotoriousEntity::createMarine, MARINES).m_20699_(0.6F, 2.0F).m_20712_("mineminenomi:marine_vice_admiral"));
   public static final RegistryObject<EntityType<MorganEntity>> MORGAN = ModRegistry.<EntityType<MorganEntity>>registerEntityType("Morgan", () -> ModRegistry.createMobType(MorganEntity::new, MARINES).m_20699_(0.8F, 2.7F).m_20712_("mineminenomi:morgan"));
   public static final RegistryObject<EntityType<CelestialDragonEntity>> CELESTIAL_DRAGON = registerWorldGovWithSpawnEgg("Celestial Dragon", () -> ModRegistry.createMobType(CelestialDragonEntity::new, MARINES).m_20712_("mineminenomi:celestial_dragon"));
   public static final RegistryObject<EntityType<GruntEntity>> PIRATE_GRUNT = registerPirateWithSpawnEgg("Pirate Grunt", () -> ModRegistry.createMobType(GruntEntity::createPirateGrunt, PIRATES).m_20712_("mineminenomi:pirate_grunt"));
   public static final RegistryObject<EntityType<BruteEntity>> PIRATE_BRUTE = registerPirateWithSpawnEgg("Pirate Brute", () -> ModRegistry.createMobType(BruteEntity::createPirateBrute, PIRATES).m_20699_(0.8F, 2.3F).m_20712_("mineminenomi:pirate_brute"));
   public static final RegistryObject<EntityType<CaptainEntity>> PIRATE_CAPTAIN = registerPirateWithSpawnEgg("Pirate Captain", () -> ModRegistry.createMobType(CaptainEntity::createPirateCaptain, PIRATES).m_20712_("mineminenomi:pirate_captain"));
   public static final RegistryObject<EntityType<PirateTraderEntity>> PIRATE_TRADER = registerPirateWithSpawnEgg("Pirate Trader", () -> ModRegistry.createMobType(PirateTraderEntity::new, PIRATES).m_20712_("mineminenomi:pirate_trader"));
   public static final RegistryObject<EntityType<NotoriousEntity>> PIRATE_NOTORIOUS_CAPTAIN = registerPirateWithSpawnEgg("Pirate Notorious Captain", () -> ModRegistry.createMobType(NotoriousEntity::createPirate, MARINES).m_20699_(0.6F, 2.0F).m_20712_("mineminenomi:pirate_notorious_captain"));
   public static final RegistryObject<EntityType<BuggyEntity>> BUGGY = ModRegistry.<EntityType<BuggyEntity>>registerEntityType("Buggy", () -> ModRegistry.createMobType(BuggyEntity::new, PIRATES).m_20712_("mineminenomi:buggy"));
   public static final RegistryObject<EntityType<AlvidaEntity>> ALVIDA = ModRegistry.<EntityType<AlvidaEntity>>registerEntityType("Alvida", () -> ModRegistry.createMobType(AlvidaEntity::new, PIRATES).m_20712_("mineminenomi:alvida"));
   public static final RegistryObject<EntityType<AlvidaEntity>> ALVIDA_SLIM = ModRegistry.<EntityType<AlvidaEntity>>registerEntityType("Alvida", "alvida2", () -> ModRegistry.createMobType(AlvidaEntity::new, PIRATES).m_20712_("mineminenomi:alvida2"));
   public static final RegistryObject<EntityType<CabajiEntity>> CABAJI = ModRegistry.<EntityType<CabajiEntity>>registerEntityType("Cabaji", () -> ModRegistry.createMobType(CabajiEntity::new, PIRATES).m_20712_("mineminenomi:cabaji"));
   public static final RegistryObject<EntityType<ShamEntity>> SHAM = ModRegistry.<EntityType<ShamEntity>>registerEntityType("Sham", () -> ModRegistry.createMobType(ShamEntity::new, PIRATES).m_20712_("mineminenomi:sham"));
   public static final RegistryObject<EntityType<BuchiEntity>> BUCHI = ModRegistry.<EntityType<BuchiEntity>>registerEntityType("Buchi", () -> ModRegistry.createMobType(BuchiEntity::new, PIRATES).m_20712_("mineminenomi:buchi"));
   public static final RegistryObject<EntityType<JangoEntity>> JANGO = ModRegistry.<EntityType<JangoEntity>>registerEntityType("Jango", () -> ModRegistry.createMobType(JangoEntity::new, PIRATES).m_20712_("mineminenomi:jango"));
   public static final RegistryObject<EntityType<KuroEntity>> KURO = ModRegistry.<EntityType<KuroEntity>>registerEntityType("Kuro", () -> ModRegistry.createMobType(KuroEntity::new, PIRATES).m_20712_("mineminenomi:kuro"));
   public static final RegistryObject<EntityType<GinEntity>> GIN = ModRegistry.<EntityType<GinEntity>>registerEntityType("Gin", () -> ModRegistry.createMobType(GinEntity::new, PIRATES).m_20712_("mineminenomi:gin"));
   public static final RegistryObject<EntityType<PearlEntity>> PEARL = ModRegistry.<EntityType<PearlEntity>>registerEntityType("Pearl", () -> ModRegistry.createMobType(PearlEntity::new, PIRATES).m_20699_(0.8F, 2.5F).m_20712_("mineminenomi:pearl"));
   public static final RegistryObject<EntityType<DonKriegEntity>> DON_KRIEG = ModRegistry.<EntityType<DonKriegEntity>>registerEntityType("Don Krieg", () -> ModRegistry.createMobType(DonKriegEntity::new, PIRATES).m_20699_(0.8F, 2.5F).m_20712_("mineminenomi:don_krieg"));
   public static final RegistryObject<EntityType<ChewEntity>> CHEW = ModRegistry.<EntityType<ChewEntity>>registerEntityType("Chew", () -> ModRegistry.createMobType(ChewEntity::new, PIRATES).m_20699_(0.75F, 2.75F).m_20712_("mineminenomi:chew"));
   public static final RegistryObject<EntityType<KuroobiEntity>> KUROOBI = ModRegistry.<EntityType<KuroobiEntity>>registerEntityType("Kuroobi", () -> ModRegistry.createMobType(KuroobiEntity::new, PIRATES).m_20699_(0.75F, 2.7F).m_20712_("mineminenomi:kuroobi"));
   public static final RegistryObject<EntityType<ArlongEntity>> ARLONG = ModRegistry.<EntityType<ArlongEntity>>registerEntityType("Arlong", () -> ModRegistry.createMobType(ArlongEntity::new, PIRATES).m_20699_(0.75F, 2.75F).m_20712_("mineminenomi:arlong"));
   public static final RegistryObject<EntityType<Mr5Entity>> MR5 = ModRegistry.<EntityType<Mr5Entity>>registerEntityType("Mr 5", () -> ModRegistry.createMobType(Mr5Entity::new, PIRATES).m_20712_("mineminenomi:mr5"));
   public static final RegistryObject<EntityType<MissValentineEntity>> MISS_VALENTINE = ModRegistry.<EntityType<MissValentineEntity>>registerEntityType("Miss Valentine", () -> ModRegistry.createMobType(MissValentineEntity::new, PIRATES).m_20712_("mineminenomi:miss_valentine"));
   public static final RegistryObject<EntityType<Mr3Entity>> MR3 = ModRegistry.<EntityType<Mr3Entity>>registerEntityType("Mr 3", () -> ModRegistry.createMobType(Mr3Entity::new, PIRATES).m_20712_("mineminenomi:mr3"));
   public static final RegistryObject<EntityType<Mr1Entity>> MR1 = ModRegistry.<EntityType<Mr1Entity>>registerEntityType("Mr 1", () -> ModRegistry.createMobType(Mr1Entity::new, PIRATES).m_20699_(0.9F, 2.3F).m_20712_("mineminenomi:mr1"));
   public static final RegistryObject<EntityType<Mr0Entity>> MR0 = ModRegistry.<EntityType<Mr0Entity>>registerEntityType("Mr 0", () -> ModRegistry.createMobType(Mr0Entity::new, PIRATES).m_20699_(0.7F, 2.0F).m_20712_("mineminenomi:mr0"));
   public static final RegistryObject<EntityType<GruntEntity>> BANDIT_GRUNT = registerBanditWithSpawnEgg("Bandit Grunt", () -> ModRegistry.createMobType(GruntEntity::createBanditGrunt, BANDITS).m_20712_("mineminenomi:bandit_grunt"));
   public static final RegistryObject<EntityType<BruteEntity>> BANDIT_BRUTE = registerBanditWithSpawnEgg("Bandit Brute", () -> ModRegistry.createMobType(BruteEntity::createBanditBrute, BANDITS).m_20699_(0.8F, 2.3F).m_20712_("mineminenomi:bandit_brute"));
   public static final RegistryObject<EntityType<SniperEntity>> BANDIT_SNIPER = registerBanditWithSpawnEgg("Bandit Sniper", () -> ModRegistry.createMobType(SniperEntity::createBanditSniper, BANDITS).m_20712_("mineminenomi:bandit_sniper"));
   public static final RegistryObject<EntityType<CaptainEntity>> BANDIT_CAPTAIN = registerBanditWithSpawnEgg("Bandit Leader", () -> ModRegistry.createMobType(CaptainEntity::createBanditCaptain, BANDITS).m_20712_("mineminenomi:bandit_captain"));
   public static final RegistryObject<EntityType<HigumaEntity>> HIGUMA = ModRegistry.<EntityType<HigumaEntity>>registerEntityType("Higuma", () -> ModRegistry.createMobType(HigumaEntity::new, BANDITS).m_20699_(0.8F, 1.9F).m_20712_("mineminenomi:higuma"));
   public static final RegistryObject<EntityType<SwordsmanTrainerEntity>> SWORDSMAN_TRAINER = registerFactionlessWithSpawnEgg("Swordsman Trainer", () -> ModRegistry.createMobType(SwordsmanTrainerEntity::new, MobCategory.MISC).m_20712_("mineminenomi:swordsman_trainer"));
   public static final RegistryObject<EntityType<SniperTrainerEntity>> SNIPER_TRAINER = registerFactionlessWithSpawnEgg("Sniper Trainer", () -> ModRegistry.createMobType(SniperTrainerEntity::new, MobCategory.MISC).m_20712_("mineminenomi:sniper_trainer"));
   public static final RegistryObject<EntityType<ArtOfWeatherTrainerEntity>> ART_OF_WEATHER_TRAINER = registerFactionlessWithSpawnEgg("Art of Weather Trainer", () -> ModRegistry.createMobType(ArtOfWeatherTrainerEntity::new, MobCategory.MISC).m_20712_("mineminenomi:art_of_weather_trainer"));
   public static final RegistryObject<EntityType<DoctorTrainerEntity>> DOCTOR_TRAINER = registerFactionlessWithSpawnEgg("Doctor Trainer", () -> ModRegistry.createMobType(DoctorTrainerEntity::new, MobCategory.MISC).m_20712_("mineminenomi:doctor_trainer"));
   public static final RegistryObject<EntityType<BrawlerTrainerEntity>> BRAWLER_TRAINER = registerFactionlessWithSpawnEgg("Brawler Trainer", () -> ModRegistry.createMobType(BrawlerTrainerEntity::new, MobCategory.MISC).m_20712_("mineminenomi:brawler_trainer"));
   public static final RegistryObject<EntityType<BlackLegTrainerEntity>> BLACK_LEG_TRAINER = registerFactionlessWithSpawnEgg("Black Leg Trainer", () -> ModRegistry.createMobType(BlackLegTrainerEntity::new, MobCategory.MISC).m_20712_("mineminenomi:black_leg_trainer"));
   public static final RegistryObject<EntityType<SkypieanCivilianEntity>> SKYPIEAN_CIVILIAN = registerFactionlessWithSpawnEgg("Skypiean Civilian", () -> ModRegistry.createMobType(SkypieanCivilianEntity::new, MobCategory.MISC).m_20712_("mineminenomi:skypiean_civilian"));
   public static final RegistryObject<EntityType<SkypieanTraderEntity>> SKYPIEAN_TRADER = registerFactionlessWithSpawnEgg("Skypiean Trader", () -> ModRegistry.createMobType(SkypieanTraderEntity::new, MobCategory.MISC).m_20712_("mineminenomi:skypiean_trader"));
   public static final RegistryObject<EntityType<BarkeeperEntity>> BARKEEPER = registerFactionlessWithSpawnEgg("Barkeeper", () -> ModRegistry.createMobType(BarkeeperEntity::new, MobCategory.MISC).m_20712_("mineminenomi:barkeeper"));
   public static final RegistryObject<EntityType<DenDenMushiEntity>> DEN_DEN_MUSHI = registerAnimalWithSpawnEgg("Den Den Mushi", () -> ModRegistry.createMobType(DenDenMushiEntity::new, MobCategory.CREATURE).m_20699_(0.8F, 0.8F).m_20712_("mineminenomi:den_den_mushi"));
   public static final RegistryObject<EntityType<LapahnEntity>> LAPAHN = registerAnimalWithSpawnEgg("Lapahn", () -> ModRegistry.createMobType(LapahnEntity::new, MobCategory.CREATURE).m_20699_(1.5F, 2.3F).m_20712_("mineminenomi:lapahn"));
   public static final RegistryObject<EntityType<YagaraBullEntity>> YAGARA_BULL = registerAnimalWithSpawnEgg("Yagara Bull", () -> ModRegistry.createMobType(YagaraBullEntity::new, MobCategory.WATER_CREATURE).m_20699_(1.4F, 1.6F).m_20702_(10).m_20712_("mineminenomi:yagara_bull"));
   public static final RegistryObject<EntityType<HumandrillEntity>> HUMANDRILL = registerAnimalWithSpawnEgg("Humandrill", () -> ModRegistry.createMobType(HumandrillEntity::new, MobCategory.CREATURE).m_20699_(1.0F, 2.5F).m_20712_("mineminenomi:humandrill"));
   public static final RegistryObject<EntityType<FightingFishEntity>> FIGHTING_FISH = registerAnimalWithSpawnEgg("Fighting Fish", () -> ModRegistry.createMobType(FightingFishEntity::new, MobCategory.WATER_CREATURE).m_20699_(3.0F, 3.0F).m_20712_("mineminenomi:fighting_fish"));
   public static final RegistryObject<EntityType<BananawaniEntity>> BANANAWANI = registerAnimalWithSpawnEgg("Bananawani", () -> ModRegistry.createMobType(BananawaniEntity::new, MobCategory.CREATURE).m_20699_(2.0F, 1.75F).m_20712_("mineminenomi:bananwani"));
   public static final RegistryObject<EntityType<BigDuckEntity>> BIG_DUCK = registerAnimalWithSpawnEgg("Super Spot-Billed Duck", () -> ModRegistry.createMobType(BigDuckEntity::new, MobCategory.CREATURE).m_20699_(1.25F, 1.75F).m_20712_("mineminenomi:big_duck"));
   public static final RegistryObject<EntityType<SeaCowEntity>> SEA_COW = registerAnimalWithSpawnEgg("Sea Cow", () -> ModRegistry.createMobType(SeaCowEntity::new, MobCategory.WATER_CREATURE).m_20699_(1.5F, 1.5F).m_20712_("mineminenomi:sea_cow"));
   public static final RegistryObject<EntityType<WhiteWalkieEntity>> WHITE_WALKIE = registerAnimalWithSpawnEgg("White Walkie", () -> ModRegistry.createMobType(WhiteWalkieEntity::new, MobCategory.CREATURE).m_20699_(2.0F, 2.0F).m_20712_("mineminenomi:white_walkie"));
   public static final RegistryObject<EntityType<PandaSharkEntity>> PANDA_SHARK = registerAnimalWithSpawnEgg("Panda Shark", () -> ModRegistry.createMobType(PandaSharkEntity::new, MobCategory.WATER_CREATURE).m_20699_(1.5F, 1.5F).m_20712_("mineminenomi:panda_shark"));
   public static final RegistryObject<EntityType<FlyingFishEntity>> FLYING_FISH = registerAnimalWithSpawnEgg("Flying Fish", () -> ModRegistry.createMobType(FlyingFishEntity::new, MobCategory.WATER_CREATURE).m_20699_(1.5F, 1.5F).m_20712_("mineminenomi:flying_fish"));
   public static final RegistryObject<EntityType<BlugoriEntity>> BLUGORI = registerAnimalWithSpawnEgg("Blugori", () -> ModRegistry.createMobType(BlugoriEntity::new, MobCategory.CREATURE).m_20699_(1.0F, 1.5F).m_20712_("mineminenomi:blugori"));
   public static final RegistryObject<EntityType<BlagoriEntity>> BLAGORI = registerAnimalWithSpawnEgg("Blagori", () -> ModRegistry.createMobType(BlagoriEntity::new, MobCategory.CREATURE).m_20699_(1.0F, 1.5F).m_20712_("mineminenomi:blagori"));
   public static final RegistryObject<EntityType<KungFuDugongEntity>> KUNG_FU_DUGONG = registerAnimalWithSpawnEgg("Kung Fu Dugong", () -> ModRegistry.createMobType(KungFuDugongEntity::new, MobCategory.CREATURE).m_20699_(0.6F, 1.2F).m_20712_("mineminenomi:kung_fu_dugong"));
   public static final RegistryObject<EntityType<WrestlingDugongEntity>> WRESTLING_DUGONG = registerAnimalWithSpawnEgg("Wrestling Dugong", () -> ModRegistry.createMobType(WrestlingDugongEntity::new, MobCategory.CREATURE).m_20699_(0.6F, 1.2F).m_20712_("mineminenomi:wrestling_dugong"));
   public static final RegistryObject<EntityType<BoxingDugongEntity>> BOXING_DUGONG = registerAnimalWithSpawnEgg("Boxing Dugong", () -> ModRegistry.createMobType(BoxingDugongEntity::new, MobCategory.CREATURE).m_20699_(0.6F, 1.2F).m_20712_("mineminenomi:boxing_dugong"));
   public static final RegistryObject<EntityType<LegendaryMasterDugongEntity>> LEGENDARY_MASTER_DUGONG = registerAnimalWithSpawnEgg("Legendary Master Dugong", () -> ModRegistry.createMobType(LegendaryMasterDugongEntity::new, MobCategory.CREATURE).m_20699_(0.6F, 1.2F).m_20712_("mineminenomi:legendary_master_dugong"));
   public static final RegistryObject<EntityType<WanderingDugongEntity>> WANDERING_DUGONG = registerAnimalWithSpawnEgg("Wandering Dugong", () -> ModRegistry.createMobType(WanderingDugongEntity::new, MobCategory.CREATURE).m_20699_(0.6F, 1.2F).m_20712_("mineminenomi:wandering_dugong"));
   public static final RegistryObject<EntityType<MirageCloneEntity>> MIRAGE_CLONE = ModRegistry.<EntityType<MirageCloneEntity>>registerEntityType("Mirage Clone", () -> ModRegistry.createMobType(MirageCloneEntity::new, MobCategory.MISC).m_20712_("mineminenomi:mirage_clone"));
   public static final RegistryObject<EntityType<WaxCloneEntity>> WAX_CLONE = ModRegistry.<EntityType<WaxCloneEntity>>registerEntityType("Wax Clone", () -> ModRegistry.createMobType(WaxCloneEntity::new, MobCategory.MISC).m_20712_("mineminenomi:wax_clone"));
   public static final RegistryObject<EntityType<BlackKnightEntity>> BLACK_KNIGHT = ModRegistry.<EntityType<BlackKnightEntity>>registerEntityType("Black Knight", () -> ModRegistry.createMobType(BlackKnightEntity::new, MobCategory.MISC).m_20712_("mineminenomi:black_knight"));
   public static final RegistryObject<EntityType<DoppelmanEntity>> DOPPELMAN = ModRegistry.<EntityType<DoppelmanEntity>>registerEntityType("Doppelman", () -> ModRegistry.createMobType(DoppelmanEntity::new, MobCategory.MISC).m_20712_("mineminenomi:doppelman"));
   public static final RegistryObject<EntityType<NightmareSoldierEntity>> NIGHTMARE_SOLDIER = ModRegistry.<EntityType<NightmareSoldierEntity>>registerEntityType("Nightmare Soldier", () -> ModRegistry.createMobType(NightmareSoldierEntity::new, MobCategory.MISC).m_20712_("mineminenomi:nightmare_soldier"));
   public static final RegistryObject<EntityType<PhysicalBodyEntity>> PHYSICAL_BODY = ModRegistry.<EntityType<PhysicalBodyEntity>>registerEntityType("Physical Body", () -> ModRegistry.createEntityType(PhysicalBodyEntity::new, MobCategory.MISC).m_20712_("mineminenomi:physical_body"));
   public static final RegistryObject<EntityType<BottomHalfBodyEntity>> BOTTOM_HALF = ModRegistry.<EntityType<BottomHalfBodyEntity>>registerEntityType("Bottom Half", () -> ModRegistry.createEntityType(BottomHalfBodyEntity::new, MobCategory.MISC).setUpdateInterval(1).m_20712_("mineminenomi:bottom_half"));

   private static <T extends EntityType<? extends Mob>> RegistryObject<T> registerMarineWithSpawnEgg(String name, Supplier<T> supp) {
      RegistryObject<T> reg = ModRegistry.<T>registerEntityType(name, supp);
      Supplier<ForgeSpawnEggItem> eggItem = () -> new ForgeSpawnEggItem(reg, 150145, 16250871, new Item.Properties());
      ModRegistry.registerSpawnEggItem(name, eggItem);
      return reg;
   }

   private static <T extends EntityType<? extends Mob>> RegistryObject<T> registerWorldGovWithSpawnEgg(String name, Supplier<T> supp) {
      RegistryObject<T> reg = ModRegistry.<T>registerEntityType(name, supp);
      ModRegistry.registerSpawnEggItem(name, () -> new ForgeSpawnEggItem(reg, 12434877, 3092271, new Item.Properties()));
      return reg;
   }

   private static <T extends EntityType<? extends Mob>> RegistryObject<T> registerPirateWithSpawnEgg(String name, Supplier<T> supp) {
      RegistryObject<T> reg = ModRegistry.<T>registerEntityType(name, supp);
      Supplier<ForgeSpawnEggItem> eggItem = () -> new ForgeSpawnEggItem(reg, 12655644, 16250871, new Item.Properties());
      ModRegistry.registerSpawnEggItem(name, eggItem);
      return reg;
   }

   private static <T extends EntityType<? extends Mob>> RegistryObject<T> registerBanditWithSpawnEgg(String name, Supplier<T> supp) {
      RegistryObject<T> reg = ModRegistry.<T>registerEntityType(name, supp);
      Supplier<ForgeSpawnEggItem> eggItem = () -> new ForgeSpawnEggItem(reg, 7885653, 16250871, new Item.Properties());
      ModRegistry.registerSpawnEggItem(name, eggItem);
      return reg;
   }

   private static <T extends EntityType<? extends Mob>> RegistryObject<T> registerFactionlessWithSpawnEgg(String name, Supplier<T> supp) {
      RegistryObject<T> reg = ModRegistry.<T>registerEntityType(name, supp);
      Supplier<ForgeSpawnEggItem> eggItem = () -> new ForgeSpawnEggItem(reg, 16498508, 16250871, new Item.Properties());
      ModRegistry.registerSpawnEggItem(name, eggItem);
      return reg;
   }

   private static <T extends EntityType<? extends Mob>> RegistryObject<T> registerAnimalWithSpawnEgg(String name, Supplier<T> supp) {
      RegistryObject<T> reg = ModRegistry.<T>registerEntityType(name, supp);
      Supplier<ForgeSpawnEggItem> eggItem = () -> new ForgeSpawnEggItem(reg, 10996276, 10680264, new Item.Properties());
      ModRegistry.registerSpawnEggItem(name, eggItem);
      return reg;
   }

   private static <T extends EntityType<? extends Mob>> RegistryObject<T> registerDummyWithSpawnEgg(String name, Supplier<T> supp) {
      RegistryObject<T> reg = ModRegistry.<T>registerEntityType(name, supp);
      Supplier<ForgeSpawnEggItem> eggItem = () -> new ForgeSpawnEggItem(reg, 16711901, 15662848, new Item.Properties());
      ModRegistry.registerSpawnEggItem(name, eggItem);
      return reg;
   }

   public static void init() {
   }

   public static void createAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)MARINE_GRUNT.get(), GruntEntity.createAttributes().m_22265_());
      event.put((EntityType)MARINE_BRUTE.get(), BruteEntity.createAttributes().m_22265_());
      event.put((EntityType)MARINE_CAPTAIN.get(), CaptainEntity.createAttributes().m_22265_());
      event.put((EntityType)MARINE_SNIPER.get(), SniperEntity.createAttributes().m_22265_());
      event.put((EntityType)MARINE_TRADER.get(), TraderEntity.createAttributes().m_22265_());
      event.put((EntityType)PACIFISTA.get(), PacifistaEntity.createAttributes().m_22265_());
      event.put((EntityType)MARINE_VICE_ADMIRAL.get(), NotoriousEntity.createAttributes().m_22265_());
      event.put((EntityType)MORGAN.get(), MorganEntity.createAttributes().m_22265_());
      event.put((EntityType)CELESTIAL_DRAGON.get(), CelestialDragonEntity.createAttributes().m_22265_());
      event.put((EntityType)PIRATE_GRUNT.get(), GruntEntity.createAttributes().m_22265_());
      event.put((EntityType)PIRATE_BRUTE.get(), BruteEntity.createAttributes().m_22265_());
      event.put((EntityType)PIRATE_CAPTAIN.get(), CaptainEntity.createAttributes().m_22265_());
      event.put((EntityType)PIRATE_TRADER.get(), PirateTraderEntity.createAttributes().m_22265_());
      event.put((EntityType)PIRATE_NOTORIOUS_CAPTAIN.get(), NotoriousEntity.createAttributes().m_22265_());
      event.put((EntityType)BUGGY.get(), BuggyEntity.createAttributes().m_22265_());
      event.put((EntityType)ALVIDA.get(), AlvidaEntity.createAttributes().m_22265_());
      event.put((EntityType)ALVIDA_SLIM.get(), AlvidaEntity.createAttributes().m_22265_());
      event.put((EntityType)CABAJI.get(), CabajiEntity.createAttributes().m_22265_());
      event.put((EntityType)SHAM.get(), ShamEntity.createAttributes().m_22265_());
      event.put((EntityType)BUCHI.get(), BuchiEntity.createAttributes().m_22265_());
      event.put((EntityType)JANGO.get(), JangoEntity.createAttributes().m_22265_());
      event.put((EntityType)KURO.get(), KuroEntity.createAttributes().m_22265_());
      event.put((EntityType)GIN.get(), GinEntity.createAttributes().m_22265_());
      event.put((EntityType)PEARL.get(), PearlEntity.createAttributes().m_22265_());
      event.put((EntityType)DON_KRIEG.get(), DonKriegEntity.createAttributes().m_22265_());
      event.put((EntityType)CHEW.get(), ChewEntity.createAttributes().m_22265_());
      event.put((EntityType)KUROOBI.get(), KuroobiEntity.createAttributes().m_22265_());
      event.put((EntityType)ARLONG.get(), ArlongEntity.createAttributes().m_22265_());
      event.put((EntityType)MR5.get(), Mr5Entity.createAttributes().m_22265_());
      event.put((EntityType)MISS_VALENTINE.get(), MissValentineEntity.createAttributes().m_22265_());
      event.put((EntityType)MR3.get(), Mr3Entity.createAttributes().m_22265_());
      event.put((EntityType)MR1.get(), Mr1Entity.createAttributes().m_22265_());
      event.put((EntityType)MR0.get(), Mr0Entity.createAttributes().m_22265_());
      event.put((EntityType)BANDIT_GRUNT.get(), GruntEntity.createAttributes().m_22265_());
      event.put((EntityType)BANDIT_BRUTE.get(), BruteEntity.createAttributes().m_22265_());
      event.put((EntityType)BANDIT_SNIPER.get(), SniperEntity.createAttributes().m_22265_());
      event.put((EntityType)BANDIT_CAPTAIN.get(), CaptainEntity.createAttributes().m_22265_());
      event.put((EntityType)HIGUMA.get(), HigumaEntity.createAttributes().m_22265_());
      event.put((EntityType)SWORDSMAN_TRAINER.get(), SwordsmanTrainerEntity.createAttributes().m_22265_());
      event.put((EntityType)SNIPER_TRAINER.get(), SniperTrainerEntity.createAttributes().m_22265_());
      event.put((EntityType)DOCTOR_TRAINER.get(), DoctorTrainerEntity.createAttributes().m_22265_());
      event.put((EntityType)ART_OF_WEATHER_TRAINER.get(), ArtOfWeatherTrainerEntity.createAttributes().m_22265_());
      event.put((EntityType)BRAWLER_TRAINER.get(), BrawlerTrainerEntity.createAttributes().m_22265_());
      event.put((EntityType)BLACK_LEG_TRAINER.get(), BlackLegTrainerEntity.createAttributes().m_22265_());
      event.put((EntityType)SKYPIEAN_CIVILIAN.get(), SkypieanCivilianEntity.createAttributes().m_22265_());
      event.put((EntityType)SKYPIEAN_TRADER.get(), SkypieanTraderEntity.createAttributes().m_22265_());
      event.put((EntityType)BARKEEPER.get(), BarkeeperEntity.createAttributes().m_22265_());
      event.put((EntityType)DEN_DEN_MUSHI.get(), DenDenMushiEntity.createAttributes().m_22265_());
      event.put((EntityType)LAPAHN.get(), LapahnEntity.createAttributes().m_22265_());
      event.put((EntityType)YAGARA_BULL.get(), YagaraBullEntity.createAttributes().m_22265_());
      event.put((EntityType)HUMANDRILL.get(), HumandrillEntity.createAttributes().m_22265_());
      event.put((EntityType)FIGHTING_FISH.get(), FightingFishEntity.createAttributes().m_22265_());
      event.put((EntityType)BANANAWANI.get(), BananawaniEntity.createAttributes().m_22265_());
      event.put((EntityType)BIG_DUCK.get(), BigDuckEntity.createAttributes().m_22265_());
      event.put((EntityType)SEA_COW.get(), SeaCowEntity.createAttributes().m_22265_());
      event.put((EntityType)WHITE_WALKIE.get(), WhiteWalkieEntity.createAttributes().m_22265_());
      event.put((EntityType)PANDA_SHARK.get(), PandaSharkEntity.createAttributes().m_22265_());
      event.put((EntityType)FLYING_FISH.get(), FlyingFishEntity.createAttributes().m_22265_());
      event.put((EntityType)BLUGORI.get(), BlugoriEntity.createAttributes().m_22265_());
      event.put((EntityType)BLAGORI.get(), BlagoriEntity.createAttributes().m_22265_());
      event.put((EntityType)KUNG_FU_DUGONG.get(), AbstractDugongEntity.createAttributes().m_22265_());
      event.put((EntityType)WRESTLING_DUGONG.get(), WrestlingDugongEntity.createAttributes().m_22265_());
      event.put((EntityType)BOXING_DUGONG.get(), BoxingDugongEntity.createAttributes().m_22265_());
      event.put((EntityType)LEGENDARY_MASTER_DUGONG.get(), LegendaryMasterDugongEntity.createAttributes().m_22265_());
      event.put((EntityType)WANDERING_DUGONG.get(), WanderingDugongEntity.createAttributes().m_22265_());
      event.put((EntityType)MIRAGE_CLONE.get(), MirageCloneEntity.createAttributes().m_22265_());
      event.put((EntityType)WAX_CLONE.get(), WaxCloneEntity.createAttributes().m_22265_());
      event.put((EntityType)PHYSICAL_BODY.get(), PhysicalBodyEntity.createAttributes().m_22265_());
      event.put((EntityType)BOTTOM_HALF.get(), BottomHalfBodyEntity.createAttributes().m_22265_());
      event.put((EntityType)BLACK_KNIGHT.get(), BlackKnightEntity.createAttributes().m_22265_());
      event.put((EntityType)DOPPELMAN.get(), DoppelmanEntity.createAttributes().m_22265_());
      event.put((EntityType)NIGHTMARE_SOLDIER.get(), NightmareSoldierEntity.createAttributes().m_22265_());
   }

   public static void setupSpawnPlacement(SpawnPlacementRegisterEvent event) {
      if ((Boolean)WorldEventsConfig.SPAWN_WORLD_HUMANOIDS.get()) {
         event.register((EntityType)MARINE_GRUNT.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, GruntEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)MARINE_BRUTE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, BruteEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)MARINE_SNIPER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SniperEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)MARINE_CAPTAIN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, CaptainEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)MARINE_TRADER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, OPEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)PACIFISTA.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, PacifistaEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)MARINE_VICE_ADMIRAL.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, OPEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)PIRATE_GRUNT.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, GruntEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)PIRATE_BRUTE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, BruteEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)PIRATE_CAPTAIN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, CaptainEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)PIRATE_TRADER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, OPEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)PIRATE_NOTORIOUS_CAPTAIN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, OPEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)BANDIT_GRUNT.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, GruntEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)BANDIT_BRUTE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, BruteEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)BANDIT_SNIPER.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SniperEntity::checkSpawnRules, Operation.REPLACE);
      }

      if ((Boolean)WorldEventsConfig.SPAWN_WORLD_ANIMALS.get()) {
         event.register((EntityType)DEN_DEN_MUSHI.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_, Operation.REPLACE);
         event.register((EntityType)LAPAHN.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)YAGARA_BULL.get(), Type.NO_RESTRICTIONS, Types.MOTION_BLOCKING_NO_LEAVES, YagaraBullEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)HUMANDRILL.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         SpawnPlacements.Type ffPlacement = WyHelper.isAprilFirst() ? Type.NO_RESTRICTIONS : Type.IN_WATER;
         event.register((EntityType)FIGHTING_FISH.get(), ffPlacement, Types.MOTION_BLOCKING_NO_LEAVES, FightingFishEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)BANANAWANI.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)BIG_DUCK.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)WHITE_WALKIE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)PANDA_SHARK.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, PandaSharkEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)FLYING_FISH.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, FlyingFishEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)SEA_COW.get(), Type.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, SeaCowEntity::checkSpawnRules, Operation.REPLACE);
         event.register((EntityType)BLUGORI.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)BLAGORI.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)KUNG_FU_DUGONG.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)WRESTLING_DUGONG.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)BOXING_DUGONG.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)LEGENDARY_MASTER_DUGONG.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, Operation.REPLACE);
         event.register((EntityType)WANDERING_DUGONG.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, WanderingDugongEntity::checkSpawnRules, Operation.REPLACE);
      }

   }

   public static class Client {
      public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
         event.registerLayerDefinition(DugongModel.LAYER_LOCATION, DugongModel::createBodyLayer);
         event.registerLayerDefinition(DenDenMushiModel.LAYER_LOCATION, DenDenMushiModel::createBodyLayer);
         event.registerLayerDefinition(LapahnModel.LAYER_LOCATION, LapahnModel::createBodyLayer);
         event.registerLayerDefinition(BigDuckModel.LAYER_LOCATION, BigDuckModel::createBodyLayer);
         event.registerLayerDefinition(HumandrillModel.LAYER_LOCATION, HumandrillModel::createBodyLayer);
         event.registerLayerDefinition(HumandrillArmorModel.LAYER_LOCATION, HumandrillArmorModel::createBodyLayer);
         event.registerLayerDefinition(BananawaniModel.LAYER_LOCATION, BananawaniModel::createBodyLayer);
         event.registerLayerDefinition(BananawaniSaddleModel.LAYER_LOCATION, BananawaniSaddleModel::createBodyLayer);
         event.registerLayerDefinition(GorillaModel.LAYER_LOCATION, GorillaModel::createBodyLayer);
         event.registerLayerDefinition(WhiteWalkieModel.LAYER_LOCATION, WhiteWalkieModel::createBodyLayer);
         event.registerLayerDefinition(WhiteWalkieSaddleModel.LAYER_LOCATION, WhiteWalkieSaddleModel::createBodyLayer);
         event.registerLayerDefinition(PandaSharkModel.LAYER_LOCATION, PandaSharkModel::createBodyLayer);
         event.registerLayerDefinition(FightingFishModel.LAYER_LOCATION, FightingFishModel::createBodyLayer);
         event.registerLayerDefinition(YagaraBullModel.LAYER_LOCATION, YagaraBullModel::createBodyLayer);
         event.registerLayerDefinition(SeaCowModel.LAYER_LOCATION, SeaCowModel::createBodyLayer);
         event.registerLayerDefinition(FlyingFishModel.LAYER_LOCATION, FlyingFishModel::createBodyLayer);
         event.registerLayerDefinition(PacifistaModel.LAYER_LOCATION, PacifistaModel::createBodyLayer);
         event.registerLayerDefinition(PhysicalBodyModel.WIDE_LAYER_LOCATION, () -> PhysicalBodyModel.createBodyLayer(false));
         event.registerLayerDefinition(PhysicalBodyModel.SLIM_LAYER_LOCATION, () -> PhysicalBodyModel.createBodyLayer(true));
         event.registerLayerDefinition(CelestialDragonSlaveRideModel.LAYER_LOCATION, CelestialDragonSlaveRideModel::createBodyLayer);
         event.registerLayerDefinition(BottomHalfModel.LAYER_LOCATION, BottomHalfModel::createBodyLayer);
         event.registerLayerDefinition(KuroobiHairModel.LAYER_LOCATION, KuroobiHairModel::createBodyLayer);
         event.registerLayerDefinition(BuggyHairModel.LAYER_LOCATION, BuggyHairModel::createBodyLayer);
         event.registerLayerDefinition(Mr3HairModel.LAYER_LOCATION, Mr3HairModel::createBodyLayer);
         event.registerLayerDefinition(WizardBeardModel.LAYER_LOCATION, WizardBeardModel::createBodyLayer);
      }

      public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event, EntityRendererProvider.Context ctx) {
         event.registerEntityRenderer((EntityType)ModMobs.MARINE_GRUNT.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.MARINE_BRUTE.get(), new OPHumanoidRenderer.Factory(ctx, 1.5F, 1.25F, 1.5F));
         event.registerEntityRenderer((EntityType)ModMobs.MARINE_SNIPER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.MARINE_CAPTAIN.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.MARINE_TRADER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.PACIFISTA.get(), new PacifistaRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.MORGAN.get(), (new OPHumanoidRenderer.Factory(ctx, 1.25F, 1.3F, 1.25F)).setTexture(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morgan.png")));
         event.registerEntityRenderer((EntityType)ModMobs.MARINE_VICE_ADMIRAL.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.CELESTIAL_DRAGON.get(), new CelestialDragonRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.PIRATE_GRUNT.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.PIRATE_BRUTE.get(), new OPHumanoidRenderer.Factory(ctx, 1.5F, 1.25F, 1.5F));
         event.registerEntityRenderer((EntityType)ModMobs.PIRATE_CAPTAIN.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.PIRATE_TRADER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.PIRATE_NOTORIOUS_CAPTAIN.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.BUGGY.get(), (new OPHumanoidRenderer.Factory(ctx)).setTexture(texture("buggy")));
         event.registerEntityRenderer((EntityType)ModMobs.ALVIDA.get(), (new OPHumanoidRenderer.Factory(ctx, 1.25F, 1.0F, 1.25F)).setTexture(texture("alvida")));
         event.registerEntityRenderer((EntityType)ModMobs.ALVIDA_SLIM.get(), (new OPHumanoidRenderer.Factory(ctx)).setTexture(texture("alvida_slim")));
         event.registerEntityRenderer((EntityType)ModMobs.CABAJI.get(), new CabajiRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.SHAM.get(), (new OPHumanoidRenderer.Factory(ctx, 0.9F, 1.0F, 0.9F)).setTexture(texture("sham")));
         event.registerEntityRenderer((EntityType)ModMobs.BUCHI.get(), (new OPHumanoidRenderer.Factory(ctx, 1.12F, 1.1F, 1.12F)).setTexture(texture("buchi")));
         event.registerEntityRenderer((EntityType)ModMobs.JANGO.get(), new JangoRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.KURO.get(), (new OPHumanoidRenderer.Factory(ctx)).setTexture(texture("kuro")));
         event.registerEntityRenderer((EntityType)ModMobs.GIN.get(), (new OPHumanoidRenderer.Factory(ctx)).setTexture(texture("gin")));
         event.registerEntityRenderer((EntityType)ModMobs.PEARL.get(), (new OPHumanoidRenderer.Factory(ctx, 1.5F, 1.25F, 1.5F)).setTexture(texture("pearl")));
         event.registerEntityRenderer((EntityType)ModMobs.DON_KRIEG.get(), (new OPHumanoidRenderer.Factory(ctx, 1.4F)).setTexture(texture("don_krieg")));
         event.registerEntityRenderer((EntityType)ModMobs.CHEW.get(), (new OPHumanoidRenderer.Factory(ctx, 1.35F)).setModelLayer(ModelLayers.f_171162_).setTexture(texture("chew")));
         event.registerEntityRenderer((EntityType)ModMobs.KUROOBI.get(), new KuroobiRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.ARLONG.get(), (new OPHumanoidRenderer.Factory(ctx, 1.3F)).setTexture(texture("arlong")));
         event.registerEntityRenderer((EntityType)ModMobs.MR5.get(), (new OPHumanoidRenderer.Factory(ctx)).setTexture(texture("mr5")));
         event.registerEntityRenderer((EntityType)ModMobs.MISS_VALENTINE.get(), (new OPHumanoidRenderer.Factory(ctx)).setTexture(texture("miss_valentine")));
         event.registerEntityRenderer((EntityType)ModMobs.MR3.get(), Mr3Renderer::new);
         event.registerEntityRenderer((EntityType)ModMobs.MR1.get(), (new OPHumanoidRenderer.Factory(ctx, 1.25F, 1.2F, 1.25F)).setTexture(texture("mr1")));
         event.registerEntityRenderer((EntityType)ModMobs.MR0.get(), (new OPHumanoidRenderer.Factory(ctx, 1.1F)).setTexture(texture("mr0")));
         event.registerEntityRenderer((EntityType)ModMobs.BANDIT_GRUNT.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.BANDIT_BRUTE.get(), new OPHumanoidRenderer.Factory(ctx, 1.5F, 1.25F, 1.5F));
         event.registerEntityRenderer((EntityType)ModMobs.BANDIT_SNIPER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.BANDIT_CAPTAIN.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.HIGUMA.get(), (new OPHumanoidRenderer.Factory(ctx, 1.1F)).setTexture(texture("higuma")));
         event.registerEntityRenderer((EntityType)ModMobs.SWORDSMAN_TRAINER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.SNIPER_TRAINER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.ART_OF_WEATHER_TRAINER.get(), new ArtOfWeatherTrainerRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.DOCTOR_TRAINER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.BRAWLER_TRAINER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.BLACK_LEG_TRAINER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.SKYPIEAN_CIVILIAN.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.SKYPIEAN_TRADER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.BARKEEPER.get(), new OPHumanoidRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.DEN_DEN_MUSHI.get(), new DenDenMushiRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.LAPAHN.get(), new LapahnRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.YAGARA_BULL.get(), new YagaraBullRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.HUMANDRILL.get(), new HumandrillRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.FIGHTING_FISH.get(), new FightingFishRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.BANANAWANI.get(), new BananawaniRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.BIG_DUCK.get(), new BigDuckRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.SEA_COW.get(), new SeaCowRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.WHITE_WALKIE.get(), new WhiteWalkieRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.PANDA_SHARK.get(), new PandaSharkRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.FLYING_FISH.get(), new FlyingFishRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.BLUGORI.get(), new GorillaRenderer.Factory(ctx, GorillaRenderer.BLUGORI_TEXTURE));
         event.registerEntityRenderer((EntityType)ModMobs.BLAGORI.get(), new GorillaRenderer.Factory(ctx, GorillaRenderer.BLAGORI_TEXTURE));
         event.registerEntityRenderer((EntityType)ModMobs.KUNG_FU_DUGONG.get(), new DugongRenderer.Factory(ctx, texture("kung_fu_dugong")));
         event.registerEntityRenderer((EntityType)ModMobs.WRESTLING_DUGONG.get(), new DugongRenderer.Factory(ctx, texture("wrestling_dugong")));
         event.registerEntityRenderer((EntityType)ModMobs.BOXING_DUGONG.get(), new DugongRenderer.Factory(ctx, texture("boxing_dugong")));
         event.registerEntityRenderer((EntityType)ModMobs.LEGENDARY_MASTER_DUGONG.get(), new DugongRenderer.Factory(ctx, texture("legendary_master_dugong")));
         event.registerEntityRenderer((EntityType)ModMobs.WANDERING_DUGONG.get(), new WanderingDugongRenderer.Factory(ctx));
         event.registerEntityRenderer((EntityType)ModMobs.MIRAGE_CLONE.get(), new CloneHumanoidRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.WAX_CLONE.get(), new CloneHumanoidRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.BLACK_KNIGHT.get(), new CloneHumanoidRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.DOPPELMAN.get(), new DoppelmanRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.NIGHTMARE_SOLDIER.get(), new NightmareSoldierRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.PHYSICAL_BODY.get(), new PhysicalBodyRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModMobs.BOTTOM_HALF.get(), new BottomHalfBodyRenderer.Factory());
      }

      private static ResourceLocation texture(String name) {
         return ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/" + name + ".png");
      }
   }
}
