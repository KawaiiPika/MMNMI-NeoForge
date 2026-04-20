package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModFruits {
    
    public static final DeferredItem<AkumaNoMiItem> GOMU_GOMU_NO_MI = ModRegistry.ITEMS.register("gomu_gomu_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.GOMU_GOMU_NO_PISTOL, ModAbilities.GOMU_GOMU_NO_GATLING, ModAbilities.GOMU_GOMU_NO_BAZOOKA, ModAbilities.GOMU_GOMU_NO_ROCKET, ModAbilities.GEAR_SECOND, ModAbilities.GEAR_THIRD, ModAbilities.GEAR_FOURTH, ModAbilities.GOMU_GOMU_NO_DAWN_WHIP));
            
    public static final DeferredItem<AkumaNoMiItem> MERA_MERA_NO_MI = ModRegistry.ITEMS.register("mera_mera_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.HIGAN, ModAbilities.HIKEN, ModAbilities.DAI_ENKA_ENTEI, ModAbilities.HIBASHIRA, ModAbilities.JUJIKA, ModAbilities.MERA_LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> HIE_HIE_NO_MI = ModRegistry.ITEMS.register("hie_hie_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.ICE_AGE, ModAbilities.ICE_SABER, ModAbilities.ICE_BLOCK_PARTISAN, ModAbilities.ICE_TIME));
            
    public static final DeferredItem<AkumaNoMiItem> GORO_GORO_NO_MI = ModRegistry.ITEMS.register("goro_goro_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.EL_THOR, ModAbilities.SANGO, ModAbilities.VARI, ModAbilities.VOLT_AMARU, ModAbilities.RAIGO, ModAbilities.SPARK_STEP));
            
    public static final DeferredItem<AkumaNoMiItem> HITO_HITO_NO_MI = ModRegistry.ITEMS.register("hito_hito_no_mi", 
            () -> new AkumaNoMiItem(1, FruitType.ZOAN));

    public static final DeferredItem<AkumaNoMiItem> OPE_OPE_NO_MI = ModRegistry.ITEMS.register("ope_ope_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.ROOM, ModAbilities.COUNTER_SHOCK, ModAbilities.SHAMBLES, ModAbilities.MES));

    public static final DeferredItem<AkumaNoMiItem> YAMI_YAMI_NO_MI = ModRegistry.ITEMS.register("yami_yami_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.BLACK_HOLE, ModAbilities.KUROUZU, ModAbilities.DARK_MATTER, ModAbilities.BLACK_WORLD));
            
    public static final DeferredItem<AkumaNoMiItem> MAGU_MAGU_NO_MI = ModRegistry.ITEMS.register("magu_magu_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.DAI_FUNKA, ModAbilities.MAGMA_COATING, ModAbilities.LAVA_FLOW, ModAbilities.MEIGO, ModAbilities.RYUSEI_KAZAN));
            
    public static final DeferredItem<AkumaNoMiItem> PIKA_PIKA_NO_MI = ModRegistry.ITEMS.register("pika_pika_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.AMATERASU, ModAbilities.YASAKANI_NO_MAGATAMA, ModAbilities.YATA_NO_KAGAMI, ModAbilities.LIGHT_ACCELERATION, ModAbilities.FLASH));
            
    public static final DeferredItem<AkumaNoMiItem> MOKU_MOKU_NO_MI = ModRegistry.ITEMS.register("moku_moku_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.WHITE_BLOW, ModAbilities.WHITE_OUT, ModAbilities.WHITE_SNAKE, ModAbilities.WHITE_STRIKE, ModAbilities.SMOKE_LAUNCH, ModAbilities.WHITE_LAUNCHER));
            
    public static final DeferredItem<AkumaNoMiItem> SUNA_SUNA_NO_MI = ModRegistry.ITEMS.register("suna_suna_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA, ModAbilities.DESERT_SPADA, ModAbilities.SABLES, ModAbilities.GROUND_DEATH, ModAbilities.BARJAN));

    public static final DeferredItem<AkumaNoMiItem> GURA_GURA_NO_MI = ModRegistry.ITEMS.register("gura_gura_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA, ModAbilities.GEKISHIN, ModAbilities.KAISHIN, ModAbilities.SHIMA_YURASHI, ModAbilities.KABUTOWARI, ModAbilities.GURA_IMMUNITY, ModAbilities.TENCHI_MEIDO, ModAbilities.SHINGEN_NO_ICHIGEKI));

    public static final DeferredItem<AkumaNoMiItem> ITO_ITO_NO_MI = ModRegistry.ITEMS.register("ito_ito_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA, ModAbilities.TORIKAGO, ModAbilities.PARASITE, ModAbilities.SORA_NO_MICHI, ModAbilities.OVERHEAT, ModAbilities.BLACK_KNIGHT, ModAbilities.TAMAITO, ModAbilities.KUMO_NO_SUGAKI));

    public static final DeferredItem<AkumaNoMiItem> NIKYU_NIKYU_NO_MI = ModRegistry.ITEMS.register("nikyu_nikyu_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA, ModAbilities.NIKYU_PUSH, ModAbilities.PAD_HO, ModAbilities.URSUS_SHOCK, ModAbilities.HANPATSU, ModAbilities.PUNI, ModAbilities.PAIN_REPEL));

    public static final DeferredItem<AkumaNoMiItem> BARI_BARI_NO_MI = ModRegistry.ITEMS.register("bari_bari_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.BARI_BARI_NO_PISTOL, ModAbilities.BARRIER_WALL, ModAbilities.BARRIER_BALL, ModAbilities.BARRIER_CRASH, ModAbilities.BARRIERBILITY_BAT, ModAbilities.BARRIERBILITY_STAIRS));

    public static final DeferredItem<AkumaNoMiItem> HANA_HANA_NO_MI = ModRegistry.ITEMS.register("hana_hana_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.CIEN_FLEUR, ModAbilities.CLUTCH));

    public static final DeferredItem<AkumaNoMiItem> HORO_HORO_NO_MI = ModRegistry.ITEMS.register("horo_horo_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.NEGATIVE_HOLLOW, ModAbilities.MINI_HOLLOW));

    public static final DeferredItem<AkumaNoMiItem> KAGE_KAGE_NO_MI = ModRegistry.ITEMS.register("kage_kage_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.KAGE_GIRI, ModAbilities.KAGE_KAKUMEI, ModAbilities.KAGEMUSHA, ModAbilities.BRICK_BAT, ModAbilities.SHADOWS_ASGARD, ModAbilities.DOPPELMAN, ModAbilities.NIGHTMARE_SOLDIERS, ModAbilities.TSUNO_TOKAGE));

    public static final DeferredItem<AkumaNoMiItem> BOMU_BOMU_NO_MI = ModRegistry.ITEMS.register("bomu_bomu_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.BAKUDAN, ModAbilities.NOSE_FANCY_CANNON));

    public static final DeferredItem<AkumaNoMiItem> SUKE_SUKE_NO_MI = ModRegistry.ITEMS.register("suke_suke_no_mi", 
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA, ModAbilities.SUKE_PUNCH, ModAbilities.SKATTING));

    public static final DeferredItem<AkumaNoMiItem> ZOU_ZOU_NO_MI = ModRegistry.ITEMS.register("zou_zou_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.ZOAN, ModAbilities.ZOU_GUARD_POINT, ModAbilities.ZOU_HEAVY_POINT));

    public static final DeferredItem<AkumaNoMiItem> RYU_RYU_NO_MI_ALLOSAURUS = ModRegistry.ITEMS.register("ryu_ryu_no_mi_allosaurus", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN, ModAbilities.ALLOSAURUS_WALK_POINT, ModAbilities.ALLOSAURUS_HEAVY_POINT, ModAbilities.ANCIENT_BITE, ModAbilities.ANCIENT_TAIL_SPIN));

    public static final DeferredItem<AkumaNoMiItem> RYU_RYU_NO_MI_BRACHIOSAURUS = ModRegistry.ITEMS.register("ryu_ryu_no_mi_brachiosaurus", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN, ModAbilities.BRACHIOSAURUS_GUARD_POINT, ModAbilities.BRACHIOSAURUS_HEAVY_POINT, ModAbilities.BRACHIO_BOMBER, ModAbilities.BRACHIO_GRAB));

    public static final DeferredItem<AkumaNoMiItem> RYU_RYU_NO_MI_PTERANODON = ModRegistry.ITEMS.register("ryu_ryu_no_mi_pteranodon", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN, ModAbilities.PTERANODON_FLY_POINT, ModAbilities.PTERANODON_ASSAULT_POINT, ModAbilities.BEAK_GRAB, ModAbilities.BARIZODON, ModAbilities.TANKYUDON, ModAbilities.TEMPURAUDON));

    public static final DeferredItem<AkumaNoMiItem> ZOU_ZOU_NO_MI_MAMMOTH = ModRegistry.ITEMS.register("zou_zou_no_mi_mammoth", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN, ModAbilities.MAMMOTH_GUARD_POINT, ModAbilities.MAMMOTH_HEAVY_POINT, ModAbilities.ANCIENT_STOMP, ModAbilities.ANCIENT_SWEEP, ModAbilities.ANCIENT_TRUNK_SHOT, ModAbilities.MAMMOTH_TRAMPLE));

    public static final DeferredItem<AkumaNoMiItem> HITO_HITO_NO_MI_DAIBUTSU = ModRegistry.ITEMS.register("hito_hito_no_mi_daibutsu", 
            () -> new AkumaNoMiItem(3, FruitType.MYTHICAL_ZOAN, ModAbilities.HITODAIBUTSU, ModAbilities.PUNCH)); // Placeholder for Impact Blast

    public static final DeferredItem<AkumaNoMiItem> TORI_TORI_NO_MI_PHOENIX = ModRegistry.ITEMS.register("tori_tori_no_mi_phoenix", 
            () -> new AkumaNoMiItem(3, FruitType.MYTHICAL_ZOAN, ModAbilities.PHOENIX_ASSAULT_POINT, ModAbilities.PHOENIX_FLY_POINT, ModAbilities.PHOENIX_GOEN, ModAbilities.SAISEI_NO_HONO, ModAbilities.FUJIAZAMI, ModAbilities.TENSEI_NO_SOEN, ModAbilities.BLUE_BIRD, ModAbilities.FLAMES_OF_REGENERATION));

        public static final DeferredItem<AkumaNoMiItem> AWA_AWA_NO_MI = ModRegistry.ITEMS.register("awa_awa_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> BAKU_BAKU_NO_MI = ModRegistry.ITEMS.register("baku_baku_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> BANE_BANE_NO_MI = ModRegistry.ITEMS.register("bane_bane_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> BARA_BARA_NO_MI = ModRegistry.ITEMS.register("bara_bara_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> BETA_BETA_NO_MI = ModRegistry.ITEMS.register("beta_beta_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> CHIYU_CHIYU_NO_MI = ModRegistry.ITEMS.register("chiyu_chiyu_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> DEKA_DEKA_NO_MI = ModRegistry.ITEMS.register("deka_deka_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> DOA_DOA_NO_MI = ModRegistry.ITEMS.register("doa_doa_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> DOKU_DOKU_NO_MI = ModRegistry.ITEMS.register("doku_doku_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> DORU_DORU_NO_MI = ModRegistry.ITEMS.register("doru_doru_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> GASU_GASU_NO_MI = ModRegistry.ITEMS.register("gasu_gasu_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
    public static final DeferredItem<AkumaNoMiItem> GOE_GOE_NO_MI = ModRegistry.ITEMS.register("goe_goe_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> HISO_HISO_NO_MI = ModRegistry.ITEMS.register("hiso_hiso_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> HORU_HORU_NO_MI = ModRegistry.ITEMS.register("horu_horu_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> JIKI_JIKI_NO_MI = ModRegistry.ITEMS.register("jiki_jiki_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> KACHI_KACHI_NO_MI = ModRegistry.ITEMS.register("kachi_kachi_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> KAME_KAME_NO_MI = ModRegistry.ITEMS.register("kame_kame_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.ZOAN));
    public static final DeferredItem<AkumaNoMiItem> KARU_KARU_NO_MI = ModRegistry.ITEMS.register("karu_karu_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> KILO_KILO_NO_MI = ModRegistry.ITEMS.register("kilo_kilo_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> KIRA_KIRA_NO_MI = ModRegistry.ITEMS.register("kira_kira_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> KOBU_KOBU_NO_MI = ModRegistry.ITEMS.register("kobu_kobu_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> KUKU_KUKU_NO_MI = ModRegistry.ITEMS.register("kuku_kuku_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> MERO_MERO_NO_MI = ModRegistry.ITEMS.register("mero_mero_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> MINI_MINI_NO_MI = ModRegistry.ITEMS.register("mini_mini_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> MOCHI_MOCHI_NO_MI = ModRegistry.ITEMS.register("mochi_mochi_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> MOGU_MOGU_NO_MI = ModRegistry.ITEMS.register("mogu_mogu_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.ZOAN));
    public static final DeferredItem<AkumaNoMiItem> NAGI_NAGI_NO_MI = ModRegistry.ITEMS.register("nagi_nagi_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> NEKO_NEKO_NO_MI_LEOPARD = ModRegistry.ITEMS.register("neko_neko_no_mi_leopard",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN));
    public static final DeferredItem<AkumaNoMiItem> NETSU_NETSU_NO_MI = ModRegistry.ITEMS.register("netsu_netsu_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> NORO_NORO_NO_MI = ModRegistry.ITEMS.register("noro_noro_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> ORI_ORI_NO_MI = ModRegistry.ITEMS.register("ori_ori_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> OTO_OTO_NO_MI = ModRegistry.ITEMS.register("oto_oto_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> PERO_PERO_NO_MI = ModRegistry.ITEMS.register("pero_pero_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> SABI_SABI_NO_MI = ModRegistry.ITEMS.register("sabi_sabi_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> SAI_SAI_NO_MI = ModRegistry.ITEMS.register("sai_sai_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN));
    public static final DeferredItem<AkumaNoMiItem> SARA_SARA_NO_MI_AXOLOTL = ModRegistry.ITEMS.register("sara_sara_no_mi_axolotl",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN));
    public static final DeferredItem<AkumaNoMiItem> SUBE_SUBE_NO_MI = ModRegistry.ITEMS.register("sube_sube_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> SUI_SUI_NO_MI = ModRegistry.ITEMS.register("sui_sui_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> SUPA_SUPA_NO_MI = ModRegistry.ITEMS.register("supa_supa_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> USHI_USHI_NO_MI_BISON = ModRegistry.ITEMS.register("ushi_ushi_no_mi_bison",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN));
    public static final DeferredItem<AkumaNoMiItem> USHI_USHI_NO_MI_GIRAFFE = ModRegistry.ITEMS.register("ushi_ushi_no_mi_giraffe",
            () -> new AkumaNoMiItem(1, FruitType.ZOAN));
    public static final DeferredItem<AkumaNoMiItem> WARA_WARA_NO_MI = ModRegistry.ITEMS.register("wara_wara_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> YOMI_YOMI_NO_MI = ModRegistry.ITEMS.register("yomi_yomi_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> YUKI_YUKI_NO_MI = ModRegistry.ITEMS.register("yuki_yuki_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
    public static final DeferredItem<AkumaNoMiItem> ZUSHI_ZUSHI_NO_MI = ModRegistry.ITEMS.register("zushi_zushi_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));

    public static void init() {}
}
