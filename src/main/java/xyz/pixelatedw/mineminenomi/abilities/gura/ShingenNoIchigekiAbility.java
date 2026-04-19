package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Shingen no Ichigeki — Heart Vibrating Hit; user delivers a direct shockwave hit.
 * Deals extreme damage to a single target and knocks them back.
 */
public class ShingenNoIchigekiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gura_gura_no_mi");
    public ShingenNoIchigekiAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(1.5).move(look.scale(1.5)))) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 20.0F);
                Vec3 push = look.scale(3.0).add(0, 0.4, 0);
                living.setDeltaMovement(push);
                living.hurtMarked = true;
                return;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.shingen_no_ichigeki"); }
}
