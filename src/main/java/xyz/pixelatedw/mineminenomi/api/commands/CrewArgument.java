package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.commands.SharedSuggestionProvider;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class CrewArgument implements ArgumentType<Crew> {
    public Crew parse(StringReader reader) throws CommandSyntaxException {
        return FactionsWorldData.get().getCrewByName(reader.readQuotedString());
    }

    public static CrewArgument crew() {
        return new CrewArgument();
    }

    public static <S> Crew getCrew(CommandContext<S> context, String name) {
        return context.getArgument(name, Crew.class);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader stringreader = new StringReader(builder.getInput());
        stringreader.setCursor(builder.getStart());
        return this.suggestCrew(builder);
    }

    private CompletableFuture<Suggestions> suggestCrew(SuggestionsBuilder builder) {
        String input = builder.getInput();
        int start = builder.getStart();
        StringReader stringReader = new StringReader(input);
        stringReader.setCursor(start);
        boolean quoted = stringReader.canRead() && stringReader.peek() == '"';
        if (quoted) {
            stringReader.skip();
        }

        FactionsWorldData data = FactionsWorldData.get();
        if (data == null) {
            return SharedSuggestionProvider.suggest(new String[0], builder);
        }
        List<Crew> crews = data.getCrews();
        if (crews == null || crews.isEmpty()) {
            return SharedSuggestionProvider.suggest(new String[0], builder);
        } else {
            return SharedSuggestionProvider.suggest(crews.stream().map(crew -> "\"" + crew.getName() + "\""), builder);
        }
    }
}
