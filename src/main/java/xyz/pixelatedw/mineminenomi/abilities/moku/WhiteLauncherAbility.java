package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class WhiteLauncherAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi");
    public WhiteLauncherAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Smoker launches a blast of smoke that pushes targets away
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0).move(look.scale(5.0)))) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 7.0F);
                living.setDeltaMovement(look.scale(3.0).add(0, 0.5, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.white_launcher"); }
}
