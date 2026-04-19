package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class AbilityTooltipsHelper {
   public static AbilityDescriptionLine.IDescriptionLine getRequiredActiveAbilityTooltip(RegistryObject<? extends AbilityCore<?>>... core) {
      return (entity, ability) -> {
         Component tooltip = null;
         if (core.length == 1) {
            tooltip = Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{"§a" + ((AbilityCore)core[0].get()).getLocalizedName().getString() + "§r"});
         } else if (core.length > 1) {
            tooltip = Component.m_237110_(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE, new Object[]{"§a" + ((AbilityCore)core[0].get()).getLocalizedName().getString() + "§r", "§a" + ((AbilityCore)core[1].get()).getLocalizedName().getString() + "§r"});
         }

         return tooltip;
      };
   }

   public static AbilityDescriptionLine.IDescriptionLine getRequiredMorphTooltip(RegistryObject<MorphInfo>... morphs) {
      return (entity, ability) -> {
         Component tooltip = null;
         if (morphs.length == 1) {
            tooltip = Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{"§a" + ((MorphInfo)morphs[0].get()).getDisplayName().getString() + "§r"});
         } else if (morphs.length > 1) {
            tooltip = Component.m_237110_(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE, new Object[]{"§a" + ((MorphInfo)morphs[0].get()).getDisplayName().getString() + "§r", "§a" + ((MorphInfo)morphs[1].get()).getDisplayName().getString() + "§r"});
         }

         return tooltip;
      };
   }
}
