package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class AbilityHelper {
    public static boolean verifyIfAbilityIsBanned(AbilityCore<?> core) {
        return false;
    }

    public static void setDeltaMovement(LivingEntity entity, double x, double y, double z) {
        entity.setDeltaMovement(x, y, z);
        entity.hurtMarked = true;
        if (entity instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket(entity));
        }
    }

    public static void sendAbilityMessage(LivingEntity entity, net.minecraft.network.chat.Component message) {
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            player.displayClientMessage(message, true);
        }
    }

    public static void slowEntityFall(LivingEntity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.5, 1.0));
    }

    public static net.minecraft.world.phys.EntityHitResult pickEntity(LivingEntity shooter, double range) {
        net.minecraft.world.phys.Vec3 start = shooter.getEyePosition();
        net.minecraft.world.phys.Vec3 look = shooter.getViewVector(1.0F);
        net.minecraft.world.phys.Vec3 end = start.add(look.scale(range));
        net.minecraft.world.phys.AABB aabb = shooter.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0D);
        
        return net.minecraft.world.entity.projectile.ProjectileUtil.getEntityHitResult(
            shooter.level(), shooter, start, end, aabb, 
            entity -> !entity.isSpectator() && entity.isPickable() && entity instanceof LivingEntity
        );
    }

    public static void checkAndUnlockAbility(LivingEntity entity, AbilityCore<?> core) {
        // Stub
    }

    public static double getDifferenceToFloor(net.minecraft.world.entity.Entity entity) {
        return entity.position().distanceTo(getFloorLevel(entity));
    }

    public static net.minecraft.world.phys.Vec3 getFloorLevel(net.minecraft.world.entity.Entity entity) {
        net.minecraft.world.phys.Vec3 startVec = entity.position();
        net.minecraft.world.phys.Vec3 endVec = startVec.add(0.0D, -256.0D, 0.0D);
        net.minecraft.world.phys.BlockHitResult blockResult = entity.level().clip(new net.minecraft.world.level.ClipContext(startVec, endVec, net.minecraft.world.level.ClipContext.Block.OUTLINE, net.minecraft.world.level.ClipContext.Fluid.ANY, entity));
        return blockResult.getLocation();
    }
}
