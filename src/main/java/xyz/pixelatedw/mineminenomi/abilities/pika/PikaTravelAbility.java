package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PikaTravelAbility extends Ability {

    public PikaTravelAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.pika_travel");
    }
}
