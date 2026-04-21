package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.SangoEntity;

public class SangoAbility extends Ability {

    public SangoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
                SangoEntity sango = new SangoEntity(entity.level(), entity);
                float spread = 15.0F;
                float xRot = entity.getXRot() + (entity.getRandom().nextFloat() - 0.5F) * spread;
                float yRot = entity.getYRot() + (entity.getRandom().nextFloat() - 0.5F) * spread;
                sango.shootFromRotation(entity, xRot, yRot, 0.0F, 3.5F, 1.0F);
                entity.level().addFreshEntity(sango);
            }
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_THUNDER, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 2.0F);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sango");
    }
}
