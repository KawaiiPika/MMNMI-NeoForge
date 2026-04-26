package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class VoltAmaruFlightAbility extends Ability {

    public VoltAmaruFlightAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null && stats.isAbilityActive("mineminenomi:volt_amaru")) {
            // Give flight when Volt Amaru is active
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                if (!player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                }
            }
        }
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.volt_amaru_flight");
    }
}
