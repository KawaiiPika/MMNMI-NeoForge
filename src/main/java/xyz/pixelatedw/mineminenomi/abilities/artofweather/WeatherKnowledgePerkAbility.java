package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class WeatherKnowledgePerkAbility extends Ability {

    public WeatherKnowledgePerkAbility() {}

    @Override
    protected void startUsing(net.minecraft.world.entity.LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Weather Knowledge Perk");
    }
}
