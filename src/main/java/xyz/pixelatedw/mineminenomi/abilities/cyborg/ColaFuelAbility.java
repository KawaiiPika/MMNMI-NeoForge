package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class ColaFuelAbility extends PerkAbility {
    public ColaFuelAbility() {
        super("Cola Fuel", "Allows using Cola as an energy source");
    }
    @Override
    public void tick(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }
    }
}
