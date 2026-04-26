package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.MokoProjectile;

public class MokoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public MokoAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in tick
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration % 10 == 0 && duration <= 60) {
            MokoProjectile proj = new MokoProjectile(entity.level(), entity, this);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 2.0F);
            entity.level().addFreshEntity(proj);
        }
        if (duration > 60) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 280);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.moko");
    }
}
