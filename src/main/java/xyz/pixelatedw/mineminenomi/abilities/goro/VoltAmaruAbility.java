package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Volt Amaru — full body lightning transformation, grants major speed + damage + lightning aura. */
public class VoltAmaruAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi");
    public VoltAmaruAbility() { super(FRUIT); }

    private boolean active = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        active = true;
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 600, 3));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 600, 2));
        if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("ability.mineminenomi.volt_amaru"), true);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        active = false;
    }

    @Override
    public boolean isUsing(LivingEntity entity) { return active; }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.volt_amaru"); }
}
