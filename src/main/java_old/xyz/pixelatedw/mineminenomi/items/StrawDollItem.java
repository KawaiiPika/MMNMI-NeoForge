package xyz.pixelatedw.mineminenomi.items;

import java.awt.Color;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.enums.SoulboundMark;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class StrawDollItem extends Item implements IMultiChannelColorItem {
   public StrawDollItem() {
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
            } else if (props.hasStrawDoll()) {
               player.m_150109_().m_36057_(itemStack);
               return InteractionResultHolder.m_19100_(itemStack);
            } else if (itemStack.m_41783_() != null && owner == player) {
               props.setStrawDoll(true);
               ModNetwork.sendToServer(new SSyncEntityStatsPacket(player, props));
               player.m_150109_().m_36057_(itemStack);
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

                  EntityStatsCapability.get((LivingEntity)target.getValue()).ifPresent((props) -> props.setStrawDoll(true));
               } else {
                  ExtendedWorldData.get().markRestore((UUID)target.getKey(), SoulboundMark.RESTORE_DOLL);
               }
            }
         }

         return false;
      }
   }

   public static ItemStack createDollStack(LivingEntity target) {
      ItemStack stack = new ItemStack((ItemLike)ModItems.STRAW_DOLL.get());
      SoulboundItemHelper.setOwner(stack, target);
      float red = target.m_217043_().m_188501_();
      float green = target.m_217043_().m_188501_();
      float blue = target.m_217043_().m_188501_();
      IMultiChannelColorItem.dyeArmor(stack, 0, (new Color(red, green, blue)).getRGB());
      setStrawDollHealth(stack, target.m_21223_());
      Component hoverName = Component.m_237110_(ModI18n.ITEM_STRAW_DOLL, new Object[]{target.m_5446_().getString()});
      stack.m_41714_(hoverName);
      return stack;
   }

   /** @deprecated */
   @Deprecated
   public static void setStrawDollOwner(ItemStack itemStack, LivingEntity entity) {
      SoulboundItemHelper.setOwner(itemStack, entity);
      float red = entity.m_217043_().m_188501_();
      float green = entity.m_217043_().m_188501_();
      float blue = entity.m_217043_().m_188501_();
      itemStack.m_41698_("display").m_128405_("color", (new Color(red, green, blue)).getRGB());
   }

   public static float getStrawDollHealth(ItemStack itemStack) {
      return itemStack.m_41784_().m_128457_("health");
   }

   public static void setStrawDollHealth(ItemStack itemStack, float health) {
      float maxHealth = (float)(ServerConfig.getDorikiLimit() / ServerConfig.getHealthGainFrequency());
      itemStack.m_41784_().m_128350_("health", Mth.m_14036_(health, 0.0F, maxHealth));
   }

   public static void alterStrawDollHealth(ItemStack itemStack, float health) {
      float maxHealth = (float)(ServerConfig.getDorikiLimit() / ServerConfig.getHealthGainFrequency());
      float currentHealth = itemStack.m_41784_().m_128457_("health");
      itemStack.m_41784_().m_128350_("health", Mth.m_14036_(currentHealth + health, 0.0F, maxHealth));
   }

   public int getDefaultLayerColor(int layer) {
      return -1;
   }

   public boolean canBeDyed() {
      return true;
   }

   public int getMaxLayers() {
      return 1;
   }
}
