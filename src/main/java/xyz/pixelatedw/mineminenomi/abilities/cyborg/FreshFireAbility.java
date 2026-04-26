package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.FreshFireProjectile;

public class FreshFireAbility extends Ability {
    private static final int COLA_REQUIRED_PER_BURST = 1;
    private static final int MAX_BURSTS = 10;
    private int burstsFired = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        this.burstsFired = 0;
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration % 3 == 0) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null) {
                int currentCola = stats.getBasic().cola();
                if (currentCola >= COLA_REQUIRED_PER_BURST && this.burstsFired < MAX_BURSTS) {
                    stats.setBasic(new PlayerStats.BasicStats(
                        stats.getBasic().doriki(), currentCola - COLA_REQUIRED_PER_BURST, stats.getBasic().ultraCola(),
                        stats.getBasic().loyalty(), stats.getBasic().bounty(), stats.getBasic().belly(),
                        stats.getBasic().extol(), stats.getBasic().identity(), stats.getBasic().hasShadow(),
                        stats.getBasic().hasHeart(), stats.getBasic().hasStrawDoll(), stats.getBasic().isRogue(),
                        stats.getBasic().stamina(), stats.getBasic().maxStamina(), stats.getBasic().trainingPoints()
                    ));
                    if (entity.level() instanceof ServerLevel serverLevel) {
                        stats.sync(entity);
                        FreshFireProjectile projectile = new FreshFireProjectile(serverLevel, entity);
                        projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 5.0F);
                        serverLevel.addFreshEntity(projectile);
                    }
                    this.burstsFired++;
                } else {
                    this.stop(entity);
                }
            } else {
                this.stop(entity);
            }
        }
    }
    @Override
    protected void stopUsing(LivingEntity entity) { this.startCooldown(entity, 100L); }
    @Override
    public Component getDisplayName() { return Component.literal("Fresh Fire"); }
}
