package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class BubblyCoralItem extends Item {
   public static final int EFFECT_DURATION = 3600;

   public BubblyCoralItem() {
      super((new Item.Properties()).m_41503_(3));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      if (player.m_21023_((MobEffect)ModEffects.BUBBLY_CORAL.get())) {
         return InteractionResultHolder.m_19098_(itemStack);
      } else {
         player.m_7292_(new MobEffectInstance((MobEffect)ModEffects.BUBBLY_CORAL.get(), 3600, 0));
         itemStack.m_41622_(1, player, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
         return InteractionResultHolder.m_19090_(itemStack);
      }
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      return false;
   }
}
