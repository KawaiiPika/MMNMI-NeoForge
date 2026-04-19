package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.api.WyBlockTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.blocks.BlockProtectionTags;
import xyz.pixelatedw.mineminenomi.datagen.tags.blocks.VanillaTags;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class ModBlockTagsProvider extends WyBlockTagsProvider {
   public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "mineminenomi", existingFileHelper);
   }

   public void m_6577_(HolderLookup.Provider provider) {
      VanillaTags.addTags(this);
      BlockProtectionTags.addTags(this);
      this.m_206424_(ModTags.Blocks.MANGROVE_LOGS).m_255179_(new Block[]{(Block)ModBlocks.MANGROVE_LOG.get(), (Block)ModBlocks.MANGROVE_WOOD.get(), (Block)ModBlocks.STRIPPED_MANGROVE_LOG.get(), (Block)ModBlocks.STRIPPED_MANGROVE_WOOD.get()});
      this.m_206424_(ModTags.Blocks.KAIROSEKI).m_255179_(new Block[]{(Block)ModBlocks.KAIROSEKI.get(), (Block)ModBlocks.KAIROSEKI_BARS.get(), (Block)ModBlocks.KAIROSEKI_ORE.get(), (Block)ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get()});
      this.m_206424_(ModTags.Blocks.RUSTY).m_255179_(new Block[]{Blocks.f_49996_, Blocks.f_50075_, Blocks.f_50183_, Blocks.f_50376_, Blocks.f_50166_, Blocks.f_50322_, Blocks.f_50323_, Blocks.f_50324_, Blocks.f_50332_});
      this.m_206424_(ModTags.Blocks.NO_PHASE).m_255179_(new Block[]{Blocks.f_50752_, Blocks.f_50375_, (Block)ModBlocks.BARRIER.get()}).m_206428_(ModTags.Blocks.KAIROSEKI);
      this.m_206424_(ModTags.Blocks.LOGIA_BLOCK_PASS_HIE).m_206428_(BlockTags.f_13047_);
      this.m_206424_(ModTags.Blocks.LOGIA_BLOCK_PASS_GORO).m_206428_(ModTags.Blocks.RUSTY).m_206428_(net.minecraftforge.common.Tags.Blocks.STORAGE_BLOCKS_GOLD);
      this.m_206424_(ModTags.Blocks.LOGIA_BLOCK_PASS_PIKA).m_206428_(net.minecraftforge.common.Tags.Blocks.GLASS).m_206428_(net.minecraftforge.common.Tags.Blocks.GLASS_PANES);
      this.m_206424_(ModTags.Blocks.LOGIA_BLOCK_PASS_SUNA).m_206428_(net.minecraftforge.common.Tags.Blocks.SAND).m_206428_(net.minecraftforge.common.Tags.Blocks.SANDSTONE).m_255179_(new Block[]{Blocks.f_50263_, Blocks.f_50406_, Blocks.f_50613_, Blocks.f_50407_, Blocks.f_50636_, Blocks.f_50649_});
      this.m_206424_(ModTags.Blocks.LOGIA_BLOCK_PASS_YUKI).m_255179_(new Block[]{Blocks.f_50125_, Blocks.f_50127_, (Block)ModBlocks.HARDENED_SNOW.get()});
      this.m_206424_(BlockTags.f_13106_).m_206428_(ModTags.Blocks.MANGROVE_LOGS);
      this.m_206424_(BlockTags.f_13105_).m_206428_(ModTags.Blocks.MANGROVE_LOGS);
      this.m_206424_(BlockTags.f_13090_).m_255245_((Block)ModBlocks.MANGROVE_PLANKS.get());
      this.m_206424_(BlockTags.f_13035_).m_255245_((Block)ModBlocks.MANGROVE_LEAVES.get());
      this.m_206424_(BlockTags.f_144282_).m_255179_(new Block[]{(Block)ModBlocks.KAIROSEKI_ORE.get(), (Block)ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get()});
      this.m_206424_(BlockTags.f_144280_).m_255245_((Block)ModBlocks.DESIGN_BARREL.get());
      this.m_206424_(BlockTags.f_144285_).m_255179_(new Block[]{(Block)ModBlocks.KAIROSEKI_ORE.get(), (Block)ModBlocks.DEEPSLATE_KAIROSEKI_ORE.get()});
   }
}
