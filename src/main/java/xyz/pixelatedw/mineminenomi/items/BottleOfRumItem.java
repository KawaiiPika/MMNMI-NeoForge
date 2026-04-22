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

public class BottleOfRumItem extends Item {

    public BottleOfRumItem() {
        super(new Item.Properties().stacksTo(5).food(ModFoods.ALCOHOL));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            if (entity.hasEffect(ModEffects.DRUNK)) {
                MobEffectInstance effect = entity.getEffect(ModEffects.DRUNK);
                int amp = effect.getAmplifier();
                int duration = effect.getDuration();
                entity.addEffect(new MobEffectInstance(ModEffects.DRUNK, duration + 400, Math.min(amp + 2, 4)));
            } else {
                entity.addEffect(new MobEffectInstance(ModEffects.DRUNK, 600, 1));
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
