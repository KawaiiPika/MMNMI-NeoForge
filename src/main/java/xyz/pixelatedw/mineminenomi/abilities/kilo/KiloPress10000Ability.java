package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class KiloPress10000Ability extends Ability {
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import java.util.List;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class KiloPress10000Ability extends Ability {
    private static final ResourceLocation JUMP_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_jump");
    private static final ResourceLocation KNOCKBACK_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_knockback");
    private static final ResourceLocation SPEED_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_speed");

    private double initialPosY = 0.0D;

    public KiloPress10000Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.onGround() && !entity.level().isClientSide) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null) {
                stats.setAbilityState("kilo_press_y", (int) entity.getY());
            }
            entity.setDeltaMovement(entity.getDeltaMovement().x, -5.0, entity.getDeltaMovement().z);
        } else {
            this.stop(entity); // must be in air
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 20); // rough cooldown based on formula
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 1200) {
            this.stop(entity);
            return;
        }

        if (entity.onGround()) {
            if (!entity.level().isClientSide) {
                PlayerStats stats = PlayerStats.get(entity);
                if (stats != null) {
                    int initialY = stats.getAbilityState("kilo_press_y");
                    if (initialY > 0 && entity.getY() < initialY) {
                        float damage = Mth.clamp(initialY - (float)entity.getY(), 1.0F, 80.0F);
                        var aabb = entity.getBoundingBox().inflate(5.0);
                        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                            target.hurt(entity.damageSources().mobAttack(entity), damage);
                        }
                        // TODO: spawn particles
                    }
                }
            }
        if (entity.onGround()) {
            return;
        }

        applyModifiers(entity);
        AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, -5.0D, entity.getDeltaMovement().z);
        this.initialPosY = entity.getY();
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.onGround()) {
            if (this.initialPosY > 0.0D && entity.getY() < this.initialPosY) {
                if (!entity.level().isClientSide) {
                    float damage = (float) Mth.clamp(this.initialPosY - entity.getY(), 1.0D, 80.0D);
                    if (damage > 0.0F) {
                        List<LivingEntity> nearTargets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0D));
                        for (LivingEntity target : nearTargets) {
                            if (target != entity) {
                                target.hurt(entity.damageSources().mobAttack(entity), damage);
                            }
                        }

                        List<LivingEntity> farTargets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5.0D));
                        farTargets.removeAll(nearTargets);
                        for (LivingEntity target : farTargets) {
                            if (target != entity) {
                                target.hurt(entity.damageSources().mobAttack(entity), damage);
                            }
                        }
                    }
                }
            }
            this.initialPosY = 0.0D;
            this.stop(entity);
        }
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        return this.isUsing(entity) && source.is(net.minecraft.tags.DamageTypeTags.IS_FALL);
    protected void stopUsing(LivingEntity entity) {
        removeModifiers(entity);
        float cooldown = 20.0F + getDuration(entity);
        this.startCooldown(entity, (long) cooldown);
    }

    private void applyModifiers(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.JUMP_STRENGTH);
            if (jumpAttr != null && jumpAttr.getModifier(JUMP_MOD_ID) == null) {
                jumpAttr.addTransientModifier(new AttributeModifier(JUMP_MOD_ID, -10.0, AttributeModifier.Operation.ADD_VALUE));
            }

            var kbAttr = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null && kbAttr.getModifier(KNOCKBACK_MOD_ID) == null) {
                kbAttr.addTransientModifier(new AttributeModifier(KNOCKBACK_MOD_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }

            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null && speedAttr.getModifier(SPEED_MOD_ID) == null) {
                speedAttr.addTransientModifier(new AttributeModifier(SPEED_MOD_ID, -0.02, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    private void removeModifiers(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.JUMP_STRENGTH);
            if (jumpAttr != null) jumpAttr.removeModifier(JUMP_MOD_ID);

            var kbAttr = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null) kbAttr.removeModifier(KNOCKBACK_MOD_ID);

            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(SPEED_MOD_ID);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.10000_kilo_press");
    }
}
