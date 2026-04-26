package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DesertGrandeEspadaAbility extends Ability {
    public DesertGrandeEspadaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.desert_grande_espada");
    }
}
