package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class WateringCanItem extends Item {
    private static final int MAX_TEARS = 250;

    public WateringCanItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();

        if (currentHealth >= maxHealth) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            int currentTears = getTearAmount(itemStack);
            if (currentTears > 0) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(itemStack);
            }
            return InteractionResultHolder.pass(itemStack);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            float currentHealth = entity.getHealth();
            float maxHealth = entity.getMaxHealth();
            float healAmount = Math.min(maxHealth * 0.05F, maxHealth - currentHealth);

            entity.heal(healAmount);
            alterTearAmount(stack, (int) (-healAmount));
        }
        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int currentTears = getTearAmount(stack);
        tooltip.add(Component.translatable("item.mineminenomi.watering_can.tears", currentTears).withStyle(ChatFormatting.GRAY));
    }

    public static int getTearAmount(ItemStack itemStack) {
        CustomData data = itemStack.get(DataComponents.CUSTOM_DATA);
        return data != null ? data.copyTag().getInt("tears") : 0;
    }

    public static void alterTearAmount(ItemStack itemStack, int amount) {
        int currentTears = getTearAmount(itemStack);
        CustomData.update(DataComponents.CUSTOM_DATA, itemStack, tag -> {
            tag.putInt("tears", Mth.clamp(currentTears + amount, 0, MAX_TEARS));
        });
    }
}
