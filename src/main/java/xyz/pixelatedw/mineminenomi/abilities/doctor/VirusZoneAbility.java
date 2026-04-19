package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class VirusZoneAbility extends Ability {

    public VirusZoneAbility() {}

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
            AABB area = entity.getBoundingBox().inflate(6.0);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1));
                }
            }
            entity.sendSystemMessage(Component.literal("Virus Zone Deployed!"));
        }
        
        this.startCooldown(entity, 500);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Virus Zone");
    }
}
