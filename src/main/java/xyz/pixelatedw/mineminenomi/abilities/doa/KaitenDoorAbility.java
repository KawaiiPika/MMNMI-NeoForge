package xyz.pixelatedw.mineminenomi.abilities.doa;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility;


public class KaitenDoorAbility extends PunchAbility {

    public KaitenDoorAbility() {
        super();
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kaiten_door");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        super.startUsing(entity);
    }

}
