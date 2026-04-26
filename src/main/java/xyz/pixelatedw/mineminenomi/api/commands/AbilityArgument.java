package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

import java.util.concurrent.CompletableFuture;

public class AbilityArgument implements ArgumentType<Ability> {

    @Override
    public Ability parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourcelocation = ResourceLocation.read(reader);
        return ModAbilities.REGISTRY.get(resourcelocation);
    }

    public static AbilityArgument ability() {
        return new AbilityArgument();
    }

    public static <S> Ability getAbility(CommandContext<S> context, String name) {
        return context.getArgument(name, Ability.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringreader = new StringReader(builder.getInput());
        stringreader.setCursor(builder.getStart());
        return this.suggestAbility(builder);
    }

    private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ModAbilities.REGISTRY.keySet(), builder);
    }
}
