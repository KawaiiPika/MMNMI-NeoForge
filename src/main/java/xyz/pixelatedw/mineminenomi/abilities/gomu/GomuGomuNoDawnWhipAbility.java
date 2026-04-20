package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Gomu Gomu no Dawn Whip — wide horizontal spinning whip strike. */
public class GomuGomuNoDawnWhipAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    public GomuGomuNoDawnWhipAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // 360 degree spin-whip
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(6.0))) {
            if (living != entity) {
                Vec3 dir = living.position().subtract(entity.position()).normalize();
                living.hurt(entity.damageSources().mobAttack(entity), 10.0F);
                living.setDeltaMovement(dir.scale(2.0).add(0, 0.4, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gomu_gomu_no_dawn_whip"); }
}
