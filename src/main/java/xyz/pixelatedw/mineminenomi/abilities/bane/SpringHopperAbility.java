package xyz.pixelatedw.mineminenomi.abilities.bane;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SpringHopperAbility extends Ability {
    private int jumpPower = 0;

    public SpringHopperAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bane_bane_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            // Simple placeholder logic for jump boost in this port
            if (entity.onGround()) {
                Vec3 speed = entity.getLookAngle().multiply(0.25 * jumpPower, 1.0, 0.25 * jumpPower);
                entity.setDeltaMovement(speed.x, entity.getDeltaMovement().y + 1.0, speed.z);
                if (jumpPower < 9) {
                    jumpPower++;
                }
            } else {
                jumpPower = 0;
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.spring_hopper");
    }
}
