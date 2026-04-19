package xyz.pixelatedw.mineminenomi.api;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public abstract class WyBlockTagsProvider extends BlockTagsProvider {
   public WyBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, modId, existingFileHelper);
   }

   public abstract void m_6577_(HolderLookup.Provider var1);

   public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> m_206424_(TagKey<Block> key) {
      return super.m_206424_(key);
   }
}
