package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KachiageHaisokuAbility extends Ability {

    public KachiageHaisokuAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) {
        return amount;
    }

    @Override
    public void onDamageTakenByTarget(LivingEntity entity, LivingEntity target, DamageSource source) {
        if (this.canUse(entity).isSuccess()) {
            float damage = 20.0F;
            if (entity.isInWater()) {
                damage *= 2.0F;
            }
            
            target.hurt(entity.damageSources().mobAttack(entity), damage);
            target.setDeltaMovement(target.getDeltaMovement().add(0, 0.6, 0));
            target.hurtMarked = true;

            if (!entity.level().isClientSide) {
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_STRONG, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            this.startCooldown(entity, 160);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kachiage_haisoku");
    }
}
