package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Kage Kakumei — Shadow Revolution; user wraps themselves in shadow, 
 * gaining brief invincibility and dealing damage to adjacent enemies.
 */
public class KageKakumeiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi");
    public KageKakumeiAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Shadow cloak — brief absorption
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
            net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 60, 4));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
            net.minecraft.world.effect.MobEffects.INVISIBILITY, 60, 0));
        // Burst adjacent enemies
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0))) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 8.0F);
                var dir = living.position().subtract(entity.position()).normalize();
                living.setDeltaMovement(dir.scale(2.5).add(0, 0.3, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.kage_kakumei"); }
}
