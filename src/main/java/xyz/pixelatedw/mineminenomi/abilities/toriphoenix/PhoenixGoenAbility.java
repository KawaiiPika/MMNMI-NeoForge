package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PhoenixGoenAbility extends Ability {

    public PhoenixGoenAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_phoenix"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide && !entity.onGround()) {
            // Placeholder: Actual repeated shooting logic using PlayerTickEvent needed later
            // For now just triggering cooldown
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 160, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.phoenixgoen");
    }
}
