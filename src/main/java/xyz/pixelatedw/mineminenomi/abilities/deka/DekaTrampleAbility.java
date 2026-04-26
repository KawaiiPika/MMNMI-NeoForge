package xyz.pixelatedw.mineminenomi.abilities.deka;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DekaTrampleAbility extends Ability {

    public DekaTrampleAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "deka_deka_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && entity.onGround() && entity.isSprinting()) {
            if (entity.tickCount % 5 != 0) return; // limit tick frequency for trample
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5.0), e -> e != entity)) {
                target.hurt(entity.damageSources().mobAttack(entity), 8.0F);
                Vec3 knockback = entity.getLookAngle().scale(2.0);
                target.push(knockback.x, 0.2, knockback.z);
            }
        }
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.deka_trample");
    }
}
