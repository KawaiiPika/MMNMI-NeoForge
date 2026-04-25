package xyz.pixelatedw.mineminenomi.abilities.kachi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class VulcanizationAbility extends Ability {

    public VulcanizationAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kachi_kachi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            // Note: Stat modifications like Armor should be applied using Data Components/Attachments or active effect logic.
            // Simplified for this task scope.
            var trace = xyz.pixelatedw.mineminenomi.api.WyHelper.rayTraceEntities(entity, 3.0);
            Entity target = trace != null ? trace.getEntity() : null;
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.hurt(entity.damageSources().mobAttack(entity), 4.0F);
            }
            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.vulcanization");
    }
}
