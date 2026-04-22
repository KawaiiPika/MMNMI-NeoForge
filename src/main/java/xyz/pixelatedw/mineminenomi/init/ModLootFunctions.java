package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import xyz.pixelatedw.mineminenomi.loot.SetWantedPosterDataFunction;

import java.util.function.Supplier;

public class ModLootFunctions {
    public static final Supplier<LootItemFunctionType<SetWantedPosterDataFunction>> SET_WANTED_POSTER_DATA = ModRegistry.LOOT_FUNCTION_TYPES.register("set_wanted_poster_data", () -> new LootItemFunctionType<>(SetWantedPosterDataFunction.CODEC));

    public static void init() {}
}