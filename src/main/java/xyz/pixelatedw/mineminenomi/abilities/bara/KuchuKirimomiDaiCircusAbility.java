package xyz.pixelatedw.mineminenomi.abilities.bara;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KuchuKirimomiDaiCircusAbility extends Ability {
    public KuchuKirimomiDaiCircusAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_bara_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Stub implementation
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kuchu_kirimomi_dai_circus");
    }
}
