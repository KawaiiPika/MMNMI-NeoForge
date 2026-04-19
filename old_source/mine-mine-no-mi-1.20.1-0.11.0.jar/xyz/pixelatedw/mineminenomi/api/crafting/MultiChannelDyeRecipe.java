package xyz.pixelatedw.mineminenomi.api.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class MultiChannelDyeRecipe extends CustomRecipe {
   private int layer = 0;

   public MultiChannelDyeRecipe(ResourceLocation id, CraftingBookCategory category) {
      super(id, category);
   }

   public boolean matches(CraftingContainer container, Level level) {
      ItemStack itemstack = ItemStack.f_41583_;
      List<ItemStack> list = Lists.newArrayList();

      for(int i = 0; i < container.m_6643_(); ++i) {
         ItemStack itemstack1 = container.m_8020_(i);
         if (!itemstack1.m_41619_()) {
            Item var8 = itemstack1.m_41720_();
            if (var8 instanceof IMultiChannelColorItem) {
               IMultiChannelColorItem colorItem = (IMultiChannelColorItem)var8;
               if (!colorItem.canBeDyed()) {
                  return false;
               }

               if (!itemstack.m_41619_()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (!(itemstack1.m_41720_() instanceof DyeItem)) {
                  return false;
               }

               list.add(itemstack1);
            }
         }
      }

      return !itemstack.m_41619_() && !list.isEmpty();
   }

   public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
      List<DyeItem> list = Lists.newArrayList();
      ItemStack itemstack = ItemStack.f_41583_;

      for(int i = 0; i < container.m_6643_(); ++i) {
         ItemStack itemstack1 = container.m_8020_(i);
         if (!itemstack1.m_41619_()) {
            Item item = itemstack1.m_41720_();
            if (item instanceof IMultiChannelColorItem) {
               IMultiChannelColorItem colorItem = (IMultiChannelColorItem)item;
               this.layer = Math.min(this.layer, colorItem.getMaxLayers() - 1);
               if (!itemstack.m_41619_()) {
                  return ItemStack.f_41583_;
               }

               itemstack = itemstack1.m_41777_();
            } else {
               if (!(item instanceof DyeItem)) {
                  return ItemStack.f_41583_;
               }

               list.add((DyeItem)item);
            }
         }
      }

      return !itemstack.m_41619_() && !list.isEmpty() ? IMultiChannelColorItem.dyeArmor(itemstack, this.layer, list) : ItemStack.f_41583_;
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer)ModRecipes.MULTI_CHANNEL_ARMOR_DYE.get();
   }

   public void setLayer(int layer) {
      this.layer = layer;
   }

   public int getLayer() {
      return this.layer;
   }
}
