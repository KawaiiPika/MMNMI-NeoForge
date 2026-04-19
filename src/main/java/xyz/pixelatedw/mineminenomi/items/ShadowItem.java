package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class ShadowItem extends Item {

    public ShadowItem() {
        super(new Item.Properties().stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null) {
                if (!stats.hasShadow()) {
                    stats.setHasShadow(true);
                    stats.sync(player);
                } else {
                    // Shadow boost logic (Kage Kage no Mi interaction)
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0));
                }
            }
            stack.shrink(1);
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }
}
