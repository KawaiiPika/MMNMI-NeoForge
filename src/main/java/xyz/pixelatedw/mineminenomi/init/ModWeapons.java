package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.items.weapons.*;

public class ModWeapons {
    
    public static final DeferredItem<ModSwordItem> KIKOKU = ModRegistry.ITEMS.register("kikoku", 
            () -> new ModSwordItem(ModTiers.KIKOKU, 7));
            
    public static final DeferredItem<ModSwordItem> SANDAI_KITETSU = ModRegistry.ITEMS.register("sandai_kitetsu", 
            () -> new ModSwordItem(ModTiers.SANDAI_KITETSU, 8, -2.0F));
            
    public static final DeferredItem<ModSwordItem> WADO_ICHIMONJI = ModRegistry.ITEMS.register("wado_ichimonji", 
            () -> new ModSwordItem(ModTiers.WADO_ICHIMONJI, 8, -2.0F));
            
    public static final DeferredItem<ModSwordItem> SHUSUI = ModRegistry.ITEMS.register("shusui", 
            () -> new ModSwordItem(ModTiers.SHUSUI, 9, -2.0F));
            
    public static final DeferredItem<ModSwordItem> YORU = ModRegistry.ITEMS.register("yoru", 
            () -> new ModSwordItem(ModTiers.YORU, 11, -2.0F));

    public static final DeferredItem<ScissorsItem> SCISSORS = ModRegistry.ITEMS.register("scissors", 
            ScissorsItem::new);

    public static final DeferredItem<net.minecraft.world.item.TieredItem> UMBRELLA = ModRegistry.ITEMS.register("umbrella", 
            () -> new net.minecraft.world.item.TieredItem(ModTiers.UMBRELLA, new net.minecraft.world.item.Item.Properties()));

