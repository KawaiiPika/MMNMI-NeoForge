package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class IceSaberAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi");
    public IceSaberAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Simple ice blade strike in front
        var look = entity.getLookAngle();
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.5).move(look.scale(3.0)))) {
            if (living != entity) {
                living.hurt(entity.damageSources().mobAttack(entity), 10.0F);
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 60, 3));
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.ice_saber"); }
}
