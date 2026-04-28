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
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.SenhachijuPoundHoProjectile;

public class SenhachijuPoundHoAbility extends Ability {

    private static final float COOLDOWN = 320.0F;

    @Override
    public Result canUse(LivingEntity entity) {
        Result result = super.canUse(entity);
        if (result.isFail()) return result;
        return AbilityUseConditions.requiresSword(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.setData(ModDataAttachments.ANIMATION_STATE, new AnimationStateData(Optional.of(ResourceLocation.fromNamespaceAndPath("mineminenomi", "over_shoulder_slash")), entity.level().getGameTime()));
            ((ServerLevel)entity.level()).getChunkSource().broadcastAndSend(entity, new ClientboundAnimatePacket(entity, 0));
            SenhachijuPoundHoProjectile proj = new SenhachijuPoundHoProjectile(entity.level(), entity);
            proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.2F, 0.1F);
            entity.level().addFreshEntity(proj);
            
            startCooldown(entity, (long)COOLDOWN);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Senhachiju Pound Ho");
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.setData(ModDataAttachments.ANIMATION_STATE, new AnimationStateData());
        }
    }
}