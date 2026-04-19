package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class MentionHelper {
   public static final Style MENTION_STYLE;

   public static @Nullable Component tryParseAndMention(RegistryObject<? extends Object> obj) {
      return !obj.isPresent() ? null : tryMentionObject(obj.get());
   }

   public static @Nullable Component tryMentionObject(Object entry) {
      try {
         if (entry instanceof AbilityCore<?> core) {
            return mentionAbility(core);
         }

         if (entry instanceof Item item) {
            return mentionItem(item);
         }

         if (entry instanceof MobEffect effect) {
            return mentionEffect(effect);
         }

         if (entry instanceof MorphInfo morph) {
            return mentionMorph(morph);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }

      return null;
   }

   public static Component mentionAbility(AbilityCore<?> core) {
      return core.getLocalizedName().m_6881_().m_6270_(MENTION_STYLE);
   }

   public static Component mentionMorph(MorphInfo info) {
      return info.getDisplayName().m_6881_().m_6270_(MENTION_STYLE);
   }

   public static Component mentionItem(Item item) {
      MutableComponent comp = Component.m_237115_(item.m_5524_());
      return comp.m_6270_(MENTION_STYLE);
   }

   public static Component mentionEffect(MobEffect effect) {
      return effect.m_19482_().m_6881_().m_6270_(MENTION_STYLE);
   }

   public static Component mentionText(Object text) {
      return text instanceof Component ? ((Component)text).m_6881_().m_6270_(MENTION_STYLE) : Component.m_237113_(text.toString()).m_6270_(MENTION_STYLE);
   }

   static {
      MENTION_STYLE = Style.f_131099_.m_131140_(ChatFormatting.GREEN);
   }
}
