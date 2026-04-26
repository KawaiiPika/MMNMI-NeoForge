package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class ColaOverdriveAbility extends Ability {
    private static final int DRAIN_PER_TICK = 2;
    private static final long HOLD_TIME = 300L;
    private static final long BASE_COOLDOWN = 100L;
    private static final ResourceLocation MOVEMENT_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "cola_overdrive_speed");
    private static final ResourceLocation SWIM_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "cola_overdrive_swim_speed");
    private static final ResourceLocation STEP_HEIGHT_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "cola_overdrive_step_height");

    private long continuityStartTime = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null && stats.getBasic().cola() > 0) {
            this.continuityStartTime = entity.level().getGameTime();
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;
        if (currentDuration > HOLD_TIME) {
            this.stop(entity);
            return;
        }
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            int currentCola = stats.getBasic().cola();
            if (currentCola <= 0) {
                if (entity instanceof ServerPlayer player) {
                    player.sendSystemMessage(Component.translatable("ability.mineminenomi.message.not_enough_cola"));
                }
                this.stop(entity);
                return;
            }
            int newCola = Math.max(0, currentCola - DRAIN_PER_TICK);
            stats.setBasic(new PlayerStats.BasicStats(
                stats.getBasic().doriki(), newCola, stats.getBasic().ultraCola(),
                stats.getBasic().loyalty(), stats.getBasic().bounty(), stats.getBasic().belly(),
                stats.getBasic().extol(), stats.getBasic().identity(), stats.getBasic().hasShadow(),
                stats.getBasic().hasHeart(), stats.getBasic().hasStrawDoll(), stats.getBasic().isRogue(),
                stats.getBasic().stamina(), stats.getBasic().maxStamina(), stats.getBasic().trainingPoints()
            ));
            if (entity instanceof ServerPlayer player) {
                stats.sync(player);
            }
            entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 5, 0, false, false));
            if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null && entity.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(MOVEMENT_SPEED_MODIFIER_ID) == null) {
                entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_ID, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
            if (entity.getAttribute(NeoForgeMod.SWIM_SPEED) != null && entity.getAttribute(NeoForgeMod.SWIM_SPEED).getModifier(SWIM_SPEED_MODIFIER_ID) == null) {
                entity.getAttribute(NeoForgeMod.SWIM_SPEED).addTransientModifier(new AttributeModifier(SWIM_SPEED_MODIFIER_ID, 1.0, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
            if (entity.getAttribute(Attributes.STEP_HEIGHT) != null && entity.getAttribute(Attributes.STEP_HEIGHT).getModifier(STEP_HEIGHT_MODIFIER_ID) == null) {
                entity.getAttribute(Attributes.STEP_HEIGHT).addTransientModifier(new AttributeModifier(STEP_HEIGHT_MODIFIER_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity.getAttribute(Attributes.MOVEMENT_SPEED) != null) entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MOVEMENT_SPEED_MODIFIER_ID);
        if (entity.getAttribute(NeoForgeMod.SWIM_SPEED) != null) entity.getAttribute(NeoForgeMod.SWIM_SPEED).removeModifier(SWIM_SPEED_MODIFIER_ID);
        if (entity.getAttribute(Attributes.STEP_HEIGHT) != null) entity.getAttribute(Attributes.STEP_HEIGHT).removeModifier(STEP_HEIGHT_MODIFIER_ID);
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;
        this.startCooldown(entity, BASE_COOLDOWN + currentDuration);
    }
    @Override
    public Component getDisplayName() { return Component.literal("Cola Overdrive"); }
}
