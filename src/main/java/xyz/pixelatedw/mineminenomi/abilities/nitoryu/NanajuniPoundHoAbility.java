package xyz.pixelatedw.mineminenomi.abilities.nitoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.NanajuniPoundHoProjectile;

public class NanajuniPoundHoAbility extends Ability {

    private static final float COOLDOWN = 320.0F;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            NanajuniPoundHoProjectile proj = new NanajuniPoundHoProjectile(entity.level(), entity);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.6F, 0.5F);
            entity.level().addFreshEntity(proj);
            
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), (int)COOLDOWN, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Nanajuni Pound Ho");
    }
}
