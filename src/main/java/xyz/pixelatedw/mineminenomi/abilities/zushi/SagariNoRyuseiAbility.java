package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.SagariNoRyuseiProjectile;
import net.minecraft.world.phys.HitResult;

public class SagariNoRyuseiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public SagariNoRyuseiAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for(int i = 2; i < 16; i += 2) {
                GraviZoneAbility.gravityRing(entity, 4, i, false);
            }

            boolean has2nd = entity.getRandom().nextInt(3) == 0;
            spawnMeteor(entity, 0);
            if (has2nd) {
                spawnMeteor(entity, 1);
            }

            this.startCooldown(entity, 900);
        }
    }

    private void spawnMeteor(LivingEntity entity, int count) {
        HitResult mop = WyHelper.rayTraceEntities(entity, 64.0D);
        if (mop == null || mop.getLocation() == null) {
            // Check block ray trace
            mop = WyHelper.rayTraceBlockSafe(entity, 64.0F);
        }
        if (mop == null || mop.getLocation() == null) return;

        double x = mop.getLocation().x;
        double y = mop.getLocation().y;
        double z = mop.getLocation().z;
        float size = count == 0 ? (float)entity.getRandom().nextInt(7) + 24.0F : (float)entity.getRandom().nextInt(3) + 8.0F;

        SagariNoRyuseiProjectile proj = new SagariNoRyuseiProjectile(entity.level(), entity, this);
        proj.setSize(size);
        proj.setPos(x, y + 90.0D, z);
        proj.setXRot(0.0F);
        proj.setYRot(0.0F);
        proj.setDeltaMovement(0.0D, -1.85D, 0.0D);
        entity.level().addFreshEntity(proj);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sagari_no_ryusei");
    }
}
