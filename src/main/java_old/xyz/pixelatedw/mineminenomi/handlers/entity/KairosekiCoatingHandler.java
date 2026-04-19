package xyz.pixelatedw.mineminenomi.handlers.entity;

import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.IKairosekiCoating;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.KairosekiCoatingCapability;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class KairosekiCoatingHandler {
   public static final String COATING_LEVEL_TAG = "coatingLevel";

   public static void applyExistingKairosekiCoatingFromItem(Player player, InteractionHand hand, Entity entity) {
      ItemStack stack = player.m_21120_(hand);
      int coatingLevel = stack.m_41784_().m_128451_("coatingLevel");
      if (coatingLevel > 0) {
         IKairosekiCoating coatingData = (IKairosekiCoating)KairosekiCoatingCapability.get(entity).orElse((Object)null);
         if (coatingData == null) {
            return;
         }

         coatingData.setCoatingLevel(coatingLevel);
      }

   }

   public static boolean applyKairosekiCoat(Player player, Entity target, ItemStack stack) {
      IKairosekiCoating coatingData = (IKairosekiCoating)KairosekiCoatingCapability.get(target).orElse((Object)null);
      if (coatingData != null && !coatingData.isFullyCoated() && !stack.m_41619_() && stack.m_41720_().equals(ModItems.DENSE_KAIROSEKI.get())) {
         int amount = Math.min(stack.m_41613_(), 5);
         if (coatingData.addCoatingLevel(amount) && !player.m_150110_().f_35937_) {
            stack.m_41774_(amount);
            return true;
         }
      }

      return false;
   }

   @Nullable
   public static ItemEntity dropCoatedItem(Entity target, ItemStack stack) {
      IKairosekiCoating coatingData = (IKairosekiCoating)KairosekiCoatingCapability.get(target).orElse((Object)null);
      if (coatingData != null && coatingData.getCoatingLevel() > 0) {
         stack.m_41784_().m_128405_("coatingLevel", coatingData.getCoatingLevel());
         return target.m_5552_(stack, 0.0F);
      } else {
         return null;
      }
   }
}
