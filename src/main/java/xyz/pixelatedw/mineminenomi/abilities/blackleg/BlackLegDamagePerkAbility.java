package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class BlackLegDamagePerkAbility extends Ability {

    public BlackLegDamagePerkAbility() {}

    @Override
    protected void startUsing(net.minecraft.world.entity.LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Black Leg Damage Perk");
    }
}
