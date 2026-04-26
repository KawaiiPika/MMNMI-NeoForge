package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

import java.util.concurrent.CompletableFuture;

public class FruitArgument implements ArgumentType<AkumaNoMiItem> {

    @Override
    public AkumaNoMiItem parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        Item item = BuiltInRegistries.ITEM.get(resourceLocation);
        if (item instanceof AkumaNoMiItem fruit) {
            return fruit;
        }
        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
    }

    public static FruitArgument fruit() {
        return new FruitArgument();
    }

    public static <S> AkumaNoMiItem getFruit(CommandContext<S> context, String name) {
        return context.getArgument(name, AkumaNoMiItem.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringReader = new StringReader(builder.getInput());
        stringReader.setCursor(builder.getStart());
        return this.suggestFruit(builder);
    }

    private CompletableFuture<Suggestions> suggestFruit(SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(
                ModFruits.getDevilFruits().stream().map(fruit -> BuiltInRegistries.ITEM.getKey(fruit)),
                builder
        );
    }
}
