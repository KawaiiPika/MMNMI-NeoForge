package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

public class DoruDoruBallAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final float MAX_DURATION = 200.0F;

    public DoruDoruBallAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.isOnFire()) {
            this.stop(entity);
            return;
        }

        if (!entity.level().isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 5, true, false));
            entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 5, true, false));

            PlayerStats stats = PlayerStats.get(entity);
            boolean isChampionActive = false;
            if (stats != null) {
                ResourceLocation championId = ModAbilities.REGISTRY.getKey(ModAbilities.REGISTRY.get(ResourceLocation.fromNamespaceAndPath("mineminenomi", "candle_champion")));
                if (championId != null && stats.isAbilityActive(championId.toString())) {
                    isChampionActive = true;
                }
            }
        }

        if (getDuration(entity) >= MAX_DURATION) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            entity.removeEffect(MobEffects.JUMP);
        }
        long duration = this.getDuration(entity);
        this.startCooldown(entity, (long) (30.0F + duration));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.doru_doru_ball");
    }
}
