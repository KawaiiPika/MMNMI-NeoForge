package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SablesGuardAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi");
    public SablesGuardAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 100, 3));

            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.0)).forEach(target -> {
                if (target != entity) {
                    Vec3 push = target.position().subtract(entity.position()).normalize().scale(1.5).add(0, 0.5, 0);
                    target.setDeltaMovement(push);
                }
            });
            this.startCooldown(entity, 240);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.sables_guard"); }
}
