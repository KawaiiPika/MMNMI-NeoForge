package xyz.pixelatedw.mineminenomi.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import xyz.pixelatedw.mineminenomi.datagen.advancements.AdventureAdvancements;
import xyz.pixelatedw.mineminenomi.datagen.advancements.ChallengeAdvancements;

public class ModAdvancementsProvider extends ForgeAdvancementProvider {
   public ModAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
      super(output, lookupProvider, fileHelper, List.of(new ChallengeAdvancements(fileHelper), new AdventureAdvancements(fileHelper)));
   }
}
