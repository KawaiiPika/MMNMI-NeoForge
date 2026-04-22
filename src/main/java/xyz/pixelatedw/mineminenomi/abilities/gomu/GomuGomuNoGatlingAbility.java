package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GomuGomuNoGatlingAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    public GomuGomuNoGatlingAbility() { super(FRUIT); }

    private static final int GATLING_DURATION = 40; // 2 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GOMU_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.2F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < GATLING_DURATION) {
            if (!entity.level().isClientSide && duration % 2 == 0) {
                xyz.pixelatedw.mineminenomi.entities.projectiles.GomuPistolEntity punch = new xyz.pixelatedw.mineminenomi.entities.projectiles.GomuPistolEntity(entity.level(), entity);
                punch.shootFromRotation(entity, entity.getXRot() + (entity.getRandom().nextFloat() - 0.5F) * 10, entity.getYRot() + (entity.getRandom().nextFloat() - 0.5F) * 10, 0.0F, 2.0F, 0.2F);
                entity.level().addFreshEntity(punch);
            }
        } else {
            this.startCooldown(entity, 60);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gomu_gomu_no_gatling"); }
}
