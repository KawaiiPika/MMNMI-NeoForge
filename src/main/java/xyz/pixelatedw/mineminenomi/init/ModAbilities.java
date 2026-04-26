package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.neoforged.neoforge.registries.RegisterEvent;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.*;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.*;

import java.util.function.Supplier;

public class ModAbilities {
    public static final ResourceKey<Registry<Ability>> REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ModMain.PROJECT_ID, "abilities"));
    public static final DeferredRegister<Ability> ABILITIES = DeferredRegister.create(REGISTRY_KEY, ModMain.PROJECT_ID);
    public static Registry<Ability> REGISTRY;

    public static final Supplier<Ability> PUNCH = ABILITIES.register("punch", xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility::new);
    public static final Supplier<Ability> SHISHI_SONSON = ABILITIES.register("shishi_sonson", xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility::new);
    public static final Supplier<Ability> YAKKODORI = ABILITIES.register("yakkodori", xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility::new);
    public static final Supplier<Ability> SANBYAKUROKUJU_POUND_HO = ABILITIES.register("sanbyakurokuju_pound_ho", xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility::new);
    public static final Supplier<Ability> SANJUROKU_POUND_HO = ABILITIES.register("sanjuroku_pound_ho", xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanjurokuPoundHoAbility::new);
    public static final Supplier<Ability> HIRYU_KAEN = ABILITIES.register("hiryu_kaen", xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility::new);
    public static final Supplier<Ability> O_TATSUMAKI = ABILITIES.register("o_tatsumaki", xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility::new);
    public static final Supplier<Ability> TATSU_MAKI = ABILITIES.register("tatsu_maki", xyz.pixelatedw.mineminenomi.abilities.santoryu.TatsuMakiAbility::new);
    public static final Supplier<Ability> NANAJUNI_POUND_HO = ABILITIES.register("nanajuni_pound_ho", xyz.pixelatedw.mineminenomi.abilities.nitoryu.NanajuniPoundHoAbility::new);
    public static final Supplier<Ability> NANAHYAKUNIJU_POUND_HO = ABILITIES.register("nanahyakuniju_pound_ho", xyz.pixelatedw.mineminenomi.abilities.nitoryu.NanahyakunijuPoundHoAbility::new);
    public static final Supplier<Ability> HYAKUHACHI_POUND_HO = ABILITIES.register("hyakuhachi_pound_ho", xyz.pixelatedw.mineminenomi.abilities.santoryu.HyakuhachiPoundHoAbility::new);
    public static final Supplier<Ability> SENHACHIJU_POUND_HO = ABILITIES.register("senhachiju_pound_ho", xyz.pixelatedw.mineminenomi.abilities.santoryu.SenhachijuPoundHoAbility::new);

    // Black Leg
    public static final Supplier<Ability> DIABLE_JAMBE = ABILITIES.register("diable_jambe", xyz.pixelatedw.mineminenomi.abilities.blackleg.DiableJambeAbility::new);
    public static final Supplier<Ability> SKYWALK = ABILITIES.register("skywalk", xyz.pixelatedw.mineminenomi.abilities.blackleg.SkywalkAbility::new);
    public static final Supplier<Ability> ANTI_MANNER_KICK_COURSE = ABILITIES.register("anti_manner_kick_course", xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility::new);
    public static final Supplier<Ability> EXTRA_HACHIS = ABILITIES.register("extra_hachis", xyz.pixelatedw.mineminenomi.abilities.blackleg.ExtraHachisAbility::new);
    public static final Supplier<Ability> PARTY_TABLE_KICK_COURSE = ABILITIES.register("party_table_kick_course", xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility::new);
    public static final Supplier<Ability> CONCASSE = ABILITIES.register("concasse", xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility::new);
    public static final Supplier<Ability> BIEN_CUIT_GRILL_SHOT = ABILITIES.register("bien_cuit_grill_shot", xyz.pixelatedw.mineminenomi.abilities.blackleg.BienCuitGrillShotAbility::new);
    public static final Supplier<Ability> BLACK_LEG_DAMAGE_PERK = ABILITIES.register("black_leg_damage", xyz.pixelatedw.mineminenomi.abilities.blackleg.BlackLegDamagePerkAbility::new);
    public static final Supplier<Ability> BLACK_LEG_SPEED_PERK = ABILITIES.register("black_leg_speed", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Black Leg Speed", "Increased speed while using Black Leg"));
    public static final Supplier<Ability> HITODAIBUTSU = ABILITIES.register("hito_hito_no_mi_model_daibutsu", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Hito Hito no Mi: Model Daibutsu", "Impact Blast ability"));
    public static final java.util.function.Supplier<Ability> SPRING_DEATH_KNOCK = ABILITIES.register("spring_death_knock", xyz.pixelatedw.mineminenomi.abilities.bane.SpringDeathKnockAbility::new);
    public static final java.util.function.Supplier<Ability> SPRING_HOPPER = ABILITIES.register("spring_hopper", xyz.pixelatedw.mineminenomi.abilities.bane.SpringHopperAbility::new);
    public static final java.util.function.Supplier<Ability> SPRING_SNIPE = ABILITIES.register("spring_snipe", xyz.pixelatedw.mineminenomi.abilities.bane.SpringSnipeAbility::new);
    public static final java.util.function.Supplier<Ability> DEKA_DEKA = ABILITIES.register("deka_deka", xyz.pixelatedw.mineminenomi.abilities.deka.DekaDekaAbility::new);
    public static final java.util.function.Supplier<Ability> DEKA_TRAMPLE = ABILITIES.register("deka_trample", xyz.pixelatedw.mineminenomi.abilities.deka.DekaTrampleAbility::new);
    public static final java.util.function.Supplier<Ability> CHIYU_HORMONE = ABILITIES.register("chiyu_hormone", xyz.pixelatedw.mineminenomi.abilities.horu.ChiyuHormoneAbility::new);
    public static final java.util.function.Supplier<Ability> DEATH_WINK = ABILITIES.register("death_wink", xyz.pixelatedw.mineminenomi.abilities.horu.DeathWinkAbility::new);
    public static final java.util.function.Supplier<Ability> GANMEN_SEICHO_HORMONE = ABILITIES.register("ganmen_seicho_hormone", xyz.pixelatedw.mineminenomi.abilities.horu.GanmenSeichoHormoneAbility::new);
    public static final java.util.function.Supplier<Ability> ONNA_HORMONE = ABILITIES.register("onna_hormone", xyz.pixelatedw.mineminenomi.abilities.horu.OnnaHormoneAbility::new);
    public static final java.util.function.Supplier<Ability> TENSION_HORMONE = ABILITIES.register("tension_hormone", xyz.pixelatedw.mineminenomi.abilities.horu.TensionHormoneAbility::new);
    public static final java.util.function.Supplier<Ability> ATTRACT = ABILITIES.register("attract", xyz.pixelatedw.mineminenomi.abilities.jiki.AttractAbility::new);
    public static final java.util.function.Supplier<Ability> DAMNED_PUNK = ABILITIES.register("damned_punk", xyz.pixelatedw.mineminenomi.abilities.jiki.DamnedPunkAbility::new);
    public static final java.util.function.Supplier<Ability> GENOCIDE_RAID = ABILITIES.register("genocide_raid", xyz.pixelatedw.mineminenomi.abilities.jiki.GenocideRaidAbility::new);
    public static final java.util.function.Supplier<Ability> MAGNETIC_ITEMS = ABILITIES.register("magnetic_items", xyz.pixelatedw.mineminenomi.abilities.jiki.MagneticItemsAbility::new);
    public static final java.util.function.Supplier<Ability> PUNK_CORNA_DIO = ABILITIES.register("punk_corna_dio", xyz.pixelatedw.mineminenomi.abilities.jiki.PunkCornaDioAbility::new);
    public static final java.util.function.Supplier<Ability> PUNK_CROSS = ABILITIES.register("punk_cross", xyz.pixelatedw.mineminenomi.abilities.jiki.PunkCrossAbility::new);
    public static final java.util.function.Supplier<Ability> PUNK_GIBSON = ABILITIES.register("punk_gibson", xyz.pixelatedw.mineminenomi.abilities.jiki.PunkGibsonAbility::new);
    public static final java.util.function.Supplier<Ability> PUNK_PISTOLS = ABILITIES.register("punk_pistols", xyz.pixelatedw.mineminenomi.abilities.jiki.PunkPistolsAbility::new);
    public static final java.util.function.Supplier<Ability> REPEL = ABILITIES.register("repel", xyz.pixelatedw.mineminenomi.abilities.jiki.RepelAbility::new);
    public static final java.util.function.Supplier<Ability> EVAPORATE = ABILITIES.register("evaporate", xyz.pixelatedw.mineminenomi.abilities.kachi.EvaporateAbility::new);
    public static final java.util.function.Supplier<Ability> HOT_BOILING_SPECIAL = ABILITIES.register("hot_boiling_special", xyz.pixelatedw.mineminenomi.abilities.kachi.HotBoilingSpecialAbility::new);
    public static final java.util.function.Supplier<Ability> VULCANIZATION = ABILITIES.register("vulcanization", xyz.pixelatedw.mineminenomi.abilities.kachi.VulcanizationAbility::new);
    public static final java.util.function.Supplier<Ability> INGA_ZARASHI = ABILITIES.register("inga_zarashi", xyz.pixelatedw.mineminenomi.abilities.karu.IngaZarashiAbility::new);
    public static final java.util.function.Supplier<Ability> KARMA = ABILITIES.register("karma", xyz.pixelatedw.mineminenomi.abilities.karu.KarmaAbility::new);
    public static final java.util.function.Supplier<Ability> SHOUREI = ABILITIES.register("shourei", xyz.pixelatedw.mineminenomi.abilities.kobu.ShoureiAbility::new);
    public static final java.util.function.Supplier<Ability> MINI_MINI = ABILITIES.register("mini_mini", xyz.pixelatedw.mineminenomi.abilities.mini.MiniMiniAbility::new);
    public static final java.util.function.Supplier<Ability> PAPER_FLOAT = ABILITIES.register("paper_float", xyz.pixelatedw.mineminenomi.abilities.mini.PaperFloatAbility::new);
    public static final java.util.function.Supplier<Ability> SILENT = ABILITIES.register("silent", xyz.pixelatedw.mineminenomi.abilities.nagi.SilentAbility::new);
    public static final java.util.function.Supplier<Ability> RUST_BREAK = ABILITIES.register("rust_break", xyz.pixelatedw.mineminenomi.abilities.sabi.RustBreakAbility::new);
    public static final java.util.function.Supplier<Ability> RUST_SKIN = ABILITIES.register("rust_skin", xyz.pixelatedw.mineminenomi.abilities.sabi.RustSkinAbility::new);
    public static final java.util.function.Supplier<Ability> RUST_TOUCH = ABILITIES.register("rust_touch", xyz.pixelatedw.mineminenomi.abilities.sabi.RustTouchAbility::new);

    public static final java.util.function.Supplier<Ability> BUSOSHOKU_HAKI = ABILITIES.register("busoshoku_haki", xyz.pixelatedw.mineminenomi.api.abilities.haki.BusoshokuHakiAbility::new);
    public static final java.util.function.Supplier<Ability> KENBUNSHOKU_HAKI = ABILITIES.register("kenbunshoku_haki", xyz.pixelatedw.mineminenomi.api.abilities.haki.KenbunshokuHakiAbility::new);
    public static final java.util.function.Supplier<Ability> GEPPO = ABILITIES.register("geppo", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.GeppoAbility::new);
    public static final java.util.function.Supplier<Ability> SORU = ABILITIES.register("soru", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.SoruAbility::new);
    public static final java.util.function.Supplier<Ability> GOMU_GOMU_NO_PISTOL = ABILITIES.register("gomu_gomu_no_pistol", xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoPistolAbility::new);
    public static final java.util.function.Supplier<Ability> GOMU_GOMU_NO_GATLING = ABILITIES.register("gomu_gomu_no_gatling", xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoGatlingAbility::new);
    public static final java.util.function.Supplier<Ability> GOMU_GOMU_NO_BAZOOKA = ABILITIES.register("gomu_gomu_no_bazooka", xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoBazookaAbility::new);
    public static final java.util.function.Supplier<Ability> GOMU_GOMU_NO_ROCKET = ABILITIES.register("gomu_gomu_no_rocket", xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoRocketAbility::new);
    public static final java.util.function.Supplier<Ability> HIGAN = ABILITIES.register("higan", xyz.pixelatedw.mineminenomi.abilities.mera.HiganAbility::new);
    public static final java.util.function.Supplier<Ability> HIKEN = ABILITIES.register("hiken", xyz.pixelatedw.mineminenomi.abilities.mera.HikenAbility::new);
    public static final java.util.function.Supplier<Ability> DAI_ENKA_ENTEI = ABILITIES.register("dai_enka_entei", xyz.pixelatedw.mineminenomi.abilities.mera.DaiEnkaEnteiAbility::new);
    public static final java.util.function.Supplier<Ability> DAI_FUNKA = ABILITIES.register("dai_funka", xyz.pixelatedw.mineminenomi.abilities.magu.DaiFunkaAbility::new);
    public static final java.util.function.Supplier<Ability> MAGMA_COATING = ABILITIES.register("magma_coating", xyz.pixelatedw.mineminenomi.abilities.magu.MagmaCoatingAbility::new);
    public static final java.util.function.Supplier<Ability> LAVA_FLOW = ABILITIES.register("lava_flow", xyz.pixelatedw.mineminenomi.abilities.magu.LavaFlowAbility::new);
    public static final java.util.function.Supplier<Ability> MEIGO = ABILITIES.register("meigo", xyz.pixelatedw.mineminenomi.abilities.magu.MeigoAbility::new);
    public static final java.util.function.Supplier<Ability> RYUSEI_KAZAN = ABILITIES.register("ryusei_kazan", xyz.pixelatedw.mineminenomi.abilities.magu.RyuseiKazanAbility::new);
    public static final java.util.function.Supplier<Ability> ICE_AGE = ABILITIES.register("ice_age", xyz.pixelatedw.mineminenomi.abilities.hie.IceAgeAbility::new);

    // Sniper
    public static final Supplier<Ability> KAEN_BOSHI = ABILITIES.register("kaen_boshi", xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility::new);
    public static final Supplier<Ability> KEMURI_BOSHI = ABILITIES.register("kemuri_boshi", xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility::new);
    public static final Supplier<Ability> NEMURI_BOSHI = ABILITIES.register("nemuri_boshi", xyz.pixelatedw.mineminenomi.abilities.sniper.NemuriBoshiAbility::new);
    public static final Supplier<Ability> TETSU_BOSHI = ABILITIES.register("tetsu_boshi", xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility::new);
    public static final Supplier<Ability> TOKUYO_ABURA_BOSHI = ABILITIES.register("tokuyo_abura_boshi", xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility::new);
    public static final Supplier<Ability> HI_NO_TORI_BOSHI = ABILITIES.register("hi_no_tori_boshi", xyz.pixelatedw.mineminenomi.abilities.sniper.HiNoToriBoshiAbility::new);
    public static final Supplier<Ability> RENPATSU_NAMARI_BOSHI = ABILITIES.register("renpatsu_namari_boshi", xyz.pixelatedw.mineminenomi.abilities.sniper.RenpatsuNamariBoshiAbility::new);
    public static final Supplier<Ability> SNIPER_ACCURACY_PERK = ABILITIES.register("sniper_accuracy", xyz.pixelatedw.mineminenomi.abilities.sniper.SniperAccuracyPerkAbility::new);
    public static final Supplier<Ability> ZOOM_PERK = ABILITIES.register("zoom", xyz.pixelatedw.mineminenomi.abilities.sniper.ZoomAbility::new);

    // Brawler
    public static final Supplier<Ability> CHARGED_PUNCH = ABILITIES.register("charged_punch", xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility::new);
    public static final Supplier<Ability> PUNCH_RUSH = ABILITIES.register("punch_rush", xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility::new);
    public static final Supplier<Ability> TACKLE = ABILITIES.register("tackle", xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility::new);
    public static final Supplier<Ability> HAKAI_HO = ABILITIES.register("hakai_ho", xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility::new);
    public static final Supplier<Ability> JISHIN_HO = ABILITIES.register("jishin_ho", xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility::new);
    public static final Supplier<Ability> GENKOTSU_METEOR = ABILITIES.register("genkotsu_meteor", xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility::new);
    public static final Supplier<Ability> KING_PUNCH = ABILITIES.register("king_punch", xyz.pixelatedw.mineminenomi.abilities.brawler.KingPunchAbility::new);
    public static final Supplier<Ability> BRAWLER_DAMAGE_PERK = ABILITIES.register("brawler_damage", xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerDamagePerkAbility::new);

    // Doctor
    public static final Supplier<Ability> FIRST_AID = ABILITIES.register("first_aid", xyz.pixelatedw.mineminenomi.abilities.doctor.FirstAidAbility::new);
    public static final Supplier<Ability> ANTIDOTE_SHOT = ABILITIES.register("antidote_shot", xyz.pixelatedw.mineminenomi.abilities.doctor.AntidoteShotAbility::new);
    public static final Supplier<Ability> DOPING = ABILITIES.register("doping", xyz.pixelatedw.mineminenomi.abilities.doctor.DopingAbility::new);
    public static final Supplier<Ability> VIRUS_ZONE = ABILITIES.register("virus_zone", xyz.pixelatedw.mineminenomi.abilities.doctor.VirusZoneAbility::new);
    public static final Supplier<Ability> MEDIC_BAG_EXPLOSION = ABILITIES.register("medic_bag_explosion", xyz.pixelatedw.mineminenomi.abilities.doctor.MedicBagExplosionAbility::new);
    public static final Supplier<Ability> MEDICAL_EXPERTISE_PERK = ABILITIES.register("medical_expertise", xyz.pixelatedw.mineminenomi.abilities.doctor.MedicalExpertisePerkAbility::new);

    // Art of Weather
    public static final Supplier<Ability> HEAT_BALL = ABILITIES.register("heat_ball", xyz.pixelatedw.mineminenomi.abilities.artofweather.HeatBallAbility::new);
    public static final Supplier<Ability> COOL_BALL = ABILITIES.register("cool_ball", xyz.pixelatedw.mineminenomi.abilities.artofweather.CoolBallAbility::new);
    public static final Supplier<Ability> THUNDER_BALL = ABILITIES.register("thunder_ball", xyz.pixelatedw.mineminenomi.abilities.artofweather.ThunderBallAbility::new);
    public static final Supplier<Ability> THUNDERBOLT_TEMPO = ABILITIES.register("thunderbolt_tempo", xyz.pixelatedw.mineminenomi.abilities.artofweather.ThunderboltTempoAbility::new);
    public static final Supplier<Ability> MIRAGE_TEMPO = ABILITIES.register("mirage_tempo", xyz.pixelatedw.mineminenomi.abilities.artofweather.MirageTempoAbility::new);
    public static final Supplier<Ability> CYCLONE_TEMPO = ABILITIES.register("cyclone_tempo", xyz.pixelatedw.mineminenomi.abilities.artofweather.CycloneTempoAbility::new);
    public static final Supplier<Ability> THUNDER_LANCE_TEMPO = ABILITIES.register("thunder_lance_tempo", xyz.pixelatedw.mineminenomi.abilities.artofweather.ThunderLanceTempoAbility::new);
    public static final Supplier<Ability> WEATHER_KNOWLEDGE_PERK = ABILITIES.register("weather_knowledge", xyz.pixelatedw.mineminenomi.abilities.artofweather.WeatherKnowledgePerkAbility::new);
    public static final java.util.function.Supplier<Ability> ICE_SABER = ABILITIES.register("ice_saber", xyz.pixelatedw.mineminenomi.abilities.hie.IceSaberAbility::new);
    public static final java.util.function.Supplier<Ability> ICE_BLOCK_PARTISAN = ABILITIES.register("ice_block_partisan", xyz.pixelatedw.mineminenomi.abilities.hie.IceBlockPartisanAbility::new);
    public static final java.util.function.Supplier<Ability> EL_THOR = ABILITIES.register("el_thor", xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility::new);
    public static final java.util.function.Supplier<Ability> SANGO = ABILITIES.register("sango", xyz.pixelatedw.mineminenomi.abilities.goro.SangoAbility::new);
    public static final java.util.function.Supplier<Ability> BLACK_HOLE = ABILITIES.register("black_hole", xyz.pixelatedw.mineminenomi.abilities.yami.BlackHoleAbility::new);
    public static final java.util.function.Supplier<Ability> KUROUZU = ABILITIES.register("kurouzu", xyz.pixelatedw.mineminenomi.abilities.yami.KurouzuAbility::new);
    public static final java.util.function.Supplier<Ability> WHITE_BLOW = ABILITIES.register("white_blow", xyz.pixelatedw.mineminenomi.abilities.moku.WhiteBlowAbility::new);
    public static final java.util.function.Supplier<Ability> WHITE_OUT = ABILITIES.register("white_out", xyz.pixelatedw.mineminenomi.abilities.moku.WhiteOutAbility::new);
    public static final java.util.function.Supplier<Ability> DESERT_SPADA = ABILITIES.register("desert_spada", xyz.pixelatedw.mineminenomi.abilities.suna.DesertSpadaAbility::new);
    public static final java.util.function.Supplier<Ability> SABLES = ABILITIES.register("sables", xyz.pixelatedw.mineminenomi.abilities.suna.SablesAbility::new);
    
    // Paramecia - Bara Bara
    public static final java.util.function.Supplier<Ability> BARA_BARA_HO = ABILITIES.register("bara_bara_ho", xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraHoAbility::new);
    public static final java.util.function.Supplier<Ability> BARA_BARA_FESTIVAL = ABILITIES.register("bara_bara_festival", xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraFestivalAbility::new);
    
    // Paramecia - Bomu Bomu
    public static final java.util.function.Supplier<Ability> BAKUDAN = ABILITIES.register("bakudan", xyz.pixelatedw.mineminenomi.abilities.bomu.BakudanAbility::new);
    public static final java.util.function.Supplier<Ability> NOSE_FANCY_CANNON = ABILITIES.register("nose_fancy_cannon", xyz.pixelatedw.mineminenomi.abilities.bomu.NoseFancyCannonAbility::new);

    // Paramecia - Suke Suke
    public static final java.util.function.Supplier<Ability> SUKE_PUNCH = ABILITIES.register("suke_punch", xyz.pixelatedw.mineminenomi.abilities.suke.SukePunchAbility::new);
    public static final java.util.function.Supplier<Ability> SKATTING = ABILITIES.register("skatting", xyz.pixelatedw.mineminenomi.abilities.suke.SkattingAbility::new);

    // Paramecia - Ope
    public static final java.util.function.Supplier<Ability> ROOM = ABILITIES.register("room", xyz.pixelatedw.mineminenomi.abilities.ope.RoomAbility::new);
    public static final java.util.function.Supplier<Ability> COUNTER_SHOCK = ABILITIES.register("counter_shock", xyz.pixelatedw.mineminenomi.abilities.ope.CounterShockAbility::new);
    public static final java.util.function.Supplier<Ability> SHAMBLES = ABILITIES.register("shambles", xyz.pixelatedw.mineminenomi.abilities.ope.ShamblesAbility::new);
    public static final java.util.function.Supplier<Ability> MES = ABILITIES.register("mes", xyz.pixelatedw.mineminenomi.abilities.ope.MesAbility::new);

    public static final java.util.function.Supplier<Ability> AMATERASU = ABILITIES.register("amaterasu", xyz.pixelatedw.mineminenomi.abilities.pika.AmaterasuAbility::new);
    public static final java.util.function.Supplier<Ability> YASAKANI_NO_MAGATAMA = ABILITIES.register("yasakani_no_magatama", xyz.pixelatedw.mineminenomi.abilities.pika.YasakaniNoMagatamaAbility::new);
    public static final java.util.function.Supplier<Ability> HAOSHOKU_HAKI = ABILITIES.register("haoshoku_haki", xyz.pixelatedw.mineminenomi.api.abilities.haki.HaoshokuHakiAbility::new);
    public static final java.util.function.Supplier<Ability> RANKYAKU = ABILITIES.register("rankyaku", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.RankyakuAbility::new);
    public static final java.util.function.Supplier<Ability> SHIGAN = ABILITIES.register("shigan", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.ShiganAbility::new);

    // Rokushiki — remaining
    public static final java.util.function.Supplier<Ability> TEKKAI = ABILITIES.register("tekkai", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.TekkaiAbility::new);
    public static final java.util.function.Supplier<Ability> KAMIE = ABILITIES.register("kamie", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.KamieAbility::new);
    public static final java.util.function.Supplier<Ability> ROKUOGAN = ABILITIES.register("rokuogan", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.RokuoganAbility::new);

    // Basic passives / misc
    public static final java.util.function.Supplier<Ability> EMPTY_HANDS = ABILITIES.register("empty_hands", xyz.pixelatedw.mineminenomi.api.abilities.basic.EmptyHandsAbility::new);
    public static final java.util.function.Supplier<Ability> KNOCKDOWN = ABILITIES.register("knockdown", xyz.pixelatedw.mineminenomi.api.abilities.basic.KnockdownAbility::new);
    public static final java.util.function.Supplier<Ability> COMMAND = ABILITIES.register("command", xyz.pixelatedw.mineminenomi.api.abilities.basic.CommandAbility::new);

    // Gomu Gomu no Mi — additional
    public static final java.util.function.Supplier<Ability> GEAR_SECOND = ABILITIES.register("gear_second", xyz.pixelatedw.mineminenomi.abilities.gomu.GearSecondAbility::new);
    public static final java.util.function.Supplier<Ability> GEAR_THIRD = ABILITIES.register("gear_third", xyz.pixelatedw.mineminenomi.abilities.gomu.GearThirdAbility::new);
    public static final java.util.function.Supplier<Ability> GOMU_GOMU_NO_DAWN_WHIP = ABILITIES.register("gomu_gomu_no_dawn_whip", xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoDawnWhipAbility::new);

    // Mera Mera no Mi — additional
    public static final java.util.function.Supplier<Ability> HIBASHIRA = ABILITIES.register("hibashira", xyz.pixelatedw.mineminenomi.abilities.mera.HibashiraAbility::new);
    public static final java.util.function.Supplier<Ability> JUJIKA = ABILITIES.register("jujika", xyz.pixelatedw.mineminenomi.abilities.mera.JujikaAbility::new);
    public static final java.util.function.Supplier<Ability> HEAT_DASH = ABILITIES.register("heat_dash", xyz.pixelatedw.mineminenomi.abilities.mera.HeatDashAbility::new);
    public static final java.util.function.Supplier<Ability> HIDARUMA = ABILITIES.register("hidaruma", xyz.pixelatedw.mineminenomi.abilities.mera.HidarumaAbility::new);
    public static final java.util.function.Supplier<Ability> KYOKAEN = ABILITIES.register("kyokaen", xyz.pixelatedw.mineminenomi.abilities.mera.KyokaenAbility::new);

    // Hie Hie no Mi — additional
    public static final java.util.function.Supplier<Ability> ICE_TIME = ABILITIES.register("ice_time", xyz.pixelatedw.mineminenomi.abilities.hie.IceTimeAbility::new);
    public static final java.util.function.Supplier<Ability> ICE_BALL = ABILITIES.register("ice_ball", xyz.pixelatedw.mineminenomi.abilities.hie.IceBallAbility::new);
    public static final java.util.function.Supplier<Ability> ICE_BLOCK_AVALANCHE = ABILITIES.register("ice_block_avalanche", xyz.pixelatedw.mineminenomi.abilities.hie.IceBlockAvalancheAbility::new);
    public static final java.util.function.Supplier<Ability> ICE_BLOCK_PHEASANT = ABILITIES.register("ice_block_pheasant", xyz.pixelatedw.mineminenomi.abilities.hie.IceBlockPheasantAbility::new);

    // Goro Goro no Mi — additional
    public static final java.util.function.Supplier<Ability> VARI = ABILITIES.register("1_million_vari", xyz.pixelatedw.mineminenomi.abilities.goro.VariAbility::new);
    public static final java.util.function.Supplier<Ability> VOLT_AMARU = ABILITIES.register("volt_amaru", xyz.pixelatedw.mineminenomi.abilities.goro.VoltAmaruAbility::new);

    // Pika Pika no Mi — additional
    public static final java.util.function.Supplier<Ability> LIGHT_ACCELERATION = ABILITIES.register("light_acceleration", xyz.pixelatedw.mineminenomi.abilities.pika.LightAccelerationAbility::new);
    public static final java.util.function.Supplier<Ability> FLASH = ABILITIES.register("flash", xyz.pixelatedw.mineminenomi.abilities.pika.FlashAbility::new);
    public static final java.util.function.Supplier<Ability> YATA_NO_KAGAMI = ABILITIES.register("yata_no_kagami", xyz.pixelatedw.mineminenomi.abilities.pika.YataNoKagamiAbility::new);

    // Moku Moku no Mi — additional
    public static final java.util.function.Supplier<Ability> WHITE_SNAKE = ABILITIES.register("white_snake", xyz.pixelatedw.mineminenomi.abilities.moku.WhiteSnakeAbility::new);
    public static final java.util.function.Supplier<Ability> WHITE_STRIKE = ABILITIES.register("white_strike", xyz.pixelatedw.mineminenomi.abilities.moku.WhiteStrikeAbility::new);
    public static final java.util.function.Supplier<Ability> SMOKE_LAUNCH = ABILITIES.register("smoke_launch", xyz.pixelatedw.mineminenomi.abilities.moku.SmokeLaunchAbility::new);
    public static final java.util.function.Supplier<Ability> WHITE_LAUNCHER = ABILITIES.register("white_launcher", xyz.pixelatedw.mineminenomi.abilities.moku.WhiteLauncherAbility::new);

    // Goro Goro no Mi — additional
    public static final java.util.function.Supplier<Ability> RAIGO = ABILITIES.register("raigo", xyz.pixelatedw.mineminenomi.abilities.goro.RaigoAbility::new);
    public static final java.util.function.Supplier<Ability> SPARK_STEP = ABILITIES.register("spark_step", xyz.pixelatedw.mineminenomi.abilities.goro.SparkStepAbility::new);

    // Gura Gura no Mi
    public static final java.util.function.Supplier<Ability> GEKISHIN = ABILITIES.register("gekishin", xyz.pixelatedw.mineminenomi.abilities.gura.GekishinAbility::new);
    public static final java.util.function.Supplier<Ability> KAISHIN = ABILITIES.register("kaishin", xyz.pixelatedw.mineminenomi.abilities.gura.KaishinAbility::new);
    public static final java.util.function.Supplier<Ability> SHIMA_YURASHI = ABILITIES.register("shima_yurashi", xyz.pixelatedw.mineminenomi.abilities.gura.ShimaYurashiAbility::new);
    public static final java.util.function.Supplier<Ability> KABUTOWARI = ABILITIES.register("kabutowari", xyz.pixelatedw.mineminenomi.abilities.gura.KabutowariAbility::new);
    public static final java.util.function.Supplier<Ability> GURA_IMMUNITY = ABILITIES.register("gura_immunity", xyz.pixelatedw.mineminenomi.abilities.gura.GuraImmunityAbility::new);
    public static final java.util.function.Supplier<Ability> TENCHI_MEIDO = ABILITIES.register("tenchi_meido", xyz.pixelatedw.mineminenomi.abilities.gura.TenchiMeidoAbility::new);
    public static final java.util.function.Supplier<Ability> SHINGEN_NO_ICHIGEKI = ABILITIES.register("shingen_no_ichigeki", xyz.pixelatedw.mineminenomi.abilities.gura.ShingenNoIchigekiAbility::new);

    // Bari Bari no Mi
    public static final java.util.function.Supplier<Ability> BARI_BARI_NO_PISTOL = ABILITIES.register("bari_bari_no_pistol", xyz.pixelatedw.mineminenomi.abilities.bari.BariBarriNoPistolAbility::new);
    public static final java.util.function.Supplier<Ability> BARRIER_WALL = ABILITIES.register("barrier_wall", xyz.pixelatedw.mineminenomi.abilities.bari.BarrierWallAbility::new);
    public static final java.util.function.Supplier<Ability> BARRIER_BALL = ABILITIES.register("barrier_ball", xyz.pixelatedw.mineminenomi.abilities.bari.BarrierBallAbility::new);
    public static final java.util.function.Supplier<Ability> BARRIER_CRASH = ABILITIES.register("barrier_crash", xyz.pixelatedw.mineminenomi.abilities.bari.BarrierCrashAbility::new);
    public static final java.util.function.Supplier<Ability> BARRIERBILITY_BAT = ABILITIES.register("barrierbility_bat", xyz.pixelatedw.mineminenomi.abilities.bari.BarrierbilityBatAbility::new);
    public static final java.util.function.Supplier<Ability> BARRIERBILITY_STAIRS = ABILITIES.register("barrierbility_stairs", xyz.pixelatedw.mineminenomi.abilities.bari.BarrierbilityStairsAbility::new);

    // Nikyu Nikyu no Mi
    public static final java.util.function.Supplier<Ability> NIKYU_PUSH = ABILITIES.register("nikyu_push", xyz.pixelatedw.mineminenomi.abilities.nikyu.NikyuPushAbility::new);
    public static final java.util.function.Supplier<Ability> PAD_HO = ABILITIES.register("pad_ho", xyz.pixelatedw.mineminenomi.abilities.nikyu.PadHoAbility::new);
    public static final java.util.function.Supplier<Ability> URSUS_SHOCK = ABILITIES.register("ursus_shock", xyz.pixelatedw.mineminenomi.abilities.nikyu.UrsusShockAbility::new);
    public static final java.util.function.Supplier<Ability> HANPATSU = ABILITIES.register("hanpatsu", xyz.pixelatedw.mineminenomi.abilities.nikyu.HanpatsuAbility::new);
    public static final java.util.function.Supplier<Ability> PUNI = ABILITIES.register("puni", xyz.pixelatedw.mineminenomi.abilities.nikyu.PuniAbility::new);
    public static final java.util.function.Supplier<Ability> PAIN_REPEL = ABILITIES.register("pain_repel", xyz.pixelatedw.mineminenomi.abilities.nikyu.PainRepelAbility::new);

    // Kage Kage no Mi
    public static final java.util.function.Supplier<Ability> KAGE_GIRI = ABILITIES.register("kage_giri", xyz.pixelatedw.mineminenomi.abilities.kage.KageGiriAbility::new);
    public static final java.util.function.Supplier<Ability> KAGE_KAKUMEI = ABILITIES.register("kage_kakumei", xyz.pixelatedw.mineminenomi.abilities.kage.KageKakumeiAbility::new);
    public static final java.util.function.Supplier<Ability> KAGEMUSHA = ABILITIES.register("kagemusha", xyz.pixelatedw.mineminenomi.abilities.kage.KagemushaAbility::new);
    public static final java.util.function.Supplier<Ability> BRICK_BAT = ABILITIES.register("brick_bat", xyz.pixelatedw.mineminenomi.abilities.kage.BrickBatAbility::new);
    public static final java.util.function.Supplier<Ability> SHADOWS_ASGARD = ABILITIES.register("shadows_asgard", xyz.pixelatedw.mineminenomi.abilities.kage.ShadowsAsgardAbility::new);
    public static final java.util.function.Supplier<Ability> DOPPELMAN = ABILITIES.register("doppelman", xyz.pixelatedw.mineminenomi.abilities.kage.DoppelmanAbility::new);
    public static final java.util.function.Supplier<Ability> NIGHTMARE_SOLDIERS = ABILITIES.register("nightmare_soldiers", xyz.pixelatedw.mineminenomi.abilities.kage.NightmareSoldiersAbility::new);
    public static final java.util.function.Supplier<Ability> TSUNO_TOKAGE = ABILITIES.register("tsuno_tokage", xyz.pixelatedw.mineminenomi.abilities.kage.TsunoTokageAbility::new);

    // Ito Ito no Mi
    public static final java.util.function.Supplier<Ability> TORIKAGO = ABILITIES.register("torikago", xyz.pixelatedw.mineminenomi.abilities.ito.TorikagoAbility::new);
    public static final java.util.function.Supplier<Ability> PARASITE = ABILITIES.register("parasite", xyz.pixelatedw.mineminenomi.abilities.ito.ParasiteAbility::new);
    public static final java.util.function.Supplier<Ability> SORA_NO_MICHI = ABILITIES.register("sora_no_michi", xyz.pixelatedw.mineminenomi.abilities.ito.SoraNoMichiAbility::new);
    public static final java.util.function.Supplier<Ability> OVERHEAT = ABILITIES.register("overheat", xyz.pixelatedw.mineminenomi.abilities.ito.OverheatAbility::new);
    public static final java.util.function.Supplier<Ability> BLACK_KNIGHT = ABILITIES.register("black_knight", xyz.pixelatedw.mineminenomi.abilities.ito.BlackKnightAbility::new);
    public static final java.util.function.Supplier<Ability> TAMAITO = ABILITIES.register("tamaito", xyz.pixelatedw.mineminenomi.abilities.ito.TamaitoAbility::new);
    public static final java.util.function.Supplier<Ability> KUMO_NO_SUGAKI = ABILITIES.register("kumo_no_sugaki", xyz.pixelatedw.mineminenomi.abilities.ito.KumoNoSugakiAbility::new);

    // Doku Doku no Mi
    public static final java.util.function.Supplier<Ability> VENOM_ROAD = ABILITIES.register("venom_road", xyz.pixelatedw.mineminenomi.abilities.doku.VenomRoadAbility::new);
    public static final java.util.function.Supplier<Ability> HYDRA = ABILITIES.register("hydra", xyz.pixelatedw.mineminenomi.abilities.doku.HydraAbility::new);
    public static final java.util.function.Supplier<Ability> POISON_IMMUNITY = ABILITIES.register("poison_immunity", xyz.pixelatedw.mineminenomi.abilities.doku.PoisonImmunityAbility::new);

    // Hana Hana no Mi
    public static final java.util.function.Supplier<Ability> CIEN_FLEUR = ABILITIES.register("cien_fleur", xyz.pixelatedw.mineminenomi.abilities.hana.CienFleurAbility::new);
    public static final java.util.function.Supplier<Ability> CLUTCH = ABILITIES.register("clutch", xyz.pixelatedw.mineminenomi.abilities.hana.ClutchAbility::new);

    // Suna Suna no Mi — additional
    public static final java.util.function.Supplier<Ability> GROUND_DEATH = ABILITIES.register("ground_death", xyz.pixelatedw.mineminenomi.abilities.suna.GroundDeathAbility::new);
    public static final java.util.function.Supplier<Ability> BARJAN = ABILITIES.register("barjan", xyz.pixelatedw.mineminenomi.abilities.suna.BarjanAbility::new);

    // Yami Yami no Mi — additional
    public static final java.util.function.Supplier<Ability> DARK_MATTER = ABILITIES.register("dark_matter", xyz.pixelatedw.mineminenomi.abilities.yami.DarkMatterAbility::new);
    public static final java.util.function.Supplier<Ability> BLACK_WORLD = ABILITIES.register("black_world", xyz.pixelatedw.mineminenomi.abilities.yami.BlackWorldAbility::new);

    // Gomu Gomu no Mi — Gear Fourth
    public static final java.util.function.Supplier<Ability> GEAR_FOURTH = ABILITIES.register("gear_fourth", xyz.pixelatedw.mineminenomi.abilities.gomu.GearFourthAbility::new);

    // Horo Horo no Mi
    public static final java.util.function.Supplier<Ability> NEGATIVE_HOLLOW = ABILITIES.register("negative_hollow", xyz.pixelatedw.mineminenomi.abilities.horo.NegativeHollowAbility::new);
    public static final java.util.function.Supplier<Ability> MINI_HOLLOW = ABILITIES.register("mini_hollow", xyz.pixelatedw.mineminenomi.abilities.horo.MiniHollowAbility::new);

    // Wara Wara no Mi
    public static final java.util.function.Supplier<Ability> LIFE_MINUS = ABILITIES.register("life_minus", xyz.pixelatedw.mineminenomi.abilities.wara.LifeMinusAbility::new);

    // Mero Mero no Mi
    public static final java.util.function.Supplier<Ability> MERO_MERO_MELLOW = ABILITIES.register("mero_mero_mellow", xyz.pixelatedw.mineminenomi.abilities.mero.MeroMeroMellowAbility::new);
    public static final java.util.function.Supplier<Ability> PISTOL_KISS = ABILITIES.register("pistol_kiss", xyz.pixelatedw.mineminenomi.abilities.mero.PistolKissAbility::new);

    // Pero Pero no Mi
    public static final java.util.function.Supplier<Ability> CANDY_WAVE = ABILITIES.register("candy_wave", xyz.pixelatedw.mineminenomi.abilities.pero.CandyWaveAbility::new);
    public static final java.util.function.Supplier<Ability> CANDY_ARMOR = ABILITIES.register("candy_armor", xyz.pixelatedw.mineminenomi.abilities.pero.CandyArmorAbility::new);

    // Sube Sube no Mi
    public static final java.util.function.Supplier<Ability> SUBE_SUBE_DEFLECT = ABILITIES.register("sube_sube_deflect", xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeDeflectAbility::new);
    public static final java.util.function.Supplier<Ability> SUBE_SUBE_SPUR = ABILITIES.register("sube_sube_spur", xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeSpurAbility::new);

    // Noro Noro no Mi
    public static final java.util.function.Supplier<Ability> NORO_NORO_BEAM = ABILITIES.register("noro_noro_beam", xyz.pixelatedw.mineminenomi.abilities.noro.NoroNoroBeamAbility::new);

    // Haki
    public static final java.util.function.Supplier<Ability> BUSOSHOKU_HAKI_HARDENING = ABILITIES.register("busoshoku_haki_hardening", xyz.pixelatedw.mineminenomi.api.abilities.haki.BusoshokuHakiHardeningAbility::new);
    public static final java.util.function.Supplier<Ability> BUSOSHOKU_HAKI_EMISSION = ABILITIES.register("busoshoku_haki_emission", xyz.pixelatedw.mineminenomi.api.abilities.haki.BusoshokuHakiEmissionAbility::new);
    public static final java.util.function.Supplier<Ability> BUSOSHOKU_HAKI_INTERNAL_DESTRUCTION = ABILITIES.register("busoshoku_haki_internal_destruction", xyz.pixelatedw.mineminenomi.api.abilities.haki.BusoshokuHakiInternalDestructionAbility::new);
    public static final java.util.function.Supplier<Ability> BUSOSHOKU_HAKI_FULL_BODY_HARDENING = ABILITIES.register("busoshoku_haki_full_body_hardening", xyz.pixelatedw.mineminenomi.api.abilities.haki.BusoshokuHakiFullBodyHardeningAbility::new);
    public static final java.util.function.Supplier<Ability> BUSOSHOKU_HAKI_IMBUING = ABILITIES.register("busoshoku_haki_imbuing", xyz.pixelatedw.mineminenomi.api.abilities.haki.BusoshokuHakiImbuingAbility::new);
    public static final java.util.function.Supplier<Ability> HAOSHOKU_HAKI_INFUSION = ABILITIES.register("haoshoku_haki_infusion", xyz.pixelatedw.mineminenomi.api.abilities.haki.HaoshokuHakiInfusionAbility::new);
    public static final java.util.function.Supplier<Ability> KENBUNSHOKU_HAKI_AURA = ABILITIES.register("kenbunshoku_haki_aura", xyz.pixelatedw.mineminenomi.api.abilities.haki.KenbunshokuHakiAuraAbility::new);
    public static final java.util.function.Supplier<Ability> KENBUNSHOKU_HAKI_FUTURE_SIGHT = ABILITIES.register("kenbunshoku_haki_future_sight", xyz.pixelatedw.mineminenomi.api.abilities.haki.KenbunshokuHakiFutureSightAbility::new);

    // Rokushiki — additional
    public static final java.util.function.Supplier<Ability> KAMISORI = ABILITIES.register("kamisori", xyz.pixelatedw.mineminenomi.api.abilities.rokushiki.KamisoriAbility::new);

    // Logia Passive Abilities
    public static final java.util.function.Supplier<Ability> MERA_LOGIA = ABILITIES.register("mera_logia", xyz.pixelatedw.mineminenomi.abilities.mera.MeraLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> MAGU_LOGIA = ABILITIES.register("magu_logia", xyz.pixelatedw.mineminenomi.abilities.magu.MaguLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> HIE_LOGIA = ABILITIES.register("hie_logia", xyz.pixelatedw.mineminenomi.abilities.hie.HieLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> PIKA_LOGIA = ABILITIES.register("pika_logia", xyz.pixelatedw.mineminenomi.abilities.pika.PikaLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> MOKU_LOGIA = ABILITIES.register("moku_logia", xyz.pixelatedw.mineminenomi.abilities.moku.MokuLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> GORO_LOGIA = ABILITIES.register("goro_logia", xyz.pixelatedw.mineminenomi.abilities.goro.GoroLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> SUNA_LOGIA = ABILITIES.register("suna_logia", xyz.pixelatedw.mineminenomi.abilities.suna.SunaLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> YUKI_LOGIA = ABILITIES.register("yuki_logia", xyz.pixelatedw.mineminenomi.abilities.yuki.YukiLogiaAbility::new);
    public static final java.util.function.Supplier<Ability> GASU_LOGIA = ABILITIES.register("gasu_logia", xyz.pixelatedw.mineminenomi.abilities.gasu.GasuLogiaAbility::new);

    // Zou Zou no Mi
    public static final java.util.function.Supplier<Ability> ZOU_GUARD_POINT = ABILITIES.register("zou_guard_point", xyz.pixelatedw.mineminenomi.abilities.zou.ZouGuardPointAbility::new);
    public static final java.util.function.Supplier<Ability> ZOU_HEAVY_POINT = ABILITIES.register("zou_heavy_point", xyz.pixelatedw.mineminenomi.abilities.zou.ZouHeavyPointAbility::new);

    // Zou Zou no Mi (Mammoth)
    public static final java.util.function.Supplier<Ability> MAMMOTH_GUARD_POINT = ABILITIES.register("mammoth_guard_point", xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothGuardPointAbility::new);
    public static final java.util.function.Supplier<Ability> MAMMOTH_HEAVY_POINT = ABILITIES.register("mammoth_heavy_point", xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> ANCIENT_STOMP = ABILITIES.register("ancient_stomp", xyz.pixelatedw.mineminenomi.abilities.zoumammoth.AncientStompAbility::new);
    public static final java.util.function.Supplier<Ability> ANCIENT_SWEEP = ABILITIES.register("ancient_sweep", xyz.pixelatedw.mineminenomi.abilities.zoumammoth.AncientSweepAbility::new);
    public static final java.util.function.Supplier<Ability> ANCIENT_TRUNK_SHOT = ABILITIES.register("ancient_trunk_shot", xyz.pixelatedw.mineminenomi.abilities.zoumammoth.AncientTrunkShotAbility::new);
    public static final java.util.function.Supplier<Ability> MAMMOTH_TRAMPLE = ABILITIES.register("mammoth_trample", xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothTrampleAbility::new);

    // Ryu Ryu no Mi (Allosaurus)
    public static final java.util.function.Supplier<Ability> ALLOSAURUS_WALK_POINT = ABILITIES.register("allosaurus_walk_point", xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AllosaurusWalkPointAbility::new);

    // Race Abilities
                public static final java.util.function.Supplier<Ability> MANTRA = ABILITIES.register("mantra", xyz.pixelatedw.mineminenomi.abilities.race.MantraAbility::new);
    public static final java.util.function.Supplier<Ability> ALLOSAURUS_HEAVY_POINT = ABILITIES.register("allosaurus_heavy_point", xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AllosaurusHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> ANCIENT_BITE = ABILITIES.register("ancient_bite", xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AncientBiteAbility::new);
    public static final java.util.function.Supplier<Ability> ANCIENT_TAIL_SPIN = ABILITIES.register("ancient_tail_spin", xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AncientTailSpinAbility::new);

    // Fishman Karate

    // Ryu Ryu no Mi (Brachiosaurus)
    public static final java.util.function.Supplier<Ability> BRACHIOSAURUS_GUARD_POINT = ABILITIES.register("brachiosaurus_guard_point", xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachiosaurusGuardPointAbility::new);
    public static final java.util.function.Supplier<Ability> BRACHIOSAURUS_HEAVY_POINT = ABILITIES.register("brachiosaurus_heavy_point", xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachiosaurusHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> BRACHIO_BOMBER = ABILITIES.register("brachio_bomber", xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachioBomberAbility::new);
    public static final java.util.function.Supplier<Ability> BRACHIO_GRAB = ABILITIES.register("brachio_grab", xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachioGrabAbility::new);

    // Ryu Ryu no Mi (Pteranodon)
    public static final java.util.function.Supplier<Ability> PTERANODON_FLY_POINT = ABILITIES.register("pteranodon_fly_point", xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.PteranodonFlyPointAbility::new);
    public static final java.util.function.Supplier<Ability> PTERANODON_ASSAULT_POINT = ABILITIES.register("pteranodon_assault_point", xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.PteranodonAssaultPointAbility::new);
    public static final java.util.function.Supplier<Ability> BEAK_GRAB = ABILITIES.register("beak_grab", xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.BeakGrabAbility::new);
    public static final java.util.function.Supplier<Ability> BARIZODON = ABILITIES.register("barizodon", xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.BarizodonAbility::new);
    public static final java.util.function.Supplier<Ability> TANKYUDON = ABILITIES.register("tankyudon", xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.TankyudonAbility::new);
    public static final java.util.function.Supplier<Ability> TEMPURAUDON = ABILITIES.register("tempuraudon", xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.TempuraudonAbility::new);

    // Sara Sara no Mi (Axolotl)
    public static final java.util.function.Supplier<Ability> AXOLOTL_WALK_POINT = ABILITIES.register("axolotl_walk_point", xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlWalkPointAbility::new);
    public static final java.util.function.Supplier<Ability> AXOLOTL_HEAVY_POINT = ABILITIES.register("axolotl_heavy_point", xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> AXOLOTL_HEAL = ABILITIES.register("axolotl_heal", xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlHealAbility::new);
    public static final java.util.function.Supplier<Ability> HEART_REGEN = ABILITIES.register("heart_regen", xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.HeartRegenAbility::new);
    public static final java.util.function.Supplier<Ability> PLAY_DEAD = ABILITIES.register("play_dead", xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.PlayDeadAbility::new);
    public static final java.util.function.Supplier<Ability> POISON_SPIT = ABILITIES.register("poison_spit", xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.PoisonSpitAbility::new);

    // Ushi Ushi no Mi (Bison)
    public static final java.util.function.Supplier<Ability> BISON_WALK_POINT = ABILITIES.register("bison_walk_point", xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonWalkPointAbility::new);
    public static final java.util.function.Supplier<Ability> BISON_HEAVY_POINT = ABILITIES.register("bison_heavy_point", xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> BISON_SMASH = ABILITIES.register("bison_smash", xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonSmashAbility::new);
    public static final java.util.function.Supplier<Ability> FIDDLE_BANFF = ABILITIES.register("fiddle_banff", xyz.pixelatedw.mineminenomi.abilities.ushibison.FiddleBanffAbility::new);
    public static final java.util.function.Supplier<Ability> KOKUTEI_CROSS = ABILITIES.register("kokutei_cross", xyz.pixelatedw.mineminenomi.abilities.ushibison.KokuteiCrossAbility::new);

    // Ushi Ushi no Mi (Giraffe)
    public static final java.util.function.Supplier<Ability> GIRAFFE_WALK_POINT = ABILITIES.register("giraffe_walk_point", xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.GiraffeWalkPointAbility::new);
    public static final java.util.function.Supplier<Ability> GIRAFFE_HEAVY_POINT = ABILITIES.register("giraffe_heavy_point", xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.GiraffeHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> BIGAN = ABILITIES.register("bigan", xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.BiganAbility::new);

    // Sai Sai no Mi
    public static final java.util.function.Supplier<Ability> SAI_WALK_POINT = ABILITIES.register("sai_walk_point", xyz.pixelatedw.mineminenomi.abilities.sai.SaiWalkPointAbility::new);
    public static final java.util.function.Supplier<Ability> SAI_HEAVY_POINT = ABILITIES.register("sai_heavy_point", xyz.pixelatedw.mineminenomi.abilities.sai.SaiHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> HORN_DASH = ABILITIES.register("horn_dash", xyz.pixelatedw.mineminenomi.abilities.sai.HornDashAbility::new);
    public static final java.util.function.Supplier<Ability> RHINO_SMASH = ABILITIES.register("rhino_smash", xyz.pixelatedw.mineminenomi.abilities.sai.RhinoSmashAbility::new);

    // Mogu Mogu no Mi
    public static final java.util.function.Supplier<Ability> MOGU_HEAVY_POINT = ABILITIES.register("mogu_heavy_point", xyz.pixelatedw.mineminenomi.abilities.mogu.MoguHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> MOGURA_BANANA = ABILITIES.register("mogura_banana", xyz.pixelatedw.mineminenomi.abilities.mogu.MoguraBananaAbility::new);
    public static final java.util.function.Supplier<Ability> MOGURA_TONPO = ABILITIES.register("mogura_tonpo", xyz.pixelatedw.mineminenomi.abilities.mogu.MoguraTonpoAbility::new);

    // Tori Tori no Mi (Phoenix)
    public static final java.util.function.Supplier<Ability> PHOENIX_FLY_POINT = ABILITIES.register("phoenix_fly_point", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixFlyPointAbility::new);
    public static final java.util.function.Supplier<Ability> PHOENIX_ASSAULT_POINT = ABILITIES.register("phoenix_assault_point", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixAssaultPointAbility::new);
    public static final java.util.function.Supplier<Ability> BLUE_BIRD = ABILITIES.register("blue_bird", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.BlueBirdAbility::new);
    public static final java.util.function.Supplier<Ability> FLAMES_OF_REGENERATION = ABILITIES.register("flames_of_regeneration", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.FlamesOfRegenerationAbility::new);
    public static final java.util.function.Supplier<Ability> FUJIAZAMI = ABILITIES.register("fujiazami", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.FujiazamiAbility::new);
    public static final java.util.function.Supplier<Ability> PHOENIX_GOEN = ABILITIES.register("phoenix_goen", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixGoenAbility::new);
    public static final java.util.function.Supplier<Ability> SAISEI_NO_HONO = ABILITIES.register("saisei_no_hono", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.SaiseiNoHonoAbility::new);
    public static final java.util.function.Supplier<Ability> TENSEI_NO_SOEN = ABILITIES.register("tensei_no_soen", xyz.pixelatedw.mineminenomi.abilities.toriphoenix.TenseiNoSoenAbility::new);

    // Kame Kame no Mi
    public static final java.util.function.Supplier<Ability> KAME_WALK_POINT = ABILITIES.register("kame_walk_point", xyz.pixelatedw.mineminenomi.abilities.kame.KameWalkPointAbility::new);
    public static final java.util.function.Supplier<Ability> KAME_GUARD_POINT = ABILITIES.register("kame_guard_point", xyz.pixelatedw.mineminenomi.abilities.kame.KameGuardPointAbility::new);

    // Neko Neko no Mi (Leopard)
    public static final java.util.function.Supplier<Ability> LEOPARD_WALK_POINT = ABILITIES.register("leopard_walk_point", xyz.pixelatedw.mineminenomi.abilities.nekoleopard.LeopardWalkPointAbility::new);
    public static final java.util.function.Supplier<Ability> LEOPARD_HEAVY_POINT = ABILITIES.register("leopard_heavy_point", xyz.pixelatedw.mineminenomi.abilities.nekoleopard.LeopardHeavyPointAbility::new);
    public static final java.util.function.Supplier<Ability> CLAW_STRIKE = ABILITIES.register("claw_strike", xyz.pixelatedw.mineminenomi.abilities.nekoleopard.ClawStrikeAbility::new);
    public static final java.util.function.Supplier<Ability> FEROCIOUS_LEAP = ABILITIES.register("ferocious_leap", xyz.pixelatedw.mineminenomi.abilities.nekoleopard.FerociousLeapAbility::new);
    public static final java.util.function.Supplier<Ability> NEKO_NIGHT_VISION = ABILITIES.register("neko_night_vision", xyz.pixelatedw.mineminenomi.abilities.nekoleopard.NekoNightVisionAbility::new);

    // Perks
    public static final Supplier<Ability> CYBORG_ARMOR_PERK = ABILITIES.register("cyborg_armor", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Cyborg Armor", "Provides natural armor"));
    public static final Supplier<Ability> FISHMAN_SWIM_SPEED_PERK = ABILITIES.register("fishman_swim_speed", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Fishman Swim Speed", "Faster swimming speed"));
    public static final Supplier<Ability> FISHMAN_DAMAGE_PERK = ABILITIES.register("fishman_damage", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Fishman Damage", "Increased damage underwater"));
    public static final Supplier<Ability> MINK_SPEED_PERK = ABILITIES.register("mink_speed", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Mink Speed", "Increased movement speed"));
    public static final Supplier<Ability> MINK_JUMP_PERK = ABILITIES.register("mink_jump", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Mink Jump", "Higher jump height"));
    public static final Supplier<Ability> SWORDSMAN_DAMAGE_PERK = ABILITIES.register("swordsman_damage", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Swordsman Damage", "Increased sword damage"));
    public static final Supplier<Ability> SNIPER_ACCURACY_PERK_OLD = ABILITIES.register("sniper_accuracy_old", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Sniper Accuracy", "Better accuracy with guns"));
    public static final Supplier<Ability> SNIPER_GOGGLES_PERK_OLD = ABILITIES.register("sniper_goggles_old", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Sniper Goggles", "Toggleable zoom"));
    public static final Supplier<Ability> BRAWLER_DAMAGE_PERK_OLD = ABILITIES.register("brawler_damage_old", () -> new xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility("Brawler Damage", "Increased punch damage"));

    // Awa Awa no Mi
    public static final Supplier<Ability> GOLDEN_HOUR = ABILITIES.register("golden_hour", xyz.pixelatedw.mineminenomi.abilities.awa.GoldenHourAbility::new);
    public static final Supplier<Ability> RELAX_HOUR = ABILITIES.register("relax_hour", xyz.pixelatedw.mineminenomi.abilities.awa.RelaxHourAbility::new);
    public static final Supplier<Ability> SOAP_DEFENSE = ABILITIES.register("soap_defense", xyz.pixelatedw.mineminenomi.abilities.awa.SoapDefenseAbility::new);


    // Yuki Yuki no Mi
    public static final java.util.function.Supplier<Ability> YUKI_RABI = ABILITIES.register("yuki_rabi", xyz.pixelatedw.mineminenomi.abilities.yuki.YukiRabiAbility::new);
    public static final java.util.function.Supplier<Ability> YUKI_TRAVEL = ABILITIES.register("yuki_travel", xyz.pixelatedw.mineminenomi.abilities.yuki.YukiTravelAbility::new);
    public static final java.util.function.Supplier<Ability> YUKI_IMMUNITY = ABILITIES.register("yuki_immunity", xyz.pixelatedw.mineminenomi.abilities.yuki.YukiImmunityAbility::new);
    public static final java.util.function.Supplier<Ability> YUKI_GAKI = ABILITIES.register("yuki_gaki", xyz.pixelatedw.mineminenomi.abilities.yuki.YukiGakiAbility::new);
    public static final java.util.function.Supplier<Ability> TABIRA_YUKI = ABILITIES.register("tabira_yuki", xyz.pixelatedw.mineminenomi.abilities.yuki.TabiraYukiAbility::new);

    // Gasu Gasu no Mi
    public static final java.util.function.Supplier<Ability> GASU_IMMUNITY = ABILITIES.register("gasu_immunity", xyz.pixelatedw.mineminenomi.abilities.gasu.GasuImmunityAbility::new);
    public static final java.util.function.Supplier<Ability> GASU_FLY = ABILITIES.register("gasu_fly", xyz.pixelatedw.mineminenomi.abilities.gasu.GasuFlyAbility::new);

    // Yomi Yomi no Mi
    public static final java.util.function.Supplier<Ability> YOMI_NO_REIKI = ABILITIES.register("yomi_no_reiki", xyz.pixelatedw.mineminenomi.abilities.yomi.YomiNoReikiAbility::new);
    public static final java.util.function.Supplier<Ability> YOMI_IMMUNITY = ABILITIES.register("yomi_immunity", xyz.pixelatedw.mineminenomi.abilities.yomi.YomiImmunityAbility::new);

<<<<<<< HEAD
    public static final Supplier<Ability> KILO_PRESS_1 = ABILITIES.register("1_kilo_press", xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress1Ability::new);
    public static final Supplier<Ability> KILO_PUNCH_5000 = ABILITIES.register("5000_kilo_punch", xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPunch5000Ability::new);
    public static final Supplier<Ability> KILO_PRESS_10000 = ABILITIES.register("10000_kilo_press", xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress10000Ability::new);

    public static final Supplier<Ability> GRAVI_PULL = ABILITIES.register("gravi_pull", xyz.pixelatedw.mineminenomi.abilities.zushi.GraviPullAbility::new);
    public static final Supplier<Ability> GRAVI_ZONE = ABILITIES.register("gravi_zone", xyz.pixelatedw.mineminenomi.abilities.zushi.GraviZoneAbility::new);
    public static final Supplier<Ability> JIGOKU_TABI = ABILITIES.register("jigoku_tabi", xyz.pixelatedw.mineminenomi.abilities.zushi.JigokuTabiAbility::new);
    public static final Supplier<Ability> MOKO = ABILITIES.register("moko", xyz.pixelatedw.mineminenomi.abilities.zushi.MokoAbility::new);
    public static final Supplier<Ability> SAGARI_NO_RYUSEI = ABILITIES.register("sagari_no_ryusei", xyz.pixelatedw.mineminenomi.abilities.zushi.SagariNoRyuseiAbility::new);
    public static final Supplier<Ability> ABARE_HIMATSURI = ABILITIES.register("abare_himatsuri", xyz.pixelatedw.mineminenomi.abilities.zushi.AbareHimatsuriAbility::new);
=======

    public static final Supplier<Ability> COLA_BACKPACK_BONUS = ABILITIES.register("cola_backpack_bonus", xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaBackpackBonusAbility::new);
    public static final Supplier<Ability> COLA_FUEL = ABILITIES.register("cola_fuel", xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaFuelAbility::new);
    public static final Supplier<Ability> COLA_OVERDRIVE = ABILITIES.register("cola_overdrive", xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaOverdriveAbility::new);
    public static final Supplier<Ability> COUP_DE_BOO = ABILITIES.register("coup_de_boo", xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeBooAbility::new);
    public static final Supplier<Ability> COUP_DE_VENT = ABILITIES.register("coup_de_vent", xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeVentAbility::new);
    public static final Supplier<Ability> CYBORG_PASSIVE_BONUSES = ABILITIES.register("cyborg_passive_bonuses", xyz.pixelatedw.mineminenomi.abilities.cyborg.CyborgPassiveBonusesAbility::new);
    public static final Supplier<Ability> FRESH_FIRE = ABILITIES.register("fresh_fire", xyz.pixelatedw.mineminenomi.abilities.cyborg.FreshFireAbility::new);
    public static final Supplier<Ability> RADICAL_BEAM = ABILITIES.register("radical_beam", xyz.pixelatedw.mineminenomi.abilities.cyborg.RadicalBeamAbility::new);

    public static final Supplier<Ability> SOUTHLAND_SUPLEX = ABILITIES.register("southland_suplex", xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility::new);
    public static final Supplier<Ability> SUPLEX = ABILITIES.register("suplex", xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility::new);

    public static final Supplier<Ability> STRONG_RIGHT = ABILITIES.register("strong_right", xyz.pixelatedw.mineminenomi.abilities.cyborg.StrongRightAbility::new);

    public static final Supplier<Ability> ELECLAW = ABILITIES.register("eleclaw", xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility::new);
    public static final Supplier<Ability> ELECTRICAL_LUNA = ABILITIES.register("electrical_luna", xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalLunaAbility::new);
    public static final Supplier<Ability> ELECTRICAL_MISSILE = ABILITIES.register("electrical_missile", xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalMissileAbility::new);
    public static final Supplier<Ability> ELECTRICAL_SHOWER = ABILITIES.register("electrical_shower", xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalShowerAbility::new);
    public static final Supplier<Ability> ELECTRICAL_TEMPESTA = ABILITIES.register("electrical_tempesta", xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalTempestaAbility::new);
    public static final Supplier<Ability> MINK_PASSIVE_BONUSES = ABILITIES.register("mink_passive_bonuses", xyz.pixelatedw.mineminenomi.abilities.electro.MinkPassiveBonusesAbility::new);
    public static final Supplier<Ability> SULONG = ABILITIES.register("sulong", xyz.pixelatedw.mineminenomi.abilities.electro.SulongAbility::new);
    public static final Supplier<Ability> SULONG_CHECK = ABILITIES.register("sulong_check", xyz.pixelatedw.mineminenomi.abilities.electro.SulongCheckAbility::new);

    public static final Supplier<Ability> FISHMAN_PASSIVE_BONUSES = ABILITIES.register("fishman_passive_bonuses", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility::new);
    public static final Supplier<Ability> KACHIAGE_HAISOKU = ABILITIES.register("kachiage_haisoku", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility::new);
    public static final Supplier<Ability> KARAKUSAGAWARA_SEIKEN = ABILITIES.register("karakusagawara_seiken", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility::new);
    public static final Supplier<Ability> MIZU_OSU = ABILITIES.register("mizu_osu", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuOsuAbility::new);
    public static final Supplier<Ability> MIZU_SHURYUDAN = ABILITIES.register("mizu_shuryudan", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuShuryudanAbility::new);
    public static final Supplier<Ability> MIZU_TAIHO = ABILITIES.register("mizu_taiho", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuTaihoAbility::new);
    public static final Supplier<Ability> PACK_OF_SHARKS = ABILITIES.register("pack_of_sharks", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.PackOfSharksAbility::new);
    public static final Supplier<Ability> SAMEHADA_SHOTEI = ABILITIES.register("samehada_shotei", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility::new);
    public static final Supplier<Ability> SHARK_ON_TOOTH = ABILITIES.register("shark_on_tooth", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SharkOnToothAbility::new);
    public static final Supplier<Ability> TWO_FISH_ENGINE = ABILITIES.register("two_fish_engine", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.TwoFishEngineAbility::new);
    public static final Supplier<Ability> UCHIMIZU = ABILITIES.register("uchimizu", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility::new);
    public static final Supplier<Ability> YARINAMI = ABILITIES.register("yarinami", xyz.pixelatedw.mineminenomi.abilities.fishmankarate.YarinamiAbility::new);

    public static final Supplier<Ability> BRAWLER_PASSIVE_BONUSES = ABILITIES.register("brawler_passive_bonuses", xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility::new);
    public static final Supplier<Ability> DAMAGE_ABSORPTION = ABILITIES.register("damage_absorption", xyz.pixelatedw.mineminenomi.abilities.brawler.DamageAbsorptionAbility::new);
    public static final Supplier<Ability> SPINNING_BRAWL = ABILITIES.register("spinning_brawl", xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility::new);

    public static final Supplier<Ability> HASSHOKEN_PASSIVE_BONUSES = ABILITIES.register("hasshoken_passive_bonuses", xyz.pixelatedw.mineminenomi.abilities.hasshoken.HasshokenPassiveBonusesAbility::new);
    public static final Supplier<Ability> BUJAOGEN = ABILITIES.register("bujaogen", xyz.pixelatedw.mineminenomi.abilities.hasshoken.BujaogenAbility::new);
    public static final Supplier<Ability> BUTO = ABILITIES.register("buto", xyz.pixelatedw.mineminenomi.abilities.hasshoken.ButoAbility::new);
    public static final Supplier<Ability> BUTO_KAITEN = ABILITIES.register("buto_kaiten", xyz.pixelatedw.mineminenomi.abilities.hasshoken.ButoKaitenAbility::new);
    public static final Supplier<Ability> KIRYU_KIRIKUGI = ABILITIES.register("kiryu_kirikugi", xyz.pixelatedw.mineminenomi.abilities.hasshoken.KiryuKirikugiAbility::new);
    public static final Supplier<Ability> MUKIRYU_MUKIRIKUGI = ABILITIES.register("mukiryu_mukirikugi", xyz.pixelatedw.mineminenomi.abilities.hasshoken.MukiryuMukirikugiAbility::new);

    public static final Supplier<Ability> RYU_NO_IBUKI = ABILITIES.register("ryu_no_ibuki", xyz.pixelatedw.mineminenomi.abilities.ryusoken.RyuNoIbukiAbility::new);
    public static final Supplier<Ability> RYU_NO_KAGIZUME = ABILITIES.register("ryu_no_kagizume", xyz.pixelatedw.mineminenomi.abilities.ryusoken.RyuNoKagizumeAbility::new);
>>>>>>> origin/main

    public static void register(IEventBus bus) {
        ABILITIES.register(bus);
    }

}
