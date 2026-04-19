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
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;

public class ChallengeArgument implements ArgumentType<ChallengeCore<?>> {
   public ChallengeCore<?> parse(StringReader reader) throws CommandSyntaxException {
      ResourceLocation resourcelocation = ResourceLocation.m_135818_(reader);
      ChallengeCore<?> challenge = (ChallengeCore)((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValue(resourcelocation);
      return challenge;
   }

   public static ChallengeArgument challenge() {
      return new ChallengeArgument();
   }

   public static <S> ChallengeCore<?> getChallenge(CommandContext<S> context, String name) {
      return (ChallengeCore)context.getArgument(name, ChallengeCore.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringreader = new StringReader(builder.getInput());
      stringreader.setCursor(builder.getStart());
      return this.suggestAbility(builder);
   }

   private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder builder) {
      return SharedSuggestionProvider.m_82926_(((IForgeRegistry)WyRegistry.CHALLENGES.get()).getKeys(), builder);
   }
}
