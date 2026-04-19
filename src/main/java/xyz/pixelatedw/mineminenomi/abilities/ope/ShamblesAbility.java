package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ShamblesAbility extends Ability {

    public ShamblesAbility() {
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
            net.minecraft.world.phys.EntityHitResult hit = xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.pickEntity(entity, 30.0);
            if (hit != null && hit.getEntity() instanceof LivingEntity target) {
                if (RoomAbility.isEntityInRoom(target)) {
                    Vec3 pPos = entity.position();
                    Vec3 tPos = target.position();

                    entity.teleportTo(tPos.x, tPos.y, tPos.z);
                    target.teleportTo(pPos.x, pPos.y, pPos.z);

                    entity.level().playSound(null, pPos.x, pPos.y, pPos.z, 
                        net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT, 
                        net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.5F);
                    entity.level().playSound(null, tPos.x, tPos.y, tPos.z, 
                        net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT, 
                        net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.5F);
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
        return Component.translatable("ability.mineminenomi.shambles");
    }
}
