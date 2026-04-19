package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class SakeBottleItem extends Item {

    public SakeBottleItem() {
        super(new Item.Properties().stacksTo(5).food(ModFoods.ALCOHOL));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                // TODO: Open Create Crew Screen
            }
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            if (entity.hasEffect(ModEffects.DRUNK)) {
                MobEffectInstance effect = entity.getEffect(ModEffects.DRUNK);
                int amp = effect.getAmplifier();
                int duration = effect.getDuration();
                entity.addEffect(new MobEffectInstance(ModEffects.DRUNK, duration + 200, Math.min(amp + 1, 4)));
            } else {
                entity.addEffect(new MobEffectInstance(ModEffects.DRUNK, 400, 0));
            }
            stack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}
