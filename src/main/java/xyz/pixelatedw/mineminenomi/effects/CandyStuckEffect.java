package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class CandyStuckEffect extends DamageOverTimeEffect {
    public CandyStuckEffect() {
        super(0xFF69B4, (entity) -> entity.damageSources().magic(), 1.0F, 40);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "candy_stuck_speed"), -100.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "candy_stuck_swim"), -256.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "candy_stuck_damage"), -4.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "candy_stuck_knockback"), 100.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.REGEN_RATE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "candy_stuck_regen"), -4.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.TOUGHNESS, ResourceLocation.fromNamespaceAndPath("mineminenomi", "candy_stuck_toughness"), 2.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "candy_stuck_jump"), -256.0, AttributeModifier.Operation.ADD_VALUE);
    }
}
