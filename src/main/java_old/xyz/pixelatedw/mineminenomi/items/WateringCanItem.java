package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class WateringCanItem extends Item {
   private static final int MAX_TEARS = 250;

   public WateringCanItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      float currentHealth = player.m_21223_();
      float maxHealth = player.m_21233_();
      if (currentHealth >= maxHealth) {
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         int requiredTears = 0;
         if (currentHealth > maxHealth - maxHealth * 0.05F) {
            requiredTears = (int)(maxHealth - currentHealth);
         } else {
            requiredTears = (int)(maxHealth * 0.05F);
         }

         int currentTears = getTearAmount(itemStack);
         if (currentTears > 0 && currentTears >= requiredTears) {
            player.m_6672_(hand);
         }

         return InteractionResultHolder.m_19090_(itemStack);
      }
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      float currentHealth = entity.m_21223_();
      float maxHealth = entity.m_21233_();
      float healAmount = 0.0F;
      if (currentHealth > maxHealth - maxHealth * 0.05F) {
         healAmount = maxHealth - currentHealth;
      } else {
         healAmount = maxHealth * 0.05F;
      }

      entity.m_5634_(healAmount);
      alterTearAmount(itemStack, (int)(-healAmount));
      return itemStack;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public int m_8105_(ItemStack stack) {
      return 32;
   }

   public void m_7373_(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag par4) {
      int currentTears = getTearAmount(itemStack);
      list.add(Component.m_237110_(ModI18n.ITEM_WATERING_CAN_TEARS, new Object[]{currentTears}).m_130940_(ChatFormatting.GRAY));
   }

   public static int getTearAmount(ItemStack itemStack) {
      return !itemStack.m_41782_() ? 0 : itemStack.m_41784_().m_128451_("tears");
   }

   public static void alterTearAmount(ItemStack itemStack, int amount) {
      int currentTears = getTearAmount(itemStack);
      itemStack.m_41784_().m_128405_("tears", Mth.m_14045_(currentTears + amount, 0, 250));
   }
}
