package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GomuGomuNoRocketAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    public GomuGomuNoRocketAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Self-launch
        entity.setDeltaMovement(look.scale(3.5).add(0, 0.5, 0));
        entity.resetFallDistance();
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gomu_gomu_no_rocket"); }
}
