package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Vari — electric shockwave burst. */
public class VariAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi");
    public VariAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(7.0))) {
            if (living != entity) {
                living.hurt(entity.damageSources().lightningBolt(), 10.0F);
                Vec3 dir = living.position().subtract(entity.position()).normalize();
                living.setDeltaMovement(dir.scale(2.0).add(0, 0.3, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.1_million_vari"); }
}
