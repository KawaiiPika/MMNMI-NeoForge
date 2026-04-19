package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.abilities.kuku.GourmetamorphosisAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class KukuPassivesHandler {
   private static final Component FOOD_TOOLTIP;

   public static void addKukuEdibleTooltips(Player player, ItemStack heldStack, List<Component> tooltip) {
      if (!(heldStack.m_41720_() instanceof AkumaNoMiItem)) {
         boolean hasAbilityActive = (Boolean)AbilityCapability.get(player).map((props) -> (GourmetamorphosisAbility)props.getEquippedAbility((AbilityCore)GourmetamorphosisAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
         if (hasAbilityActive && !tooltip.contains(FOOD_TOOLTIP)) {
            tooltip.add(Component.m_237119_());
            tooltip.add(FOOD_TOOLTIP);
         }

      }
   }

   public static void enableKukuEating(Player player, ItemStack heldStack) {
      if (!(heldStack.m_41720_() instanceof AkumaNoMiItem)) {
         boolean hasAbilityActive = (Boolean)AbilityCapability.get(player).map((props) -> (GourmetamorphosisAbility)props.getEquippedAbility((AbilityCore)GourmetamorphosisAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
         if (hasAbilityActive) {
            int foodlevel = 2;
            float saturation = 0.25F;
            if (heldStack.m_41614_()) {
               FoodProperties food = heldStack.m_41720_().m_41473_();
               foodlevel += food.m_38744_();
               saturation += food.m_38745_();
            }

            player.m_21060_(heldStack, 16);
            player.m_5496_(player.m_7866_(heldStack), 0.5F + 0.5F * (float)player.m_217043_().m_188503_(2), (player.m_217043_().m_188501_() - player.m_217043_().m_188501_()) * 0.2F + 1.0F);
            player.m_21166_(EquipmentSlot.MAINHAND);
            player.m_36324_().m_38707_(foodlevel, saturation);
            heldStack.m_41774_(1);
         }

      }
   }

   static {
      FOOD_TOOLTIP = ModI18n.ITEM_GOURMETAMORPHOSIS_FOOD.m_6881_().m_6270_(Style.f_131099_.m_131157_(ChatFormatting.YELLOW));
   }
}
