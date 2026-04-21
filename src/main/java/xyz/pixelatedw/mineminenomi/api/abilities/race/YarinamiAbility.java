package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class YarinamiAbility extends Ability {

    public YarinamiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.YarinamiProjectile projectile = new xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.YarinamiProjectile(entity.level(), entity);
            projectile.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
            projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.8F, 1.0F);
            entity.level().addFreshEntity(projectile);
            
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.BOAT_PADDLE_WATER, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
            
            this.startCooldown(entity, 400);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yarinami");
    }
}
