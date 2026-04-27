package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KiloPress1Ability extends Ability {
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class KiloPress1Ability extends Ability {
    private static final float HOLD_TIME = 1200.0F;
    private static final ResourceLocation JUMP_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_1_jump");

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
        applyModifiers(entity);
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration > 10 && entity.onGround()) {
            this.stop(entity);
        } else {
            boolean hasUmbrella = false;
            boolean inAir = !entity.onGround() && entity.getDeltaMovement().y < 0.0D;
            if (hasUmbrella && inAir) {
                AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, entity.getDeltaMovement().y / 2.0D, entity.getDeltaMovement().z);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        removeModifiers(entity);
        float cooldown = 20.0F + getDuration(entity);
        this.startCooldown(entity, (long) cooldown);
    }

    private void applyModifiers(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.JUMP_STRENGTH);
            if (jumpAttr != null && jumpAttr.getModifier(JUMP_MOD_ID) == null) {
                jumpAttr.addTransientModifier(new AttributeModifier(JUMP_MOD_ID, 4.8, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    private void removeModifiers(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.JUMP_STRENGTH);
            if (jumpAttr != null) jumpAttr.removeModifier(JUMP_MOD_ID);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.1_kilo_press");
    }
}
