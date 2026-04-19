package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class WantedPosterPackageItem extends Item {
   public WantedPosterPackageItem() {
      super(new Item.Properties());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!world.f_46443_) {
         BlockHitResult hitResult = Item.m_41435_(world, player, Fluid.NONE);
         if (hitResult.m_6662_() == Type.MISS) {
            return InteractionResultHolder.m_19098_(itemstack);
         }

         if (hitResult.m_6662_() != Type.BLOCK) {
            return InteractionResultHolder.m_19098_(itemstack);
         }

         Vec3 pos = hitResult.m_82450_();
         WantedPosterPackageEntity entity = (WantedPosterPackageEntity)((EntityType)ModEntities.WANTED_POSTER_PACKAGE.get()).m_20615_(world);
         entity.m_146884_(pos);
         world.m_7967_(entity);
      }

      if (!player.m_150110_().f_35937_) {
         itemstack.m_41774_(1);
      }

      return InteractionResultHolder.m_19090_(itemstack);
   }
}
