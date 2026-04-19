package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ModItemTagsProvider extends ItemTagsProvider {
   public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, blockTagProvider, "mineminenomi", existingFileHelper);
   }

   protected void m_6577_(HolderLookup.Provider provider) {
      this.m_206421_(ModTags.Blocks.MANGROVE_LOGS, ModTags.Items.MANGROVE_LOGS);
      this.m_206424_(ModTags.Items.BANNED_ITEMS_CHALLANGES).m_255179_(new Item[]{(Item)ModItems.TIER_1_BOX.get(), (Item)ModItems.TIER_2_BOX.get(), (Item)ModItems.TIER_3_BOX.get(), (Item)ModItems.CHALLENGE_POSTER.get()});
      this.m_206424_(ModTags.Items.KAIROSEKI).m_255179_(new Item[]{(Item)ModItems.KAIROSEKI.get(), (Item)ModItems.KAIROSEKI_BULLET.get(), (Item)ModItems.KAIROSEKI_HANDCUFFS.get(), (Item)ModItems.KAIROSEKI_NET.get(), (Item)ModItems.DENSE_KAIROSEKI.get()});
      this.m_206421_(ModTags.Blocks.KAIROSEKI, ModTags.Items.KAIROSEKI);
      Item[] swords = new Item[]{(Item)ModWeapons.SCISSORS.get(), (Item)ModWeapons.KATANA.get(), (Item)ModWeapons.BROADSWORD.get(), (Item)ModWeapons.SPEAR.get(), (Item)ModWeapons.CLEAVER.get(), (Item)ModWeapons.DAGGER.get(), (Item)ModWeapons.BISENTO.get(), (Item)ModWeapons.AXE.get(), (Item)ModWeapons.CUTLASS.get(), (Item)ModWeapons.SAMEKIRI_BOCHO.get(), (Item)ModWeapons.CAT_CLAWS.get(), (Item)ModWeapons.GRYPHON.get(), (Item)ModWeapons.DAISENSO.get(), (Item)ModWeapons.ACE.get(), (Item)ModWeapons.MOGURA.get(), (Item)ModWeapons.DALTONS_SPADE.get(), (Item)ModWeapons.MIHAWKS_KNIFE.get(), (Item)ModWeapons.SANDAI_KITETSU.get(), (Item)ModWeapons.WADO_ICHIMONJI.get(), (Item)ModWeapons.NIDAI_KITETSU.get(), (Item)ModWeapons.SHUSUI.get(), (Item)ModWeapons.ENMA.get(), (Item)ModWeapons.AME_NO_HABAKIRI.get(), (Item)ModWeapons.SOUL_SOLID.get(), (Item)ModWeapons.DURANDAL.get(), (Item)ModWeapons.KIKOKU.get(), (Item)ModWeapons.KIRIBACHI.get(), (Item)ModWeapons.YORU.get(), (Item)ModWeapons.MURAKUMOGIRI.get(), (Item)ModWeapons.HOOK.get(), (Item)ModWeapons.CHAKRAM.get(), (Item)ModWeapons.AXE_HAND.get()};
      Item[] blunts = new Item[]{(Item)ModWeapons.PIPE.get(), (Item)ModWeapons.TONFA.get(), (Item)ModWeapons.MACE.get(), (Item)ModWeapons.HASSAIKAI.get()};
      Item[] guns = new Item[]{(Item)ModWeapons.FLINTLOCK.get(), (Item)ModWeapons.SENRIKU.get(), (Item)ModWeapons.BAZOOKA.get(), (Item)ModWeapons.WALKER.get()};
      this.m_206424_(ModTags.Items.MAGNETIC).m_255179_(swords).m_255179_(blunts).m_255179_(guns).m_255179_(new Item[]{Items.f_42416_, Items.f_41834_, Items.f_41913_, Items.f_151050_, Items.f_150995_, Items.f_42749_, Items.f_42025_, Items.f_42128_, Items.f_42146_, Items.f_42341_, Items.f_42544_, Items.f_42155_, Items.f_42449_, Items.f_42519_, Items.f_42657_, Items.f_42520_, Items.f_42694_, Items.f_42693_, Items.f_41964_, Items.f_42386_, Items.f_42387_, Items.f_42385_, Items.f_42384_, Items.f_42383_, Items.f_42446_, Items.f_42522_, Items.f_42740_, Items.f_42409_, Items.f_42468_, Items.f_42469_, Items.f_42470_, Items.f_42471_, Items.f_42651_, Items.f_42574_, Items.f_42026_, (Item)ModItems.NORMAL_HANDCUFFS.get(), (Item)ModItems.EXPLOSIVE_HANDCUFFS.get(), (Item)ModItems.TIER_2_BOX.get(), (Item)ModItems.BULLET.get(), (Item)ModItems.CANNON_BALL.get()});
      this.m_206424_(ItemTags.f_271388_).m_255179_(swords);
      this.m_206424_(ModTags.Items.BLUNTS).m_255179_(blunts);
      this.m_206424_(ModTags.Items.GUNS).m_255179_(guns);
      this.m_206424_(net.minecraftforge.common.Tags.Items.TOOLS_BOWS).m_255245_((Item)ModWeapons.KUJA_BOW.get());
      this.m_206424_(ModTags.Items.CLIMA_TACTS).m_255179_(new Item[]{(Item)ModWeapons.CLIMA_TACT.get(), (Item)ModWeapons.PERFECT_CLIMA_TACT.get(), (Item)ModWeapons.SORCERY_CLIMA_TACT.get()});
      this.m_206424_(ModTags.Items.RUSTY).m_206428_(ModTags.Items.MAGNETIC);
      this.m_206421_(ModTags.Blocks.RUSTY, ModTags.Items.RUSTY);
      ModValues.DEVIL_FRUITS.stream().filter((fruit) -> fruit.getType() == FruitType.PARAMECIA).forEach((fruit) -> this.m_206424_(ModTags.Items.PARAMECIA).m_255245_(fruit));
      ModValues.DEVIL_FRUITS.stream().filter((fruit) -> fruit.getType() == FruitType.LOGIA).forEach((fruit) -> this.m_206424_(ModTags.Items.LOGIA).m_255245_(fruit));
      ModValues.DEVIL_FRUITS.stream().filter((fruit) -> fruit.getType() == FruitType.ZOAN || fruit.getType() == FruitType.ANCIENT_ZOAN || fruit.getType() == FruitType.MYTHICAL_ZOAN).forEach((fruit) -> this.m_206424_(ModTags.Items.ZOAN).m_255245_(fruit));
      this.m_206424_(ModTags.Items.DEVIL_FRUIT).m_206428_(ModTags.Items.PARAMECIA).m_206428_(ModTags.Items.LOGIA).m_206428_(ModTags.Items.ZOAN);
      this.m_206424_(ModTags.Items.SUPREME_GRADE).m_255179_(new Item[]{(Item)ModWeapons.YORU.get(), (Item)ModWeapons.MURAKUMOGIRI.get(), (Item)ModWeapons.ACE.get()});
      this.m_206424_(ModTags.Items.GREAT_GRADE).m_255179_(new Item[]{(Item)ModWeapons.NIDAI_KITETSU.get(), (Item)ModWeapons.WADO_ICHIMONJI.get(), (Item)ModWeapons.ENMA.get(), (Item)ModWeapons.SHUSUI.get(), (Item)ModWeapons.AME_NO_HABAKIRI.get()});
      this.m_206424_(ModTags.Items.FRUIT_REINCARNATION).m_255179_(new Item[]{Items.f_42410_, (Item)ModItems.TANGERINE.get()});
      this.m_206424_(ModTags.Items.GUN_AMMO).m_255179_(new Item[]{(Item)ModItems.BULLET.get(), (Item)ModItems.KAIROSEKI_BULLET.get()});
      this.m_206424_(ModTags.Items.BAZOOKA_AMMO).m_255245_((Item)ModItems.CANNON_BALL.get());
      this.m_206424_(ModTags.Items.RUST_IMMUNITY).m_255179_(new Item[]{(Item)ModWeapons.JITTE.get(), (Item)ModWeapons.NONOSAMA_STAFF.get(), (Item)ModWeapons.NONOSAMA_TRIDENT.get()});
      this.m_206424_(ModTags.Items.HARDENING_DAMAGE_BONUS);
      this.m_206424_(ModTags.Items.IMBUING_DAMAGE_BONUS).m_206428_(ModTags.Items.BLUNTS).m_206428_(ItemTags.f_271388_).m_206428_(ModTags.Items.CLIMA_TACTS).m_206428_(ModTags.Items.GUNS).m_206428_(net.minecraftforge.common.Tags.Items.TOOLS_BOWS);
      this.m_206424_(ModTags.Items.MELEE_ENCHANTABLE_BY_SMITHING).m_206428_(ItemTags.f_271388_).m_206428_(ModTags.Items.BLUNTS).m_206428_(ModTags.Items.CLIMA_TACTS);
      this.m_206424_(ModTags.Items.RANGED_ENCHANTABLE_BY_SMITHING).m_206428_(net.minecraftforge.common.Tags.Items.TOOLS_BOWS).m_206428_(ModTags.Items.GUNS);
      this.m_206424_(ItemTags.f_13182_).m_206428_(ModTags.Items.MANGROVE_LOGS);
      this.m_206424_(ItemTags.f_13168_).m_255245_(((Block)ModBlocks.MANGROVE_PLANKS.get()).m_5456_());
   }
}
