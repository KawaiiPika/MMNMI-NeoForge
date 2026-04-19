package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AxeDialProjectile;

public class AxeDialItem extends BlockItem {
   public AxeDialItem(Block block) {
      super(block, (new Item.Properties()).m_41487_(16));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!world.f_46443_) {
         AxeDialProjectile proj = new AxeDialProjectile(player.m_9236_(), player);
         world.m_7967_(proj);
         proj.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, 2.0F, 1.0F);
         player.m_36335_().m_41524_(this, 10);
         itemstack.m_41774_(1);
      }

      return InteractionResultHolder.m_19090_(itemstack);
   }
}
