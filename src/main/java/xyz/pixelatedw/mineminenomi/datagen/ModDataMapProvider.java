package xyz.pixelatedw.mineminenomi.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import xyz.pixelatedw.mineminenomi.init.ModItems;

import java.util.concurrent.CompletableFuture;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModDataMapProvider extends DataMapProvider {
    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(((DeferredHolder<?,?>)ModItems.FLAME_DIAL).getId(), new FurnaceFuel(10000), false)
                .add(((DeferredHolder<?,?>)ModItems.WANTED_POSTER_PACKAGE).getId(), new FurnaceFuel(300), false)
                .add(((DeferredHolder<?,?>)ModItems.WANTED_POSTER).getId(), new FurnaceFuel(200), false)
                .add(((DeferredHolder<?,?>)ModItems.FLAG).getId(), new FurnaceFuel(200), false)
                .add(((DeferredHolder<?,?>)ModItems.CHALLENGE_POSTER).getId(), new FurnaceFuel(200), false)
                .add(((DeferredHolder<?,?>)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA).getId(), new FurnaceFuel(200), false)
                .add(((DeferredHolder<?,?>)ModItems.VIVRE_CARD).getId(), new FurnaceFuel(50), false);
    }
}
