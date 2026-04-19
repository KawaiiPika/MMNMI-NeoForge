package xyz.pixelatedw.mineminenomi.api.chat;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;

public record ReferenceContents(RegistryObject<?> object) implements ComponentContents {
   public <T> Optional<T> m_213874_(FormattedText.ContentConsumer<T> consumer) {
      Component comp = MentionHelper.tryParseAndMention(this.object);
      return comp.m_5651_(consumer);
   }

   public <T> Optional<T> m_213724_(FormattedText.StyledContentConsumer<T> consumer, Style style) {
      Component comp = MentionHelper.tryParseAndMention(this.object);
      return comp.m_7451_(consumer, style);
   }

   public String toString() {
      return "reference{" + String.valueOf(this.object) + "}";
   }
}
