package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ShadowItem extends Item {
   public ShadowItem() {
      super((new Item.Properties()).m_41489_(Foods.f_38828_));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19090_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof Player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         if (props == null) {
            return itemStack;
         }

         if (!props.hasShadow()) {
            props.setShadow(true);
            ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(entity), entity);
         } else if (entity.m_21124_(MobEffects.f_19600_) != null && entity.m_21124_(MobEffects.f_19606_) != null) {
            int duration = entity.m_21124_(MobEffects.f_19600_).m_19557_();
            int amplifier = 0;
            duration += 100;
            if (duration > 500) {
               amplifier = 1;
            }

            if (duration > 1200) {
               duration = 1200;
            }

            entity.m_21195_(MobEffects.f_19600_);
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, duration, amplifier));
            duration = entity.m_21124_(MobEffects.f_19606_).m_19557_();
            amplifier = 0;
            duration += 100;
            if (duration > 500) {
               amplifier = 1;
            }

            if (duration > 1200) {
               duration = 1200;
            }

            entity.m_21195_(MobEffects.f_19606_);
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, duration, amplifier));
         } else {
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 100, 0, false, false));
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 100, 0, false, false));
         }

         itemStack.m_41774_(1);
      }

      return itemStack;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.EAT;
   }
}
