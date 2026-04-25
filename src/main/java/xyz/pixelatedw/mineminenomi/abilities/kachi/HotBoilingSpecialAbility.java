package xyz.pixelatedw.mineminenomi.abilities.kachi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class HotBoilingSpecialAbility extends Ability {

    public HotBoilingSpecialAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kachi_kachi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var trace = xyz.pixelatedw.mineminenomi.api.WyHelper.rayTraceEntities(entity, 3.0);
            Entity target = trace != null ? trace.getEntity() : null;
            if (target instanceof LivingEntity livingTarget) {
                if (livingTarget.hurt(entity.damageSources().mobAttack(entity), 20.0F)) {
                    livingTarget.igniteForSeconds(20);
                }
            }
            this.startCooldown(entity, 100);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.hot_boiling_special");
    }
}
