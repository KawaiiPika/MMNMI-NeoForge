package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SagariNoRyuseiAbility extends Ability {
    private static final int COOLDOWN = 900;
    private int count;

    public SagariNoRyuseiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            this.count = 0;

            boolean has2nd = entity.getRandom().nextInt(3) == 0;

            Arrow proj1 = createProjectile(entity);
            entity.level().addFreshEntity(proj1);

            if (has2nd) {
                this.count++;
                Arrow proj2 = createProjectile(entity);
                entity.level().addFreshEntity(proj2);
            }

            this.stop(entity);
            this.startCooldown(entity, COOLDOWN);
        }
    }

    private Arrow createProjectile(LivingEntity entity) {
        HitResult mop = entity.pick(20.0D, 0.0F, false);
        Vec3 target = mop.getLocation();

        Arrow proj = new Arrow(entity.level(), target.x, target.y + 90.0D, target.z, entity.getItemInHand(entity.getUsedItemHand()), null);
        proj.setOwner(entity);
        proj.setXRot(0.0F);
        proj.setYRot(0.0F);

        proj.setDeltaMovement(0.0D, -1.85D, 0.0D);
        return proj;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sagari_no_ryusei");
    }
}
