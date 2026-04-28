package xyz.pixelatedw.mineminenomi.abilities.santoryu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;
import xyz.pixelatedw.mineminenomi.data.entity.AnimationStateData;
import net.minecraft.resources.ResourceLocation;
import java.util.Optional;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.TatsuMakiProjectile;

public class TatsuMakiAbility extends Ability {

    private static final float COOLDOWN = 240.0F;

    @Override
    public Result canUse(LivingEntity entity) {
        Result result = super.canUse(entity);
        if (result.isFail()) return result;
        return AbilityUseConditions.requiresSword(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.setData(ModDataAttachments.ANIMATION_STATE, new AnimationStateData(Optional.of(ResourceLocation.fromNamespaceAndPath("mineminenomi", "body_rotation_wide_arms")), entity.level().getGameTime()));
            TatsuMakiProjectile proj = new TatsuMakiProjectile(entity.level(), entity);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.2F, 1.0F);
            entity.level().addFreshEntity(proj);
            
            startCooldown(entity, (long)COOLDOWN);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Tatsu Maki");
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.setData(ModDataAttachments.ANIMATION_STATE, new AnimationStateData());
        }
    }
}