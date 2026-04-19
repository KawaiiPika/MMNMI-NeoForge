package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ExtolPouchItem extends Item {
   public ExtolPouchItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public void m_6883_(ItemStack itemStack, Level world, Entity entity, int par4, boolean par5) {
      if (!world.f_46443_ && !itemStack.m_41782_()) {
         itemStack.m_41784_().m_128356_("extol", (long)((int)WyHelper.randomWithRange(5, 250)));
      }

   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      ItemStack itemstack = player.m_21120_(hand);
      if (props == null) {
         return InteractionResultHolder.m_19100_(itemstack);
      } else if (!world.f_46443_) {
         long amount = itemstack.m_41784_().m_128454_("extol");
         boolean obtainedFullAmount = props.getExtol() + amount <= 999999999L;
         if (obtainedFullAmount) {
            props.alterExtol(amount, StatChangeSource.NATURAL);
            ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
            player.m_150109_().m_36057_(itemstack);
            player.m_213846_(Component.m_237110_(ModI18n.ITEM_MESSAGE_POUCH_EXTOL_GAINED, new Object[]{amount}));
            return InteractionResultHolder.m_19090_(itemstack);
         } else if (props.getExtol() < 999999999L) {
            long freeAmount = 999999999L - props.getExtol();
            long leftoverAmount = amount - freeAmount;
            if (leftoverAmount < 0L) {
               return InteractionResultHolder.m_19100_(itemstack);
            } else {
               props.alterExtol(freeAmount, StatChangeSource.NATURAL);
               ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
               itemstack.m_41784_().m_128356_("extol", leftoverAmount);
               player.m_213846_(Component.m_237110_(ModI18n.ITEM_MESSAGE_POUCH_EXTOL_GAINED, new Object[]{amount}));
               return InteractionResultHolder.m_19090_(itemstack);
            }
         } else {
            player.m_213846_(this.m_41466_());
            player.m_213846_(ModI18n.ITEM_MESSAGE_EXTOL_FULL);
            return InteractionResultHolder.m_19100_(itemstack);
         }
      } else {
         return InteractionResultHolder.m_19090_(itemstack);
      }
   }

   public void m_7373_(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
      if (itemStack.m_41782_()) {
         list.add(Component.m_237110_(ModI18n.ITEM_MESSAGE_EXTOL_POUCH_AMOUNT, new Object[]{itemStack.m_41784_().m_128454_("extol")}).m_130940_(ChatFormatting.GRAY));
      }

   }
}
