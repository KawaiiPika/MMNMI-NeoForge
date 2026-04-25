package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;


public class DoruDoruArtsPickaxeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");

    public DoruDoruArtsPickaxeAbility() {
        super(FRUIT);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.doru_doru_arts_pickaxe");
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
