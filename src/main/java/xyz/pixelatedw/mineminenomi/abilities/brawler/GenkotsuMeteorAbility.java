package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GenkotsuMeteorAbility extends Ability {

    private boolean isJumping = false;

    public GenkotsuMeteorAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.setDeltaMovement(0, 1.2, 0);
        this.isJumping = true;
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isJumping && entity.onGround() && duration > 5) {
            this.performImpact(entity);
            this.isJumping = false;
            this.startCooldown(entity, 200);
            this.stop(entity);
        }
        
        if (duration >= 60) { // Timeout
            this.stop(entity);
        }
    }

    private void performImpact(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        AABB area = entity.getBoundingBox().inflate(6.0);
        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                target.hurt(entity.damageSources().mobAttack(entity), 25.0f);
                Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(2.0);
                target.setDeltaMovement(knockback.x, 0.8, knockback.z);
            }
        }
        entity.sendSystemMessage(Component.literal("Genkotsu Meteor!"));
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Genkotsu Meteor");
    }
}
