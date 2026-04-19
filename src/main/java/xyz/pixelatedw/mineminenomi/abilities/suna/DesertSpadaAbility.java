package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.DesertSpadaEntity;

public class DesertSpadaAbility extends Ability {

    public DesertSpadaAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            DesertSpadaEntity spada = new DesertSpadaEntity(entity.level(), entity);
            spada.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.1F);
            entity.level().addFreshEntity(spada);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.SUNA_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
            
            this.startCooldown(entity, 100);
        }
        
        if (entity.level().isClientSide) {
             for(int i = 0; i < 5; i++) {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.CLOUD, 
                    entity.getX(), entity.getY() + 1, entity.getZ(), 
                    (entity.getRandom().nextDouble() - 0.5) * 0.5, 0.1, (entity.getRandom().nextDouble() - 0.5) * 0.5);
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.desert_spada");
    }
}
