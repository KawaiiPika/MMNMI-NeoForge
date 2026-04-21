package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceKey;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class StickyEffect extends MobEffect {
    public StickyEffect() {
        super(MobEffectCategory.HARMFUL, 0x58DB54);
        
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "sticky_knockback"), 1.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "sticky_speed"), -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "sticky_jump"), -255.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "sticky_damage"), -4.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "sticky_attack_speed"), -6.0, AttributeModifier.Operation.ADD_VALUE);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isOnFire() && entity.tickCount > 0) {
            entity.clearFire();
            entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 6.0F, Level.ExplosionInteraction.BLOCK);
            entity.removeEffect(entity.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.MOB_EFFECT).getHolderOrThrow(ResourceKey.create(net.minecraft.core.registries.Registries.MOB_EFFECT, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "sticky"))));
        }
        return true;
    }
}
