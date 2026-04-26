package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class ColaBackpackBonusAbility extends PerkAbility {
    public static final int EXTRA_COLA = 500;
    public ColaBackpackBonusAbility() {
        super("Cola Backpack Bonus", "Provides extra cola capacity");
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
