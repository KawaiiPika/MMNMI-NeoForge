package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Light Acceleration — Pika Pika teleport dash toward look direction at light speed. */
public class LightAccelerationAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi");
    public LightAccelerationAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle().normalize().scale(20.0);
        entity.setDeltaMovement(look.x, look.y, look.z);
        entity.hurtMarked = true;
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.light_acceleration"); }
}
