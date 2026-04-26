package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class EleclawAbility extends Ability {

    private static final int MAX_STACKS = 9;
    private static final long COOLDOWN = 200L;

    private static final ResourceLocation ATTACK_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "eleclaw_attack_speed");

    private int currentStacks = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.currentStacks = MAX_STACKS;
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (entity.isInWater() || this.currentStacks <= 0) {
            this.stop(entity);
            return;
        }

        if (entity.getAttribute(Attributes.ATTACK_SPEED) != null && entity.getAttribute(Attributes.ATTACK_SPEED).getModifier(ATTACK_SPEED_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, 0.35, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity.getAttribute(Attributes.ATTACK_SPEED) != null) {
            entity.getAttribute(Attributes.ATTACK_SPEED).removeModifier(ATTACK_SPEED_MODIFIER_ID);
        }
        this.currentStacks = 0;
        this.startCooldown(entity, COOLDOWN);
    }

    public void consumeStack(LivingEntity entity, int amount) {
        if (!ElectroHelper.hasSulongActive(entity)) {
            this.currentStacks -= amount;
        }
    }

    public int getStacks() {
        return this.currentStacks;
    }

    public void applyOnHit(LivingEntity attacker, LivingEntity target) {
        if (attacker.getRandom().nextInt(10) == 0) {
            // Paralysis effect doesn't natively exist yet, assume generic STUN or SLOWNESS
            target.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 10, 5, false, false));
        }
        this.consumeStack(attacker, 1);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Eleclaw");
    }
}
