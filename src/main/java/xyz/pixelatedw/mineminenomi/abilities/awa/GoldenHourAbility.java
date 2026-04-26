package xyz.pixelatedw.mineminenomi.abilities.awa;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class GoldenHourAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "awa_awa_no_mi");
    private static final float RANGE = 10.0F;
    private static final long MAX_DURATION = 200;

    public GoldenHourAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Init
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) entity.level();
            AABB area = new AABB(entity.blockPosition()).inflate(RANGE);

            for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity && !target.isInWater()) {
                    if (!target.hasEffect(ModEffects.WASHED)) {
                        target.addEffect(new MobEffectInstance(ModEffects.WASHED, 100, 1));
                    }
                }
            }
        }

        if (getDuration(entity) >= MAX_DURATION) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        long duration = this.getDuration(entity);
        this.startCooldown(entity, (long) (200.0F + duration * 5.0F));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.golden_hour");
    }
}
