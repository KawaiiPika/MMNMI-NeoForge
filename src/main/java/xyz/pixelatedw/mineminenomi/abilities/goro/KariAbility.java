package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KariAbility extends Ability {

    public KariAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            AABB aabb = entity.getBoundingBox().inflate(15.0);
            entity.level().getEntitiesOfClass(LivingEntity.class, aabb).forEach(target -> {
                if (target != entity) {
                    target.hurt(entity.damageSources().lightningBolt(), 15.0F);
                    net.minecraft.world.entity.LightningBolt bolt = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(entity.level());
                    if (bolt != null) {
                        bolt.moveTo(target.position());
                        entity.level().addFreshEntity(bolt);
                    }
                }
            });
            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kari");
    }
}
