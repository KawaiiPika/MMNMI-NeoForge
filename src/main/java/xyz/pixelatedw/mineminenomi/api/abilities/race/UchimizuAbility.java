package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class UchimizuAbility extends Ability {

    public UchimizuAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile projectile = new xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile(entity.level(), entity);
            projectile.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
            projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.EXPERIENCE_BOTTLE_THROW, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.5F);
            
            this.startCooldown(entity, 100);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.uchimizu");
    }
}
