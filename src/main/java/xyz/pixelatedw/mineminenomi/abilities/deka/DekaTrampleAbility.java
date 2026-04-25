package xyz.pixelatedw.mineminenomi.abilities.deka;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DekaTrampleAbility extends Ability {

    public DekaTrampleAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "deka_deka_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && entity.onGround() && entity.isSprinting()) {
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(5.0))) {
                if (target instanceof LivingEntity living) {
                    living.hurt(entity.damageSources().mobAttack(entity), 8.0F);
                    Vec3 knockback = entity.getLookAngle().scale(2.0);
                    living.setDeltaMovement(knockback.x, 0.2, knockback.z);
                }
            }
        }
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.deka_trample");
    }
}
