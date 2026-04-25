package xyz.pixelatedw.mineminenomi.api.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class MultiChannelDyeRecipe extends CustomRecipe {
   private int layer = 0;

   public MultiChannelDyeRecipe(CraftingBookCategory category) {
      super(category);
   }

   @Override
   public boolean matches(CraftingInput container, Level level) {
      ItemStack itemstack = ItemStack.EMPTY;
      List<ItemStack> list = Lists.newArrayList();

      for(int i = 0; i < container.size(); ++i) {
         ItemStack itemstack1 = container.getItem(i);
         if (!itemstack1.isEmpty()) {
            Item var8 = itemstack1.getItem();
            if (var8 instanceof IMultiChannelColorItem) {
               IMultiChannelColorItem colorItem = (IMultiChannelColorItem)var8;
               if (!colorItem.canBeDyed()) {
                  return false;
               }

               if (!itemstack.isEmpty()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (!(itemstack1.getItem() instanceof DyeItem)) {
                  return false;
               }

               list.add(itemstack1);
            }
         }
      }

      return !itemstack.isEmpty() && !list.isEmpty();
   }

   @Override
   public ItemStack assemble(CraftingInput container, net.minecraft.core.HolderLookup.Provider registryAccess) {
      List<DyeItem> list = Lists.newArrayList();
      ItemStack itemstack = ItemStack.EMPTY;

      for(int i = 0; i < container.size(); ++i) {
         ItemStack itemstack1 = container.getItem(i);
         if (!itemstack1.isEmpty()) {
            Item item = itemstack1.getItem();
            if (item instanceof IMultiChannelColorItem) {
               IMultiChannelColorItem colorItem = (IMultiChannelColorItem)item;
               this.layer = Math.min(this.layer, colorItem.getMaxLayers() - 1);
               if (!itemstack.isEmpty()) {
                  return ItemStack.EMPTY;
               }

               itemstack = itemstack1.copy();
            } else {
               if (!(item instanceof DyeItem)) {
                  return ItemStack.EMPTY;
               }

               list.add((DyeItem)item);
            }
         }
      }

      return !itemstack.isEmpty() && !list.isEmpty() ? IMultiChannelColorItem.dyeArmor(itemstack, this.layer, list) : ItemStack.EMPTY;
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> getSerializer() {
      return ModRecipes.MULTI_CHANNEL_ARMOR_DYE.get();
   }

   public void setLayer(int layer) {
      this.layer = layer;
   }

   public int getLayer() {
      return this.layer;
   }
}
