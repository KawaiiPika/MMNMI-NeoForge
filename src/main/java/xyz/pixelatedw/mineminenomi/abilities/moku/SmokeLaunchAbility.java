package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SmokeLaunchAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi");
    public SmokeLaunchAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Smoker launches himself forward with smoke propulsion
        entity.setDeltaMovement(look.scale(3.0).add(0, 0.5, 0));
        entity.resetFallDistance();
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.smoke_launch"); }
}
