package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;


public class AxeDialItem extends BlockItem {
   public AxeDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16));
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!world.isClientSide) {
         net.minecraft.world.entity.projectile.Snowball proj = new net.minecraft.world.entity.projectile.Snowball(world, player);
         world.addFreshEntity(proj);
         proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
         player.getCooldowns().addCooldown(this, 10);
         itemstack.shrink(1);
      }

      return InteractionResultHolder.success(itemstack);
   }
}
