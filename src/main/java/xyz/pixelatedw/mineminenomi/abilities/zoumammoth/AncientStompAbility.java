package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.List;

public class AncientStompAbility extends Ability {

    public AncientStompAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi_model_mammoth"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            // Simulating one big stomp for now, full repeater logic should be handled via a PlayerTickEvent system
            AABB aabb = entity.getBoundingBox().inflate(7.0D);
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), 10.0F)) {
                    target.addEffect(new MobEffectInstance(ModEffects.DIZZY, 20, 0));
                    target.setDeltaMovement(target.getDeltaMovement().add(0.0D, 0.25D, 0.0D));
                }
            }

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 200, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ancientstomp");
    }
}
