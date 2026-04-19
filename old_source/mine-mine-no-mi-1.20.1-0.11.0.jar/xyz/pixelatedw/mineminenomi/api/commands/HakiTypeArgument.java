package xyz.pixelatedw.mineminenomi.api.commands;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;

public class HakiTypeArgument<T extends Enum<T>> implements ArgumentType<T> {
   private final Class<T> enumClass;

   public static HakiTypeArgument<HakiType> hakiType() {
      return new HakiTypeArgument<HakiType>(HakiType.class);
   }

   public HakiTypeArgument(Class<T> enumClass) {
      this.enumClass = enumClass;
   }

   public T parse(StringReader reader) throws CommandSyntaxException {
      return (T)Enum.valueOf(this.enumClass, reader.readUnquotedString());
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      return SharedSuggestionProvider.m_82981_(Stream.of((Enum[])this.enumClass.getEnumConstants()).map(Object::toString), builder);
   }

   public Collection<String> getExamples() {
      return (Collection)Stream.of((Enum[])this.enumClass.getEnumConstants()).map(Object::toString).collect(Collectors.toList());
   }

   public static class Info<T extends Enum<T>> implements ArgumentTypeInfo<HakiTypeArgument<T>, Info<T>.Template> {
      public void serializeToNetwork(Info<T>.Template template, FriendlyByteBuf buffer) {
         buffer.m_130070_(template.enumClass.getName());
      }

      public Info<T>.Template deserializeFromNetwork(FriendlyByteBuf buffer) {
         try {
            String name = buffer.m_130277_();
            return new Template(Class.forName(name));
         } catch (ClassNotFoundException var3) {
            return null;
         }
      }

      public void serializeToJson(Info<T>.Template template, JsonObject json) {
         json.addProperty("enum", template.enumClass.getName());
      }

      public Info<T>.Template unpack(HakiTypeArgument<T> argument) {
         return new Template(argument.enumClass);
      }

      public class Template implements ArgumentTypeInfo.Template<HakiTypeArgument<T>> {
         final Class<T> enumClass;

         Template(Class<T> enumClass) {
            this.enumClass = enumClass;
         }

         public HakiTypeArgument<T> instantiate(CommandBuildContext p_223435_) {
            return new HakiTypeArgument<T>(this.enumClass);
         }

         public ArgumentTypeInfo<HakiTypeArgument<T>, ?> m_213709_() {
            return Info.this;
         }
      }
   }
}
