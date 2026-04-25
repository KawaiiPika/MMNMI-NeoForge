package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class AncientTrunkShotAbility extends Ability {

    public AncientTrunkShotAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi_model_mammoth"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            AABB aabb = entity.getBoundingBox().inflate(1.5D);
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), 15.0F)) {
                    Vec3 dirVec = entity.position().subtract(target.position()).normalize().multiply(5.0D, 1.0D, 5.0D);
                    target.setDeltaMovement(target.getDeltaMovement().add(-dirVec.x, 0.65D, -dirVec.z));
                    target.hasImpulse = true;
                }
                break; // Only hit one target
            }

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 100, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ancienttrunkshot");
    }
}
