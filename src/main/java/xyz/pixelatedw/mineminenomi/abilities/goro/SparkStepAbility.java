package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SparkStepAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi");
    public SparkStepAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Enel's lightning-fast dash
        entity.setDeltaMovement(look.scale(4.0).add(0, 0.2, 0));
        entity.resetFallDistance();
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.spark_step"); }
}
