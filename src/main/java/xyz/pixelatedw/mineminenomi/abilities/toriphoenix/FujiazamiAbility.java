package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FujiazamiAbility extends Ability {

    public FujiazamiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_phoenix"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide && !entity.onGround()) {
            // Guard/continuous logic will be properly mapped in later tick loop passes.
            // Setting a simulated cooldown for testing now.
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null && getAbilityId() != null) {
                stats.setAbilityCooldown(getAbilityId().toString(), 200, entity.level().getGameTime());
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.fujiazami");
    }
}
