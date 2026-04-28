package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DesertEncierroAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi");
    public DesertEncierroAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(2.0).move(look.scale(3.0)))) {
                if (target instanceof LivingEntity living) {
                    living.hurt(entity.damageSources().dryOut(), 15.0F);
                    living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.WEAKNESS, 100, 1));
                    living.hurtMarked = true;
                    break;
                }
            }
            this.startCooldown(entity, 120);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.desert_encierro"); }
}
