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
      super(block, (new Item.Properties()).stacksTo(16));
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (!world.isClientSide() && !player.isCrouching()) {
         Vec3 look = player.getLookAngle();
         SmallFireball fireball = new SmallFireball(world, player, new Vec3(player.getLookAngle().x(), player.getLookAngle().y(), player.getLookAngle().z()));
         fireball.setPos(player.getX(), player.getY() + (double)1.5F, player.getZ());
         // fireball.xPower = // look.x * 0.2;
         // fireball.yPower = // look.y * 0.2;
         // fireball.zPower = // look.z * 0.2;
         world.addFreshEntity(fireball);
         player.getCooldowns().addCooldown(this, 10);
         itemstack.shrink(1);
      }

      return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
   }
}
