package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class AbareHimatsuriAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi");

    public AbareHimatsuriAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity instanceof Player player) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity instanceof Player player) {
            player.fallDistance = 0.0F;
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }

            if (player.isSprinting()) {
                AbilityHelper.setDeltaMovement(player, player.getDeltaMovement().x * 0.69D, player.getDeltaMovement().y, player.getDeltaMovement().z * 0.69D);
                player.setSprinting(false);
            }
        }

        if (duration > 1200) {
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
        this.startCooldown(entity, 300);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.abare_himatsuri");
    }
}
