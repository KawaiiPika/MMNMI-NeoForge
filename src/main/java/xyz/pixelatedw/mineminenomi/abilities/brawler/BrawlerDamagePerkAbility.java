package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class BrawlerDamagePerkAbility extends Ability {

    public BrawlerDamagePerkAbility() {}

    @Override
    protected void startUsing(net.minecraft.world.entity.LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Brawler Damage Perk");
    }
}
