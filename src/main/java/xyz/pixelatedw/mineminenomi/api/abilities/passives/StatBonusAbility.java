package xyz.pixelatedw.mineminenomi.api.abilities.passives;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class StatBonusAbility extends Ability {

    private final Map<Attribute, AttributeModifier> staticModifiers = new HashMap<>();
    private final Map<Attribute, Function<LivingEntity, AttributeModifier>> dynamicModifiers = new HashMap<>();

    public StatBonusAbility() {
        super();
    }

    public StatBonusAbility(ResourceLocation requiredFruit) {
        super(requiredFruit);
    }

    public void pushStaticAttribute(Attribute attr, AttributeModifier mod) {
        this.staticModifiers.put(attr, mod);
    }

    public void pushDynamicAttribute(Attribute attr, Function<LivingEntity, AttributeModifier> func) {
        this.dynamicModifiers.put(attr, func);
    }

    public abstract Predicate<LivingEntity> getCheck();

    @Override
    protected void startUsing(LivingEntity entity) {
        // Not used as an active ability
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    public void applyModifiers(LivingEntity entity) {
        if (!getCheck().test(entity)) {
            removeModifiers(entity);
            return;
        }

        // Apply static modifiers
        for (Map.Entry<Attribute, AttributeModifier> entry : staticModifiers.entrySet()) {
            applyModifier(entity, entry.getKey(), entry.getValue());
        }

        // Apply dynamic modifiers
        for (Map.Entry<Attribute, Function<LivingEntity, AttributeModifier>> entry : dynamicModifiers.entrySet()) {
            AttributeModifier mod = entry.getValue().apply(entity);
            if (mod != null) {
                applyModifier(entity, entry.getKey(), mod);
            }
        }
    }

    public void removeModifiers(LivingEntity entity) {
        for (Map.Entry<Attribute, AttributeModifier> entry : staticModifiers.entrySet()) {
            removeModifier(entity, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Attribute, Function<LivingEntity, AttributeModifier>> entry : dynamicModifiers.entrySet()) {
            AttributeModifier mod = entry.getValue().apply(entity);
            if (mod != null) {
                removeModifier(entity, entry.getKey(), mod);
            }
        }
    }

    private void applyModifier(LivingEntity entity, Attribute attr, AttributeModifier modifier) {
        net.minecraft.core.Holder<Attribute> holder = net.minecraft.core.registries.BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attr);
        AttributeInstance instance = entity.getAttribute(holder);
        if (instance != null) {
            if (!instance.hasModifier(modifier.id())) {
                instance.addTransientModifier(modifier);
            } else if (dynamicModifiers.containsKey(attr)) {
                // If it's a dynamic modifier, we might need to update the value
                AttributeModifier existing = instance.getModifier(modifier.id());
                if (existing != null && existing.amount() != modifier.amount()) {
                    instance.removeModifier(modifier.id());
                    instance.addTransientModifier(modifier);
                }
            }
        }
    }

    private void removeModifier(LivingEntity entity, Attribute attr, AttributeModifier modifier) {
        net.minecraft.core.Holder<Attribute> holder = net.minecraft.core.registries.BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attr);
        AttributeInstance instance = entity.getAttribute(holder);
        if (instance != null) {
            instance.removeModifier(modifier.id());
        }
    }

    @Override
    public void tick(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            applyModifiers(entity);
        }
    }

    // Add new hooks for incoming damage
    public float onIncomingDamage(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source, float amount) {
        return amount;
    }

    public boolean onIncomingDamageCheckInvulnerability(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }

    public boolean onAttackKnockdown(LivingEntity entity, LivingEntity target, float damage) {
        return false;
    }
}
