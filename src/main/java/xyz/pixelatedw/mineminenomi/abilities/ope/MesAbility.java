package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MesAbility extends Ability {

    public MesAbility() {
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
            net.minecraft.world.phys.EntityHitResult hit = xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.pickEntity(entity, 3.0);
            if (hit != null && hit.getEntity() instanceof LivingEntity target) {
                if (RoomAbility.isEntityInRoom(target)) {
                    target.hurt(entity.damageSources().mobAttack(entity), 10.0F);
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 2));
                    
                    entity.level().playSound(null, target.getX(), target.getY(), target.getZ(), 
                        net.minecraft.sounds.SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH, 
                        net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    if (entity instanceof net.minecraft.world.entity.player.Player player) {
                        player.displayClientMessage(Component.translatable("ability.mineminenomi.message.target_not_in_room"), true);
                    }
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mes");
    }
}
