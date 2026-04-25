package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Pain Repel — user extracts pain (damage) from themselves and releases it as a shockwave.
 * Heals the user and deals damage to nearby enemies.
 */
public class PainRepelAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "nikyu_nikyu_no_mi");
    public PainRepelAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        float healAmount = entity.getMaxHealth() - entity.getHealth();
        if (healAmount > 0) {
            entity.heal(healAmount);
            // Release the "pain" as a burst
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(5.0))) {
                if (target instanceof LivingEntity living) {
                    living.hurt(entity.damageSources().mobAttack(entity), healAmount);
                    living.hurtMarked = true;
                }
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.pain_repel"); }
}
