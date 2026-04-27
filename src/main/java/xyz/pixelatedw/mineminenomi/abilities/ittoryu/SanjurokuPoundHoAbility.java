package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.SanjurokuPoundHoProjectile;

public class SanjurokuPoundHoAbility extends Ability {

    private static final float COOLDOWN = 200.0F;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            SanjurokuPoundHoProjectile proj = new SanjurokuPoundHoProjectile(entity.level(), entity);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
            entity.level().addFreshEntity(proj);
            
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), (int)COOLDOWN, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Sanjuroku Pound Ho");
    }
}
