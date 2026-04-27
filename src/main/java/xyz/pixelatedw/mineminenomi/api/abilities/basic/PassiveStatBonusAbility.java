package xyz.pixelatedw.mineminenomi.api.abilities.basic;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class PassiveStatBonusAbility extends PassiveAbility {
    private final Map<Holder<Attribute>, Function<LivingEntity, AttributeModifier>> dynamicAttributes = new HashMap<>();
    private final Map<Holder<Attribute>, AttributeModifier> staticAttributes = new HashMap<>();

    public PassiveStatBonusAbility() {
        super();
    }

    public void pushStaticAttribute(Holder<Attribute> attr, AttributeModifier mod) {
        this.staticAttributes.put(attr, mod);
    }

    public void pushDynamicAttribute(Holder<Attribute> attr, Function<LivingEntity, AttributeModifier> func) {
        this.dynamicAttributes.put(attr, func);
    }

    public abstract Predicate<LivingEntity> getCheck();

    @Override
    public void tick(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            boolean canApply = getCheck().test(entity);

            for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : staticAttributes.entrySet()) {
                net.minecraft.world.entity.ai.attributes.AttributeInstance instance = entity.getAttribute(entry.getKey());
                if (instance != null) {
                    if (canApply && !instance.hasModifier(entry.getValue().id())) {
                        instance.addTransientModifier(entry.getValue());
                    } else if (!canApply && instance.hasModifier(entry.getValue().id())) {
                        instance.removeModifier(entry.getValue().id());
                    }
                }
            }

            for (Map.Entry<Holder<Attribute>, Function<LivingEntity, AttributeModifier>> entry : dynamicAttributes.entrySet()) {
                net.minecraft.world.entity.ai.attributes.AttributeInstance instance = entity.getAttribute(entry.getKey());
                if (instance != null) {
                    AttributeModifier mod = entry.getValue().apply(entity);
                    if (canApply && !instance.hasModifier(mod.id())) {
                        instance.addTransientModifier(mod);
                    } else if (!canApply && instance.hasModifier(mod.id())) {
                        instance.removeModifier(mod.id());
                    } else if (canApply && instance.hasModifier(mod.id())) {
                        AttributeModifier current = instance.getModifier(mod.id());
                        if (current != null && current.amount() != mod.amount()) {
                            instance.removeModifier(mod.id());
                            instance.addTransientModifier(mod);
                        }
                    }
                }
            }
        }
    }
}
