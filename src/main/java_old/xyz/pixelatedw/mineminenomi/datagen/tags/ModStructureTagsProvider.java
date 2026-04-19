package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModStructureTagsProvider extends TagsProvider<Structure> {
   public ModStructureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, Registries.f_256944_, lookupProvider, "mineminenomi", existingFileHelper);
   }

   protected void m_6577_(HolderLookup.Provider provider) {
   }
}
