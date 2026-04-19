package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityGroups;

public class Suggests {
   public static SuggestionProvider<CommandSourceStack> allAbilityGroups() {
      SuggestionProvider<CommandSourceStack> prov = (context, builder) -> SharedSuggestionProvider.m_82970_(AbilityGroups.getAllStringIds(), builder);
      return prov;
   }
}
