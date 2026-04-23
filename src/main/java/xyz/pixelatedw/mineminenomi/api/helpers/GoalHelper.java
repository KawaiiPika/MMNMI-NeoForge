package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;

public class GoalHelper {
    private static final TargetingConditions ALIVE_TARGET = TargetingConditions.forNonCombat();

    public static boolean hasAliveTarget(Mob entity) {
        LivingEntity target = entity.getTarget();
        return target != null && target.isAlive() && ALIVE_TARGET.test(entity, target);
    }

    public static boolean isWithinDistance(LivingEntity entity, LivingEntity target, double distance) {
        return entity.distanceToSqr(target) <= distance * distance;
    }

    public static boolean canMove(LivingEntity entity) {
        return !entity.isPassenger() && entity.isAlive() && !entity.hasEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN);
    }

    public static void lookAtEntity(Mob entity, @Nullable Entity target) {
        if (target != null) {
            entity.lookAt(Anchor.EYES, target.position().add(0.0D, target.getEyeHeight(), 0.0D));
        }
    }
}
