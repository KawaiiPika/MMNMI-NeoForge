package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class AncientSweepAbility extends Ability {

    public AncientSweepAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi_model_mammoth"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            // Simplified sweep check, charge component to be added later
            float damage = 15.0F;
            float radius = 3.0F;

            // In actual code, check morph for guard point to expand
            xyz.pixelatedw.mineminenomi.data.entity.MorphData morphData = entity.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.MORPH_DATA);
            if (morphData.activeMorphs().contains(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mammoth_guard"))) {
                radius *= 2.0F;
                damage += 5.0F;
            }

            AABB aabb = entity.getBoundingBox().inflate(radius).move(entity.getLookAngle().scale(radius));
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), damage)) {
                    Vec3 speed = entity.getLookAngle().normalize().scale(3.0);
                    target.setDeltaMovement(target.getDeltaMovement().add(speed.x, 0.5D, speed.z));
                }
            }

            entity.swing(net.minecraft.world.InteractionHand.MAIN_HAND, true);

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 160, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ancientsweep");
    }
}
