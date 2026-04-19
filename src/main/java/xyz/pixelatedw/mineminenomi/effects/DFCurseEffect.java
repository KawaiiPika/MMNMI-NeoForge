package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class DFCurseEffect extends MobEffect {
    public DFCurseEffect() {
        super(MobEffectCategory.HARMFUL, 0);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "df_curse_speed"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "df_curse_swim"), -0.7, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "df_curse_damage"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "df_curse_attack_speed"), -4.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.MINING_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "df_curse_mining"), -0.5, AttributeModifier.Operation.ADD_VALUE);
    }
}
