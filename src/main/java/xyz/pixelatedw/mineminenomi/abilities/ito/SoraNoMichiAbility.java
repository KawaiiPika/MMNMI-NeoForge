package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Sora no Michi — Sky Path; creates a string path in the sky, allowing flight/levitation. */
public class SoraNoMichiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ito_ito_no_mi");
    public SoraNoMichiAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Levitate upward along string path
        Vec3 current = entity.getDeltaMovement();
        entity.setDeltaMovement(current.x * 0.5, 1.2, current.z * 0.5);
        entity.resetFallDistance();
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
            net.minecraft.world.effect.MobEffects.SLOW_FALLING, 60, 0));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.sora_no_michi"); }
}
