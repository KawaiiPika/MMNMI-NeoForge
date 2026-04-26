package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class GoalHelper {
    private static final TargetingConditions ALIVE_TARGET = TargetingConditions.forNonCombat();

    public static boolean hasAliveTarget(Mob entity) {
        LivingEntity target = entity.getTarget();
        return target != null && target.isAlive() && ALIVE_TARGET.test(entity, target);
    }

    public static boolean isWithinDistance(LivingEntity entity, LivingEntity target, double distance) {
        return entity.distanceToSqr(target) <= distance * distance;
    }

    public static boolean isOutsideDistance(LivingEntity entity, LivingEntity target, double distance) {
        return entity.distanceToSqr(target) > distance * distance;
    }

    public static void lookAtEntity(Mob entity, @Nullable Entity target) {
        if (target != null) {
            entity.lookAt(Anchor.EYES, target.position().add(0.0D, target.getEyeHeight(), 0.0D));
        }
    }

    public static boolean canSee(Mob entity, Entity target) {
        return entity.getSensing().hasLineOfSight(target);
    }

    public static boolean hasEnoughTargetsAround(Mob entity, int minEnemies, float distance) {
        AABB aabb = entity.getBoundingBox().inflate(distance);
        List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e.isAlive() && e != entity);
        int confirmedTargets = 0;

        for (LivingEntity target : targets) {
            if (entity.distanceToSqr(target) <= (distance * distance) && canSee(entity, target)) {
                ++confirmedTargets;
            }
            if (confirmedTargets >= minEnemies) {
                return true;
            }
        }
        return false;
    }
}
