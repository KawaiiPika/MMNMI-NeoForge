package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FlamesOfRegenerationAbility extends Ability {

    public FlamesOfRegenerationAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_phoenix"));
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
    public float onIncomingDamage(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source, float amount) {
        if (source.is(net.minecraft.world.damagesource.DamageTypes.IN_FIRE) || source.is(net.minecraft.world.damagesource.DamageTypes.ON_FIRE)) {
            // Phoenix flames heal from fire
            if (!entity.level().isClientSide) {
                entity.heal(amount);

                ((net.minecraft.server.level.ServerLevel) entity.level()).sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                    entity.getX(), entity.getY() + 1.0, entity.getZ(),
                    10, 0.5, 0.5, 0.5, 0.05);
            }
            return 0.0F; // Cancel damage
        }

        return amount;
    }

    @Override
    public void onDamageTake(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source, float amount) {
        // Custom regeneration check happens AFTER damage is safely taken via LivingDamageEvent.Post
        if (!entity.level().isClientSide && amount > 0) {
            entity.heal(amount * 0.5F); // Heal back 50% of the damage taken
            ((net.minecraft.server.level.ServerLevel) entity.level()).sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                entity.getX(), entity.getY() + 1.0, entity.getZ(),
                10, 0.5, 0.5, 0.5, 0.05);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.flamesofregeneration");
    }
}
