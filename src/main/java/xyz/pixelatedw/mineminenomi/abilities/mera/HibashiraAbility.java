package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Hibashira — creates a pillar of fire at target location. */
public class HibashiraAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi");
    public HibashiraAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        var look = entity.getLookAngle();
        // Fire pillar — set fire to nearby area
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0).move(look.scale(5.0)))) {
            if (target instanceof LivingEntity living) {
                living.igniteForSeconds(5);
                living.hurt(entity.damageSources().onFire(), 8.0F);
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.hibashira"); }
}
