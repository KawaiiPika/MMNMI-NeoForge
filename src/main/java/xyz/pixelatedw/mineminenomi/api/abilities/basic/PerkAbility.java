package xyz.pixelatedw.mineminenomi.api.abilities.basic;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PerkAbility extends Ability {

    private final String name;
    private final String description;

    public PerkAbility(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(name);
    }
}
