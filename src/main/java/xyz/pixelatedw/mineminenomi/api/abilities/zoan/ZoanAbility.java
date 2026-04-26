package xyz.pixelatedw.mineminenomi.api.abilities.zoan;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public abstract class ZoanAbility extends Ability {

    private final ResourceLocation modifierId;

    public ZoanAbility(ResourceLocation requiredFruit) {
        super(requiredFruit);
        // Base the modifier ID on the ability's ID later when used
        this.modifierId = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zoan_transformation");
    }

    public abstract ResourceLocation getMorphModelId();

    public abstract double getScaleModifier();

    public abstract double getHealthModifier();

    public abstract double getDamageModifier();

    @Override
    protected void startUsing(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null) {
            // Iterating directly over getActiveAbilities() is safe here because PlayerStats uses a
            // copy-on-write pattern for its lists. Any modification (like toggling an ability)
            // will replace the list reference in PlayerStats rather than modifying the list being iterated.
            for (String abilityIdStr : stats.getActiveAbilities()) {
                ResourceLocation id = ResourceLocation.parse(abilityIdStr);
                Ability activeAbility = xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.get(id);
                if (activeAbility instanceof ZoanAbility && activeAbility != this) {
                    // Toggle off the other Zoan ability
                    activeAbility.use(entity);
                }
            }
        }

        if (entity.hasData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA)) {
            xyz.pixelatedw.mineminenomi.data.entity.MorphData morphData = entity.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA);
            java.util.List<ResourceLocation> activeMorphs = new java.util.ArrayList<>(morphData.activeMorphs());
            if (!activeMorphs.contains(getMorphModelId())) activeMorphs.add(getMorphModelId());
            entity.setData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA, new xyz.pixelatedw.mineminenomi.data.entity.MorphData(java.util.Optional.of(getMorphModelId()), activeMorphs));
        }

        float healthPercentage = entity.getHealth() / entity.getMaxHealth();

        applyModifier(entity, Attributes.SCALE, getScaleModifier(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        applyModifier(entity, Attributes.MAX_HEALTH, getHealthModifier(), AttributeModifier.Operation.ADD_VALUE);
        applyModifier(entity, Attributes.ATTACK_DAMAGE, getDamageModifier(), AttributeModifier.Operation.ADD_VALUE);
        
        // Also adjust step height if scaled up significantly
        if (getScaleModifier() > 0.5) {
            applyModifier(entity, Attributes.STEP_HEIGHT, 1.0, AttributeModifier.Operation.ADD_VALUE);
        }

        entity.setHealth(entity.getMaxHealth() * healthPercentage);
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity.hasData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA)) {
            xyz.pixelatedw.mineminenomi.data.entity.MorphData morphData = entity.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA);
            java.util.List<ResourceLocation> activeMorphs = new java.util.ArrayList<>(morphData.activeMorphs());
            activeMorphs.remove(getMorphModelId());
            java.util.Optional<ResourceLocation> current = activeMorphs.isEmpty() ? java.util.Optional.empty() : java.util.Optional.of(activeMorphs.get(0));
            entity.setData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA, new xyz.pixelatedw.mineminenomi.data.entity.MorphData(current, activeMorphs));
        }

        float healthPercentage = entity.getHealth() / entity.getMaxHealth();

        removeModifier(entity, Attributes.SCALE);
        removeModifier(entity, Attributes.MAX_HEALTH);
        removeModifier(entity, Attributes.ATTACK_DAMAGE);
        removeModifier(entity, Attributes.STEP_HEIGHT);

        entity.setHealth(entity.getMaxHealth() * healthPercentage);
    }

    private void applyModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, double amount, AttributeModifier.Operation operation) {
        if (amount == 0) return;
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            AttributeModifier modifier = new AttributeModifier(modifierId, amount, operation);
            if (!instance.hasModifier(modifierId)) {
                instance.addPermanentModifier(modifier);
            }
        }
    }

    private void removeModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(modifierId);
        }
    }
}
