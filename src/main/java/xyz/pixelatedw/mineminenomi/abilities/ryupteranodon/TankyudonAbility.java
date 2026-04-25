package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.List;

public class TankyudonAbility extends Ability {

    public TankyudonAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_pteranodon"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);

            AABB aabb = entity.getBoundingBox().inflate(1.2D).move(entity.getLookAngle().scale(1.5D));
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), 10.0F)) {
                    Vec3 pushVec = entity.getDeltaMovement().subtract(target.getDeltaMovement()).normalize().scale(2.2);
                    target.setDeltaMovement(target.getDeltaMovement().add(pushVec));
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
        return Component.translatable("ability.mineminenomi.tankyudon");
    }
}
