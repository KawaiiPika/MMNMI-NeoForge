package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class FlameDialItem extends BlockItem {
   public FlameDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16).component(xyz.pixelatedw.mineminenomi.init.ModDataComponents.ENERGY.get(), 0));
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!world.isClientSide && !player.isShiftKeyDown()) {
         Vec3 look = player.getLookAngle();
         SmallFireball fireball = new SmallFireball(world, player, look.normalize().scale(0.2));
         fireball.setPos(player.getX(), player.getY() + (double)1.5F, player.getZ());



         world.addFreshEntity(fireball);
         player.getCooldowns().addCooldown(this, 10);
         itemstack.shrink(1);
      }

      return InteractionResultHolder.success(itemstack);
   }
}
