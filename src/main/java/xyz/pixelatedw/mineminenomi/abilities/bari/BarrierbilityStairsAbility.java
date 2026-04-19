package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Barrierbility Stairs — creates invisible stairs.
 * Grants a brief jump boost to simulate climbing.
 */
public class BarrierbilityStairsAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_mi");
    public BarrierbilityStairsAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 5));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.barrierbility_stairs"); }
}
