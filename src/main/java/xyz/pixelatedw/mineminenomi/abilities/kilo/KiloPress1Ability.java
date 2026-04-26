package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KiloPress1Ability extends Ability {

    public KiloPress1Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in onTick
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 20);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 10 && entity.onGround()) {
            this.stop(entity);
            return;
        }

        if (duration > 1200) {
            this.stop(entity);
            return;
        }

        if (!entity.level().isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 5, 9, false, false, false));
        }

        boolean hasUmbrella = entity.getMainHandItem().getItem().toString().contains("umbrella") || entity.getOffhandItem().getItem().toString().contains("umbrella");
        boolean inAir = !entity.onGround() && entity.getDeltaMovement().y < 0.0;
        if (hasUmbrella && inAir) {
            entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y / 2.0, entity.getDeltaMovement().z);
        }
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        return this.isUsing(entity) && source.is(net.minecraft.tags.DamageTypeTags.IS_FALL);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.1_kilo_press");
    }
}
