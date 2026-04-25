package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

import java.util.List;

public class ClawStrikeAbility extends Ability {

    public ClawStrikeAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "neko_neko_no_mi_model_leopard"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            AABB aabb = entity.getBoundingBox().inflate(1.5D);
            List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), 20.0F)) {
                    entity.level().playSound(null, target.getX(), target.getY(), target.getZ(), ModSounds.SHIGAN_SFX.get(), net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);
                }
                break; // Only strike one target
            }

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 100, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.clawstrike");
    }
}
