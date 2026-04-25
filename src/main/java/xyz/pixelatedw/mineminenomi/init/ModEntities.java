package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import xyz.pixelatedw.mineminenomi.entities.projectiles.GomuPistolEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.*;

import java.util.function.Supplier;

public class ModEntities {
    public static final Supplier<EntityType<GomuPistolEntity>> GOMU_PISTOL = ModRegistry.ENTITY_TYPES.register("gomu_pistol",
            () -> EntityType.Builder.<GomuPistolEntity>of(GomuPistolEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("gomu_pistol"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.GomuBazookaEntity>> GOMU_BAZOOKA = ModRegistry.ENTITY_TYPES.register("gomu_bazooka",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.GomuBazookaEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.GomuBazookaEntity::new, MobCategory.MISC)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("gomu_bazooka"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.HiganEntity>> HIGAN = ModRegistry.ENTITY_TYPES.register("higan",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.HiganEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.HiganEntity::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("higan"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.HikenEntity>> HIKEN = ModRegistry.ENTITY_TYPES.register("hiken",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.HikenEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.HikenEntity::new, MobCategory.MISC)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("hiken"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.DaiFunkaEntity>> DAI_FUNKA = ModRegistry.ENTITY_TYPES.register("dai_funka",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.DaiFunkaEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.DaiFunkaEntity::new, MobCategory.MISC)
                    .sized(2.5F, 2.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("dai_funka"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.YasakaniEntity>> YASAKANI = ModRegistry.ENTITY_TYPES.register("yasakani",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.YasakaniEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.YasakaniEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("yasakani"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.MeigoEntity>> MEIGO = ModRegistry.ENTITY_TYPES.register("meigo",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.MeigoEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.MeigoEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("meigo"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.RyuseiKazanEntity>> RYUSEI_KAZAN = ModRegistry.ENTITY_TYPES.register("ryusei_kazan",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.RyuseiKazanEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.RyuseiKazanEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("ryusei_kazan"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.AmaterasuEntity>> AMATERASU = ModRegistry.ENTITY_TYPES.register("amaterasu",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.AmaterasuEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.AmaterasuEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.8F, 0.8F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("amaterasu"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.PartisanEntity>> PARTISAN = ModRegistry.ENTITY_TYPES.register("partisan",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.PartisanEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.PartisanEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("partisan"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.SangoEntity>> SANGO = ModRegistry.ENTITY_TYPES.register("sango",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.SangoEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.SangoEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("sango"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteBlowEntity>> WHITE_BLOW = ModRegistry.ENTITY_TYPES.register("white_blow",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteBlowEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteBlowEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("white_blow"));

    public static final Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity>> SNIPER_PELLET = ModRegistry.ENTITY_TYPES.register("sniper_pellet",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("sniper_pellet"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.DesertSpadaEntity>> DESERT_SPADA = ModRegistry.ENTITY_TYPES.register("desert_spada",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.DesertSpadaEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.DesertSpadaEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.7F, 0.7F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("desert_spada"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.SphereEntity>> SPHERE = ModRegistry.ENTITY_TYPES.register("sphere",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.SphereEntity>of(xyz.pixelatedw.mineminenomi.entities.SphereEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(64)
                    .updateInterval(20)
                    .build("sphere"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.GekishinEntity>> GEKISHIN = ModRegistry.ENTITY_TYPES.register("gekishin",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.GekishinEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.GekishinEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(3.0F, 3.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("gekishin"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.PadHoEntity>> PAD_HO = ModRegistry.ENTITY_TYPES.register("pad_ho",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.PadHoEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.PadHoEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("pad_ho"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.OverheatEntity>> OVERHEAT = ModRegistry.ENTITY_TYPES.register("overheat",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.OverheatEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.OverheatEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("overheat"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.MeroMeroMellowEntity>> MERO_MERO_MELLOW = ModRegistry.ENTITY_TYPES.register("mero_mero_mellow",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.MeroMeroMellowEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.MeroMeroMellowEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("mero_mero_mellow"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.NoseFancyCannonEntity>> NOSE_FANCY_CANNON = ModRegistry.ENTITY_TYPES.register("nose_fancy_cannon",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.NoseFancyCannonEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.NoseFancyCannonEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("nose_fancy_cannon"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.BrickBatEntity>> BRICK_BAT = ModRegistry.ENTITY_TYPES.register("brick_bat",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.BrickBatEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.BrickBatEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("brick_bat"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.BaraBaraHoEntity>> BARA_BARA_HO = ModRegistry.ENTITY_TYPES.register("bara_bara_ho",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.BaraBaraHoEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.BaraBaraHoEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("bara_bara_ho"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.NegativeHollowEntity>> NEGATIVE_HOLLOW = ModRegistry.ENTITY_TYPES.register("negative_hollow",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.NegativeHollowEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.NegativeHollowEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("negative_hollow"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.GomuGomuNoBazookaEntity>> GOMU_GOMU_NO_BAZOOKA = ModRegistry.ENTITY_TYPES.register("gomu_gomu_no_bazooka",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.GomuGomuNoBazookaEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.GomuGomuNoBazookaEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.8F, 0.8F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("gomu_gomu_no_bazooka"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.NoroNoroBeamEntity>> NORO_NORO_BEAM = ModRegistry.ENTITY_TYPES.register("noro_noro_beam",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.NoroNoroBeamEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.NoroNoroBeamEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("noro_noro_beam"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.PistolKissEntity>> PISTOL_KISS = ModRegistry.ENTITY_TYPES.register("pistol_kiss",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.PistolKissEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.PistolKissEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("pistol_kiss"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteOutEntity>> WHITE_OUT = ModRegistry.ENTITY_TYPES.register("white_out",
            () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteOutEntity>of(xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteOutEntity::new, net.minecraft.world.entity.MobCategory.MISC)
                    .sized(1.2F, 1.2F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("white_out"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity>> THROWN_SPEAR = ModRegistry.ENTITY_TYPES.register("thrown_spear",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity>of(xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("thrown_spear"));

    public static final Supplier<EntityType<YakkodoriProjectile>> YAKKODORI = ModRegistry.ENTITY_TYPES.register("yakkodori",
            () -> EntityType.Builder.<YakkodoriProjectile>of(YakkodoriProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("yakkodori"));

    public static final Supplier<EntityType<SanbyakurokujuPoundHoProjectile>> SANBYAKUROKUJU_POUND_HO = ModRegistry.ENTITY_TYPES.register("sanbyakurokuju_pound_ho",
            () -> EntityType.Builder.<SanbyakurokujuPoundHoProjectile>of(SanbyakurokujuPoundHoProjectile::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("sanbyakurokuju_pound_ho"));

    public static final Supplier<EntityType<SanjurokuPoundHoProjectile>> SANJUROKU_POUND_HO_PROJECTILE = ModRegistry.ENTITY_TYPES.register("sanjuroku_pound_ho_projectile",
            () -> EntityType.Builder.<SanjurokuPoundHoProjectile>of(SanjurokuPoundHoProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("sanjuroku_pound_ho_projectile"));

    public static final Supplier<EntityType<TatsuMakiProjectile>> TATSU_MAKI_PROJECTILE = ModRegistry.ENTITY_TYPES.register("tatsu_maki_projectile",
            () -> EntityType.Builder.<TatsuMakiProjectile>of(TatsuMakiProjectile::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("tatsu_maki_projectile"));

    public static final Supplier<EntityType<NanajuniPoundHoProjectile>> NANAJUNI_POUND_HO_PROJECTILE = ModRegistry.ENTITY_TYPES.register("nanajuni_pound_ho_projectile",
            () -> EntityType.Builder.<NanajuniPoundHoProjectile>of(NanajuniPoundHoProjectile::new, MobCategory.MISC)
                    .sized(0.7F, 0.7F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("nanajuni_pound_ho_projectile"));

    public static final Supplier<EntityType<NanahyakunijuPoundHoProjectile>> NANAHYAKUNIJU_POUND_HO_PROJECTILE = ModRegistry.ENTITY_TYPES.register("nanahyakuniju_pound_ho_projectile",
            () -> EntityType.Builder.<NanahyakunijuPoundHoProjectile>of(NanahyakunijuPoundHoProjectile::new, MobCategory.MISC)
                    .sized(1.2F, 1.2F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("nanahyakuniju_pound_ho_projectile"));

    public static final Supplier<EntityType<HyakuhachiPoundHoProjectile>> HYAKUHACHI_POUND_HO_PROJECTILE = ModRegistry.ENTITY_TYPES.register("hyakuhachi_pound_ho_projectile",
            () -> EntityType.Builder.<HyakuhachiPoundHoProjectile>of(HyakuhachiPoundHoProjectile::new, MobCategory.MISC)
                    .sized(0.8F, 0.8F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("hyakuhachi_pound_ho_projectile"));

    public static final Supplier<EntityType<SenhachijuPoundHoProjectile>> SENHACHIJU_POUND_HO_PROJECTILE = ModRegistry.ENTITY_TYPES.register("senhachiju_pound_ho_projectile",
            () -> EntityType.Builder.<SenhachijuPoundHoProjectile>of(SenhachijuPoundHoProjectile::new, MobCategory.MISC)
                    .sized(1.4F, 1.4F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("senhachiju_pound_ho_projectile"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile>> UCHIMIZU = ModRegistry.ENTITY_TYPES.register("uchimizu",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("uchimizu"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.YarinamiProjectile>> YARINAMI = ModRegistry.ENTITY_TYPES.register("yarinami",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.YarinamiProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.YarinamiProjectile::new, MobCategory.MISC)
                    .sized(2.0F, 2.0F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("yarinami"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuTaihoProjectile>> MIZU_TAIHO = ModRegistry.ENTITY_TYPES.register("mizu_taiho",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuTaihoProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuTaihoProjectile::new, MobCategory.MISC)
                    .sized(2.5F, 2.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("mizu_taiho"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuShuryudanProjectile>> MIZU_SHURYUDAN = ModRegistry.ENTITY_TYPES.register("mizu_shuryudan",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuShuryudanProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuShuryudanProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("mizu_shuryudan"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.StrongRightProjectile>> STRONG_RIGHT = ModRegistry.ENTITY_TYPES.register("strong_right", () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.StrongRightProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.StrongRightProjectile::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(10).updateInterval(20).build("strong_right"));
    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.RadicalBeamProjectile>> RADICAL_BEAM = ModRegistry.ENTITY_TYPES.register("radical_beam", () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.RadicalBeamProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.RadicalBeamProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(20).build("radical_beam"));
    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.FreshFireProjectile>> FRESH_FIRE = ModRegistry.ENTITY_TYPES.register("fresh_fire", () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.FreshFireProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.FreshFireProjectile::new, MobCategory.MISC).sized(3.0F, 3.0F).clientTrackingRange(10).updateInterval(20).build("fresh_fire"));
    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.CoupDeVentProjectile>> COUP_DE_VENT = ModRegistry.ENTITY_TYPES.register("coup_de_vent", () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.CoupDeVentProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.CoupDeVentProjectile::new, MobCategory.MISC).sized(5.5F, 5.5F).clientTrackingRange(10).updateInterval(20).build("coup_de_vent"));
    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalLunaProjectile>> ELECTRICAL_LUNA = ModRegistry.ENTITY_TYPES.register("electrical_luna", () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalLunaProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalLunaProjectile::new, MobCategory.MISC).sized(1.5F, 1.5F).clientTrackingRange(10).updateInterval(20).build("electrical_luna"));
    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalShowerProjectile>> ELECTRICAL_SHOWER = ModRegistry.ENTITY_TYPES.register("electrical_shower", () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalShowerProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalShowerProjectile::new, MobCategory.MISC).sized(1.5F, 1.5F).clientTrackingRange(10).updateInterval(20).build("electrical_shower"));
    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectroVisualProjectile>> ELECTRO_VISUAL = ModRegistry.ENTITY_TYPES.register("electro_visual", () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectroVisualProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectroVisualProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(20).build("electro_visual"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.PackOfSharksProjectile>> PACK_OF_SHARKS = ModRegistry.ENTITY_TYPES.register("pack_of_sharks",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.PackOfSharksProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.PackOfSharksProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build("pack_of_sharks"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.BarizodonProjectile>> BARIZODON = ModRegistry.ENTITY_TYPES.register("barizodon",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.BarizodonProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.BarizodonProjectile::new, MobCategory.MISC).sized(1.5F, 1.5F).clientTrackingRange(10).updateInterval(20).build("barizodon"));

    public static final Supplier<EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.TempuraudonProjectile>> TEMPURAUDON = ModRegistry.ENTITY_TYPES.register("tempuraudon",
            () -> EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.TempuraudonProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.TempuraudonProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(20).build("tempuraudon"));

    public static final java.util.function.Supplier<net.minecraft.world.entity.EntityType<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa.RelaxHourProjectile>> RELAX_HOUR_PROJECTILE = ModRegistry.ENTITY_TYPES.register("relax_hour_projectile", () -> net.minecraft.world.entity.EntityType.Builder.<xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa.RelaxHourProjectile>of(xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa.RelaxHourProjectile::new, net.minecraft.world.entity.MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("relax_hour_projectile"));

    public static void init() {}
}
