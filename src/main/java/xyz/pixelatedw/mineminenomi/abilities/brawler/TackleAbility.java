package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class TackleAbility extends Ability {

    private boolean hasHit = false;

    public TackleAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        entity.setDeltaMovement(look.x * 2.0, 0.2, look.z * 2.0);
        this.hasHit = false;
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!this.hasHit) {
            AABB area = entity.getBoundingBox().inflate(0.5);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity) {
                    target.hurt(entity.damageSources().mobAttack(entity), 10.0f);
                    Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(1.2);
                    target.setDeltaMovement(knockback.x, 0.4, knockback.z);
                    this.hasHit = true;
                    break;
                }
            }
        }
        
        if (duration >= 10 || this.hasHit) {
            this.startCooldown(entity, 80);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Tackle");
    }
}
