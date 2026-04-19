package xyz.pixelatedw.mineminenomi.api.events;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

public class SmithingTableEvent extends Event {
   private Player player;
   private SmithingRecipe recipe;
   private Container container;
   private ItemStack baseSlot;
   private ItemStack additionSlot;
   private ItemStack resultSlot;
   private int baseQuantity;

   public SmithingTableEvent(Container container, Player player, SmithingRecipe recipe, @Nullable ItemStack baseSlot, @Nullable ItemStack additionSlot) {
      this.baseSlot = ItemStack.f_41583_;
      this.additionSlot = ItemStack.f_41583_;
      this.resultSlot = ItemStack.f_41583_;
      this.container = container;
      this.player = player;
      this.recipe = recipe;
      this.baseSlot = baseSlot != null ? baseSlot : ItemStack.f_41583_;
      this.additionSlot = additionSlot != null ? additionSlot : ItemStack.f_41583_;
   }

   public Container getContainer() {
      return this.container;
   }

   public SmithingRecipe getRecipe() {
      return this.recipe;
   }

   public Player getPlayer() {
      return this.player;
   }

   public ItemStack getBaseSlot() {
      return this.baseSlot;
   }

   public ItemStack getAdditionSlot() {
      return this.additionSlot;
   }

   public ItemStack getResultSlot() {
      return this.resultSlot;
   }

   public int getBaseQuantity() {
      return this.baseQuantity;
   }

   public void setBaseSlot(@Nullable ItemStack slot) {
      this.baseSlot = slot;
   }

   public void setAdditionSlot(@Nullable ItemStack slot) {
      this.additionSlot = slot;
   }
}
