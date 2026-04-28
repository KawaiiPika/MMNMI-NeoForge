package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class WhitePullAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi");

    public WhitePullAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            net.minecraft.world.phys.HitResult hit = entity.pick(30.0D, 0.0F, false);
            if (hit.getType() != net.minecraft.world.phys.HitResult.Type.MISS) {
                Vec3 targetPos = hit.getLocation();
                Vec3 pullVec = targetPos.subtract(entity.position()).normalize().scale(2.5);
                entity.setDeltaMovement(pullVec);
                entity.hurtMarked = true;

                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    xyz.pixelatedw.mineminenomi.init.ModSounds.MOKU_SFX.get(),
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);

                this.startCooldown(entity, 100);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.white_pull");
    }
}
