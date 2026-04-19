package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.WyTierBuilder;

public class ModTiers {
   public static final Tier SCISSORS = WyTierBuilder.aboveStone(500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("scissors"));
   public static final Tier KIKOKU = WyTierBuilder.aboveDiamond(1000, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("kikoku"));
   public static final Tier KIRIBACHI = WyTierBuilder.aboveIron(400, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("kiribachi"));
   public static final Tier YORU = WyTierBuilder.aboveNetherite(2000, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("yoru"));
   public static final Tier MURAKUMOGIRI = WyTierBuilder.aboveNetherite(2500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("murakumogiri"));
   public static final Tier HOOK = WyTierBuilder.aboveStone(250, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("hook"));
   public static final Tier UMBRELLA = WyTierBuilder.aboveStone(200, () -> Ingredient.m_204132_(ItemTags.f_13167_), id("umbrella"));
   public static final Tier NONOSAMA_STAFF = WyTierBuilder.aboveIron(400, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42417_}), id("nonosama_staff"));
   public static final Tier NONOSAMA_TRIDENT = WyTierBuilder.aboveIron(400, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42417_}), id("nonosama_trident"));
   public static final Tier HAMMER_5T = WyTierBuilder.aboveNothing(250, () -> Ingredient.f_43901_, id("hammer_5t"));
   public static final Tier ACES_KNIFE = WyTierBuilder.aboveStone(200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("aces_knife"));
   public static final Tier MIHAWKS_KNIFE = WyTierBuilder.aboveStone(200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("mihawks_knife"));
   public static final Tier SANDAI_KITETSU = WyTierBuilder.aboveDiamond(1200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("sandai_kitetsu"));
   public static final Tier WADO_ICHIMONJI = WyTierBuilder.aboveDiamond(1200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("wado_ichimonji"));
   public static final Tier NIDAI_KITETSU = WyTierBuilder.aboveDiamond(1200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("nidai_kitetsu"));
   public static final Tier SHUSUI = WyTierBuilder.aboveDiamond(1500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("shusui"));
   public static final Tier ENMA = WyTierBuilder.aboveDiamond(1500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("enma"));
   public static final Tier AME_NO_HABAKIRI = WyTierBuilder.aboveDiamond(1500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("ame_no_habakiri"));
   public static final Tier SOUL_SOLID = WyTierBuilder.aboveIron(500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("soul_solid"));
   public static final Tier DURANDAL = WyTierBuilder.aboveIron(400, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("durandal"));
   public static final Tier DAISENSO = WyTierBuilder.aboveIron(600, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("daisenso"));
   public static final Tier ACE = WyTierBuilder.aboveNetherite(2500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("ace"));
   public static final Tier MOGURA = WyTierBuilder.aboveDiamond(1700, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("mogura"));
   public static final Tier DALTONS_SPADE = WyTierBuilder.aboveIron(800, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("daltons_spade"));
   public static final Tier SAMEKIRI_BOCHO = WyTierBuilder.aboveIron(900, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("samekiri_bocho"));
   public static final Tier CAT_CLAWS = WyTierBuilder.aboveStone(750, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("cat_claws"));
   public static final Tier HASSAIKAI = WyTierBuilder.aboveDiamond(3000, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("hassaikai"));
   public static final Tier GRYPHON = WyTierBuilder.aboveDiamond(2200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("gryphon"));
   public static final Tier AXE_HAND = WyTierBuilder.aboveStone(500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("axe_hand"));
   public static final Tier PIPE = WyTierBuilder.aboveStone(200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("pipe"));
   public static final Tier TONFA = WyTierBuilder.aboveStone(500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("tonfa"));
   public static final Tier JITTE = WyTierBuilder.aboveStone(400, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ModItems.KAIROSEKI.get()}), id("jitte"));
   public static final Tier KATANA = WyTierBuilder.aboveStone(250, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("katana"));
   public static final Tier CUTLASS = WyTierBuilder.aboveStone(300, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("cutlass"));
   public static final Tier BROADSWORD = WyTierBuilder.aboveStone(250, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("broadsword"));
   public static final Tier BISENTO = WyTierBuilder.aboveStone(250, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("bisento"));
   public static final Tier DAGGER = WyTierBuilder.aboveStone(200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("dagger"));
   public static final Tier AXE = WyTierBuilder.aboveStone(300, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("axe"));
   public static final Tier SPEAR = WyTierBuilder.aboveStone(200, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("spear"));
   public static final Tier CLEAVER = WyTierBuilder.aboveStone(250, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("cleaver"));
   public static final Tier MACE = WyTierBuilder.aboveStone(600, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("mace"));
   public static final Tier CHAKRAM = WyTierBuilder.aboveStone(300, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_}), id("chakram"));
   public static final Tier CLIMA_TACT = WyTierBuilder.aboveStone(300, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42534_}), id("clima_tact"));
   public static final Tier PERFECT_CLIMA_TACT = WyTierBuilder.aboveStone(500, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42534_}), id("perfect_clima_tact"));
   public static final Tier SORCERY_CLIMA_TACT = WyTierBuilder.aboveIron(800, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42534_, Items.f_42417_}), id("sorcery_clima_tact"));
   public static final Tier ICE_SABER = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("ice_saber"));
   public static final Tier AMA_NO_MURAKUMO = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("ama_no_murakumo"));
   public static final Tier NORO_NORO_BEAM_SWORD = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("noro_noro_beam_sword"));
   public static final Tier DORU_DORU_ARTS_KEN = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("doru_doru_arts_ken"));
   public static final Tier BLUE_SWORD = WyTierBuilder.aboveIron(1000, () -> Ingredient.f_43901_, id("blue_sword"));
   public static final Tier TABIRA_YUKI = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("tabira_yuki"));
   public static final Tier WARABIDE_SWORD = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("warabide_sword"));
   public static final Tier BARRIERBILITY_BAT = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("barrierbility_bat"));
   public static final Tier GAMMA_KNIFE = WyTierBuilder.aboveIron(2000, () -> Ingredient.f_43901_, id("gamma_knife"));

   private static ResourceLocation id(String id) {
      return ResourceLocation.fromNamespaceAndPath("mineminenomi", id);
   }
}
