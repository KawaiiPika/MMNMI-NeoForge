package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.MeigoEntity;

public class MeigoAbility extends Ability {

    public MeigoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            MeigoEntity meigo = new MeigoEntity(entity.level(), entity);
            meigo.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.05F);
            entity.level().addFreshEntity(meigo);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.LAVA_EXTINGUISH, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.2F);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.meigo");
    }
}
