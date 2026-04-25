package xyz.pixelatedw.mineminenomi.abilities.beta;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PerkAbility;


public class BetaImmunityAbility extends PerkAbility {

    public BetaImmunityAbility() {
        super("BetaImmunity", "");
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.beta_immunity");
    }

}
