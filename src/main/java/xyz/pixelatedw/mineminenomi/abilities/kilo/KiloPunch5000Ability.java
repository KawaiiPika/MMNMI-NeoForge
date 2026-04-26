package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;

public class KiloPunch5000Ability extends PunchAbility {
    private static final ResourceLocation SPEED_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_punch_5000_speed");

    public KiloPunch5000Ability() {
        super();
    }

    @Override
    public void startUsing(LivingEntity entity) {
        super.startUsing(entity);
        if (!entity.level().isClientSide) {
            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.addOrUpdateTransientModifier(new AttributeModifier(SPEED_MOD_ID, -0.01D, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    public void stopUsing(LivingEntity entity) {
        super.stopUsing(entity);
        if (!entity.level().isClientSide) {
            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(SPEED_MOD_ID);
        }
    }

    public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
        target.hurt(source, 20.0F);
        return true;
    }

    public int getUseLimit() {
        return 1;
    }

    public float getPunchCooldown() {
        return 140.0F;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.5000_kilo_punch");
    }
}
