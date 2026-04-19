package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Doppelman — user creates a shadow clone that fights alongside them.
 * Grants a significant attack damage and speed boost.
 */
public class DoppelmanAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi");
    public DoppelmanAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 2));
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 2));
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 1));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.doppelman"); }
}
