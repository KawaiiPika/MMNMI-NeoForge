package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.core.Holder;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.enums.HandcuffType;

public class HandcuffedEffect extends MobEffect implements IBindHandsEffect {
    private final HandcuffType type;

    public HandcuffedEffect(MobEffectCategory category, int color, HandcuffType type, Holder<Attribute> movementSpeed, Holder<Attribute> attackSpeed) {
        super(category, color);
        this.type = type;
        this.addAttributeModifier(movementSpeed,
            ResourceLocation.fromNamespaceAndPath("mineminenomi", "handcuffed_speed"), 
            -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(attackSpeed,
            ResourceLocation.fromNamespaceAndPath("mineminenomi", "handcuffed_attack"), 
            -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public HandcuffedEffect(HandcuffType type, Holder<Attribute> movementSpeed, Holder<Attribute> attackSpeed) {
        this(MobEffectCategory.HARMFUL, 0, type, movementSpeed, attackSpeed);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (this.type == HandcuffType.KAIROSEKI) {
            // TODO: Disable Devil Fruit abilities
        }
        return true;
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isBlockingSwings() {
        return true;
    }

    public HandcuffType getType() {
        return this.type;
    }
}
