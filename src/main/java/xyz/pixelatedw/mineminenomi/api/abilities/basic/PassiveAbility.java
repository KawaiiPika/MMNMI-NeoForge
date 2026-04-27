package xyz.pixelatedw.mineminenomi.api.abilities.basic;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public abstract class PassiveAbility extends Ability {
    public PassiveAbility() {
        super();
    }

    @Override
    protected void startUsing(LivingEntity entity) {
    }

    public void tick(LivingEntity entity) {
    }
}
