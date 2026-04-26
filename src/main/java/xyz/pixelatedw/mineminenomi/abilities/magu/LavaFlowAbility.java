package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class LavaFlowAbility extends Ability {

    private static final int HOLD_TIME = 100;
    private int originY = -1;

    public LavaFlowAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.originY = entity.blockPosition().getY() - 5;
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (this.originY >= 0) {
                BlockPos pos = entity.blockPosition();
                boolean isBlockBelowOrigin = pos.getY() < this.originY;
                boolean areEyesInLava = entity.isEyeInFluidType(net.neoforged.neoforge.common.NeoForgeMod.LAVA_TYPE.value());

                if (!areEyesInLava) {
                    MaguHelper.generateLavaPool(entity.level(), entity.blockPosition().below(2), 4);
                } else if (areEyesInLava && !isBlockBelowOrigin) {
                    MaguHelper.generateLavaPool(entity.level(), entity.blockPosition().below(1), 3);
                }
            }
        }
        
        if (duration >= HOLD_TIME) {
            this.stop(entity);
            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.lava_flow");
    }
}
