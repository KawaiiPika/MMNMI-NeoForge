package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.zoan.ZoanAbility;

public class PhoenixFlyPointAbility extends ZoanAbility {

    public PhoenixFlyPointAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_phoenix"));
    }

    @Override
    public ResourceLocation getMorphModelId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "phoenix_fly");
    }

    @Override
    public double getScaleModifier() { return 0.0; }

    @Override
    public double getHealthModifier() { return 0; }

    @Override
    public double getDamageModifier() { return 0; }

    @Override
    protected void onTick(net.minecraft.world.entity.LivingEntity entity, long duration) {
        if (!entity.onGround()) {
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                if (!player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                }
            }
        }
    }

    @Override
    protected void stopUsing(net.minecraft.world.entity.LivingEntity entity) {
        super.stopUsing(entity);
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.phoenixflypoint");
    }
}
