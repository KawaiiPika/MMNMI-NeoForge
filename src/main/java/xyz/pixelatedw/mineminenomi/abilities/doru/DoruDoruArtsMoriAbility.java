package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class DoruDoruArtsMoriAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final long COOLDOWN = 80;
    private static final int MAX_SHOTS = 25;
    private int shotsFired = 0;
    private long lastShotTick = 0;

    public DoruDoruArtsMoriAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.shotsFired = 0;
        this.lastShotTick = entity.level().getGameTime();
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            PlayerStats stats = PlayerStats.get(entity);
            boolean isChampFight = false;

            if (stats != null) {
                ResourceLocation championId = ModAbilities.REGISTRY.getKey(ModAbilities.REGISTRY.get(ResourceLocation.fromNamespaceAndPath("mineminenomi", "candle_champion")));
                if (championId != null && stats.isAbilityActive(championId.toString())) {
                    isChampFight = true;
                }
            }

            long currentTick = entity.level().getGameTime();

            if (isChampFight) {
                if (shotsFired < MAX_SHOTS) {
                    if (currentTick - lastShotTick >= 1) {
                        shotsFired++;
                        lastShotTick = currentTick;
                    }
                } else {
                    this.stop(entity);
                }
            } else {
                if (shotsFired < 1) {
                    if (duration >= 7) {
                        shotsFired++;
                        this.stop(entity);
                    }
                }
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.doru_doru_arts_mori");
    }
}