    public static final DeferredItem<ModSwordItem> KIRIBACHI = ModRegistry.ITEMS.register("kiribachi", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.KIRIBACHI, 6).reach(0.25F)));

    public static final DeferredItem<ModSwordItem> MURAKUMOGIRI = ModRegistry.ITEMS.register("murakumogiri", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.MURAKUMOGIRI, 12).speed(-2.9F).reach(1.5F)));

    public static final DeferredItem<ModSwordItem> HOOK = ModRegistry.ITEMS.register("hook", 
            () -> new ModSwordItem(ModTiers.HOOK, 5).addEffect(MobEffects.POISON, 300));

    public static final DeferredItem<ModSwordItem> NONOSAMA_STAFF = ModRegistry.ITEMS.register("nonosama_staff", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.NONOSAMA_STAFF, 6).speed(-2.6F).reach(1.0F)));

    public static final DeferredItem<ModSpearItem> NONOSAMA_TRIDENT = ModRegistry.ITEMS.register("nonosama_trident", 
            () -> new ModSpearItem(new ModSwordItem.ModsBuilder(ModTiers.NONOSAMA_TRIDENT, 9).speed(-2.6F).reach(1.0F)));

    public static final DeferredItem<ModSwordItem> HAMMER_5T = ModRegistry.ITEMS.register("hammer_5t", 
            () -> new ModSwordItem(ModTiers.HAMMER_5T, 1));

    public static final DeferredItem<ModSwordItem> ACES_KNIFE = ModRegistry.ITEMS.register("aces_knife", 
            () -> new ModSwordItem(ModTiers.ACES_KNIFE, 4, -1.1F));

    public static final DeferredItem<ModSwordItem> MIHAWKS_KNIFE = ModRegistry.ITEMS.register("mihawks_knife", 
            () -> new ModSwordItem(ModTiers.MIHAWKS_KNIFE, 4, -1.1F));

    public static final DeferredItem<ModSwordItem> NIDAI_KITETSU = ModRegistry.ITEMS.register("nidai_kitetsu", 
            () -> new ModSwordItem(ModTiers.NIDAI_KITETSU, 8, -2.0F));

    public static final DeferredItem<ModSwordItem> ENMA = ModRegistry.ITEMS.register("enma", 
            () -> new ModSwordItem(ModTiers.ENMA, 9, -2.0F));

    public static final DeferredItem<ModSwordItem> AME_NO_HABAKIRI = ModRegistry.ITEMS.register("ame_no_habakiri", 
            () -> new ModSwordItem(ModTiers.AME_NO_HABAKIRI, 9, -2.0F));

    public static final DeferredItem<ModSwordItem> SOUL_SOLID = ModRegistry.ITEMS.register("soul_solid", 
            () -> new ModSwordItem(ModTiers.SOUL_SOLID, 6, -1.4F));

    public static final DeferredItem<ModSwordItem> DURANDAL = ModRegistry.ITEMS.register("durandal", 
            () -> new ModSwordItem(ModTiers.DURANDAL, 6, -1.5F));

    public static final DeferredItem<ModSwordItem> DAISENSO = ModRegistry.ITEMS.register("daisenso", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.DAISENSO, 5).speed(-2.6F).reach(1.0F)));

    public static final DeferredItem<ModSwordItem> ACE = ModRegistry.ITEMS.register("ace", 
            () -> new ModSwordItem(ModTiers.ACE, 11, -1.7F));

    public static final DeferredItem<ModSpearItem> MOGURA = ModRegistry.ITEMS.register("mogura", 
            () -> new ModSpearItem(new ModSwordItem.ModsBuilder(ModTiers.MOGURA, 10).speed(-2.6F).reach(1.0F)));

    public static final DeferredItem<ModSwordItem> DALTONS_SPADE = ModRegistry.ITEMS.register("daltons_spade", 
            () -> new ModSwordItem(ModTiers.DALTONS_SPADE, 6, -2.0F));

    public static final DeferredItem<ModSwordItem> SAMEKIRI_BOCHO = ModRegistry.ITEMS.register("samekiri_bocho", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.SAMEKIRI_BOCHO, 7).speed(-2.0F).reach(0.25F)));

    public static final DeferredItem<ModSwordItem> CAT_CLAWS = ModRegistry.ITEMS.register("cat_claws", 
            () -> new ModSwordItem(ModTiers.CAT_CLAWS, 7, -1.3F));

    public static final DeferredItem<ModSwordItem> HASSAIKAI = ModRegistry.ITEMS.register("hassaikai", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.HASSAIKAI, 12).speed(-3.2F).reach(0.25F)));

    public static final DeferredItem<ModSwordItem> GRYPHON = ModRegistry.ITEMS.register("gryphon", 
            () -> new ModSwordItem(ModTiers.GRYPHON, 10, -2.0F));

    public static final DeferredItem<ModSwordItem> AXE_HAND = ModRegistry.ITEMS.register("axe_hand", 
            () -> new ModSwordItem(ModTiers.AXE_HAND, 5, -2.2F));

    public static final DeferredItem<ModSwordItem> PIPE = ModRegistry.ITEMS.register("pipe", 
            () -> new ModSwordItem(ModTiers.PIPE, 5));

    public static final DeferredItem<ModSwordItem> TONFA = ModRegistry.ITEMS.register("tonfa", 
            () -> new ModSwordItem(ModTiers.TONFA, 5));

    public static final DeferredItem<ModSwordItem> JITTE = ModRegistry.ITEMS.register("jitte", 
            () -> new ModSwordItem(ModTiers.JITTE, 6).setDyeable());

    public static final DeferredItem<ModSwordItem> KATANA = ModRegistry.ITEMS.register("katana", 
            () -> new ModSwordItem(ModTiers.KATANA, 5, -2.2F).setDyeable());

    public static final DeferredItem<ModSwordItem> CUTLASS = ModRegistry.ITEMS.register("cutlass", 
            () -> new ModSwordItem(ModTiers.CUTLASS, 5, -2.4F).setDyeable());

    public static final DeferredItem<ModSwordItem> BROADSWORD = ModRegistry.ITEMS.register("broadsword", 
            () -> new ModSwordItem(ModTiers.BROADSWORD, 6, -2.5F).setDyeable());

    public static final DeferredItem<ModSwordItem> BISENTO = ModRegistry.ITEMS.register("bisento", 
            () -> new ModSwordItem(ModTiers.BISENTO, 6, -2.7F).setDyeable());

    public static final DeferredItem<ModSwordItem> DAGGER = ModRegistry.ITEMS.register("dagger", 
            () -> new ModSwordItem(ModTiers.DAGGER, 4, -1.3F).setDyeable());

    public static final DeferredItem<ModSwordItem> AXE = ModRegistry.ITEMS.register("axe", 
            () -> new ModSwordItem(ModTiers.AXE, 6, -2.7F).setDyeable());

    public static final DeferredItem<ModSpearItem> SPEAR = ModRegistry.ITEMS.register("spear", 
            () -> new ModSpearItem(new ModSwordItem.ModsBuilder(ModTiers.SPEAR, 5).speed(-2.5F).reach(1.0F)).setDyeable());

    public static final DeferredItem<ModSwordItem> CLEAVER = ModRegistry.ITEMS.register("cleaver", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.CLEAVER, 6).speed(-2.5F).reach(0.25F)).setDyeable());

    public static final DeferredItem<ModSwordItem> MACE = ModRegistry.ITEMS.register("mace", 
            () -> new ModSwordItem(new ModSwordItem.ModsBuilder(ModTiers.MACE, 8).speed(-3.2F).reach(0.25F)).setDyeable());

    public static final DeferredItem<ChakramItem> CHAKRAM = ModRegistry.ITEMS.register("chakram", 
            ChakramItem::new);

    public static final DeferredItem<ClimaTactItem> CLIMA_TACT = ModRegistry.ITEMS.register("clima_tact", 
            () -> new ClimaTactItem(ModTiers.CLIMA_TACT, 1));

    public static final DeferredItem<ClimaTactItem> PERFECT_CLIMA_TACT = ModRegistry.ITEMS.register("perfect_clima_tact", 
            () -> new ClimaTactItem(ModTiers.PERFECT_CLIMA_TACT, 2));

    public static final DeferredItem<ClimaTactItem> SORCERY_CLIMA_TACT = ModRegistry.ITEMS.register("sorcery_clima_tact", 
            () -> new ClimaTactItem(ModTiers.SORCERY_CLIMA_TACT, 4));

    public static final DeferredItem<ModGunItem> FLINTLOCK = ModRegistry.ITEMS.register("flintlock", 
            () -> new ModGunItem(200).setShotCooldown(15).setReloadCooldown(40).setInaccuracy(1.5F).setBulletSpeed(3.0F).setDamageMultiplier(1.0F).setGunpowderLimit(5));

    public static final DeferredItem<ModGunItem> SENRIKU = ModRegistry.ITEMS.register("senriku", 
            () -> new ModGunItem(800).setShotCooldown(20).setReloadCooldown(60).setInaccuracy(0.0F).setBulletSpeed(4.5F).setDamageMultiplier(2.5F).setGunpowderLimit(4));

    public static final DeferredItem<ModGunItem> BAZOOKA = ModRegistry.ITEMS.register("bazooka", 
            () -> new ModGunItem(800, ModGunItem.BAZOOKA_AMMO).setShotCooldown(60).setInaccuracy(0.5F).setBulletSpeed(3.0F).setDamageMultiplier(2.0F).setGunpowderLimit(1));

    public static final DeferredItem<ModGunItem> WALKER = ModRegistry.ITEMS.register("walker", 
            () -> new ModGunItem(2500).setShotCooldown(10).setReloadCooldown(30).setInaccuracy(0.5F).setBulletSpeed(4.0F).setDamageMultiplier(2.0F).setGunpowderLimit(7));

    public static final DeferredItem<KujaBowItem> KUJA_BOW = ModRegistry.ITEMS.register("kuja_bow", 
            KujaBowItem::new);

    public static final DeferredItem<PopGreenBowItem> KABUTO = ModRegistry.ITEMS.register("kabuto", 
            () -> new PopGreenBowItem(400));

    public static final DeferredItem<PopGreenBowItem> BLACK_KABUTO = ModRegistry.ITEMS.register("kuro_kabuto", 
            () -> new PopGreenBowItem(800));

    public static final DeferredItem<PopGreenBowItem> GINGA_PACHINKO = ModRegistry.ITEMS.register("ginga_pachinko", 
            () -> new PopGreenBowItem(200));

    public static final DeferredItem<AbilitySwordItem> ICE_SABER = ModRegistry.ITEMS.register("ice_saber", 
            () -> (AbilitySwordItem) new AbilitySwordItem(ModTiers.ICE_SABER, ModAbilities.ICE_SABER, 12, -1.8F).addEffect(ModEffects.FROSTBITE, 40, 1));

    public static final DeferredItem<AbilitySwordItem> AMA_NO_MURAKUMO = ModRegistry.ITEMS.register("ama_no_murakumo", 
            () -> new AbilitySwordItem(ModTiers.AMA_NO_MURAKUMO, () -> null, 14, 0.0F));

    public static final DeferredItem<AbilitySwordItem> NORO_NORO_BEAM_SWORD = ModRegistry.ITEMS.register("noro_noro_beam_sword", 
            () -> (AbilitySwordItem) new AbilitySwordItem(ModTiers.NORO_NORO_BEAM_SWORD, () -> null, 7).addEffect(ModEffects.MOVEMENT_BLOCKED, 40, 1));

    public static final DeferredItem<AbilitySwordItem> DORU_DORU_ARTS_KEN = ModRegistry.ITEMS.register("doru_doru_arts_ken", 
            () -> (AbilitySwordItem) new AbilitySwordItem(ModTiers.DORU_DORU_ARTS_KEN, () -> null, 7).setDyeable());

    public static final DeferredItem<DoruPickaxeItem> DORU_PICKAXE = ModRegistry.ITEMS.register("doru_doru_arts_pickaxe", 
            () -> new DoruPickaxeItem(ModTiers.DORU_DORU_ARTS_KEN, 1, -2.8F));

    public static final DeferredItem<BlueSwordItem> BLUE_SWORD = ModRegistry.ITEMS.register("blue_sword", 
            BlueSwordItem::new);

    public static final DeferredItem<AbilitySwordItem> TABIRA_YUKI = ModRegistry.ITEMS.register("tabira_yuki", 
            () -> (AbilitySwordItem) new AbilitySwordItem(ModTiers.TABIRA_YUKI, () -> null, 9, -1.9F).addEffect(ModEffects.FROSTBITE, 20, 1));

    public static final DeferredItem<WarabideSwordItem> WARABIDE_SWORD = ModRegistry.ITEMS.register("warabide_sword", 
            WarabideSwordItem::new);

    public static final DeferredItem<AbilitySwordItem> BARRIERBILITY_BAT = ModRegistry.ITEMS.register("barrierbility_bat", 
            () -> new AbilitySwordItem(ModTiers.BARRIERBILITY_BAT, ModAbilities.BARRIERBILITY_BAT, 7));

    public static final DeferredItem<AbilitySwordItem> GAMMA_KNIFE = ModRegistry.ITEMS.register("gamma_knife", 
            () -> new AbilitySwordItem(ModTiers.GAMMA_KNIFE, () -> null, 1, 0.0F));

    public static void init() {}
}
