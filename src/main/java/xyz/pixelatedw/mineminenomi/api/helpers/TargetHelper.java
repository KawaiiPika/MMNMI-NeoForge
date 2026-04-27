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
}
