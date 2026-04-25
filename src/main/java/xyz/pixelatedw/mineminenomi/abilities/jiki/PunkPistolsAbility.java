package xyz.pixelatedw.mineminenomi.abilities.jiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PunkPistolsAbility extends Ability {

    public PunkPistolsAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jiki_jiki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0).move(look.scale(8.0)))) {
                if (target instanceof LivingEntity livingTarget) {
                    livingTarget.hurt(entity.damageSources().mobAttack(entity), 15.0F);
                }
            }
            this.startCooldown(entity, 240);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.punk_pistols");
    }
}
