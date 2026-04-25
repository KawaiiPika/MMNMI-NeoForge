package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class SaiseiNoHonoAbility extends Ability {

    public SaiseiNoHonoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_phoenix"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            int healedCount = 0;

            if (entity.isCrouching()) {
                AABB aabb = entity.getBoundingBox().inflate(10.0D);
                List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e.isAlive());

                for (LivingEntity target : targets) {
                    target.heal(10.0F);
                    target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                    healedCount++;
                }
            } else {
                // Single hit trigger behavior via attack hooks to be added later. Simulating single heal.
                entity.heal(10.0F);
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                healedCount = 1;
            }

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 300 + (healedCount * 120), entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.saiseinohono");
    }
}
