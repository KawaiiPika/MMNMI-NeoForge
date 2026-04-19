package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class GeppoAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            double ySpeed = 0.8;
            if (entity.onGround()) {
                ySpeed = 1.1;
            }
            
            AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x * 1.1, ySpeed, entity.getDeltaMovement().z * 1.1);
            entity.hurtMarked = true;
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GEPPO_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.2F);
            
            this.startCooldown(entity, 20);
        } else {
             for (int i = 0; i < 5; i++) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD, 
                    entity.getX(), entity.getY(), entity.getZ(), 
                    (entity.getRandom().nextDouble() - 0.5) * 0.2, -0.1, (entity.getRandom().nextDouble() - 0.5) * 0.2);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.geppo");
    }
}
