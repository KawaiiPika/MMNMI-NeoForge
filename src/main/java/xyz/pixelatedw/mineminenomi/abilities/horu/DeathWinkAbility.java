package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class DeathWinkAbility extends Ability {

    public DeathWinkAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "horu_horu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            int power = entity.hasEffect(net.minecraft.world.effect.MobEffects.HEALTH_BOOST) ? 3 : 2;

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                xyz.pixelatedw.mineminenomi.init.ModSounds.DEATH_WINK_SFX.get(),
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.0F);

            Vec3 look = entity.getLookAngle();
            Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0).add(look.scale(8.0 * power));

            entity.level().explode(entity, entity.damageSources().explosion(entity, entity), null, pos.x, pos.y, pos.z, 1 + power, false, net.minecraft.world.level.Level.ExplosionInteraction.NONE);

            this.startCooldown(entity, 120);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.death_wink");
    }
}
