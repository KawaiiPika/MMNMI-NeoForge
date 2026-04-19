package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IBrewPotionObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class BrewPotionObjective extends Objective implements IBrewPotionObjective {
   private MobEffect[] effects;
   private Item[] types;

   public BrewPotionObjective(Quest parent, Component titleId, int count) {
      this(parent, titleId, count, new Item[]{Items.f_42589_, Items.f_42739_, Items.f_42736_}, (MobEffect[])null);
   }

   public BrewPotionObjective(Quest parent, Component titleId, int count, MobEffect[] effects) {
      this(parent, titleId, count, new Item[]{Items.f_42589_, Items.f_42739_, Items.f_42736_}, effects);
   }

   public BrewPotionObjective(Quest parent, Component titleId, int count, Item[] types, MobEffect[] effects) {
      super(parent, titleId);
      this.types = null;
      this.setMaxProgress((float)count);
      this.effects = effects;
      this.types = types;
   }

   public boolean checkPotion(Player player, ItemStack stack) {
      if (this.types == null) {
         return false;
      } else if (stack.m_41784_().m_128471_("questMark")) {
         return false;
      } else {
         boolean isPotion = false;
         boolean isCorrectEffect = true;

         for(Item item : this.types) {
            if (stack.m_41720_() == item) {
               isPotion = true;
               break;
            }
         }

         if (this.effects != null && isPotion) {
            MobEffect[] var9 = this.effects;
            int var10 = var9.length;
            byte var11 = 0;
            if (var11 < var10) {
               MobEffect effect = var9[var11];
               isCorrectEffect = PotionUtils.m_43547_(stack).stream().anyMatch((instance) -> instance.m_19544_() == effect);
            }
         } else if (this.effects == null && isPotion) {
            isCorrectEffect = PotionUtils.m_43547_(stack).stream().findAny().isPresent();
         }

         if (stack.m_41619_() && isCorrectEffect) {
            isPotion = true;
         }

         if (isPotion && isCorrectEffect) {
            stack.m_41784_().m_128379_("questMark", true);
         }

         return isPotion && isCorrectEffect;
      }
   }
}
