package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class ModBiomeTagsProvider extends TagsProvider<Biome> {
   public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, Registries.f_256952_, lookupProvider, "mineminenomi", existingFileHelper);
   }

   protected void m_6577_(HolderLookup.Provider provider) {
      this.m_206424_(ModTags.Biomes.IS_SWAMP).m_211101_(new ResourceKey[]{Biomes.f_220595_, Biomes.f_48207_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_DUGONGS).m_206428_(BiomeTags.f_207604_).m_211101_(new ResourceKey[]{Biomes.f_48203_, Biomes.f_186760_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_DUGONGS).addTags(new TagKey[]{ModTags.Biomes.CAN_SPAWN_DUGONGS, BiomeTags.f_207611_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS, BiomeTags.f_207607_, BiomeTags.f_215816_, BiomeTags.f_207609_, ModTags.Biomes.IS_SWAMP});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_BIG_DUCK).addTags(new TagKey[]{BiomeTags.f_207611_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS, BiomeTags.f_207608_, BiomeTags.f_215816_, BiomeTags.f_207609_}).m_255204_(Biomes.f_48203_);
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_DEN_DEN_MUSHI).addTags(new TagKey[]{BiomeTags.f_207611_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS, BiomeTags.f_207608_, BiomeTags.f_215816_, BiomeTags.f_207609_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_HUMANDRILL).addTags(new TagKey[]{BiomeTags.f_207611_, BiomeTags.f_207610_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_GORILLAS).addTags(new TagKey[]{BiomeTags.f_207611_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_LAPAHN).m_211101_(new ResourceKey[]{Biomes.f_186755_, Biomes.f_186757_, Biomes.f_186756_, Biomes.f_48152_, Biomes.f_186761_, Biomes.f_48182_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_BANANAWANI).m_206428_(BiomeTags.f_207604_).m_211101_(new ResourceKey[]{Biomes.f_48203_, Biomes.f_48159_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_HUMANS).addTags(new TagKey[]{BiomeTags.f_207611_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS, BiomeTags.f_207608_, BiomeTags.f_215816_, BiomeTags.f_207609_, BiomeTags.f_207604_, BiomeTags.f_207610_, BiomeTags.f_207606_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_FISH).m_211101_(new ResourceKey[]{Biomes.f_48174_, Biomes.f_48225_, Biomes.f_48167_, Biomes.f_48170_, Biomes.f_48166_});
      this.m_206424_(ModTags.Biomes.CAN_SPAWN_FIGHTING_FISH).m_211101_(new ResourceKey[]{Biomes.f_48225_, Biomes.f_48170_});
      this.m_206424_(ModTags.Biomes.HAS_STRUCTURE_CAMP).addTags(new TagKey[]{BiomeTags.f_215816_, BiomeTags.f_207608_, BiomeTags.f_207607_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS});
      this.m_206424_(ModTags.Biomes.HAS_STRUCTURE_SMALL_BASE).addTags(new TagKey[]{BiomeTags.f_207611_, BiomeTags.f_207609_, BiomeTags.f_215816_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS});
      this.m_206424_(ModTags.Biomes.HAS_STRUCTURE_LARGE_BASE).addTags(new TagKey[]{BiomeTags.f_207611_, BiomeTags.f_207609_, BiomeTags.f_215816_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS});
      this.m_206424_(ModTags.Biomes.HAS_STRUCTURE_WATCH_TOWER).addTags(new TagKey[]{BiomeTags.f_215816_, BiomeTags.f_207608_, BiomeTags.f_207607_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS});
      this.m_206424_(ModTags.Biomes.HAS_STRUCTURE_SKY_ISLAND).addTags(new TagKey[]{BiomeTags.f_207603_, BiomeTags.f_207604_}).m_255204_(Biomes.f_186760_);
      this.m_206424_(ModTags.Biomes.HAS_STRUCTURE_TRAINER).addTags(new TagKey[]{BiomeTags.f_207611_, BiomeTags.f_207609_, BiomeTags.f_215816_, net.minecraftforge.common.Tags.Biomes.IS_PLAINS});
      this.m_206424_(ModTags.Biomes.HAS_KAIROSEKI_ORE).addTags(new TagKey[]{BiomeTags.f_207604_, BiomeTags.f_207603_}).m_255204_(Biomes.f_186760_);
   }
}
