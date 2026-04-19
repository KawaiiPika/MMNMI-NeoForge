package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ChargedPunchAbility extends Ability {

    private boolean isCharging = false;
    private long chargeStartTime = 0;

    public ChargedPunchAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isCharging) {
            this.isCharging = true;
            this.chargeStartTime = entity.level().getGameTime();
            if (!entity.level().isClientSide) {
                entity.sendSystemMessage(Component.literal("Charging Punch..."));
            }
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isCharging) {
            long currentTime = entity.level().getGameTime();
            if (currentTime - this.chargeStartTime >= 20) { // 1 second charge
                this.performPunch(entity);
                this.isCharging = false;
                this.startCooldown(entity, 100);
                this.stop(entity);
            }
        }
    }

    private void performPunch(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        Vec3 look = entity.getLookAngle();
        Vec3 punchPos = entity.getEyePosition().add(look.scale(2.0));
        AABB area = new AABB(punchPos.subtract(1.5, 1.5, 1.5), punchPos.add(1.5, 1.5, 1.5));
        
        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                target.hurt(entity.damageSources().mobAttack(entity), 15.0f);
                Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(1.5);
                target.setDeltaMovement(knockback.x, 0.5, knockback.z);
            }
        }
        
        entity.sendSystemMessage(Component.literal("Charged Punch!"));
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Charged Punch");
    }
}
