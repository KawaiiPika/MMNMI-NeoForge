package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class TargetHelper {

    public static Comparator<Entity> closestComparator(Vec3 pos) {
        return Comparator.comparingDouble(entity -> entity.distanceToSqr(pos));
    }

    public static <T extends Entity> List<T> getEntitiesInArea(net.minecraft.world.level.LevelAccessor level, LivingEntity entity, double size, Class<T> clz) {
        AABB aabb = new AABB(entity.blockPosition()).inflate(size);
        return level.getEntitiesOfClass(clz, aabb, target -> target != entity && target.isAlive());
    }

    public static <T extends Entity> List<T> getEntitiesInLine(LivingEntity entity, double distance, double width, Class<T> clz) {
        java.util.List<T> targets = new java.util.ArrayList<>();
        Vec3 lookVec = entity.getLookAngle().normalize();
        Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0);
        double distanceTraveled = 0.0;

        while(distanceTraveled < distance) {
            distanceTraveled += lookVec.length();
            pos = pos.add(lookVec);
            AABB aabb = new AABB(pos.x - width/2, pos.y - width/2, pos.z - width/2, pos.x + width/2, pos.y + width/2, pos.z + width/2);
            for (T target : entity.level().getEntitiesOfClass(clz, aabb, t -> t != entity && t.isAlive())) {
                if (!targets.contains(target)) {
                    targets.add(target);
                }
            }
        }

        targets.sort(closestComparator(entity.position().add(0, entity.getEyeHeight(), 0)));
        return targets;
    }
}
