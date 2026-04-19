package xyz.pixelatedw.mineminenomi.datagen.tags.blocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.WyBlockTagsProvider;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class BlockProtectionTags {
   public static void addTags(WyBlockTagsProvider prov) {
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_AIR).m_206428_(ModTags.Blocks.VANILLA_AIR);
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_WATER).m_255245_(Blocks.f_49990_);
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_LAVA).m_255245_(Blocks.f_49991_);
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_SNOW).m_206428_(BlockTags.f_144279_);
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_CORE).addTags(new TagKey[]{BlockTags.f_13106_, BlockTags.f_13090_, BlockTags.f_144274_, BlockTags.f_13062_, BlockTags.f_13061_, BlockTags.f_13039_, BlockTags.f_13055_, BlockTags.f_144269_, BlockTags.f_13103_, BlockTags.f_13031_, BlockTags.f_13030_, BlockTags.f_13091_, BlockTags.f_198156_, BlockTags.f_13036_, BlockTags.f_13077_, BlockTags.f_13032_, BlockTags.f_13078_, BlockTags.f_13089_, BlockTags.f_13038_, BlockTags.f_257016_, BlockTags.f_13047_, BlockTags.f_13051_, net.minecraftforge.common.Tags.Blocks.SAND, net.minecraftforge.common.Tags.Blocks.SANDSTONE, net.minecraftforge.common.Tags.Blocks.COBBLESTONE, net.minecraftforge.common.Tags.Blocks.GLASS, net.minecraftforge.common.Tags.Blocks.GLASS_PANES, net.minecraftforge.common.Tags.Blocks.NETHERRACK, net.minecraftforge.common.Tags.Blocks.GRAVEL, net.minecraftforge.common.Tags.Blocks.BOOKSHELVES, ModTags.Blocks.VANILLA_BASALT, ModTags.Blocks.VANILLA_BLACKSTONE, ModTags.Blocks.VANILLA_CONCRETE, ModTags.Blocks.VANILLA_CONCRETE_POWDER, ModTags.Blocks.VANILLA_DEEPSLATE, ModTags.Blocks.VANILLA_PURPUR, ModTags.Blocks.VANILLA_QUARTZ, ModTags.Blocks.VANILLA_GLAZED_TERRACOTTA}).m_255179_(new Block[]{Blocks.f_50571_, Blocks.f_276445_, Blocks.f_271439_, Blocks.f_50180_, Blocks.f_50181_, Blocks.f_50077_, Blocks.f_50128_, Blocks.f_50129_, Blocks.f_50133_, Blocks.f_50143_, Blocks.f_50186_, Blocks.f_50033_, Blocks.f_152555_, Blocks.f_50281_, Blocks.f_50175_, Blocks.f_50076_, Blocks.f_220843_, Blocks.f_220844_, Blocks.f_50386_, Blocks.f_50093_, Blocks.f_152481_, Blocks.f_152497_, Blocks.f_152537_, Blocks.f_50135_, Blocks.f_50136_, Blocks.f_50453_, Blocks.f_50701_, Blocks.f_50335_, Blocks.f_50261_, Blocks.f_244489_, Blocks.f_50127_, Blocks.f_152499_});
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_RESTRICTED).m_255179_(new Block[]{Blocks.f_50375_, Blocks.f_50752_, Blocks.f_50257_, Blocks.f_50258_, Blocks.f_50446_, Blocks.f_50272_, Blocks.f_50448_, Blocks.f_50447_, Blocks.f_50677_, Blocks.f_50678_, Blocks.f_50110_, Blocks.f_152480_, Blocks.f_220863_, (Block)ModBlocks.PONEGLYPH.get(), (Block)ModBlocks.BARRIER.get(), (Block)ModBlocks.OPE.get(), (Block)ModBlocks.STRING_WALL.get()});
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_LAVA_IMMUNE).m_255179_(new Block[]{Blocks.f_50722_, Blocks.f_50721_}).m_206428_(ModTags.Blocks.BLOCK_PROT_RESTRICTED);
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_FOLIAGE).addTags(new TagKey[]{BlockTags.f_13035_, BlockTags.f_13037_, BlockTags.f_13040_, BlockTags.f_13104_}).m_255179_(new Block[]{Blocks.f_50191_, Blocks.f_50702_, Blocks.f_50703_, Blocks.f_50704_, Blocks.f_50653_, Blocks.f_152538_, Blocks.f_152539_});
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_OCEAN_PLANTS).addTags(new TagKey[]{BlockTags.f_13051_, BlockTags.f_13064_, BlockTags.f_13052_, ModTags.Blocks.VANILLA_DEAD_CORAL_BLOCKS, ModTags.Blocks.VANILLA_DEAD_CORALS, ModTags.Blocks.VANILLA_DEAD_WALL_CORALS}).m_255179_(new Block[]{Blocks.f_50575_, Blocks.f_50576_, Blocks.f_50037_, Blocks.f_50038_, Blocks.f_50567_});
      prov.m_206424_(ModTags.Blocks.BLOCK_PROT_LIQUIDS).m_255179_(new Block[]{Blocks.f_49990_, Blocks.f_49991_});
   }
}
