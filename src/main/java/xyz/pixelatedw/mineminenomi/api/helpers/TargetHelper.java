package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class TargetHelper {

    public static Comparator<Entity> closestComparator(Vec3 pos) {
        return Comparator.comparingDouble(entity -> entity.distanceToSqr(pos));
    }
}
