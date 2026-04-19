package xyz.pixelatedw.mineminenomi.integrations.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;

public class SimpleCurioItem implements ICurio {
   private final ItemStack stack;

   public SimpleCurioItem(ItemStack stack) {
      this.stack = stack;
   }

   public void curioTick(SlotContext ctx) {
      LivingEntity var3 = ctx.entity();
      if (var3 instanceof Player player) {
         this.stack.m_41720_().onInventoryTick(this.stack, ctx.entity().m_9236_(), player, ctx.index(), ctx.index());
      }

   }

   public void onEquip(SlotContext slotContext, ItemStack prevStack) {
      ProgressionHandler.checkForEquipmentUnlocks(slotContext.entity());
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack) {
      ProgressionHandler.checkForEquipmentUnlocks(slotContext.entity());
   }

   public boolean canEquipFromUse(SlotContext slotContext) {
      return true;
   }

   public ItemStack getStack() {
      return this.stack;
   }
}
