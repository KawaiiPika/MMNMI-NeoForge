package xyz.pixelatedw.mineminenomi.api.abilities.basic;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Command — send a "command" signal to nearby allied NPCs.
 * Category: Misc (Cat 5)
 * Tab Icon: idle_command.png
 */
public class CommandAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.VILLAGER_CELEBRATE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
             
             double radius = 10.0;
             for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius))) {
                 if (target == entity || target instanceof net.minecraft.world.entity.monster.Monster) continue;
                 
                 target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 200, 1));
                 target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 200, 1));
             }
             
             this.startCooldown(entity, 600);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.command");
    }
}
