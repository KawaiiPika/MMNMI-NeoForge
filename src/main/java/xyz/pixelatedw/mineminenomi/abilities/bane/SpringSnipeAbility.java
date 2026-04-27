package xyz.pixelatedw.mineminenomi.abilities.bane;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SpringSnipeAbility extends Ability {

    public SpringSnipeAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bane_bane_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Starts the charge phase
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        // Charge phase is 10 ticks
        if (duration < 10) {
            entity.setDeltaMovement(0, 0, 0); // freeze while charging
        } else if (duration == 10) {
            // End charge, start dash
            Vec3 speed = entity.getLookAngle().scale(6.0);
            entity.setDeltaMovement(speed);
        } else if (duration > 10 && duration <= 20) {
            // Dash phase (10 ticks)
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(1.6))) {
                if (target instanceof LivingEntity livingTarget) {
                    // Simple hit tracking: we shouldn't hit the same target multiple times.
                    // But for simplicity in this port, we hit any living target.
                    // To prevent multi-hit on the same tick, we just hurt them.
                    // Vanilla immunity frames (invulnerableTime) will prevent multiple hits.
                    livingTarget.hurt(entity.damageSources().mobAttack(entity), 50.0F);
                }
            }
        } else if (duration > 20) {
            this.stop(entity);
            this.startCooldown(entity, 280);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.spring_snipe");
    }
}
