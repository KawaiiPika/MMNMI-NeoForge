package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Jujika — cross-shaped fire burst (Fire Cross). */
public class JujikaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi");
    public JujikaAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Cross-pattern fire burst
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(7.0))) {
            if (target instanceof LivingEntity living) {
                Vec3 dir = living.position().subtract(entity.position());
                // Only hit entities in cardinal cross directions
                double absX = Math.abs(dir.x), absZ = Math.abs(dir.z);
                if (absX < 2.0 || absZ < 2.0) {
                    living.igniteForSeconds(4);
                    living.hurt(entity.damageSources().onFire(), 7.0F);
                }
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.jujika"); }
}
