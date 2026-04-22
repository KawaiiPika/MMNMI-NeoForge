package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Gear Fourth (Boundman) — inflates muscles for extreme Speed, Strength, and AoE bounce attacks.
 * Grants a suite of buffs for 20 seconds, then applies a debuff cooldown period.
 */
public class GearFourthAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    public GearFourthAbility() { super(FRUIT); }

    private boolean active = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        active = true;
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 400, 3));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 400, 4));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 400, 2));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.JUMP, 400, 3));
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        active = false;
        // Post-Gear 4 exhaustion — can't use Haki briefly
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 1));
    }

    @Override
    public boolean isUsing(LivingEntity entity) { return active; }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gear_fourth"); }
}
