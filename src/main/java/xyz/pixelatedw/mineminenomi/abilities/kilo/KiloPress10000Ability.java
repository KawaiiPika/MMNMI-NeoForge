package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import net.minecraft.util.Mth;
import java.util.List;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class KiloPress10000Ability extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi");
    private static final ResourceLocation JUMP_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_10000_jump");
    private static final ResourceLocation KNOCKBACK_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_10000_knockback");
    private static final ResourceLocation SPEED_MOD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_press_10000_speed");

    private double initialPosY = 0.0D;

    public KiloPress10000Ability() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) jumpAttr.addOrUpdateTransientModifier(new AttributeModifier(JUMP_MOD_ID, -10.0D, AttributeModifier.Operation.ADD_VALUE));

            var kbAttr = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null) kbAttr.addOrUpdateTransientModifier(new AttributeModifier(KNOCKBACK_MOD_ID, 1.0D, AttributeModifier.Operation.ADD_VALUE));

            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.addOrUpdateTransientModifier(new AttributeModifier(SPEED_MOD_ID, -0.02D, AttributeModifier.Operation.ADD_VALUE));

            AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, -5.0D, entity.getDeltaMovement().z);
            if (!entity.onGround()) {
                this.initialPosY = entity.getY();
            } else {
                this.initialPosY = 0.0D;
            }
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
             if (entity.onGround()) {
                 if (this.initialPosY > 0.0D && entity.getY() < this.initialPosY) {
                     float damage = (float)Mth.clamp(this.initialPosY - entity.getY(), 1.0D, 80.0D);
                     if (damage > 0.0F) {
                         List<LivingEntity> nearTargets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0D), e -> e != entity);
                         for(LivingEntity target : nearTargets) {
                             target.hurt(entity.damageSources().mobAttack(entity), damage);
                         }

                         List<LivingEntity> farTargets = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(5.0D), e -> e != entity);
                         farTargets.removeAll(nearTargets);
                         for(LivingEntity target : farTargets) {
                             target.hurt(entity.damageSources().mobAttack(entity), damage);
                         }
                         this.initialPosY = 0.0D;
                     }

                     if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                         for(net.minecraft.server.level.ServerPlayer p : serverLevel.players()) serverLevel.sendParticles(p, new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GREAT_STOMP.get()), true, entity.getX(), entity.getY(), entity.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                     }
                 }
                 this.stop(entity);
             }
             if (duration > 1200) {
                 this.stop(entity);
             }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var jumpAttr = entity.getAttribute(ModAttributes.JUMP_HEIGHT);
            if (jumpAttr != null) jumpAttr.removeModifier(JUMP_MOD_ID);

            var kbAttr = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kbAttr != null) kbAttr.removeModifier(KNOCKBACK_MOD_ID);

            var speedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) speedAttr.removeModifier(SPEED_MOD_ID);
        }
        this.startCooldown(entity, 20);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.10000_kilo_press");
    }
}
