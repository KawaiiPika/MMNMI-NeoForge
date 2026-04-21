package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class TabiraYukiAbility extends Ability {

    private static final int COOLDOWN = 200;

    public TabiraYukiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             ItemStack stack = new ItemStack(ModWeapons.TABIRA_YUKI.get());
             if (entity.getMainHandItem().isEmpty()) {
                 entity.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, stack);
             } else {
                 entity.spawnAtLocation(stack);
             }
             this.startCooldown(entity, COOLDOWN);
             this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.tabira_yuki");
    }
}
