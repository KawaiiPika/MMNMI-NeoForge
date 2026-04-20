package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;

import java.util.List;

public class TenseiNoSoenAbility extends Ability {

    private static final int CHARGE_TIME = 60;
    private static final int COOLDOWN = 600;
    private static final int RANGE = 30;
    private static final float DAMAGE = 50.0F;

    public TenseiNoSoenAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_1"));
    }

    @Override
    public Result canUse(LivingEntity entity) {
        Result superResult = super.canUse(entity);
        if (superResult.isFail()) {
            return superResult;
        }
        return AbilityUseConditions.requiresInAir(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled via onTick using duration
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TIME) {
            // Charging phase
            if (!entity.level().isClientSide && duration % 2 == 0) {
                // Particles spawned on client side
            }
        } else {
            // Slamming phase
            if (!entity.onGround() && AbilityHelper.getDifferenceToFloor(entity) >= 2.0) {
                Vec3 velocity = entity.getDeltaMovement();
                AbilityHelper.setDeltaMovement(entity, velocity.x, -10.0, velocity.z);
                entity.fallDistance = 0.0F;
            } else {
                // Slam impact
                if (!entity.level().isClientSide) {
                    AABB area = entity.getBoundingBox().inflate(RANGE);
                    List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, area, e -> e != entity && e.distanceTo(entity) <= RANGE);
                    for (LivingEntity target : targets) {
                        target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                    }
                }

                this.startCooldown(entity, COOLDOWN);
                this.stop(entity);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.tenseinosoen");
    }
}
