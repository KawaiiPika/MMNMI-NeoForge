package xyz.pixelatedw.mineminenomi.api.abilities.basic;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PunchAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            player.displayClientMessage(Component.literal("PUNCH!"), true);
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(player);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 100, player.level().getGameTime());
            }
        }
        // Add actual punch logic here (damage nearby entities)
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.punch");
    }
}
