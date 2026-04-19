package xyz.pixelatedw.mineminenomi.items;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class HeartItem extends Item {
   public HeartItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.m_21120_(hand);
      if (world.f_46443_) {
         return InteractionResultHolder.m_19100_(itemStack);
      } else if (itemStack.m_41783_() == null) {
         player.m_150109_().m_36057_(itemStack);
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         LivingEntity owner = (LivingEntity)SoulboundItemHelper.getOwner(world, itemStack).getValue();
         if (owner == null) {
            return InteractionResultHolder.m_19100_(itemStack);
         } else {
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(owner).orElse((Object)null);
            if (props == null) {
               return InteractionResultHolder.m_19100_(itemStack);
            } else if (props.hasHeart()) {
               player.m_150109_().m_36057_(itemStack);
               return InteractionResultHolder.m_19100_(itemStack);
            } else if (itemStack.m_41783_() != null) {
               if (owner == player) {
                  props.setHeart(true);
                  ModNetwork.sendToServer(new SSyncEntityStatsPacket(player, props));
                  player.m_150109_().m_36057_(itemStack);
               } else {
                  owner.m_6469_(ModDamageSources.getInstance().heartAttack(player), 5.0F);
                  owner.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 40, 1));
                  owner.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 40, 1));
                  if (owner.m_21223_() <= 0.0F) {
                     player.m_150109_().m_36057_(itemStack);
                  }
               }

               return InteractionResultHolder.m_19090_(itemStack);
            } else {
               return InteractionResultHolder.m_19100_(itemStack);
            }
         }
      }
   }

   public boolean onEntityItemUpdate(ItemStack itemStack, ItemEntity entityItem) {
      if (entityItem.m_9236_().f_46443_) {
         return false;
      } else {
         if (itemStack.m_41783_() != null) {
            Pair<UUID, LivingEntity> target = SoulboundItemHelper.getOwner(entityItem.m_9236_(), itemStack);
            boolean isBurning = entityItem.m_6060_();
            boolean isInVoid = entityItem.m_20183_().m_123342_() < -1;
            if (isBurning || isInVoid) {
               if (target.getValue() != null) {
                  for(ItemStack stack : ((LivingEntity)target.getValue()).m_6167_()) {
                     if (stack.m_41720_() == Items.f_42747_) {
                        stack.m_41774_(stack.m_41613_());
                     }
                  }

                  ((LivingEntity)target.getValue()).m_6469_(ModDamageSources.getInstance().heartAttack((LivingEntity)null), Float.MAX_VALUE);
               } else {
                  ExtendedWorldData.get().markDead((UUID)target.getKey());
               }
            }
         }

         return false;
      }
   }

   public static ItemStack createHeartStack(LivingEntity target) {
      ItemStack stack = new ItemStack((ItemLike)ModItems.HEART.get());
      SoulboundItemHelper.setOwner(stack, target);
      Component hoverName = Component.m_237110_(ModI18n.ITEM_HEART, new Object[]{target.m_5446_().getString()});
      stack.m_41714_(hoverName);
      return stack;
   }
}
