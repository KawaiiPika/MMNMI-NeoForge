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
