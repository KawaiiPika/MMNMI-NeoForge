package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class BlackBoxAbility extends Ability {

    public BlackBoxAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            net.minecraft.world.phys.Vec3 hitPos = entity.pick(10.0, 0, false).getLocation();
            entity.level().getEntitiesOfClass(LivingEntity.class, new net.minecraft.world.phys.AABB(hitPos, hitPos).inflate(2.0)).forEach(target -> {
                if (target != entity) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WITHER, 100, 1));
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
                }
            });
            this.startCooldown(entity, 320);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.black_box");
    }
}
