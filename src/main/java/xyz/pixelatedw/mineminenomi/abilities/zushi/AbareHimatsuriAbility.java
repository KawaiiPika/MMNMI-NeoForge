package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AbareHimatsuriAbility extends Ability {

    public AbareHimatsuriAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.abare_himatsuri");
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
            if (player.isCrouching()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(0.69D, 1.0D, 0.69D));
                player.setShiftKeyDown(false);
            }
        }

        if (duration > 1200) {
            this.stop(entity);
        }
    }

    @Override
    public void stop(LivingEntity entity) {
        super.stop(entity);
        if (entity instanceof Player player && !player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
        startCooldown(entity, 300);
    }
}
