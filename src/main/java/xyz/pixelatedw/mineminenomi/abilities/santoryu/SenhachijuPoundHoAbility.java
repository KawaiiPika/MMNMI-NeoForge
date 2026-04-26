package xyz.pixelatedw.mineminenomi.abilities.santoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.SenhachijuPoundHoProjectile;

public class SenhachijuPoundHoAbility extends Ability {

    private static final float COOLDOWN = 320.0F;

    @Override
    public Result canUse(LivingEntity entity) {
        Result result = super.canUse(entity);
        if (result.isFail()) return result;
        return AbilityUseConditions.requiresSword(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            SenhachijuPoundHoProjectile proj = new SenhachijuPoundHoProjectile(entity.level(), entity);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.2F, 0.1F);
            entity.level().addFreshEntity(proj);
            
            startCooldown(entity, (long)COOLDOWN);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Senhachiju Pound Ho");
    }
}
