package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.HydraEntity;

public class HydraAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doku_doku_no_mi");

    public HydraAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                HydraEntity projectile = new HydraEntity(entity.level(), entity);
                projectile.shootFromRotation(entity, entity.getXRot() + (entity.getRandom().nextFloat() - 0.5f) * 15, entity.getYRot() + (entity.getRandom().nextFloat() - 0.5f) * 15, 0.0F, 1.5F, 1.0F);
                entity.level().addFreshEntity(projectile);
            }

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                net.minecraft.sounds.SoundEvents.SLIME_SQUISH,
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);

            this.startCooldown(entity, 200);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.hydra");
    }
}
