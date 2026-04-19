package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.entities.PartEntityType;
import xyz.pixelatedw.mineminenomi.entities.AbilityPart;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.entities.SniperTargetEntity;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;
import xyz.pixelatedw.mineminenomi.entities.SpikeEntity;
import xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;
import xyz.pixelatedw.mineminenomi.entities.VivreCardEntity;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.ChloroBallCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.KemuriBoshiCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.MH5CloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.MirageTempoCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;
import xyz.pixelatedw.mineminenomi.models.entities.CandleLockModel;
import xyz.pixelatedw.mineminenomi.models.entities.SniperTargetModel;
import xyz.pixelatedw.mineminenomi.models.entities.TornadoModel;
import xyz.pixelatedw.mineminenomi.models.entities.WantedPosterPackageModel;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.CannonModel;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.StrikerModel;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.UnicycleModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.BombRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.ChakramRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.EmptyRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.NetRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.SniperTargetRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.SphereRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.SpikeRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.ThrowingWeaponRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.ThrownSpearRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.TornadoRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.VivreCardRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.WantedPosterPackageRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.CannonRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.StrikerRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.UnicycleRenderer;

public class ModEntities {
   public static final RegistryObject<EntityType<CannonEntity>> CANNON = ModRegistry.<EntityType<CannonEntity>>registerEntityType("Cannon", () -> Builder.m_20704_(CannonEntity::new, MobCategory.MISC).m_20699_(1.25F, 1.25F).m_20702_(10).m_20712_("mineminenomi:cannon"));
   public static final RegistryObject<EntityType<StrikerEntity>> STRIKER = ModRegistry.<EntityType<StrikerEntity>>registerEntityType("Striker", () -> Builder.m_20704_(StrikerEntity::new, MobCategory.MISC).m_20699_(1.75F, 0.5625F).m_20702_(10).m_20712_("mineminenomi:striker"));
   public static final RegistryObject<EntityType<UnicycleEntity>> UNICYCLE = ModRegistry.<EntityType<UnicycleEntity>>registerEntityType("Unicycle", () -> Builder.m_20704_(UnicycleEntity::new, MobCategory.MISC).m_20699_(0.75F, 0.75F).m_20702_(10).m_20712_("mineminenomi:unicycle"));
   public static final RegistryObject<EntityType<KemuriBoshiCloudEntity>> KEMURI_BOSHI_CLOUD = ModRegistry.<EntityType<KemuriBoshiCloudEntity>>registerEntityType("Kemuri Boshi Cloud", () -> ModRegistry.createEntityType(KemuriBoshiCloudEntity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("mineminenomi:kemuri_boshi_cloud"));
   public static final RegistryObject<EntityType<MirageTempoCloudEntity>> MIRAGE_TEMPO_CLOUD = ModRegistry.<EntityType<MirageTempoCloudEntity>>registerEntityType("Mirage Cloud", () -> ModRegistry.createEntityType(MirageTempoCloudEntity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("mineminenomi:mirage_tempo_cloud"));
   public static final RegistryObject<EntityType<WeatherCloudEntity>> WEATHER_CLOUD = ModRegistry.<EntityType<WeatherCloudEntity>>registerEntityType("Weather Cloud", () -> ModRegistry.createEntityType(WeatherCloudEntity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("mineminenomi:weather_cloud"));
   public static final RegistryObject<EntityType<MH5CloudEntity>> MH5_CLOUD = ModRegistry.<EntityType<MH5CloudEntity>>registerEntityType("MH5 Cloud", () -> ModRegistry.createEntityType(MH5CloudEntity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("mineminenomi:mh5_cloud"));
   public static final RegistryObject<EntityType<ChloroBallCloudEntity>> CHLORO_BALL_CLOUD = ModRegistry.<EntityType<ChloroBallCloudEntity>>registerEntityType("Chloro Ball Cloud", () -> ModRegistry.createEntityType(ChloroBallCloudEntity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("mineminenomi:chloro_ball_cloud"));
   public static final RegistryObject<EntityType<WantedPosterPackageEntity>> WANTED_POSTER_PACKAGE = ModRegistry.<EntityType<WantedPosterPackageEntity>>registerEntityType("Wanted Poster Package", () -> ModRegistry.createProjectileType(WantedPosterPackageEntity::new).m_20699_(1.4F, 0.25F).m_20712_("mineminenomi:wanted_poster_package"));
   public static final RegistryObject<EntityType<VivreCardEntity>> VIVRE_CARD = ModRegistry.<EntityType<VivreCardEntity>>registerEntityType("Vivre Card", () -> ModRegistry.createProjectileType(VivreCardEntity::new).m_20699_(0.4F, 0.4F).m_20712_("mineminenomi:vivre_card"));
   public static final RegistryObject<EntityType<SniperTargetEntity>> SNIPER_TARGET = ModRegistry.<EntityType<SniperTargetEntity>>registerEntityType("Sniper Target", () -> ModRegistry.createProjectileType(SniperTargetEntity::new).m_20712_("mineminenomi:sniper_target"));
   public static final RegistryObject<EntityType<BombEntity>> BOMB = ModRegistry.<EntityType<BombEntity>>registerEntityType("Bomb", () -> ModRegistry.createEntityType(BombEntity::new, MobCategory.MISC).setUpdateInterval(1).m_20699_(0.8F, 0.8F).m_20712_("mineminenomi:bomb"));
   public static final RegistryObject<EntityType<AkumaNoMiEntity>> DEVIL_FRUIT_ITEM = ModRegistry.<EntityType<AkumaNoMiEntity>>registerEntityType("Devil Fruit", () -> ModRegistry.createEntityType(AkumaNoMiEntity::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20712_("mineminenomi:df_item"));
   public static final RegistryObject<EntityType<SpikeEntity>> SPIKE = ModRegistry.<EntityType<SpikeEntity>>registerEntityType("Spike", () -> ModRegistry.createEntityType(SpikeEntity::new, MobCategory.MISC).m_20699_(0.2F, 0.2F).m_20712_("mineminenomi:spike"));
   public static final RegistryObject<EntityType<TornadoEntity>> TORNADO = ModRegistry.<EntityType<TornadoEntity>>registerEntityType("Tornado", () -> ModRegistry.createProjectileType(TornadoEntity::new).m_20699_(1.5F, 3.0F).m_20712_("mineminenomi:tornado"));
   public static final RegistryObject<EntityType<SphereEntity>> SPHERE = ModRegistry.<EntityType<SphereEntity>>registerEntityType("Sphere", () -> ModRegistry.createEntityType(SphereEntity::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20712_("mineminenomi:sphere"));
   public static final RegistryObject<EntityType<NetEntity>> ROPE_NET = ModRegistry.<EntityType<NetEntity>>registerEntityType("Rope Net", () -> ModRegistry.createProjectileType(NetEntity::new).m_20699_(1.2F, 0.5F).m_20712_("mineminenomi:rope_net"));
   public static final RegistryObject<EntityType<NetEntity>> KAIROSEKI_NET = ModRegistry.<EntityType<NetEntity>>registerEntityType("Kairoseki Net", () -> ModRegistry.createProjectileType(NetEntity::new).m_20699_(1.2F, 0.5F).m_20712_("mineminenomi:kairoseki_net"));
   public static final RegistryObject<EntityType<ThrowingWeaponEntity>> THROWING_WEAPON = ModRegistry.<EntityType<ThrowingWeaponEntity>>registerEntityType("Throwing Weapon", () -> ModRegistry.createProjectileType(ThrowingWeaponEntity::new).m_20699_(1.0F, 1.0F).m_20712_("mineminenomi:throwing_knife"));
   public static final RegistryObject<EntityType<ThrownSpearEntity>> THROWN_SPEAR = ModRegistry.<EntityType<ThrownSpearEntity>>registerEntityType("Thrown Spear", () -> ModRegistry.createProjectileType(ThrownSpearEntity::new).m_20699_(0.5F, 0.5F).m_20712_("mineminenomi:thrown_spear"));
   public static final RegistryObject<EntityType<ChakramEntity>> CHAKRAM = ModRegistry.<EntityType<ChakramEntity>>registerEntityType("Chakram", () -> ModRegistry.createProjectileType(ChakramEntity::new).m_20699_(0.75F, 0.5F).m_20712_("mineminenomi:chakram"));
   public static final RegistryObject<PartEntityType<AbilityPart, LivingEntity>> ABILITY_PART = ModRegistry.registerPartEntityType("ability_part", AbilityPart::new);

   public static void init() {
   }

   public static class Client {
      public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
         event.registerLayerDefinition(StrikerModel.LAYER_LOCATION, StrikerModel::createLayer);
         event.registerLayerDefinition(UnicycleModel.LAYER_LOCATION, UnicycleModel::createLayer);
         event.registerLayerDefinition(CannonModel.LAYER_LOCATION, CannonModel::createLayer);
         event.registerLayerDefinition(WantedPosterPackageModel.LAYER_LOCATION, WantedPosterPackageModel::createLayer);
         event.registerLayerDefinition(SniperTargetModel.LAYER_LOCATION, SniperTargetModel::createLayer);
         event.registerLayerDefinition(TornadoModel.LAYER_LOCATION, TornadoModel::createLayer);
         event.registerLayerDefinition(CandleLockModel.LAYER_LOCATION, CandleLockModel::createLayer);
      }

      public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event, EntityRendererProvider.Context ctx) {
         event.registerEntityRenderer((EntityType)ModEntities.STRIKER.get(), new StrikerRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.UNICYCLE.get(), new UnicycleRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.CANNON.get(), new CannonRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.KEMURI_BOSHI_CLOUD.get(), new EmptyRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.MIRAGE_TEMPO_CLOUD.get(), new EmptyRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.WEATHER_CLOUD.get(), new EmptyRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.MH5_CLOUD.get(), new EmptyRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.CHLORO_BALL_CLOUD.get(), new EmptyRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.WANTED_POSTER_PACKAGE.get(), new WantedPosterPackageRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.VIVRE_CARD.get(), new VivreCardRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.SNIPER_TARGET.get(), new SniperTargetRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.ROPE_NET.get(), new NetRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.KAIROSEKI_NET.get(), new NetRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.THROWN_SPEAR.get(), new ThrownSpearRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.CHAKRAM.get(), new ChakramRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.SPIKE.get(), new SpikeRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.TORNADO.get(), new TornadoRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.SPHERE.get(), new SphereRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.THROWING_WEAPON.get(), new ThrowingWeaponRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.BOMB.get(), new BombRenderer.Factory());
         event.registerEntityRenderer((EntityType)ModEntities.DEVIL_FRUIT_ITEM.get(), ItemEntityRenderer::new);
      }
   }
}
