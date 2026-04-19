package xyz.pixelatedw.mineminenomi.items.armors;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMaterials;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaBackpackItem extends Mod3DArmorItem {
   private static final int COLA_REFILL_TICKS = 20;

   public ColaBackpackItem() {
      super(ModMaterials.COLA_BACKPACK_MATERIAL, "cola_backpack", Type.CHESTPLATE);
   }

   public void onArmorTick(ItemStack stack, Level world, Player player) {
      if (!world.f_46443_) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props != null) {
            if (props.isCyborg() && player.f_19797_ % 20 == 0) {
               int colaSlot = this.getColaSlot(player);
               int ultraColaSlot = this.getUltraColaSlot(player);
               if (colaSlot != -1 && props.getCola() + 25 <= props.getMaxCola()) {
                  props.alterCola(25);
                  player.m_150109_().m_7407_(colaSlot, 1);
                  player.m_36356_(new ItemStack((ItemLike)ModItems.EMPTY_COLA.get()));
               } else if (ultraColaSlot != -1 && props.getCola() + 100 <= props.getMaxCola()) {
                  props.alterCola(100);
                  player.m_150109_().m_7407_(ultraColaSlot, 1);
                  player.m_36356_(new ItemStack((ItemLike)ModItems.EMPTY_ULTRA_COLA.get()));
               }

               ModNetwork.sendTo(new SUpdateColaAmountPacket(player), (ServerPlayer)player);
            }

         }
      }
   }

   public int getColaSlot(Player player) {
      if (!player.m_150109_().m_36063_(new ItemStack((ItemLike)ModItems.COLA.get()))) {
         return -1;
      } else {
         for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack stack = player.m_150109_().m_8020_(i);
            if (!stack.m_41619_() && stack.m_41720_() == ModItems.COLA.get()) {
               return i;
            }
         }

         return -1;
      }
   }

   public int getUltraColaSlot(Player player) {
      if (!player.m_150109_().m_36063_(new ItemStack((ItemLike)ModItems.ULTRA_COLA.get()))) {
         return -1;
      } else {
         for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack stack = player.m_150109_().m_8020_(i);
            if (!stack.m_41619_() && stack.m_41720_() == ModItems.ULTRA_COLA.get()) {
               return i;
            }
         }

         return -1;
      }
   }
}
