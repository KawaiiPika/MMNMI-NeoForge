package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.MokoProjectile;

public class MokoAbility extends Ability {

    public MokoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Continuous effect
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 280);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 18) {
            this.stop(entity);
            return;
        }

        if (!entity.level().isClientSide && duration % 6 == 0) {
            MokoProjectile proj = new MokoProjectile(entity.level(), entity);
            proj.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 3.0F, 2.0F);
            entity.level().addFreshEntity(proj);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.moko");
    }
}
