package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
// import xyz.pixelatedw.mineminenomi.entities.projectiles.MilkyDialProjectile;

public class MilkyDialItem extends BlockItem {
   public MilkyDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16));
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!world.isClientSide) {
         // MilkyDialProjectile proj = new MilkyDialProjectile(player.level(), player);
         // world.addFreshEntity(proj);
         // proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
         player.getCooldowns().addCooldown(this, 10);
         itemstack.shrink(1);
      }

      return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
   }
}
