package xyz.pixelatedw.mineminenomi.abilities.sabi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class RustTouchAbility extends Ability {

    public RustTouchAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sabi_sabi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            var trace = xyz.pixelatedw.mineminenomi.api.WyHelper.rayTraceEntities(entity, 3.0);
            Entity target = trace != null ? trace.getEntity() : null;
            if (target instanceof LivingEntity livingTarget) {
                if (livingTarget.hurt(entity.damageSources().mobAttack(entity), 10.0F)) {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 160, 2));
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, 1));
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 1));

                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        ItemStack stack = livingTarget.getItemBySlot(slot);
                        if (!stack.isEmpty() && (stack.getItem().toString().contains("iron") || stack.getItem().toString().contains("chainmail"))) {
                            if (stack.isDamageableItem()) {
                                stack.hurtAndBreak(stack.getMaxDamage() / 3 + 2, livingTarget, slot);
                            } else {
                                stack.shrink(1 + entity.getRandom().nextInt(4));
                            }
                        }
                    }
                }
            }
            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.rust_touch");
    }
}
