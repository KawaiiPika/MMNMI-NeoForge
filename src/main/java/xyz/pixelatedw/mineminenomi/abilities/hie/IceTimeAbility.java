package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Ice Time — freezes all nearby enemies solid (Slowness + heavy damage). */
public class IceTimeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi");
    public IceTimeAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(8.0))) {
            if (target instanceof LivingEntity living) {
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 10));
                living.hurt(entity.damageSources().freeze(), 10.0F);
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.ice_time"); }
}
