package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class BubblyCoralItem extends Item {
    public static final int EFFECT_DURATION = 3600;

    public BubblyCoralItem() {
        super(new Item.Properties().stacksTo(3));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (player.hasEffect(ModEffects.BUBBLY_CORAL)) {
            return InteractionResultHolder.fail(itemStack);
        }

        player.addEffect(new MobEffectInstance(ModEffects.BUBBLY_CORAL, EFFECT_DURATION, 0));

        itemStack.shrink(1);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
