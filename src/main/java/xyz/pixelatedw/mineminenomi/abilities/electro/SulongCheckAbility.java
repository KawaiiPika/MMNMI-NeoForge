package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;

public class SulongCheckAbility extends PerkAbility {

    public SulongCheckAbility() {
        super("Sulong Check", "Check if player is sulong capable");
    }

    @Override
    public void tick(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityActive(getAbilityId().toString(), true);
            }
        }
    }
}
