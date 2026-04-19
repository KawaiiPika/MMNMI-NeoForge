package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** White Strike — high-speed smoke-body tackle dealing knockback. */
public class WhiteStrikeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi");
    public WhiteStrikeAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Dash forward through targets
        entity.setDeltaMovement(look.scale(2.5));
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(2.0).move(look.scale(3.0)))) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 9.0F);
                living.setDeltaMovement(look.scale(2.0).add(0, 0.3, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.white_strike"); }
}
