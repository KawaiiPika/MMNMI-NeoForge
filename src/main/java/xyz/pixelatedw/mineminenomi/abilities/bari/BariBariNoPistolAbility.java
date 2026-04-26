package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility;

public class BariBariNoPistolAbility extends PunchAbility {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_mi");

    public BariBariNoPistolAbility() {
        super();
    }

    @Override
    public ResourceLocation getAbilityId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_pistol");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        super.startUsing(entity);
        if (!entity.level().isClientSide()) {
            net.minecraft.world.phys.Vec3 look = entity.getLookAngle();
            for (var target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0).move(look.scale(1.5)), e -> e != entity && e.isAlive())) {
                target.hurt(entity.level().damageSources().mobAttack(entity), 12.0F);
                if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.CRIT, target.getX(), target.getY() + 1, target.getZ(), 5, 0.2, 0.2, 0.2, 0.05);
                }
                break; // Only hit one
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bari_bari_no_pistol");
    }
}
