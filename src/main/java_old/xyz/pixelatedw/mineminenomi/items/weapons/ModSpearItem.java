package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class ModSpearItem extends ModSwordItem {
   public ModSpearItem(Tier tier, int damage, float attackSpeed) {
      super(tier, damage, attackSpeed);
   }

   public ModSpearItem(ModSwordItem.ModsBuilder mods) {
      super(mods, new Item.Properties());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (itemstack.m_41773_() >= itemstack.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(itemstack);
      } else if (EnchantmentHelper.m_44932_(itemstack) > 0 && !player.m_20070_()) {
         return InteractionResultHolder.m_19100_(itemstack);
      } else {
         if (!world.f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(player, ModAnimations.THROW_SPEAR, this.m_8105_(itemstack), true), player);
         }

         player.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemstack);
      }
   }

   public void m_5551_(ItemStack itemStack, Level world, LivingEntity entity, int timeLeft) {
      if (entity instanceof Player player) {
         if (!world.f_46443_) {
            ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(player, ModAnimations.THROW_SPEAR), player);
         }

         int i = this.m_8105_(itemStack) - timeLeft;
         if (i >= 10) {
            int j = EnchantmentHelper.m_44932_(itemStack);
            if (j <= 0 || player.m_20070_()) {
               itemStack.m_41622_(1, player, (p) -> p.m_21190_(entity.m_7655_()));
               player.m_36246_(Stats.f_12982_.m_12902_(this));
               if (j == 0) {
                  ThrownSpearEntity spear = new ThrownSpearEntity(player, itemStack);
                  spear.setAttackDamage(5.0F + this.m_43299_());
                  spear.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
                  if (player.m_150110_().f_35937_) {
                     spear.f_36705_ = Pickup.CREATIVE_ONLY;
                  }

                  world.m_7967_(spear);
                  world.m_6269_(player, spear, SoundEvents.f_12520_, SoundSource.PLAYERS, 1.0F, 1.0F);
                  if (!player.m_150110_().f_35937_) {
                     player.m_150109_().m_36057_(itemStack);
                  }
               } else if (j > 0) {
                  float f7 = player.m_146908_();
                  float f = player.m_146909_();
                  float f1 = -Mth.m_14031_(f7 * ((float)Math.PI / 180F)) * Mth.m_14089_(f * ((float)Math.PI / 180F));
                  float f2 = -Mth.m_14031_(f * ((float)Math.PI / 180F));
                  float f3 = Mth.m_14089_(f7 * ((float)Math.PI / 180F)) * Mth.m_14089_(f * ((float)Math.PI / 180F));
                  float f4 = Mth.m_14116_(f1 * f1 + f2 * f2 + f3 * f3);
                  float f5 = 3.0F * ((1.0F + (float)j) / 4.0F);
                  f1 *= f5 / f4;
                  f2 *= f5 / f4;
                  f3 *= f5 / f4;
                  player.m_5997_((double)f1, (double)f2, (double)f3);
                  player.m_204079_(20);
                  if (player.m_20096_()) {
                     player.m_6478_(MoverType.SELF, new Vec3((double)0.0F, (double)1.1999999F, (double)0.0F));
                  }

                  SoundEvent soundevent;
                  if (j >= 3) {
                     soundevent = SoundEvents.f_12519_;
                  } else if (j == 2) {
                     soundevent = SoundEvents.f_12518_;
                  } else {
                     soundevent = SoundEvents.f_12517_;
                  }

                  world.m_6269_((Player)null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
               }
            }
         }
      }

   }

   public UseAnim m_6164_(ItemStack pStack) {
      return UseAnim.NONE;
   }

   public int m_8105_(ItemStack pStack) {
      return 72000;
   }

   public int m_6473_() {
      return 1;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ == EnchantmentCategory.TRIDENT || enchantment.f_44672_ == EnchantmentCategory.BREAKABLE;
   }
}
