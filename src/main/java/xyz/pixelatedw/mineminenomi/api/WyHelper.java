package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.security.SecureRandom;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WyHelper {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return Character.toUpperCase(text.charAt(0)) + text.substring(1).toLowerCase();
    }

    public static double randomDouble() {
        return SECURE_RANDOM.nextDouble() * 2.0D - 1.0D;
    }

    public static double randomWithRange(double min, double max) {
        return min + (max - min) * SECURE_RANDOM.nextDouble();
    }

    public static double randomWithRange(int min, int max) {
        return (double) (SECURE_RANDOM.nextInt(max - min + 1) + min);
    }

    public static BlockHitResult rayTraceBlockSafe(LivingEntity entity, float range) {
        Vec3 start = entity.getEyePosition();
        Vec3 end = start.add(entity.getViewVector(1.0F).scale(range));
        return entity.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
    }

    public static <T extends Entity> List<T> getNearbyEntities(Vec3 pos, Level world, double xRange, double yRange, double zRange, Predicate<T> filter, Class<T> entityType) {
        AABB area = new AABB(pos.x - xRange, pos.y - yRange, pos.z - zRange, pos.x + xRange, pos.y + yRange, pos.z + zRange);
        List<T> entities = world.getEntitiesOfClass(entityType, area);
        if (filter != null) {
            return entities.stream().filter(filter).collect(Collectors.toList());
        }
        return entities;
    }

    public static void spawnParticles(ParticleOptions particle, ServerLevel level, double x, double y, double z) {
        level.sendParticles(particle, x, y, z, 5, 0.2D, 0.2D, 0.2D, 0.05D);
    }

    public static int getIndexOfItemStack(net.minecraft.world.item.Item item, net.minecraft.world.entity.player.Inventory inventory) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty() && inventory.getItem(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    public static java.awt.Color hexToRGB(String colorStr) {
        return new java.awt.Color(
            Integer.valueOf(colorStr.substring(1, 3), 16),
            Integer.valueOf(colorStr.substring(3, 5), 16),
            Integer.valueOf(colorStr.substring(5, 7), 16),
            colorStr.length() > 7 ? Integer.valueOf(colorStr.substring(7, 9), 16) : 255
        );
    }
}
