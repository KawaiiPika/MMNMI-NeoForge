package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class CandleChampionAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "candle_champion");

    private static final AttributeModifier STRENGTH_MODIFIER = new AttributeModifier(MODIFIER_ID, 4.0, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier ATTACK_SPEED_MODIFIER = new AttributeModifier(MODIFIER_ID, 0.1, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(MODIFIER_ID, 15.0, AttributeModifier.Operation.ADD_VALUE);

    private static final float MAX_DURATION = 1200.0F;

    public CandleChampionAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        addModifier(entity, ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER);
        addModifier(entity, Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER);
        addModifier(entity, Attributes.ARMOR, ARMOR_MODIFIER);
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.isOnFire() && !entity.level().isClientSide) {
            this.startTick -= 5;
        }

        if (getDuration(entity) >= MAX_DURATION) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        removeModifier(entity, ModAttributes.PUNCH_DAMAGE, MODIFIER_ID);
        removeModifier(entity, Attributes.ATTACK_SPEED, MODIFIER_ID);
        removeModifier(entity, Attributes.ARMOR, MODIFIER_ID);

        long duration = this.getDuration(entity);
        this.startCooldown(entity, (long) (40.0F + duration));
    }

    private void addModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, AttributeModifier modifier) {
        var inst = entity.getAttribute(attribute);
        if (inst != null && !inst.hasModifier(modifier.id())) {
            inst.addTransientModifier(modifier);
        }
    }

    private void removeModifier(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, ResourceLocation modifierId) {
        var inst = entity.getAttribute(attribute);
        if (inst != null) {
            inst.removeModifier(modifierId);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.candle_champion");
    }
}
