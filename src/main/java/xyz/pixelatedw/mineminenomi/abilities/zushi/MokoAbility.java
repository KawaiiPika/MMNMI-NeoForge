package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MokoAbility extends Ability {

    public MokoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.moko");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            // Note: MokoProjectile should be ported for full logic, for now simulating the cooldown start.
            startCooldown(entity, 280);
            this.stop(entity);
        }
    }
}
