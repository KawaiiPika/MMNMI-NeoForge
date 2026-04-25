package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Nikyu Push — paw-pad repulsion blast pushing enemies backward. */
public class NikyuPushAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "nikyu_nikyu_no_mi");
    public NikyuPushAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        for (var target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3.0).move(look.scale(3.0)), e -> e != entity)) {
            if (target instanceof LivingEntity living) {
                living.setDeltaMovement(look.scale(4.5).add(0, 0.3, 0));
                living.hurt(entity.damageSources().mobAttack(entity), 6.0F);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.nikyu_push"); }
}
