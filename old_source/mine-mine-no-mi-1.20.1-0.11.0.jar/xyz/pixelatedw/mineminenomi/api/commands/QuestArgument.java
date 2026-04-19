package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class QuestArgument implements ArgumentType<QuestId> {
   public QuestId<?> parse(StringReader reader) throws CommandSyntaxException {
      ResourceLocation resourcelocation = ResourceLocation.m_135818_(reader);
      QuestId<?> quest = (QuestId)((IForgeRegistry)WyRegistry.QUESTS.get()).getValue(resourcelocation);
      return quest;
   }

   public static QuestArgument quest() {
      return new QuestArgument();
   }

   public static <S> QuestId<?> getQuest(CommandContext<S> context, String name) {
      return (QuestId)context.getArgument(name, QuestId.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringreader = new StringReader(builder.getInput());
      stringreader.setCursor(builder.getStart());
      return this.suggestQuest(builder);
   }

   private CompletableFuture<Suggestions> suggestQuest(SuggestionsBuilder builder) {
      return SharedSuggestionProvider.m_82926_(((IForgeRegistry)WyRegistry.QUESTS.get()).getKeys(), builder);
   }
}
