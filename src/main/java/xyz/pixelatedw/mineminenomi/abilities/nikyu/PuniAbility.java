package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Puni — user gains paw-like qualities, allowing them to bounce and move faster.
 * Grants speed and jump boost.
 */
public class PuniAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "nikyu_nikyu_no_mi");
    public PuniAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 2));
        entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, 3));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.puni"); }
}
