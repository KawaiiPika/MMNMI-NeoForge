package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FullbrightAbility extends Ability {

    private static final int CHARGE_TIME = 20;

    public FullbrightAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ito_ito_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Start charging
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration >= CHARGE_TIME) {
            if (!entity.level().isClientSide) {
                net.minecraft.world.phys.HitResult hit = entity.pick(32.0D, 0.0F, false);
                if (hit != null) {
                    net.minecraft.world.phys.Vec3 pos = hit.getLocation();
                    for (int i = 0; i < 5; i++) {
                        double ox = pos.x + (entity.getRandom().nextDouble() - 0.5) * 4.0;
                        double oz = pos.z + (entity.getRandom().nextDouble() - 0.5) * 4.0;
                        // Spawns damage at this location
                        entity.level().getEntitiesOfClass(LivingEntity.class, new net.minecraft.world.phys.AABB(ox - 1, pos.y - 1, oz - 1, ox + 1, pos.y + 5, oz + 1)).forEach(target -> {
                            if (target != entity) {
                                target.hurt(entity.damageSources().mobAttack(entity), 15.0F);
                            }
                        });
                    }
                }
                this.startCooldown(entity, 240);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.fullbright");
    }
}
