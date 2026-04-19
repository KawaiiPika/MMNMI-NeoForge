package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SniperAccuracyPerkAbility extends Ability {

    public SniperAccuracyPerkAbility() {}

    @Override
    protected void startUsing(net.minecraft.world.entity.LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Sniper Accuracy Perk");
    }
}
