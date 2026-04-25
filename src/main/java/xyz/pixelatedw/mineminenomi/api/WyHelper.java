package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class WyHelper {

    public static EntityHitResult rayTraceEntities(Entity entity, double range) {
        Vec3 start = entity.getEyePosition();
        Vec3 look = entity.getLookAngle();
        Vec3 end = start.add(look.scale(range));
        AABB aabb = entity.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0);
        return ProjectileUtil.getEntityHitResult(entity, start, end, aabb, e -> !e.isSpectator() && e.isPickable(), range * range);
    }

    public static BlockHitResult rayTraceBlockSafe(LivingEntity entity, float range) {
        return null; // dummy for compilation
    }

    public static List<LivingEntity> getNearbyEntities(Vec3 pos, Level level, float rangeX, double rangeY, float rangeZ, Predicate<LivingEntity> predicate, Class<LivingEntity> clazz) {
        AABB aabb = new AABB(pos.x - rangeX, pos.y - rangeY, pos.z - rangeZ, pos.x + rangeX, pos.y + rangeY, pos.z + rangeZ);
        return level.getEntitiesOfClass(clazz, aabb, predicate);
    }
}
