package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class SeaKingMeatItem extends Item {
   public SeaKingMeatItem() {
      super((new Item.Properties()).m_41489_(ModFoods.SEA_KING_MEAT));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19090_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof Player player) {
         player.m_5584_(world, itemStack);
      }

      return itemStack;
   }
}
