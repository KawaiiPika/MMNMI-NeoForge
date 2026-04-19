package xyz.pixelatedw.mineminenomi.api.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuShuryudanProjectile;

public class MizuShuryudanAbility extends Ability {

    private static final int PROJECTILES = 5;

    public MizuShuryudanAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_karate"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        for (int i = 0; i < PROJECTILES; i++) {
            MizuShuryudanProjectile projectile = new MizuShuryudanProjectile(entity.level(), entity);
            Vec3 look = entity.getLookAngle();
            projectile.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
            
            double spread = 0.5;
            projectile.shoot(look.x + (entity.getRandom().nextDouble() - 0.5) * spread, 
                             look.y + (entity.getRandom().nextDouble() - 0.5) * spread, 
                             look.z + (entity.getRandom().nextDouble() - 0.5) * spread, 0.8f, 1.0f);
            
            entity.level().addFreshEntity(projectile);
        }

        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
            net.minecraft.sounds.SoundEvents.PLAYER_SPLASH, 
            net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.5F);

        this.startCooldown(entity, 240);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mizu_shuryudan");
    }
}
