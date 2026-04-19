package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;

public class UnicycleItem extends Item {
   public UnicycleItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      ItemStack heldItem = player.m_21120_(InteractionHand.MAIN_HAND);
      HitResult hitResult = m_41435_(level, player, Fluid.ANY);
      if (hitResult.m_6662_() == Type.MISS) {
         return InteractionResultHolder.m_19098_(heldItem);
      } else if (hitResult.m_6662_() == Type.BLOCK) {
         UnicycleEntity unicycle = new UnicycleEntity(level);
         unicycle.m_6034_(hitResult.m_82450_().f_82479_, hitResult.m_82450_().f_82480_, hitResult.m_82450_().f_82481_);
         unicycle.m_146922_(player.m_146908_());
         if (!level.f_46443_) {
            level.m_7967_(unicycle);
            if (!player.m_150110_().f_35937_) {
               heldItem.m_41774_(1);
            }
         }

         player.m_36246_(Stats.f_12982_.m_12902_(this));
         return InteractionResultHolder.m_19092_(heldItem, level.m_5776_());
      } else {
         return InteractionResultHolder.m_19098_(heldItem);
      }
   }
}
