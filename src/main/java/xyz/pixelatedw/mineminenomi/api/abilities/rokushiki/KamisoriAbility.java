package xyz.pixelatedw.mineminenomi.api.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KamisoriAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Kamisori is a combination of Geppo and Soru — extreme zigzag movement
        entity.setDeltaMovement(look.scale(3.0).add(0, 0.5, 0));
        entity.resetFallDistance();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kamisori");
    }
}
