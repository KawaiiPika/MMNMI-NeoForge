package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

/** Shadows Asgard — Massive buff and size increase by absorbing shadows. */
public class ShadowsAsgardAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi");
    private static final ResourceLocation SCALE_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "shadows_asgard_scale");
    private static final ResourceLocation DAMAGE_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "shadows_asgard_damage");
    
    public ShadowsAsgardAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        var scaleAttr = entity.getAttribute(Attributes.SCALE);
        if (scaleAttr != null) {
            scaleAttr.addOrUpdateTransientModifier(new AttributeModifier(SCALE_MOD_ID, 1.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        
        var damageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttr != null) {
            damageAttr.addOrUpdateTransientModifier(new AttributeModifier(DAMAGE_MOD_ID, 10.0, AttributeModifier.Operation.ADD_VALUE));
        }

        AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.shadows_asgard.on"));
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.level().isClientSide) {
            entity.level().addParticle(ParticleTypes.SQUID_INK, 
                entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                entity.getY(), 
                entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                0, 0.1, 0);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        var scaleAttr = entity.getAttribute(Attributes.SCALE);
        if (scaleAttr != null) {
            scaleAttr.removeModifier(SCALE_MOD_ID);
        }
        
        var damageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttr != null) {
            damageAttr.removeModifier(DAMAGE_MOD_ID);
        }

        AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.shadows_asgard.off"));
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.shadows_asgard"); 
    }
}
