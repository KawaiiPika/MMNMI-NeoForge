package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Dark Matter — Blackbeard shoots a massive ball of dark gravity dealing large damage + pull. */
public class DarkMatterAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yami_yami_no_mi");
    public DarkMatterAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.0).move(look.scale(12.0)))) {
            if (living != entity) {
                living.hurt(entity.damageSources().mobAttack(entity), 14.0F);
                // Gravity pull back toward impact point
                Vec3 pull = entity.position().add(look.scale(12.0)).subtract(living.position()).normalize().scale(3.0);
                living.setDeltaMovement(pull);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.dark_matter"); }
}
