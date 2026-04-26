package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import java.util.List;
import net.minecraft.world.phys.Vec3;

public class GraviZoneAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public GraviZoneAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Mode logic would usually go here, default to GUARD mode behavior
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             boolean spawnGravityRings = duration % 10 == 0;
             int range = 8;
             List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(8.0D), e -> e != entity);
             list.forEach((target) -> {
                 target.setPos(target.xo, target.yo, target.zo);
                 target.addEffect(new net.minecraft.world.effect.MobEffectInstance(xyz.pixelatedw.mineminenomi.init.ModEffects.MOVEMENT_BLOCKED, 5, 0, false, false));
             });

             if (spawnGravityRings) {
                 gravityRing(entity, range, 0, true);
                 gravityRing(entity, range - 2, 4, true);
                 gravityRing(entity, range - 4, 8, true);
             }

             if (duration > 100) {
                 this.stop(entity);
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 200);
    }

    public static void gravityRing(net.minecraft.world.entity.LivingEntity entity, int range, int yOffset, boolean visibleOnlyFromOwner) {
       if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
           for(net.minecraft.server.level.ServerPlayer p : serverLevel.players()) serverLevel.sendParticles(p, new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GRAVI_ZONE.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, (double)range, (double)yOffset, 0.0D, 0.0D);
       }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_zone_guard");
    }
}
