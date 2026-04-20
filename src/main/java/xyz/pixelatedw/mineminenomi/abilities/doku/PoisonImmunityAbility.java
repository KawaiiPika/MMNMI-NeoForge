package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Poison Immunity — Doku user passively cannot be poisoned. Active use cleanses nearby allies. */
public class PoisonImmunityAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doku_doku_no_mi");
    public PoisonImmunityAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Active: removes poison from self and nearby allies
        entity.removeEffect(net.minecraft.world.effect.MobEffects.POISON);
        entity.removeEffect(net.minecraft.world.effect.MobEffects.WITHER);
        for (LivingEntity ally : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5.0))) {
            if (ally != entity) {
                ally.removeEffect(net.minecraft.world.effect.MobEffects.POISON);
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.poison_immunity"); }
}
