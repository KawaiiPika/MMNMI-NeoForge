package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

// Note: Challenge API/registry has not been fully ported,
// using a stub for parsing to prevent compilation errors until it is ported.

public class ChallengeArgument implements ArgumentType<Object> {
    @Override
    public Object parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourcelocation = ResourceLocation.read(reader);
        // Stub: Needs to return ChallengeCore once ported
        return new Object();
    }

    public static ChallengeArgument challenge() {
        return new ChallengeArgument();
    }

    public static <S> Object getChallenge(CommandContext<S> context, String name) {
        return context.getArgument(name, Object.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringreader = new StringReader(builder.getInput());
        stringreader.setCursor(builder.getStart());
        return this.suggestChallenge(builder);
    }

    private CompletableFuture<Suggestions> suggestChallenge(SuggestionsBuilder builder) {
        // Stub: Needs to suggest from challenge registry keys
        return SharedSuggestionProvider.suggestResource(java.util.Collections.emptyList(), builder);
    }
}
