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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;

public class FactionArgument implements ArgumentType<Faction> {
   @Override
   public Faction parse(StringReader reader) throws CommandSyntaxException {
      ResourceLocation resourcelocation = ResourceLocation.read(reader);
      return ModRegistries.FACTIONS_REGISTRY.getEntries().stream()
              .filter(e -> e.getId().equals(resourcelocation))
              .map(net.neoforged.neoforge.registries.DeferredHolder::get)
              .findFirst()
              .orElse(null);
   }

   public static FactionArgument faction() {
      return new FactionArgument();
   }

   public static <S> Faction getFaction(CommandContext<S> context, String name) {
      return context.getArgument(name, Faction.class);
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringreader = new StringReader(builder.getInput());
      stringreader.setCursor(builder.getStart());
      return this.suggestAbility(builder);
   }

   private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder builder) {
      return SharedSuggestionProvider.suggestResource(ModRegistries.FACTIONS_REGISTRY.getEntries().stream().map(e -> e.getId()), builder);
   }
}
