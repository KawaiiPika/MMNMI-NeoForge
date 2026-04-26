package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public abstract class Ability {

    private net.minecraft.resources.ResourceLocation requiredFruit;

    public Ability() {}

    public Ability(net.minecraft.resources.ResourceLocation requiredFruit) {
        this.requiredFruit = requiredFruit;
    }

    public net.minecraft.resources.ResourceLocation getRequiredFruit() {
        return requiredFruit;
    }

    public void use(LivingEntity entity) {
        if (entity.level().isClientSide) return;
        if (this.isUsing(entity)) {
            this.stop(entity);
            return;
        }

        Result result = this.canUse(entity);
        if (result.isFail()) {
            if (result.getMessage() != null) {
                if (entity instanceof net.minecraft.world.entity.player.Player player) {
                    player.displayClientMessage(result.getMessage(), true);
                }
            }
        } else {
            this.startUsing(entity);
            updateActiveState(entity, true);
            if (entity.level().isClientSide) {
                xyz.pixelatedw.mineminenomi.client.renderers.layers.AuraLayer.addAbilityIcon(entity, this);
            }
        }
    }

    public void stop(LivingEntity entity) {
        this.stopUsing(entity);
        updateActiveState(entity, false);
    }

    private void updateActiveState(LivingEntity entity, boolean active) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            net.minecraft.resources.ResourceLocation id = getAbilityId();
            if (id != null) {
                stats.setAbilityActive(id.toString(), active);
                stats.sync(entity);
            }
        }
    }

    public net.minecraft.resources.ResourceLocation getAbilityId() {
        for (java.util.Map.Entry<net.minecraft.resources.ResourceKey<Ability>, Ability> entry : xyz.pixelatedw.mineminenomi.init.ModAbilities.REGISTRY.entrySet()) {
            if (entry.getValue() == this) {
                return entry.getKey().location();
            }
        }
        return null;
    }

    protected void startCooldown(LivingEntity entity, long ticks) {
        PlayerStats stats = PlayerStats.get(entity);
        net.minecraft.resources.ResourceLocation id = getAbilityId();
        if (stats != null && id != null) {
            stats.setAbilityCooldown(id.toString(), ticks, entity.level().getGameTime());
            stats.sync(entity);
        }
    }

    protected abstract void startUsing(LivingEntity entity);

    protected void stopUsing(LivingEntity entity) {}

    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        net.minecraft.resources.ResourceLocation id = getAbilityId();
        return stats != null && id != null && stats.isAbilityActive(id.toString());
    }

    public xyz.pixelatedw.mineminenomi.api.util.Result canUse(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null) {
            if (this.requiredFruit != null) {
                if (!stats.getDevilFruit().map(id -> id.equals(this.requiredFruit)).orElse(false)) {
                    return xyz.pixelatedw.mineminenomi.api.util.Result.fail(Component.translatable("ability.error.wrong_fruit"));
                }
                if (entity.hasEffect(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI)) {
                    return xyz.pixelatedw.mineminenomi.api.util.Result.fail(Component.translatable("ability.error.kairoseki"));
                }
            }
            
            net.minecraft.resources.ResourceLocation id = getAbilityId();
            if (id != null && stats.getCombat().abilityCooldowns() != null) {
                Long expiry = stats.getCombat().abilityCooldowns().get(id.toString());
                if (expiry != null && expiry > entity.level().getGameTime()) {
                    // It is on cooldown, prevent use
                    return xyz.pixelatedw.mineminenomi.api.util.Result.fail(null); // Return null component to fail silently without spamming chat
                }
            }
        }
        return xyz.pixelatedw.mineminenomi.api.util.Result.success();
    }

    public Result canUnlock(LivingEntity entity) {
        return Result.success();
    }

    public double getRequiredDoriki() {
        return 0.0;
    }

    protected long startTick = -1;

    public void tick(LivingEntity entity) {
        if (entity.level().isClientSide) return;
        if (isUsing(entity)) {
            if (startTick == -1) startTick = entity.level().getGameTime();
            this.onTick(entity, getDuration(entity));
        } else {
            startTick = -1;
        }
    }

    protected void onTick(LivingEntity entity, long duration) {}

    public boolean checkInvulnerability(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }

    public void onLogiaDodge(LivingEntity entity, LivingEntity attacker) {
    }

    public void onDamageTake(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source, float amount) {
    }

    public float onAttack(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source, float amount) {
        return amount;
    }

    public void onDamageTakenByTarget(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source) {}

    public void onDamageTaken(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {}

    public long getDuration(LivingEntity entity) {
        return startTick == -1 ? 0 : entity.level().getGameTime() - startTick;
    }

    public net.minecraft.resources.ResourceLocation getTexture() {
        net.minecraft.resources.ResourceLocation id = getAbilityId();
        
        if (id == null) {
            return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/punch.png");
        }
        
        return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/abilities/" + id.getPath() + ".png");
    }

    public boolean isPassive() {
        return false;
    }

    public abstract Component getDisplayName();
}
