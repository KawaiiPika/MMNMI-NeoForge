package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class CounterShockAbility extends Ability {

    public CounterShockAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ope_ope_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!RoomAbility.isEntityInRoom(entity)) {
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.message.only_in_room"), true);
            }
            return;
        }

        if (!entity.level().isClientSide) {
            net.minecraft.world.phys.EntityHitResult hit = xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.pickEntity(entity, 5.0);
            if (hit != null && hit.getEntity() instanceof LivingEntity target) {
                target.hurt(entity.damageSources().lightningBolt(), 25.0F);
                entity.level().playSound(null, target.getX(), target.getY(), target.getZ(), 
                    net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_IMPACT, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
                
                // Visual lightning effect
                net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) entity.level();
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, 
                    target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 
                    20, 0.5, 0.5, 0.5, 0.1);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.counter_shock");
    }
}
