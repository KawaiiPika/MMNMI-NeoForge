package xyz.pixelatedw.mineminenomi.abilities.nitoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.NanahyakunijuPoundHoProjectile;

public class NanahyakunijuPoundHoAbility extends Ability {

    private static final float COOLDOWN = 320.0F;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            NanahyakunijuPoundHoProjectile proj = new NanahyakunijuPoundHoProjectile(entity.level(), entity);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 0.2F);
            entity.level().addFreshEntity(proj);
            
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), (int)COOLDOWN, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Nanahyakuniju Pound Ho");
    }
}
