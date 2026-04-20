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
                xyz.pixelatedw.mineminenomi.networking.ModNetworking.sendTo(new xyz.pixelatedw.mineminenomi.networking.packets.SOpenCreateCrewScreenPacket(), (net.minecraft.server.level.ServerPlayer) player);
            }
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            // Apply food effects manually or let super handle eating, then add extra side effects
            player.awardStat(net.minecraft.stats.Stats.ITEM_USED.get(this));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), net.minecraft.sounds.SoundEvents.GENERIC_DRINK, net.minecraft.sounds.SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                net.minecraft.advancements.CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            if (entity.hasEffect(ModEffects.DRUNK)) {
                MobEffectInstance effect = entity.getEffect(ModEffects.DRUNK);
                int amp = effect.getAmplifier();
                int duration = effect.getDuration();
                entity.removeEffect(ModEffects.DRUNK);
                entity.addEffect(new MobEffectInstance(ModEffects.DRUNK, duration + 200, Math.min(amp + 1, 4)));
            } else {
                entity.addEffect(new MobEffectInstance(ModEffects.DRUNK, 400, 0));
            }
            if (!player.getAbilities().instabuild) stack.shrink(1);
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}
