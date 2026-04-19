package xyz.pixelatedw.mineminenomi.abilities.suke;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Skatting — Full body invisibility. */
public class SkattingAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suke_suke_no_mi");
    
    public SkattingAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
        xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.skatting.on"));
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        entity.removeEffect(MobEffects.INVISIBILITY);
        xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.skatting.off"));
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.skatting"); 
    }
}
