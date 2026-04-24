package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SkywalkAbility extends Ability {

    private long lastJumpTime = -1;

    public SkywalkAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        double upBoost = 0.5;
        double forwardBoost = 0.5;
        
        entity.setDeltaMovement(new Vec3(look.x * forwardBoost, upBoost, look.z * forwardBoost));
        entity.hurtMarked = true;
        
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GEPPO_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        
        lastJumpTime = entity.level().getGameTime();
        this.startCooldown(entity, 20); // 1 second cooldown
        this.stop(entity);
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {
        if (source.is(net.minecraft.world.damagesource.DamageTypes.FALL)) {
            if (lastJumpTime != -1 && entity.level().getGameTime() - lastJumpTime < 200) { // 10 seconds immunity
                return true;
            }
        }
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Skywalk");
    }
}
