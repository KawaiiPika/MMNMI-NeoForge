package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class KiloPunch5000Ability extends Ability {

    public KiloPunch5000Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.5000_kilo_punch");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            AABB aabb = entity.getBoundingBox().inflate(1.5D);
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());
            DamageSource source = entity.damageSources().mobAttack(entity);
            for (LivingEntity target : targets) {
                target.hurt(source, 20.0F);
            }
            startCooldown(entity, 140);
        }
    }
}
