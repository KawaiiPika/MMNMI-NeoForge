package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class TempuraudonAbility extends Ability {

    public TempuraudonAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryu_ryu_no_mi_model_pteranodon"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);

            xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.TempuraudonProjectile proj = new xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.TempuraudonProjectile(entity.level(), entity);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 5.0F, 0.0F);
            entity.level().addFreshEntity(proj);

            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 240, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.tempuraudon");
    }
}
