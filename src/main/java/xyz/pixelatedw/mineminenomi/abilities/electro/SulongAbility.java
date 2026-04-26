package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SulongAbility extends Ability {

    private static final long MIN_COOLDOWN = 100L;

    private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sulong_speed");
    private static final ResourceLocation ATTACK_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sulong_damage");
    private static final ResourceLocation JUMP_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sulong_jump");
    private static final ResourceLocation FALL_RESISTANCE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sulong_fall_resistance");
    private static final ResourceLocation STEP_HEIGHT_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sulong_step_height");

    private long continuityStartTime = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            if (ElectroHelper.canTransform(entity.level())) {
                this.continuityStartTime = entity.level().getGameTime();
            } else {
                if (entity instanceof ServerPlayer player) {
                    player.sendSystemMessage(Component.translatable("ability.mineminenomi.message.need_moon"));
                }
                this.stop(entity);
            }
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!ElectroHelper.canTransform(entity.level())) {
            this.stop(entity);
            return;
        }

        if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null && entity.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(SPEED_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(SPEED_MODIFIER_ID, 1.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null && entity.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(ATTACK_DAMAGE_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, 10.0, AttributeModifier.Operation.ADD_VALUE));
        }
        if (entity.getAttribute(Attributes.JUMP_STRENGTH) != null && entity.getAttribute(Attributes.JUMP_STRENGTH).getModifier(JUMP_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.JUMP_STRENGTH).addTransientModifier(new AttributeModifier(JUMP_MODIFIER_ID, 1.05, AttributeModifier.Operation.ADD_VALUE));
        }
        if (entity.getAttribute(Attributes.SAFE_FALL_DISTANCE) != null && entity.getAttribute(Attributes.SAFE_FALL_DISTANCE).getModifier(FALL_RESISTANCE_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.SAFE_FALL_DISTANCE).addTransientModifier(new AttributeModifier(FALL_RESISTANCE_MODIFIER_ID, 6.25, AttributeModifier.Operation.ADD_VALUE));
        }
        if (entity.getAttribute(Attributes.STEP_HEIGHT) != null && entity.getAttribute(Attributes.STEP_HEIGHT).getModifier(STEP_HEIGHT_MODIFIER_ID) == null) {
            entity.getAttribute(Attributes.STEP_HEIGHT).addTransientModifier(new AttributeModifier(STEP_HEIGHT_MODIFIER_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
            entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_ID);
        }
        if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_MODIFIER_ID);
        }
        if (entity.getAttribute(Attributes.JUMP_STRENGTH) != null) {
            entity.getAttribute(Attributes.JUMP_STRENGTH).removeModifier(JUMP_MODIFIER_ID);
        }
        if (entity.getAttribute(Attributes.SAFE_FALL_DISTANCE) != null) {
            entity.getAttribute(Attributes.SAFE_FALL_DISTANCE).removeModifier(FALL_RESISTANCE_MODIFIER_ID);
        }
        if (entity.getAttribute(Attributes.STEP_HEIGHT) != null) {
            entity.getAttribute(Attributes.STEP_HEIGHT).removeModifier(STEP_HEIGHT_MODIFIER_ID);
        }

        long duration = entity.level().getGameTime() - this.continuityStartTime;
        long cooldown = Math.max(MIN_COOLDOWN, duration * 2L);
        this.startCooldown(entity, cooldown);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Sulong");
    }
}
