package xyz.pixelatedw.mineminenomi.items.weapons;

import xyz.pixelatedw.mineminenomi.init.ModTiers;

public class ScissorsItem extends ModSwordItem {
    public ScissorsItem() {
        super(ModTiers.SCISSORS, 5, -2.8F);
    }
    
    @Override
    public boolean canPerformAction(net.minecraft.world.item.ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_SHEARS_ACTIONS.contains(itemAbility) || super.canPerformAction(stack, itemAbility);
    }

    @Override
    public boolean hurtEnemy(net.minecraft.world.item.ItemStack stack, net.minecraft.world.entity.LivingEntity target, net.minecraft.world.entity.LivingEntity attacker) {
        if (!(attacker instanceof net.minecraft.world.entity.player.Player playerAttacker)) {
            return super.hurtEnemy(stack, target, attacker);
        } else {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats attackerStats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(playerAttacker);
            boolean hasPassive = false;
            // The logic from the original has been updated for the new ability architecture
            // In the new system, KageGiriAbility is not an active/passive hybrid that we can query via addIfValid.
            // Currently, shadows are just a boolean in PlayerStats. Let's update if the user has Kage Kage no Mi.
            if (attackerStats.getDevilFruit().isPresent() && attackerStats.getDevilFruit().get().toString().equals("mineminenomi:kage_kage_no_mi")) {
                hasPassive = true;
            }

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats targetStats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(target);
            if (targetStats != null && hasPassive && stack != null && stack.getItem() == xyz.pixelatedw.mineminenomi.init.ModWeapons.SCISSORS.get() && targetStats.hasShadow()) {
                targetStats.setHasShadow(false);
                playerAttacker.getInventory().placeItemBackInInventory(new net.minecraft.world.item.ItemStack(xyz.pixelatedw.mineminenomi.init.ModItems.SHADOW.get()));
                // ModNetwork.sendToAllTracking(new SRemoveShadowEventsPacket(target), target); // Will handle syncing via the new networking

                return super.hurtEnemy(stack, target, attacker);
            } else {
                return super.hurtEnemy(stack, target, attacker);
            }
        }
    }

    @Override
    public boolean mineBlock(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.level.block.state.BlockState state, net.minecraft.core.BlockPos pos, net.minecraft.world.entity.LivingEntity entityLiving) {
        if (!level.isClientSide && !state.is(net.minecraft.tags.BlockTags.FIRE)) {
            stack.hurtAndBreak(1, entityLiving, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
        }

        net.minecraft.world.level.block.Block block = state.getBlock();
        return !state.is(net.minecraft.tags.BlockTags.LEAVES) && block != net.minecraft.world.level.block.Blocks.COBWEB && block != net.minecraft.world.level.block.Blocks.SHORT_GRASS && block != net.minecraft.world.level.block.Blocks.FERN && block != net.minecraft.world.level.block.Blocks.DEAD_BUSH && block != net.minecraft.world.level.block.Blocks.VINE && block != net.minecraft.world.level.block.Blocks.TRIPWIRE && !state.is(net.minecraft.tags.BlockTags.WOOL) ? super.mineBlock(stack, level, state, pos, entityLiving) : true;
    }

    @Override
    public boolean isCorrectToolForDrops(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.block.state.BlockState state) {
        net.minecraft.world.level.block.Block block = state.getBlock();
        return block == net.minecraft.world.level.block.Blocks.COBWEB || block == net.minecraft.world.level.block.Blocks.REDSTONE_WIRE || block == net.minecraft.world.level.block.Blocks.TRIPWIRE;
    }

    @Override
    public float getDestroySpeed(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.block.state.BlockState state) {
        net.minecraft.world.level.block.Block block = state.getBlock();
        if (block != net.minecraft.world.level.block.Blocks.COBWEB && !state.is(net.minecraft.tags.BlockTags.LEAVES)) {
            return state.is(net.minecraft.tags.BlockTags.WOOL) ? 5.0F : super.getDestroySpeed(stack, state);
        } else {
            return 15.0F;
        }
    }

    @Override
    public net.minecraft.world.InteractionResult interactLivingEntity(net.minecraft.world.item.ItemStack stack, net.minecraft.world.entity.player.Player playerIn, net.minecraft.world.entity.LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        if (entity.level().isClientSide) {
            return net.minecraft.world.InteractionResult.PASS;
        } else if (entity instanceof net.neoforged.neoforge.common.IShearable target) {
            net.minecraft.core.BlockPos pos = entity.blockPosition();
            if (target.isShearable(playerIn, stack, entity.level(), pos)) {
                java.util.List<net.minecraft.world.item.ItemStack> drops = target.onSheared(playerIn, stack, entity.level(), pos);
                java.util.Random rand = new java.util.Random();
                drops.forEach(d -> {
                    net.minecraft.world.entity.item.ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
                    if (ent != null) {
                        ent.setDeltaMovement(ent.getDeltaMovement().add((rand.nextFloat() - rand.nextFloat()) * 0.1F, rand.nextFloat() * 0.05F, (rand.nextFloat() - rand.nextFloat()) * 0.1F));
                    }
                });
                stack.hurtAndBreak(1, entity, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
            }
            return net.minecraft.world.InteractionResult.SUCCESS;
        } else {
            return net.minecraft.world.InteractionResult.PASS;
        }
    }
}
