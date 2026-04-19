package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.HikenEntity;

public class HikenAbility extends Ability {

    public HikenAbility() {
        super(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            HikenEntity hiken = new HikenEntity(entity.level(), entity);
            hiken.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 0.1F);
            entity.level().addFreshEntity(hiken);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.FIRECHARGE_USE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.8F);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.hiken");
    }
}
