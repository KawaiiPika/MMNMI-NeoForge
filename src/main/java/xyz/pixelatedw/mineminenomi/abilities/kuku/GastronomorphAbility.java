package xyz.pixelatedw.mineminenomi.abilities.kuku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;


public class GastronomorphAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kuku_kuku_no_mi");

    public GastronomorphAbility() {
        super(FRUIT);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gastronomorph");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
    }

}
