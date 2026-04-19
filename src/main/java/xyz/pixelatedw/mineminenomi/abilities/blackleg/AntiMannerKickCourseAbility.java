package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AntiMannerKickCourseAbility extends Ability {

    public AntiMannerKickCourseAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        Vec3 eyePos = entity.getEyePosition();
        Vec3 reach = eyePos.add(look.scale(3.0));
        AABB area = new AABB(eyePos, reach).inflate(1.0);
        
        boolean hit = false;
        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                target.hurt(entity.damageSources().mobAttack(entity), 15.0f);
                target.setDeltaMovement(new Vec3(0, 1.2, 0));
                target.hurtMarked = true;
                
                if (target instanceof net.minecraft.world.entity.player.Player || target instanceof net.minecraft.world.entity.Mob) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.CONFUSION, 60, 0));
                }
                hit = true;
                break; // Only hit one target
            }
        }
        
        if (hit && !entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_STRONG, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        this.startCooldown(entity, 120); // 6 seconds cooldown
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Anti-Manner Kick Course");
    }
}
