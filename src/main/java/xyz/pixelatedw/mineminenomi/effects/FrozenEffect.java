package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class FrozenEffect extends DamageOverTimeEffect {
    public FrozenEffect() {
        super(0xADD8E6, (entity) -> ModDamageSources.getInstance().frozen(entity), 1.0F, 40);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "frozen_speed"), -1000.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "frozen_swim"), -256.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "frozen_damage"), -4.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "frozen_attack_speed"), 100.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.TOUGHNESS, ResourceLocation.fromNamespaceAndPath("mineminenomi", "frozen_toughness"), 2.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.REGEN_RATE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "frozen_regen"), -4.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "frozen_jump"), -256.0, AttributeModifier.Operation.ADD_VALUE);
        
        this.setDamageScaling((amp) -> this.getBaseDamage());
    }
}
