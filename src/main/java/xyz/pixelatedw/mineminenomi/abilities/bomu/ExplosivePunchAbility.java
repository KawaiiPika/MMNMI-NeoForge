package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ExplosivePunchAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bomu_bomu_no_mi");

    public ExplosivePunchAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            net.minecraft.world.phys.HitResult mop = entity.pick(4.0D, 0.0F, false);

            if (mop.getType() != net.minecraft.world.phys.HitResult.Type.MISS) {
                Vec3 hitPos = mop.getLocation();
                entity.level().explode(entity, hitPos.x, hitPos.y, hitPos.z, 3.0F, net.minecraft.world.level.Level.ExplosionInteraction.MOB);
                this.startCooldown(entity, 60);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.explosive_punch");
    }
}
