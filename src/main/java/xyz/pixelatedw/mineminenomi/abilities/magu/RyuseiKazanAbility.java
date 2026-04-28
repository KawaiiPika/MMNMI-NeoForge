package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.RyuseiKazanEntity;

public class RyuseiKazanAbility extends Ability {

    private static final int REPEATS = 5;
    private static final int TICKS_BETWEEN = 20;

    public RyuseiKazanAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in onTick
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration % TICKS_BETWEEN == 0) {
                RyuseiKazanEntity meteor = new RyuseiKazanEntity(entity.level(), entity);
                meteor.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.2F, 5.0F);
                entity.level().addFreshEntity(meteor);
            }
            if (duration >= REPEATS * TICKS_BETWEEN) {
                this.stop(entity);
                this.startCooldown(entity, 800);
            }
        } else {
             if (duration >= REPEATS * TICKS_BETWEEN) {
                 this.stop(entity);
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ryusei_kazan");
    }
}
