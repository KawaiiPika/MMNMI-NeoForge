package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModTiers;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class ChakramItem extends ModSwordItem {
   public ChakramItem() {
      super(ModTiers.CHAKRAM, 8, -3.2F);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (itemstack.m_41773_() >= itemstack.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(itemstack);
      } else {
         if (!world.f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(player, ModAnimations.THROW_CHAKRAM, this.m_8105_(itemstack), true), player);
         }

         player.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemstack);
      }
   }

   public void m_5551_(ItemStack itemStack, Level world, LivingEntity entity, int timeLeft) {
      if (entity instanceof Player player) {
         if (!world.f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(player, ModAnimations.THROW_CHAKRAM), player);
         }

         int i = this.m_8105_(itemStack) - timeLeft;
         if (i >= 4) {
            float extraPower = (float)Mth.m_14045_(i, 4, 20) / 10.0F;
            itemStack.m_41622_(1, player, (p) -> p.m_21190_(entity.m_7655_()));
            player.m_36246_(Stats.f_12982_.m_12902_(this));
            ChakramEntity chakram = new ChakramEntity(player, itemStack);
            chakram.setAttackDamage(1.0F + extraPower + this.m_43299_());
            chakram.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, 1.0F + extraPower, 1.0F);
            if (player.m_150110_().f_35937_) {
               chakram.f_36705_ = Pickup.CREATIVE_ONLY;
            }

            world.m_7967_(chakram);
            world.m_6269_(player, chakram, SoundEvents.f_11941_, SoundSource.PLAYERS, 1.0F, 1.5F);
            if (!player.m_150110_().f_35937_) {
               player.m_150109_().m_36057_(itemStack);
            }
         }
      }

   }

   public UseAnim m_6164_(ItemStack pStack) {
      return UseAnim.BOW;
   }

   public int m_8105_(ItemStack pStack) {
      return 72000;
   }

   public int m_6473_() {
      return 1;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ == EnchantmentCategory.BREAKABLE;
   }
}
