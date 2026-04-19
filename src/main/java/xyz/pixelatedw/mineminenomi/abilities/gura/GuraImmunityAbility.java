package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Gura Immunity — user is immune to shockwaves and vibrations.
 * Grants permanent resistance while active.
 */
public class GuraImmunityAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gura_gura_no_mi");
    public GuraImmunityAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gura_immunity"); }
}
