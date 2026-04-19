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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;

public class FightingStyleArgument implements ArgumentType<FightingStyle> {
   public FightingStyle parse(StringReader reader) throws CommandSyntaxException {
      ResourceLocation resourcelocation = ResourceLocation.m_135818_(reader);
      FightingStyle faction = (FightingStyle)((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getValue(resourcelocation);
      return faction;
   }

   public static FightingStyleArgument fightingStyle() {
      return new FightingStyleArgument();
   }

   public static <S> FightingStyle getFightingStyle(CommandContext<S> context, String name) {
      return (FightingStyle)context.getArgument(name, FightingStyle.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringreader = new StringReader(builder.getInput());
      stringreader.setCursor(builder.getStart());
      return this.suggestAbility(builder);
   }

   private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder builder) {
      return SharedSuggestionProvider.m_82926_(((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getKeys(), builder);
   }
}
