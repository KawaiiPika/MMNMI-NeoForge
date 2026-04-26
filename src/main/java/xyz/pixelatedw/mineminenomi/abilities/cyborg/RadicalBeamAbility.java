package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.RadicalBeamProjectile;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class RadicalBeamAbility extends Ability {
    private static final int COLA_REQUIRED = 30;
    private static final long CHARGE_TIME = 60L;
    private long chargeStartTime = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null && stats.getBasic().cola() >= COLA_REQUIRED) {
            this.chargeStartTime = entity.level().getGameTime();
            entity.level().playSound(null, entity.blockPosition(), ModSounds.CHARGE_CYBORG_BEAM_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentCharge = entity.level().getGameTime() - this.chargeStartTime;
        if (currentCharge == 39L) {
            entity.level().playSound(null, entity.blockPosition(), ModSounds.PRE_CYBORG_BEAM_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
        }
        if (currentCharge >= CHARGE_TIME) {
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
                        entity.level().playSound(null, entity.blockPosition(), ModSounds.CYBORG_BEAM_SFX.get(), SoundSource.PLAYERS, 3.0F, 1.0F);
                        RadicalBeamProjectile projectile = new RadicalBeamProjectile(serverLevel, entity);
                        projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.0F);
                        serverLevel.addFreshEntity(projectile);
                    }
                    this.startCooldown(entity, 260L);
                }
            }
            this.stop(entity);
        }
    }
    @Override
    public Component getDisplayName() { return Component.literal("Radical Beam"); }
}
