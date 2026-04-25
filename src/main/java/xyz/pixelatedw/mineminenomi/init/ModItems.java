package xyz.pixelatedw.mineminenomi.init;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.blocks.DialBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.blocks.DialBlock;

import net.minecraft.world.item.Item;
import java.util.function.Supplier;
import xyz.pixelatedw.mineminenomi.items.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class ModItems {
    // ── Utility / Misc ────────────────────────────────────────────────────────
    public static final Supplier<Item> CHARACTER_CREATOR   = ModRegistry.ITEMS.register("character_creator",   CharacterCreatorItem::new);
    public static final Supplier<Item> KAIROSEKI           = ModRegistry.ITEMS.register("kairoseki",           () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DENSE_KAIROSEKI     = ModRegistry.ITEMS.register("dense_kairoseki",     () -> new Item(new Item.Properties()));
    public static final Supplier<Item> KEY                  = ModRegistry.ITEMS.register("key",                  () -> new Item(new Item.Properties()));

    // ── Currency Pouches ──────────────────────────────────────────────────────
    public static final Supplier<Item> BELLY_POUCH          = ModRegistry.ITEMS.register("belly_pouch",          BellyPouchItem::new);
    public static final Supplier<Item> EXTOL_POUCH          = ModRegistry.ITEMS.register("extol_pouch",          ExtolPouchItem::new);

    // ── Navigation / Communication ────────────────────────────────────────────
    public static final Supplier<Item> VIVRE_CARD           = ModRegistry.ITEMS.register("vivre_card",           VivreCardItem::new);
    public static final Supplier<Item> GOLD_DEN_DEN_MUSHI   = ModRegistry.ITEMS.register("gold_den_den_mushi",   GoldDenDenMushiItem::new);
    public static final Supplier<Item> WANTED_POSTER        = ModRegistry.ITEMS.register("wanted_poster",        WantedPosterItem::new);
    public static final Supplier<Item> WANTED_POSTER_PACKAGE= ModRegistry.ITEMS.register("wanted_poster_package",WantedPosterPackageItem::new);
    public static final Supplier<Item> CHALLENGE_POSTER     = ModRegistry.ITEMS.register("challenge_poster",     ChallengePosterItem::new);
    public static final Supplier<Item> FLAG                  = ModRegistry.ITEMS.register("flag",                  FlagItem::new);

    // ── Soulbound ─────────────────────────────────────────────────────────────
    public static final Supplier<Item> STRAW_DOLL           = ModRegistry.ITEMS.register("straw_doll",           StrawDollItem::new);
    public static final Supplier<Item> HEART                 = ModRegistry.ITEMS.register("heart",                HeartItem::new);

    // ── Food & Drink ──────────────────────────────────────────────────────────
    public static final Supplier<Item> DANDELION            = ModRegistry.ITEMS.register("dandelion",            DandelionItem::new);
    public static final Supplier<Item> WATERING_CAN         = ModRegistry.ITEMS.register("watering_can",         WateringCanItem::new);
    public static final Supplier<Item> SAKE_CUP             = ModRegistry.ITEMS.register("sake_cup",             SakeCupItem::new);
    public static final Supplier<Item> SAKE_BOTTLE          = ModRegistry.ITEMS.register("sake_bottle",          SakeBottleItem::new);
    public static final Supplier<Item> BOTTLE_OF_RUM        = ModRegistry.ITEMS.register("bottle_of_rum",        BottleOfRumItem::new);
    public static final Supplier<Item> COLA                  = ModRegistry.ITEMS.register("cola",                  ColaItem::new);
    public static final Supplier<Item> EMPTY_COLA            = ModRegistry.ITEMS.register("empty_cola",            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ULTRA_COLA           = ModRegistry.ITEMS.register("ultra_cola",           UltraColaItem::new);
    public static final Supplier<Item> EMPTY_ULTRA_COLA     = ModRegistry.ITEMS.register("empty_ultra_cola",     () -> new Item(new Item.Properties()));
    public static final Supplier<Item> TANGERINE            = ModRegistry.ITEMS.register("tangerine",            TangerineItem::new);
    public static final Supplier<Item> SEA_KING_MEAT        = ModRegistry.ITEMS.register("sea_king_meat",        SeaKingMeatItem::new);
    public static final Supplier<Item> COOKED_SEA_KING_MEAT = ModRegistry.ITEMS.register("cooked_sea_king_meat", CookedSeaKingMeatItem::new);
    public static final Supplier<Item> SHADOW               = ModRegistry.ITEMS.register("shadow",               ShadowItem::new);
    public static final Supplier<Item> CIGAR                = ModRegistry.ITEMS.register("cigar",                () -> new CigarItem(20));

    // ── Tools / Weapons ───────────────────────────────────────────────────────
    public static final Supplier<Item> CANNON               = ModRegistry.ITEMS.register("cannon",               CannonItem::new);
    public static final Supplier<Item> STRIKER              = ModRegistry.ITEMS.register("striker",              StrikerItem::new);
    public static final Supplier<Item> UNICYCLE             = ModRegistry.ITEMS.register("unicycle",             UnicycleItem::new);
    public static final Supplier<Item> NET                  = ModRegistry.ITEMS.register("net",                  NetItem::new);
    public static final Supplier<Item> BUBBLY_CORAL         = ModRegistry.ITEMS.register("bubbly_coral",         BubblyCoralItem::new);

    // ── Handcuffs ─────────────────────────────────────────────────────────────
    public static final Supplier<Item> NORMAL_HANDCUFFS     = ModRegistry.ITEMS.register("normal_handcuffs",     HandcuffsItem::new);
    public static final Supplier<Item> KAIROSEKI_HANDCUFFS  = ModRegistry.ITEMS.register("kairoseki_handcuffs",  HandcuffsItem::new);
    public static final Supplier<Item> EXPLOSIVE_HANDCUFFS  = ModRegistry.ITEMS.register("explosive_handcuffs",  HandcuffsItem::new);

    // ── Devil Fruit System ────────────────────────────────────────────────────
    public static final Supplier<Item> DEVIL_FRUIT_ENCYCLOPEDIA = ModRegistry.ITEMS.register("devil_fruit_encyclopedia", DFEncyclopediaItem::new);
    public static final Supplier<Item> WOODEN_DEVIL_FRUIT_BOX   = ModRegistry.ITEMS.register("wooden_devil_fruit_box",   () -> new AkumaNoMiBoxItem(AkumaNoMiBoxItem.TIER_1_FRUITS));
    public static final Supplier<Item> IRON_DEVIL_FRUIT_BOX     = ModRegistry.ITEMS.register("iron_devil_fruit_box",     () -> new AkumaNoMiBoxItem(AkumaNoMiBoxItem.TIER_2_FRUITS));
    public static final Supplier<Item> GOLDEN_DEVIL_FRUIT_BOX   = ModRegistry.ITEMS.register("golden_devil_fruit_box",   () -> new AkumaNoMiBoxItem(AkumaNoMiBoxItem.TIER_3_FRUITS));


    // Dials


    // Dials


    // Dials
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> BREATH_DIAL = ModRegistry.ITEMS.register("breath_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.BreathDialItem(ModBlocks.BREATH_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> EISEN_DIAL = ModRegistry.ITEMS.register("eisen_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.EisenDialItem(ModBlocks.EISEN_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> FLAME_DIAL = ModRegistry.ITEMS.register("flame_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.FlameDialItem(ModBlocks.FLAME_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> IMPACT_DIAL = ModRegistry.ITEMS.register("impact_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.ImpactDialItem(ModBlocks.IMPACT_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> REJECT_DIAL = ModRegistry.ITEMS.register("reject_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.RejectDialItem(ModBlocks.REJECT_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> AXE_DIAL = ModRegistry.ITEMS.register("axe_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.AxeDialItem(ModBlocks.AXE_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> MILKY_DIAL = ModRegistry.ITEMS.register("milky_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.MilkyDialItem(ModBlocks.MILKY_DIAL.get()));
    public static final DeferredHolder<net.minecraft.world.item.Item, net.minecraft.world.item.Item> FLASH_DIAL = ModRegistry.ITEMS.register("flash_dial", () -> new xyz.pixelatedw.mineminenomi.items.dials.FlashDialItem(ModBlocks.FLASH_DIAL.get()));

    public static void init() {
        // Triggers class loading / DeferredRegister entries
    }
}
