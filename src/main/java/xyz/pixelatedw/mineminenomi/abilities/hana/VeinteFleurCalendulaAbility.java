package xyz.pixelatedw.mineminenomi.abilities.hana;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class VeinteFleurCalendulaAbility extends Ability {
    public VeinteFleurCalendulaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hana_hana_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.veinte_fleur_calendula");
    }
}
