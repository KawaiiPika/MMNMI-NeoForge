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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;

public class RaceArgument implements ArgumentType<Race> {
   public Race parse(StringReader reader) throws CommandSyntaxException {
      ResourceLocation resourcelocation = ResourceLocation.m_135818_(reader);
      Race race = (Race)((IForgeRegistry)WyRegistry.RACES.get()).getValue(resourcelocation);
      return race;
   }

   public static RaceArgument race() {
      return new RaceArgument();
   }

   public static <S> Race getRace(CommandContext<S> context, String name) {
      return (Race)context.getArgument(name, Race.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringreader = new StringReader(builder.getInput());
      stringreader.setCursor(builder.getStart());
      return this.suggestAbility(builder);
   }

   private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder builder) {
      return SharedSuggestionProvider.m_82926_(((IForgeRegistry)WyRegistry.RACES.get()).getKeys(), builder);
   }
}
