package xyz.pixelatedw.mineminenomi.abilities.sabi;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class RustBreakAbility extends Ability {

    public RustBreakAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sabi_sabi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            HitResult hit = entity.pick(5.0, 0.0F, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockPos pos = blockHit.getBlockPos();
                if (entity.level().getBlockState(pos).is(Blocks.IRON_BLOCK)) {
                    entity.level().destroyBlock(pos, false);
                    boolean isIngot = entity.getRandom().nextBoolean();
                    for (int i = 0; i < entity.getRandom().nextInt(3); i++) {
                        ItemStack stack = isIngot ? new ItemStack(Items.IRON_INGOT) : new ItemStack(Items.IRON_NUGGET);
                        ItemEntity item = new ItemEntity(entity.level(), pos.getX(), pos.getY(), pos.getZ(), stack);
                        entity.level().addFreshEntity(item);
                    }
                    this.startCooldown(entity, 400);
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.rust_break");
    }
}
