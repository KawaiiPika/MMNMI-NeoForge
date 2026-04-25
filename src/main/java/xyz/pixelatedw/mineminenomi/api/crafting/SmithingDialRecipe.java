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
