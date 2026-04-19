package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class LovestruckEffect extends MobEffect {
    public LovestruckEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFC0CB);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "lovestruck_speed"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "lovestruck_knockback"), 1.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "lovestruck_jump"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
