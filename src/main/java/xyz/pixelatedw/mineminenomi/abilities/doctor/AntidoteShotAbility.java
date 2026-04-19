package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class AntidoteShotAbility extends Ability {

    public AntidoteShotAbility() {}

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
            LivingEntity target = entity;
            
            if (!entity.isShiftKeyDown()) {
                Vec3 look = entity.getLookAngle();
                Vec3 reach = entity.getEyePosition().add(look.scale(4.0));
                AABB area = new AABB(reach.subtract(1.0, 1.0, 1.0), reach.add(1.0, 1.0, 1.0));
                for (LivingEntity e : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                    if (e != entity) {
                        target = e;
                        break;
                    }
                }
            }

            target.removeAllEffects();
            entity.sendSystemMessage(Component.literal("Injected Antidote into " + target.getDisplayName().getString()));
        }
        
        this.startCooldown(entity, 200);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Antidote Shot");
    }
}
