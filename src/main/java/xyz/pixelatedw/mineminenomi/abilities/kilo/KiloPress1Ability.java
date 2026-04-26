package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class KiloPress1Ability extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi");
    private static final ResourceLocation JUMP_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_1_jump");

    public KiloPress1Ability() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) {
                jumpAttr.addOrUpdateTransientModifier(new AttributeModifier(JUMP_MOD_ID, 4.8D, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             if (duration > 10 && entity.onGround()) {
                 this.stop(entity);
             } else {
                 boolean hasUmbrella = entity.getMainHandItem().getItem() == ModWeapons.UMBRELLA.get() || entity.getOffhandItem().getItem() == ModWeapons.UMBRELLA.get();
                 boolean inAir = !entity.onGround() && entity.getDeltaMovement().y < 0.0D;
                 if (hasUmbrella && inAir) {
                    AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, entity.getDeltaMovement().y / 2.0D, entity.getDeltaMovement().z);
                 }
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) {
                jumpAttr.removeModifier(JUMP_MOD_ID);
            }
        }
        this.startCooldown(entity, 20);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.1_kilo_press");
    }
}
