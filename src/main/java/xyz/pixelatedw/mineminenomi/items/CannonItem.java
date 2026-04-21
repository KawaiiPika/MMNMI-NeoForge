package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;

public class CannonItem extends Item {
    public CannonItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.fail(heldItem);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            // TODO: Phase 3 - Spawn CannonEntity at hitResult position once entities are ported
            // CannonEntity cannon = new CannonEntity(level);
            // cannon.setPos(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
            // cannon.setYRot(player.getYRot());
            // if (!level.isClientSide()) {
            //     level.addFreshEntity(cannon);
            //     if (!player.getAbilities().instabuild) heldItem.shrink(1);
            // }
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());
        }
        return InteractionResultHolder.fail(heldItem);
    }
}
