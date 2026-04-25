package xyz.pixelatedw.mineminenomi.abilities.ori;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PunchAbility;


public class BindAbility extends PunchAbility {

    public BindAbility() {
        super();
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bind");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        super.startUsing(entity);
    }

}
