package xyz.pixelatedw.mineminenomi.abilities.jiki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DamnedPunkAbility extends Ability {

    public DamnedPunkAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jiki_jiki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Vec3 look = entity.getLookAngle();
            Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0).add(look.scale(10.0));

            entity.level().explode(entity, entity.damageSources().explosion(entity, entity), null, pos.x, pos.y, pos.z, 5.0F, false, net.minecraft.world.level.Level.ExplosionInteraction.NONE);

            this.startCooldown(entity, 400);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.damned_punk");
    }
}
