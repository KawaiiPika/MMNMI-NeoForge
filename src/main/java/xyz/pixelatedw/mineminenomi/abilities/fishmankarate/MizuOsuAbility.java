package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class MizuOsuAbility extends Ability {

    private static final long COOLDOWN = 160L;
    private static final float RANGE = 2.0F;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            Vec3 lookAngle = entity.getLookAngle();

            AABB area = entity.getBoundingBox().inflate(RANGE);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
            targets.remove(entity);

            for (LivingEntity target : targets) {
                Vec3 toTarget = target.position().subtract(entity.position()).normalize();
                if (lookAngle.dot(toTarget) > 0.5) {
                    Vec3 knockback = toTarget.scale(4.0);
                    target.setDeltaMovement(knockback.x, 0.2, knockback.z);
                }
            }

            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Mizu Osu");
    }
}
