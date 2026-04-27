package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KiloPunch5000Ability extends Ability {
    private static final float COOLDOWN = 140.0F;
    private static final ResourceLocation MOVEMENT_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_punch_speed");

    public KiloPunch5000Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null && speedAttr.getModifier(MOVEMENT_MOD_ID) == null) {
                speedAttr.addTransientModifier(new AttributeModifier(MOVEMENT_MOD_ID, -0.01, AttributeModifier.Operation.ADD_VALUE));
            }
        }

        net.minecraft.world.phys.HitResult mop = entity.pick(4.0D, 0.0F, false);
        if (mop instanceof net.minecraft.world.phys.EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
            target.hurt(entity.damageSources().mobAttack(entity), 20.0F);
        }

        this.stop(entity);
        this.startCooldown(entity, (long) COOLDOWN);
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(MOVEMENT_MOD_ID);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.5000_kilo_punch");
    }
}
