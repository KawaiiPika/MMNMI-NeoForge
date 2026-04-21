package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class YukiImmunityAbility extends Ability {

    public YukiImmunityAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yuki_immunity");
    }
}
