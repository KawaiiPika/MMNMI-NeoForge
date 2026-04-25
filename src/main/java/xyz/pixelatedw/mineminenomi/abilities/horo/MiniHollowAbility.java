package xyz.pixelatedw.mineminenomi.abilities.horo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Mini Hollow — a small explosive ghost that detonates on contact. */
public class MiniHollowAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "horo_horo_no_mi");
    public MiniHollowAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        for (var target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.5).move(look.scale(8.0)), e -> e != entity)) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().explosion(null), 8.0F);
                Vec3 dir = living.position().subtract(entity.position()).normalize();
                living.setDeltaMovement(dir.scale(2.0).add(0, 0.5, 0));
                living.hurtMarked = true;
                return;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.mini_hollow"); }
}
