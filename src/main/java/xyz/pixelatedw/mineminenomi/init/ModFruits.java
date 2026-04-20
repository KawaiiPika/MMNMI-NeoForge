package xyz.pixelatedw.mineminenomi.init;

import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModFruits {
    
    public static final DeferredItem<AkumaNoMiItem> GOMU_GOMU_NO_MI = ModRegistry.ITEMS.register("gomu_gomu_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
            
    public static final DeferredItem<AkumaNoMiItem> MERA_MERA_NO_MI = ModRegistry.ITEMS.register("mera_mera_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> HIE_HIE_NO_MI = ModRegistry.ITEMS.register("hie_hie_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> GORO_GORO_NO_MI = ModRegistry.ITEMS.register("goro_goro_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> HITO_HITO_NO_MI = ModRegistry.ITEMS.register("hito_hito_no_mi", 
            () -> new AkumaNoMiItem(1, FruitType.ZOAN));

    public static final DeferredItem<AkumaNoMiItem> OPE_OPE_NO_MI = ModRegistry.ITEMS.register("ope_ope_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> YAMI_YAMI_NO_MI = ModRegistry.ITEMS.register("yami_yami_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> MAGU_MAGU_NO_MI = ModRegistry.ITEMS.register("magu_magu_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> PIKA_PIKA_NO_MI = ModRegistry.ITEMS.register("pika_pika_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> MOKU_MOKU_NO_MI = ModRegistry.ITEMS.register("moku_moku_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
            
    public static final DeferredItem<AkumaNoMiItem> SUNA_SUNA_NO_MI = ModRegistry.ITEMS.register("suna_suna_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));

    public static final DeferredItem<AkumaNoMiItem> GURA_GURA_NO_MI = ModRegistry.ITEMS.register("gura_gura_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> ITO_ITO_NO_MI = ModRegistry.ITEMS.register("ito_ito_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> NIKYU_NIKYU_NO_MI = ModRegistry.ITEMS.register("nikyu_nikyu_no_mi", 
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> BARI_BARI_NO_MI = ModRegistry.ITEMS.register("bari_bari_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> HANA_HANA_NO_MI = ModRegistry.ITEMS.register("hana_hana_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> HORO_HORO_NO_MI = ModRegistry.ITEMS.register("horo_horo_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> KAGE_KAGE_NO_MI = ModRegistry.ITEMS.register("kage_kage_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> BOMU_BOMU_NO_MI = ModRegistry.ITEMS.register("bomu_bomu_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> SUKE_SUKE_NO_MI = ModRegistry.ITEMS.register("suke_suke_no_mi", 
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));

    public static final DeferredItem<AkumaNoMiItem> ZOU_ZOU_NO_MI = ModRegistry.ITEMS.register("zou_zou_no_mi", 
            () -> new AkumaNoMiItem(2, FruitType.ZOAN));

    public static final DeferredItem<AkumaNoMiItem> RYU_RYU_NO_MI_ALLOSAURUS = ModRegistry.ITEMS.register("ryu_ryu_no_mi_allosaurus", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN));

    public static final DeferredItem<AkumaNoMiItem> RYU_RYU_NO_MI_BRACHIOSAURUS = ModRegistry.ITEMS.register("ryu_ryu_no_mi_brachiosaurus", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN));

    public static final DeferredItem<AkumaNoMiItem> RYU_RYU_NO_MI_PTERANODON = ModRegistry.ITEMS.register("ryu_ryu_no_mi_pteranodon", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN));

    public static final DeferredItem<AkumaNoMiItem> ZOU_ZOU_NO_MI_MAMMOTH = ModRegistry.ITEMS.register("zou_zou_no_mi_mammoth", 
            () -> new AkumaNoMiItem(2, FruitType.ANCIENT_ZOAN));

    public static final DeferredItem<AkumaNoMiItem> HITO_HITO_NO_MI_DAIBUTSU = ModRegistry.ITEMS.register("hito_hito_no_mi_daibutsu", 
            () -> new AkumaNoMiItem(3, FruitType.MYTHICAL_ZOAN)); // Placeholder for Impact Blast

    public static final DeferredItem<AkumaNoMiItem> TORI_TORI_NO_MI_PHOENIX = ModRegistry.ITEMS.register("tori_tori_no_mi_phoenix", 
            () -> new AkumaNoMiItem(3, FruitType.MYTHICAL_ZOAN));

        public static final DeferredItem<AkumaNoMiItem> AWA_AWA_NO_MI = ModRegistry.ITEMS.register("awa_awa_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA, ModAbilities.GOLDEN_HOUR, ModAbilities.RELAX_HOUR, ModAbilities.SOAP_DEFENSE));
    public static final DeferredItem<AkumaNoMiItem> BAKU_BAKU_NO_MI = ModRegistry.ITEMS.register("baku_baku_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> BANE_BANE_NO_MI = ModRegistry.ITEMS.register("bane_bane_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> BARA_BARA_NO_MI = ModRegistry.ITEMS.register("bara_bara_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.BARA_BARA_HO, ModAbilities.BARA_BARA_FESTIVAL));
    public static final DeferredItem<AkumaNoMiItem> BETA_BETA_NO_MI = ModRegistry.ITEMS.register("beta_beta_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> CHIYU_CHIYU_NO_MI = ModRegistry.ITEMS.register("chiyu_chiyu_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> DEKA_DEKA_NO_MI = ModRegistry.ITEMS.register("deka_deka_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> DOA_DOA_NO_MI = ModRegistry.ITEMS.register("doa_doa_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> DOKU_DOKU_NO_MI = ModRegistry.ITEMS.register("doku_doku_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA, ModAbilities.HYDRA, ModAbilities.VENOM_ROAD, ModAbilities.POISON_IMMUNITY));
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
            () -> new AkumaNoMiItem(1, FruitType.ZOAN, ModAbilities.KAME_GUARD_POINT, ModAbilities.KAME_WALK_POINT));
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
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.MERO_MERO_MELLOW, ModAbilities.PISTOL_KISS));
    public static final DeferredItem<AkumaNoMiItem> MINI_MINI_NO_MI = ModRegistry.ITEMS.register("mini_mini_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> MOCHI_MOCHI_NO_MI = ModRegistry.ITEMS.register("mochi_mochi_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> MOGU_MOGU_NO_MI = ModRegistry.ITEMS.register("mogu_mogu_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.ZOAN, ModAbilities.MOGU_HEAVY_POINT, ModAbilities.MOGURA_BANANA, ModAbilities.MOGURA_TONPO));
    public static final DeferredItem<AkumaNoMiItem> NAGI_NAGI_NO_MI = ModRegistry.ITEMS.register("nagi_nagi_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> NEKO_NEKO_NO_MI_LEOPARD = ModRegistry.ITEMS.register("neko_neko_no_mi_leopard",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN, ModAbilities.LEOPARD_WALK_POINT, ModAbilities.LEOPARD_HEAVY_POINT, ModAbilities.FEROCIOUS_LEAP, ModAbilities.CLAW_STRIKE, ModAbilities.NEKO_NIGHT_VISION));
    public static final DeferredItem<AkumaNoMiItem> NETSU_NETSU_NO_MI = ModRegistry.ITEMS.register("netsu_netsu_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> NORO_NORO_NO_MI = ModRegistry.ITEMS.register("noro_noro_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA, ModAbilities.NORO_NORO_BEAM));
    public static final DeferredItem<AkumaNoMiItem> ORI_ORI_NO_MI = ModRegistry.ITEMS.register("ori_ori_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> OTO_OTO_NO_MI = ModRegistry.ITEMS.register("oto_oto_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> PERO_PERO_NO_MI = ModRegistry.ITEMS.register("pero_pero_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA, ModAbilities.CANDY_WAVE, ModAbilities.CANDY_ARMOR));
    public static final DeferredItem<AkumaNoMiItem> SABI_SABI_NO_MI = ModRegistry.ITEMS.register("sabi_sabi_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> SAI_SAI_NO_MI = ModRegistry.ITEMS.register("sai_sai_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN, ModAbilities.SAI_WALK_POINT, ModAbilities.SAI_HEAVY_POINT, ModAbilities.RHINO_SMASH, ModAbilities.HORN_DASH));
    public static final DeferredItem<AkumaNoMiItem> SARA_SARA_NO_MI_AXOLOTL = ModRegistry.ITEMS.register("sara_sara_no_mi_axolotl",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN, ModAbilities.AXOLOTL_HEAVY_POINT, ModAbilities.AXOLOTL_WALK_POINT, ModAbilities.POISON_SPIT, ModAbilities.AXOLOTL_HEAL, ModAbilities.PLAY_DEAD, ModAbilities.HEART_REGEN));
    public static final DeferredItem<AkumaNoMiItem> SUBE_SUBE_NO_MI = ModRegistry.ITEMS.register("sube_sube_no_mi",
            () -> new AkumaNoMiItem(1, FruitType.PARAMECIA, ModAbilities.SUBE_SUBE_SPUR, ModAbilities.SUBE_SUBE_DEFLECT));
    public static final DeferredItem<AkumaNoMiItem> SUI_SUI_NO_MI = ModRegistry.ITEMS.register("sui_sui_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> SUPA_SUPA_NO_MI = ModRegistry.ITEMS.register("supa_supa_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> USHI_USHI_NO_MI_BISON = ModRegistry.ITEMS.register("ushi_ushi_no_mi_bison",
            () -> new AkumaNoMiItem(2, FruitType.ZOAN, ModAbilities.BISON_HEAVY_POINT, ModAbilities.BISON_WALK_POINT, ModAbilities.FIDDLE_BANFF, ModAbilities.KOKUTEI_CROSS, ModAbilities.BISON_SMASH));
    public static final DeferredItem<AkumaNoMiItem> USHI_USHI_NO_MI_GIRAFFE = ModRegistry.ITEMS.register("ushi_ushi_no_mi_giraffe",
            () -> new AkumaNoMiItem(1, FruitType.ZOAN, ModAbilities.GIRAFFE_HEAVY_POINT, ModAbilities.GIRAFFE_WALK_POINT, ModAbilities.BIGAN));
    public static final DeferredItem<AkumaNoMiItem> WARA_WARA_NO_MI = ModRegistry.ITEMS.register("wara_wara_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA, ModAbilities.LIFE_MINUS));
    public static final DeferredItem<AkumaNoMiItem> YOMI_YOMI_NO_MI = ModRegistry.ITEMS.register("yomi_yomi_no_mi",
            () -> new AkumaNoMiItem(2, FruitType.PARAMECIA));
    public static final DeferredItem<AkumaNoMiItem> YUKI_YUKI_NO_MI = ModRegistry.ITEMS.register("yuki_yuki_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.LOGIA));
    public static final DeferredItem<AkumaNoMiItem> ZUSHI_ZUSHI_NO_MI = ModRegistry.ITEMS.register("zushi_zushi_no_mi",
            () -> new AkumaNoMiItem(3, FruitType.PARAMECIA));

    public static void init() {}
}
