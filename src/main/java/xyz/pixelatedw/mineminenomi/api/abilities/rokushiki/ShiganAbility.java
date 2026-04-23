package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ShiganAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.SHIGAN_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
             
             this.startCooldown(entity, 100);
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source, float amount) {
        return amount + 12.0F;
    }

    @Override
    public void onDamageTakenByTarget(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source) {
        if (!entity.level().isClientSide) {
            target.level().addParticle(net.minecraft.core.particles.ParticleTypes.CRIT, 
                target.getX(), target.getY() + 1.0, target.getZ(), 0, 0, 0);
            
            target.setDeltaMovement(entity.getLookAngle().scale(1.2).add(0, 0.2, 0));
            target.hurtMarked = true;
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.shigan");
    }
}
