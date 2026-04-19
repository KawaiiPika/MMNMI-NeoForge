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
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class FruitArgument implements ArgumentType<AkumaNoMiItem> {
   public AkumaNoMiItem parse(StringReader reader) throws CommandSyntaxException {
      ResourceLocation resourceLocation = ResourceLocation.m_135818_(reader);
      AkumaNoMiItem devilFruit = (AkumaNoMiItem)ForgeRegistries.ITEMS.getValue(resourceLocation);
      return devilFruit;
   }

   public static FruitArgument fruit() {
      return new FruitArgument();
   }

   public static <S> AkumaNoMiItem getFruit(CommandContext<S> context, String name) {
      return (AkumaNoMiItem)context.getArgument(name, AkumaNoMiItem.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringReader = new StringReader(builder.getInput());
      stringReader.setCursor(builder.getStart());
      return this.suggestFruit(builder);
   }

   private CompletableFuture<Suggestions> suggestFruit(SuggestionsBuilder builder) {
      return SharedSuggestionProvider.m_82957_(ModValues.DEVIL_FRUITS.stream().map(AkumaNoMiItem::getRegistryKey), builder);
   }
}
