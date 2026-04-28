package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.YasakaniNoMagatamaEntity;

public class YasakaniNoMagatamaAbility extends Ability {

    private static final int MAX_TICKS = 100; // 5 seconds

    public YasakaniNoMagatamaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.PIKA_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.2F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration % 2 == 0) {
            if (!entity.level().isClientSide) {
                for (int i = 0; i < 2; i++) {
                    YasakaniNoMagatamaEntity yasakani = new YasakaniNoMagatamaEntity(entity.level(), entity);
                    float spread = 25.0F;
                    float xRot = entity.getXRot() + (entity.getRandom().nextFloat() - 0.5F) * spread;
                    float yRot = entity.getYRot() + (entity.getRandom().nextFloat() - 0.5F) * spread;
                    yasakani.shootFromRotation(entity, xRot, yRot, 0.0F, 2.8F, 0.1F);
                    entity.level().addFreshEntity(yasakani);
                }
                
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    net.minecraft.sounds.SoundEvents.AMETHYST_BLOCK_HIT, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.5F);
            }
        }

        if (duration >= MAX_TICKS) {
            this.startCooldown(entity, 400);
            this.stop(entity);
        }
        
        // Slow fall during execution
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.5, 1.0));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yasakani_no_magatama");
    }
}
