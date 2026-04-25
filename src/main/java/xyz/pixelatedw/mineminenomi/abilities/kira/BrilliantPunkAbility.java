package xyz.pixelatedw.mineminenomi.abilities.kira;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;


public class BrilliantPunkAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kira_kira_no_mi");

    public BrilliantPunkAbility() {
        super(FRUIT);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.brilliant_punk");
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
