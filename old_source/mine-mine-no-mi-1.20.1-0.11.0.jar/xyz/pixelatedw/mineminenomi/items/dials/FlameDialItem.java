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
      super(block, (new Item.Properties()).m_41487_(16));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!world.f_46443_ && !player.m_6047_()) {
         Vec3 look = player.m_20154_();
         SmallFireball fireball = new SmallFireball(world, player, (double)1.0F, (double)1.0F, (double)1.0F);
         fireball.m_6034_(player.m_20185_(), player.m_20186_() + (double)1.5F, player.m_20189_());
         fireball.f_36813_ = look.f_82479_ * 0.2;
         fireball.f_36814_ = look.f_82480_ * 0.2;
         fireball.f_36815_ = look.f_82481_ * 0.2;
         world.m_7967_(fireball);
         player.m_36335_().m_41524_(this, 10);
         itemstack.m_41774_(1);
      }

      return InteractionResultHolder.m_19090_(itemstack);
   }
}
