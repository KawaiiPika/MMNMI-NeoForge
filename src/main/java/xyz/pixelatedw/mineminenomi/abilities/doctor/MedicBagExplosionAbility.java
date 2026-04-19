package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class MedicBagExplosionAbility extends Ability {

    public MedicBagExplosionAbility() {}

    @Override
    public Result canUse(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.isEmpty() || !chest.is(ModArmors.MEDIC_BAG.get())) {
            return Result.fail(Component.literal("You need a Medic Bag to use this!"));
        }
        return super.canUse(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            AABB area = entity.getBoundingBox().inflate(5.0);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (target == entity) continue;
                
                if (target instanceof Player || target.isAlliedTo(entity)) {
                    target.heal(8.0f);
                } else {
                    target.hurt(entity.damageSources().mobAttack(entity), 5.0f);
                    Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(2.0);
                    target.setDeltaMovement(knockback.x, 0.5, knockback.z);
                }
            }
            entity.sendSystemMessage(Component.literal("Medic Bag Burst!"));
        }
        
        this.startCooldown(entity, 800);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Medic Bag Explosion");
    }
}
