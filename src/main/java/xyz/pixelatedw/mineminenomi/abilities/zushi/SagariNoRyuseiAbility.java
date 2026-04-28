package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.SagariNoRyuseiProjectile;

public class SagariNoRyuseiAbility extends Ability {

    public SagariNoRyuseiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            // Spawn gravity rings effect handled on client later
            boolean has2nd = entity.getRandom().nextInt(3) == 0;

            net.minecraft.world.phys.Vec3 start = entity.getEyePosition();
            net.minecraft.world.phys.Vec3 look = entity.getLookAngle();
            net.minecraft.world.phys.Vec3 end = start.add(look.x * 64.0, look.y * 64.0, look.z * 64.0);
            net.minecraft.world.level.ClipContext ctx = new net.minecraft.world.level.ClipContext(start, end, net.minecraft.world.level.ClipContext.Block.OUTLINE, net.minecraft.world.level.ClipContext.Fluid.NONE, entity);
            HitResult mop = entity.level().clip(ctx);
            if (mop != null && mop.getType() != HitResult.Type.MISS) {
                double x = mop.getLocation().x;
                double y = mop.getLocation().y;
                double z = mop.getLocation().z;

                spawnMeteor(entity, x, y, z, entity.getRandom().nextInt(30 - 24) + 24);
                if (has2nd) {
                    spawnMeteor(entity, x + 5, y, z + 5, entity.getRandom().nextInt(10 - 8) + 8);
                }
            }

            this.startCooldown(entity, 900);
        }
    }

    private void spawnMeteor(LivingEntity entity, double x, double y, double z, float size) {
        SagariNoRyuseiProjectile proj = new SagariNoRyuseiProjectile(entity.level(), entity);
        proj.setSize(size);
        proj.setPos(x, y + 90.0, z);
        proj.setXRot(0.0F);
        proj.setYRot(0.0F);
        proj.setDeltaMovement(0, -3.0, 0); // basic falling speed
        entity.level().addFreshEntity(proj);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sagari_no_ryusei");
    }
}
