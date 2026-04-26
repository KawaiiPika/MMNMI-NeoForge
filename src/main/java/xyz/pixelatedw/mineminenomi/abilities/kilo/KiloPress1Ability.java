package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KiloPress1Ability extends Ability {

    public KiloPress1Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.1_kilo_press");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Nothing here, mostly handled in tick and attributes
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration > 10 && entity.onGround()) {
            this.stop(entity);
        } else {
            // Need umbrella check...
            boolean hasUmbrella = false; // entity.getMainHandItem().is(ModWeapons.UMBRELLA.get()) || entity.getOffhandItem().is(ModWeapons.UMBRELLA.get());
            boolean inAir = !entity.onGround() && entity.getDeltaMovement().y < 0.0D;
            if (hasUmbrella && inAir) {
                entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y / 2.0D, entity.getDeltaMovement().z);
            }
        }
        if (duration > 1200) {
            this.stop(entity);
        }
    }

    @Override
    public void stop(LivingEntity entity) {
        super.stop(entity);
        startCooldown(entity, 20 + getDuration(entity));
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        return isUsing(entity) && source.is(net.minecraft.tags.DamageTypeTags.IS_FALL);
    }
}
