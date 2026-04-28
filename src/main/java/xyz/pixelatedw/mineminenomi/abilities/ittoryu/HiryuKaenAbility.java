package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;

import java.util.List;

public class HiryuKaenAbility extends Ability {

    private static final float DAMAGE = 20.0F;
    private static final float RANGE = 4.5F;
    private static final int COOLDOWN = 240;

    @Override
    public Result canUse(LivingEntity entity) {
        Result result = super.canUse(entity);
        if (result.isFail()) return result;
        return AbilityUseConditions.requiresSword(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Jump high into the air
        Vec3 look = entity.getLookAngle();
        entity.setDeltaMovement(look.x * 2.0, 1.5, look.z * 2.0);
        entity.hurtMarked = true;
        if (entity instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(entity));
        }
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (isUsing(entity)) {
            // Check for targets while in air/landing
            List<LivingEntity> targets = WyHelper.getNearbyEntities(entity.position(), entity.level(), RANGE, 2.0, RANGE, 
                    e -> e != entity && e instanceof LivingEntity, LivingEntity.class);
            
            boolean hitAnything = false;
            for (LivingEntity target : targets) {
                if (!entity.level().isClientSide) {
                    target.hurt(entity.damageSources().mobAttack(entity), DAMAGE);
                }
                target.igniteForSeconds(4.0F); // 4 seconds of fire
                if (!entity.level().isClientSide) {
                    ((net.minecraft.server.level.ServerLevel)entity.level()).sendParticles(ParticleTypes.FLAME,  target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 1, 0, 0, 0, 0);
                }
                hitAnything = true;
            }

            if (hitAnything || entity.onGround() || getDuration(entity) > 60) {
                stopUsing(entity);
                startCooldown(entity, COOLDOWN);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Hiryu: Kaen");
    }
}
