package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteBlowEntity;

public class WhiteBlowAbility extends Ability {

    public WhiteBlowAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            WhiteBlowEntity blow = new WhiteBlowEntity(entity.level(), entity);
            blow.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.0F);
            entity.level().addFreshEntity(blow);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 0.5F, 1.5F);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.white_blow");
    }
}
