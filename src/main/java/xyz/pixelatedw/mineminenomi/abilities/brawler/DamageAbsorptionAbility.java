package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.List;

public class DamageAbsorptionAbility extends Ability {

    private static final long HOLD_TIME = 100L;
    private static final long COOLDOWN = 300L;
    private static final float RANGE = 10.0F;

    private static final ResourceLocation DAMAGE_BONUS_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "damage_absorption_bonus");

    private long continuityStartTime = 0;
    private float prevHealth;
    private int hits;
    private float receivedDamage;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.continuityStartTime = entity.level().getGameTime();

            if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DAMAGE_BONUS_MODIFIER_ID);
            }

            this.hits = 0;
            this.prevHealth = entity.getHealth();
            this.receivedDamage = 0.0F;

            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, (int)HOLD_TIME, 0, false, false));
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

        if (entity.getHealth() < this.prevHealth) {
            this.hits++;

        if (entity.getHealth() < this.prevHealth) {
            this.hits++;
            this.receivedDamage += (this.prevHealth - entity.getHealth());
            this.prevHealth = entity.getHealth();
        } else {
            this.prevHealth = entity.getHealth();
        }
            this.prevHealth = entity.getHealth();
        } else {
            // Update prev health just in case they healed
            this.prevHealth = entity.getHealth();
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            AABB area = entity.getBoundingBox().inflate(RANGE);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
            targets.remove(entity);

            for (LivingEntity target : targets) {
                Vec3 dirVec = target.position().subtract(entity.position()).normalize().scale(3.5);
                target.setDeltaMovement(dirVec.x, 0.0, dirVec.z);
            }

            // Spawn particles
        }

        if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            float bonus = Math.min(this.receivedDamage, 30.0F) / 5.0F;
            entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(DAMAGE_BONUS_MODIFIER_ID, bonus, AttributeModifier.Operation.ADD_VALUE));
        }

        entity.removeEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN);
        this.startCooldown(entity, COOLDOWN);
    }

    // Methods to be called by event handler
    public void onIncomingDamage(LivingEntity entity, net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent event) {
        if (this.isUsing(entity)) {
            // Could intercept damage here if needed
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Damage Absorption");
    }
}
