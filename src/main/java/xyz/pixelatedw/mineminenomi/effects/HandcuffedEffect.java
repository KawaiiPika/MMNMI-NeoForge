package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.enums.HandcuffType;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class HandcuffedEffect extends MobEffect implements IBindHandsEffect {
    private final HandcuffType type;

    public HandcuffedEffect(HandcuffType type) {
        super(MobEffectCategory.HARMFUL, 0);
        this.type = type;
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, 
            ResourceLocation.fromNamespaceAndPath("mineminenomi", "handcuffed_speed"), 
            -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, 
            ResourceLocation.fromNamespaceAndPath("mineminenomi", "handcuffed_attack"), 
            -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (this.type == HandcuffType.KAIROSEKI) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null && stats.getDevilFruit().isPresent()) {
                ResourceLocation devilFruit = stats.getDevilFruit().get();
                for (String abilityIdStr : new java.util.ArrayList<>(stats.getActiveAbilities())) {
                    ResourceLocation abilityId = ResourceLocation.parse(abilityIdStr);
                    Ability ability = ModAbilities.REGISTRY.get(abilityId);
                    if (ability != null && !ability.isPassive() && devilFruit.equals(ability.getRequiredFruit())) {
                        ability.stop(entity);
                    }
                }
            }
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
