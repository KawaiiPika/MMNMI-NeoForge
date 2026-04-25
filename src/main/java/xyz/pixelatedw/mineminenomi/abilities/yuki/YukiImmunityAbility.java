package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class YukiImmunityAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi");

    public YukiImmunityAbility() {
        super(FRUIT);
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (entity.hasEffect(ModEffects.FROSTBITE)) {
            entity.removeEffect(ModEffects.FROSTBITE);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yuki_immunities");
    }
}
