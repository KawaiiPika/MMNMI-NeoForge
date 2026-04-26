package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;

import java.util.concurrent.CompletableFuture;

public class QuestArgument implements ArgumentType<QuestId<?>> {

    @Override
    public QuestId<?> parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourcelocation = ResourceLocation.read(reader);
        return ModRegistries.QUESTS.get(resourcelocation);
    }

    public static QuestArgument quest() {
        return new QuestArgument();
    }

    public static <S> QuestId<?> getQuest(CommandContext<S> context, String name) {
        return (QuestId<?>) context.getArgument(name, QuestId.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringreader = new StringReader(builder.getInput());
        stringreader.setCursor(builder.getStart());
        return this.suggestQuest(builder);
    }

    private CompletableFuture<Suggestions> suggestQuest(SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ModRegistries.QUESTS.keySet(), builder);
    }
}
