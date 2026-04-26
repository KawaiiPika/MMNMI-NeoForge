package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SubeSubeSpurAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sube_sube_no_mi");
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sube_sube_spur_modifier");

    public SubeSubeSpurAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            net.minecraft.world.entity.ai.attributes.AttributeInstance attr = entity.getAttribute(Attributes.STEP_HEIGHT);
            if (attr != null && attr.getModifier(MODIFIER_ID) == null) {
                attr.addTransientModifier(new AttributeModifier(MODIFIER_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (this.isUsing(entity)) {
            if (entity.onGround() && !entity.isCrouching()) {
                net.minecraft.world.phys.Vec3 motion = entity.getDeltaMovement();

                net.minecraft.world.phys.Vec3 look = entity.getLookAngle();
                entity.setDeltaMovement(motion.x + look.x * 0.1, motion.y, motion.z + look.z * 0.1);

            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            net.minecraft.world.entity.ai.attributes.AttributeInstance attr = entity.getAttribute(Attributes.STEP_HEIGHT);
            if (attr != null) {
                attr.removeModifier(MODIFIER_ID);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sube_sube_spur");
    }
}
