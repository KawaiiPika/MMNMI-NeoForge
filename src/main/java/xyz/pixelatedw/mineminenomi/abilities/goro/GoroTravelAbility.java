package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GoroTravelAbility extends Ability {

    public GoroTravelAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        // Allows traveling through conductive materials
        // We will mock this with a simple passive for now as true block bypassing is very complex.
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.goro_travel");
    }
}
