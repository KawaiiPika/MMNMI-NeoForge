package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PunchRushAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration % 4 == 0) { // Every 0.2 seconds
            this.performRushHit(entity);
        }
        
        if (duration >= 40) { // 2 seconds duration
            this.startCooldown(entity, 120);
            this.stop(entity);
        }
    }

    private void performRushHit(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        Vec3 look = entity.getLookAngle();
        Vec3 punchPos = entity.getEyePosition().add(look.scale(1.5));
        AABB area = new AABB(punchPos.subtract(1.0, 1.0, 1.0), punchPos.add(1.0, 1.0, 1.0));
        
        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                target.hurt(entity.damageSources().mobAttack(entity), 4.0f);
                target.invulnerableTime = 0; // Allow rapid hits
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Punch Rush");
    }
}
