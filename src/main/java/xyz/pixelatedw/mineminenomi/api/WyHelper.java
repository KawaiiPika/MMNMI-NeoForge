package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.ClipContext;

public class WyHelper {
    private static final Random RANDOM = new Random();

    public static double randomWithRange(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }

    public static Vec3 findValidGroundLocation(Level level, Vec3 spawnPos, int radius, int offset) {
        Vec3 vec3 = null;

        for (int i = 0; i < 10; ++i) {
            int x = (int) randomWithRange(spawnPos.x() - (double) offset - (double) radius, spawnPos.x() + (double) offset + (double) radius);
            int z = (int) randomWithRange(spawnPos.z() - (double) offset - (double) radius, spawnPos.z() + (double) offset + (double) radius);

            for (int j = -5; j < 0; ++j) {
                int y = (int) (spawnPos.y() + (double) j);
                BlockPos pos = new BlockPos(x, y, z);
                BlockState state = level.getBlockState(pos.below());
                if (!state.isAir() && state.canOcclude() && !state.getCollisionShape(level, pos).equals(Shapes.empty())) {
                    vec3 = new Vec3((double) x, (double) y, (double) z);
                    break;
                }
            }

            if (vec3 != null) {
                break;
            }
        }

        return vec3;
    }

    public static <T extends Entity> List<T> getNearbyEntities(Vec3 pos, LevelAccessor world, double radius, @Nullable Predicate<Entity> predicate, Class<T> clz) {
        AABB aabb = new AABB(pos, pos.add(1.0D, 1.0D, 1.0D)).inflate(radius, radius, radius);
        return world.getEntitiesOfClass(clz, aabb, predicate != null ? predicate : entity -> true);
    }

    public static <T extends Entity> List<T> getNearbyEntities(Vec3 pos, LevelAccessor world, double sizeX, double sizeY, double sizeZ, @Nullable Predicate<Entity> predicate, Class<T> clz) {
        AABB aabb = new AABB(pos, pos.add(1.0D, 1.0D, 1.0D)).inflate(sizeX, sizeY, sizeZ);
        return world.getEntitiesOfClass(clz, aabb, predicate != null ? predicate : entity -> true);
    }

    public static List<Entity> rayTraceEntities(LivingEntity entity, double distance) {
        Vec3 lookVec = entity.getLookAngle();
        Vec3 startVec = entity.position().add(0.0D, entity.getEyeHeight(), 0.0D);
        Vec3 endVec = startVec.add(lookVec.scale(distance));
        AABB aabb = entity.getBoundingBox().expandTowards(lookVec.scale(distance)).inflate(1.0D);
        List<Entity> list = new ArrayList<>();
        Entity hitEntity = null;
        for (Entity e : entity.level().getEntities(entity, aabb, e -> e.isAlive() && e.isPickable())) {
            if (e.getBoundingBox().inflate(e.getPickRadius()).clip(startVec, endVec).isPresent()) {
                if (hitEntity == null || entity.distanceToSqr(e) < entity.distanceToSqr(hitEntity)) {
                    hitEntity = e;
                }
            }
        }
        if (hitEntity != null) {
            list.add(hitEntity);
        }
        return list;
    }

    public static HitResult rayTraceBlockSafe(LivingEntity entity, double distance) {
        Vec3 lookVec = entity.getLookAngle();
        Vec3 startVec = entity.position().add(0.0D, entity.getEyeHeight(), 0.0D);
        Vec3 endVec = startVec.add(lookVec.scale(distance));
        return entity.level().clip(new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
    }
}
