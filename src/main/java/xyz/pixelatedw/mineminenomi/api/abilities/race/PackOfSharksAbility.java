package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.PackOfSharksProjectile;

public class PackOfSharksAbility extends Ability {

    public PackOfSharksAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration % 4 == 0) {
            PackOfSharksProjectile projectile = new PackOfSharksProjectile(entity.level(), entity);
            projectile.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
            projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.5F, 5.0F);
            entity.level().addFreshEntity(projectile);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.EXPERIENCE_BOTTLE_THROW, 
                net.minecraft.sounds.SoundSource.PLAYERS, 0.5F, 1.2F);
        }
        
        if (duration >= 40) {
            this.startCooldown(entity, 300);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.pack_of_sharks");
    }
}
