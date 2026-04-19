package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class HiganAbility extends Ability {

    public HiganAbility() {
        super(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.entities.projectiles.HiganEntity higan = new xyz.pixelatedw.mineminenomi.entities.projectiles.HiganEntity(entity.level(), entity);
            higan.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.8F, 0.1F);
            entity.level().addFreshEntity(higan);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.FIRECHARGE_USE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.2F);
                
            this.startCooldown(entity, 10);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.higan");
    }
}
