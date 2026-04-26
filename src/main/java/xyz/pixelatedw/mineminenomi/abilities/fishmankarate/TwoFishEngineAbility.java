package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class TwoFishEngineAbility extends Ability {

    private static final long HOLD_TIME = 200L;
    private static final long MIN_COOLDOWN = 100L;

    private static final ResourceLocation SWIM_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "two_fish_engine_swim_speed");

    private long continuityStartTime = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
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

        if (entity.getAttribute(NeoForgeMod.SWIM_SPEED) != null && entity.getAttribute(NeoForgeMod.SWIM_SPEED).getModifier(SWIM_SPEED_MODIFIER_ID) == null) {
            entity.getAttribute(NeoForgeMod.SWIM_SPEED).addTransientModifier(new AttributeModifier(SWIM_SPEED_MODIFIER_ID, 1.75, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity.getAttribute(NeoForgeMod.SWIM_SPEED) != null) {
            entity.getAttribute(NeoForgeMod.SWIM_SPEED).removeModifier(SWIM_SPEED_MODIFIER_ID);
        }
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;
        this.startCooldown(entity, MIN_COOLDOWN + currentDuration);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Two Fish Engine");
    }
}
