package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Tenchi Meido — Heaven and Earth Shake; user grabs the air and "flips" it, 
 * dealing massive damage and knocking back all nearby entities in a huge radius.
 */
public class TenchiMeidoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gura_gura_no_mi");
    public TenchiMeidoAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Massive AoE burst
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(15.0))) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 30.0F);
                Vec3 push = living.position().subtract(entity.position()).normalize().scale(5.0).add(0, 1.0, 0);
                living.setDeltaMovement(push);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.tenchi_meido"); }
}
