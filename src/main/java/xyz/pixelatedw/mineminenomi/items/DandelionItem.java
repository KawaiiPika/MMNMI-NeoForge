package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class DandelionItem extends Item {
    public DandelionItem() {
        super(new Item.Properties().food(Foods.DRIED_KELP)); // Changed to a valid 1.21.1 food component
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity entity) {
        if (!world.isClientSide() && entity instanceof Player player) {
            player.getCooldowns().addCooldown(this, 200);
            entity.removeAllEffects();
            world.broadcastEntityEvent(entity, (byte)30);
            entity.heal(getHealAmount(itemStack));
            itemStack.shrink(1);
        }

        return itemStack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    public static float getHealAmount(ItemStack itemStack) {
        return itemStack.getOrDefault(ModDataComponents.AMOUNT.get(), 0f);
    }

    public static void setHealAmount(ItemStack itemStack, float amount) {
        float maxHealth = (float)(ServerConfig.getDorikiLimit() / ServerConfig.getHealthGainFrequency());
        itemStack.set(ModDataComponents.AMOUNT.get(), Mth.clamp(amount, 0.0F, maxHealth));
    }
}
