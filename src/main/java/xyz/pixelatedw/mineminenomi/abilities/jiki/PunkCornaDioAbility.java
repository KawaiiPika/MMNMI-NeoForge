package xyz.pixelatedw.mineminenomi.abilities.jiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PunkCornaDioAbility extends Ability {

    public PunkCornaDioAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jiki_jiki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 speed = entity.getLookAngle().scale(2.3);
            entity.setDeltaMovement(speed);

            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(6.0))) {
                if (target instanceof LivingEntity livingTarget) {
                    if (livingTarget.hurt(entity.damageSources().mobAttack(entity), 100.0F)) {
                        Vec3 knockback = entity.getLookAngle().scale(4.0);
                        livingTarget.setDeltaMovement(knockback.x, 0.2, knockback.z);
                        if (entity.getRandom().nextFloat() > 0.75F) {
                            livingTarget.addEffect(new MobEffectInstance(ModEffects.DIZZY, 100, 1, false, false));
                        }
                    }
                }
            }
            this.startCooldown(entity, 200);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.punk_corna_dio");
    }
}
