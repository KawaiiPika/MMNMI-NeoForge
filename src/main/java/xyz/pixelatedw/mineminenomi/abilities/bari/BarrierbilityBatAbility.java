package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Barrierbility Bat — shapes a barrier into a club/bat.
 * Grants increased attack speed and strength.
 */
public class BarrierbilityBatAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_mi");
    public BarrierbilityBatAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, 1));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.barrierbility_bat"); }
}
