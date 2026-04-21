package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AbsorbedBlocksAbility extends Ability {
    public AbsorbedBlocksAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yami_yami_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.absorbed_blocks");
    }
}
