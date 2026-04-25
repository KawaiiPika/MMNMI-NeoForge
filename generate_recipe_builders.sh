cat << 'INNER_EOF' > src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingDialRecipe.java
package xyz.pixelatedw.mineminenomi.api.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class SmithingDialRecipe implements SmithingRecipe {

    final Ingredient base;
    final Ingredient addition;
    final int additionAmount;
    final ItemStack result;

    public SmithingDialRecipe(Ingredient baseIngredient, Ingredient additionIngredient, int additionAmount, ItemStack result) {
        this.base = baseIngredient;
        this.addition = additionIngredient;
        this.additionAmount = additionAmount;
        this.result = result;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return this.base.test(input.base()) && this.addition.test(input.addition()) && input.addition().getCount() >= this.additionAmount;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider lookupProvider) {
        return this.result.copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider lookupProvider) {
        return this.result;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return this.addition.test(stack) && stack.getCount() >= this.additionAmount;
    }

    public int getAdditionalAmount() {
        return this.additionAmount;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SMITHING_DIAL.get();
    }

    public static class Serializer implements RecipeSerializer<SmithingDialRecipe> {

        public static final MapCodec<SmithingDialRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("base").forGetter(r -> r.base),
                Ingredient.CODEC_NONEMPTY.fieldOf("addition").forGetter(r -> r.addition),
                Codec.INT.optionalFieldOf("additionAmount", 1).forGetter(r -> r.additionAmount),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result)
        ).apply(inst, SmithingDialRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingDialRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, r -> r.base,
                Ingredient.CONTENTS_STREAM_CODEC, r -> r.addition,
                ByteBufCodecs.INT, r -> r.additionAmount,
                ItemStack.STREAM_CODEC, r -> r.result,
                SmithingDialRecipe::new
        );

        @Override
        public MapCodec<SmithingDialRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithingDialRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
INNER_EOF

cat << 'INNER_EOF' > src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/SmithingEnchantmentRecipe.java
package xyz.pixelatedw.mineminenomi.api.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;

public class SmithingEnchantmentRecipe implements SmithingRecipe {

    final Ingredient base;
    final Ingredient addition;
    final int additionAmount;
    final Holder<Enchantment> enchantment;
    final int enchantmentMaxLevel;

    public SmithingEnchantmentRecipe(Ingredient baseIngredient, Ingredient additionIngredient, int additionAmount, Holder<Enchantment> enchantment, int enchantmentMaxLevel) {
        this.base = baseIngredient;
        this.addition = additionIngredient;
        this.additionAmount = additionAmount;
        this.enchantment = enchantment;
        this.enchantmentMaxLevel = enchantmentMaxLevel;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return this.base.test(input.base()) && this.addition.test(input.addition()) && input.addition().getCount() >= this.additionAmount;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider lookupProvider) {
        ItemStack baseStack = input.base().copy();
        ItemEnchantments enchantments = baseStack.getEnchantments();
        if (enchantments.getLevel(this.enchantment) > 0) {
            return baseStack;
        } else {
            int additionCount = input.addition().getCount();
            int level = Math.min((int) Math.floor((double) (additionCount / this.additionAmount)), this.enchantmentMaxLevel);
            if (level > 0) {
                baseStack.enchant(this.enchantment, level);
            }
            return baseStack;
        }
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider lookupProvider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack) && stack.getEnchantments().getLevel(this.enchantment) <= 0;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return this.addition.test(stack) && stack.getCount() >= this.additionAmount;
    }

    public int getAdditionalAmount(SmithingRecipeInput input) {
        int additionCount = input.addition().getCount();
        int level = Math.min((int) Math.floor((double) (additionCount / this.additionAmount)), this.enchantmentMaxLevel);
        return level * this.additionAmount;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SMITHING_ENCHANTMENT.get();
    }

    public static class Serializer implements RecipeSerializer<SmithingEnchantmentRecipe> {

        public static final MapCodec<SmithingEnchantmentRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("base").forGetter(r -> r.base),
                Ingredient.CODEC_NONEMPTY.fieldOf("addition").forGetter(r -> r.addition),
                Codec.INT.optionalFieldOf("additionAmount", 1).forGetter(r -> r.additionAmount),
                Enchantment.CODEC.fieldOf("enchantment").forGetter(r -> r.enchantment),
                Codec.INT.optionalFieldOf("maxLevel", 1).forGetter(r -> r.enchantmentMaxLevel)
        ).apply(inst, SmithingEnchantmentRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingEnchantmentRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, r -> r.base,
                Ingredient.CONTENTS_STREAM_CODEC, r -> r.addition,
                ByteBufCodecs.INT, r -> r.additionAmount,
                ByteBufCodecs.holderRegistry(Registries.ENCHANTMENT), r -> r.enchantment,
                ByteBufCodecs.INT, r -> r.enchantmentMaxLevel,
                SmithingEnchantmentRecipe::new
        );

        @Override
        public MapCodec<SmithingEnchantmentRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithingEnchantmentRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
INNER_EOF

cat << 'INNER_EOF' > src/main/java/xyz/pixelatedw/mineminenomi/api/crafting/MultiChannelDyeRecipe.java
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
INNER_EOF
