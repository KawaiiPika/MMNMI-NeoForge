package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class UltraColaItem extends Item {
   public static final int ULTRA_COLA_BONUS = 20;
   public static final int COLA_REFILL = 100;

   public UltraColaItem() {
      super((new Item.Properties()).m_41487_(16).m_41489_(ModFoods.COLA));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19090_(itemstack);
   }

   public ItemStack m_5922_(ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.f_46443_ && entity instanceof ServerPlayer player) {
         EntityStatsCapability.get(player).ifPresent((props) -> {
            if (props.isCyborg()) {
               if (props.getUltraCola() <= 19) {
                  props.addUltraCola(1);
                  props.alterCola(50);
               } else {
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 100, 0, false, false));
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 200, 0, false, false));
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 200, 0, false, false));
                  props.alterCola(100);
               }

               ModNetwork.sendTo(new SUpdateColaAmountPacket(entity), player);
            } else {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 250, 0));
            }

         });
         player.m_5584_(world, itemStack);
         if (!player.m_150110_().f_35937_) {
            player.m_36356_(new ItemStack((ItemLike)ModItems.EMPTY_ULTRA_COLA.get()));
         }
      }

      return itemStack;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }
}
