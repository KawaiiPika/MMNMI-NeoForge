package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MedicalExpertisePerkAbility extends Ability {

    public MedicalExpertisePerkAbility() {}

    @Override
    protected void startUsing(net.minecraft.world.entity.LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Medical Expertise Perk");
    }
}
