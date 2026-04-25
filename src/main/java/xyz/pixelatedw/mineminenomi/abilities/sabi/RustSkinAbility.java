package xyz.pixelatedw.mineminenomi.abilities.sabi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class RustSkinAbility extends Ability {

    public RustSkinAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sabi_sabi_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (entity.hasEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN)) {
                entity.removeEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN);
            }
            if (entity.hasEffect(net.minecraft.world.effect.MobEffects.POISON)) {
                entity.removeEffect(net.minecraft.world.effect.MobEffects.POISON);
            }
        }
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {
        if (entity.level().isClientSide) return false;

        var direct = source.getDirectEntity();
        var attacker = source.getEntity();

        if (direct instanceof Projectile) {
            // Very simplified: block generic projectiles that could be metal
            return true;
        }

        if (attacker instanceof LivingEntity livingAttacker) {
            ItemStack weapon = livingAttacker.getMainHandItem();
            if (weapon.getItem() instanceof SwordItem || weapon.getItem().toString().contains("iron")) {
                if (weapon.isDamageableItem()) {
                    weapon.hurtAndBreak(weapon.getMaxDamage() / 4 + 1, livingAttacker, EquipmentSlot.MAINHAND);
                } else {
                    weapon.shrink(1);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.rust_skin");
    }
}
