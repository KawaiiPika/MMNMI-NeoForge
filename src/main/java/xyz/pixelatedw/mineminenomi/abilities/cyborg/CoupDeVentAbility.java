package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.CoupDeVentProjectile;

public class CoupDeVentAbility extends Ability {
    private static final int COLA_REQUIRED = 30;

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            int currentCola = stats.getBasic().cola();
            if (currentCola >= COLA_REQUIRED) {
                stats.setBasic(new PlayerStats.BasicStats(
                    stats.getBasic().doriki(), currentCola - COLA_REQUIRED, stats.getBasic().ultraCola(),
                    stats.getBasic().loyalty(), stats.getBasic().bounty(), stats.getBasic().belly(),
                    stats.getBasic().extol(), stats.getBasic().identity(), stats.getBasic().hasShadow(),
                    stats.getBasic().hasHeart(), stats.getBasic().hasStrawDoll(), stats.getBasic().isRogue(),
                    stats.getBasic().stamina(), stats.getBasic().maxStamina(), stats.getBasic().trainingPoints()
                ));
                if (entity.level() instanceof ServerLevel serverLevel) {
                    stats.sync(entity);
                    CoupDeVentProjectile projectile = new CoupDeVentProjectile(serverLevel, entity);
                    projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 1.0F);
                    serverLevel.addFreshEntity(projectile);
                }
                this.startCooldown(entity, 240L);
            }
        }
        this.stop(entity);
    }
    @Override
    public Component getDisplayName() { return Component.literal("Coup de Vent"); }
}
