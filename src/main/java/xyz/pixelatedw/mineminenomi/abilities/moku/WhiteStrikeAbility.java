package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class WhiteStrikeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi");
    public WhiteStrikeAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        entity.setDeltaMovement(look.scale(2.5));

        if (!entity.level().isClientSide) {
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.0).move(look.scale(3.0))).forEach(target -> {
                if (target != entity) {
                    target.hurt(entity.damageSources().mobAttack(entity), 9.0F);
                    target.setDeltaMovement(look.scale(2.0).add(0, 0.3, 0));
                    target.hurtMarked = true;
                }
            });
            this.startCooldown(entity, 400);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.white_strike"); }
}
