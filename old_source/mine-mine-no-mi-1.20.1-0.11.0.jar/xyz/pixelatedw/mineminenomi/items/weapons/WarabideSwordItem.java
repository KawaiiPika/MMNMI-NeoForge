package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.wara.WarabideSwordAbility;
import xyz.pixelatedw.mineminenomi.init.ModTiers;

public class WarabideSwordItem extends AbilitySwordItem<WarabideSwordAbility> {
   public WarabideSwordItem() {
      super(ModTiers.WARABIDE_SWORD, WarabideSwordAbility.INSTANCE, 7);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      InteractionResultHolder<ItemStack> result = super.m_7203_(world, player, hand);
      return result;
   }
}
