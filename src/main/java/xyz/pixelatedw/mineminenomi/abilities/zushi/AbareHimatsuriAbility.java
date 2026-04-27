package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AbareHimatsuriAbility extends Ability {
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AbareHimatsuriAbility extends Ability {
    private static final int COOLDOWN = 300;
    private static final int HOLD_TIME = 1200;

    public AbareHimatsuriAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity instanceof Player player) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity instanceof Player player && !player.isCreative()) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
        this.startCooldown(entity, 300);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 1200) {
            this.stop(entity);
            return;
        }

        if (entity instanceof Player player) {
            player.fallDistance = 0.0F; // Prevent fall damage while flying
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration >= HOLD_TIME) {
            this.stop(entity);
            return;
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.abare_himatsuri");
    }
}
