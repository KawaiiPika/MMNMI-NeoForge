package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class CookedSeaKingMeatItem extends Item {
   public CookedSeaKingMeatItem() {
      super((new Item.Properties()).m_41489_(ModFoods.COOKED_SEA_KING_MEAT));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19090_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof Player player) {
         player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 100, 0));
         player.m_5634_(10.0F + player.m_21233_() / 10.0F);
         player.m_5584_(world, itemStack);
      }

      return itemStack;
   }
}
