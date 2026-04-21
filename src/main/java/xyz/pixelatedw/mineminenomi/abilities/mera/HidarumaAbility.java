package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class HidarumaAbility extends Ability {

    public HidarumaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.FIRECHARGE_USE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.5F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration % 2 == 0) {
                Vec3 look = entity.getLookAngle();
                xyz.pixelatedw.mineminenomi.entities.projectiles.HiganEntity fireball = new xyz.pixelatedw.mineminenomi.entities.projectiles.HiganEntity(entity.level(), entity);
                fireball.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 0.5F);
                entity.level().addFreshEntity(fireball);
            }
        } else {
             entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.FLAME, 
                entity.getX(), entity.getY() + 1, entity.getZ(), 0, 0.1, 0);
        }

        if (duration >= 30) {
            this.stop(entity);
            this.startCooldown(entity, 200 + duration * 5);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.hidaruma");
    }
}
