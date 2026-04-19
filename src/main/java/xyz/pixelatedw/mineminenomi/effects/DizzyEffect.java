package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class DizzyEffect extends MobEffect {
    public DizzyEffect() {
        super(MobEffectCategory.HARMFUL, 0x000000);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dizzy_speed"), -0.025, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "dizzy_jump"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
