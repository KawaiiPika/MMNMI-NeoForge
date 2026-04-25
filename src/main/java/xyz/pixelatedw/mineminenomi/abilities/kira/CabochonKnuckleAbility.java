package xyz.pixelatedw.mineminenomi.abilities.kira;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility;


public class CabochonKnuckleAbility extends PunchAbility {

    public CabochonKnuckleAbility() {
        super();
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.cabochon_knuckle");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        super.startUsing(entity);
    }

}
