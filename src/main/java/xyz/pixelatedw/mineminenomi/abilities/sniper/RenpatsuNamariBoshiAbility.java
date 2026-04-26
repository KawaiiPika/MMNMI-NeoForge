package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class RenpatsuNamariBoshiAbility extends SniperProjectileAbility {

    public RenpatsuNamariBoshiAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        for (int i = 0; i < 3; i++) {
            SniperPelletEntity pellet = new SniperPelletEntity(ModEntities.SNIPER_PELLET.get(), entity.level());
            pellet.setOwner(entity);
            pellet.teleportTo(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
            pellet.setDamage(6.0f);
            
            this.shootProjectile(entity, pellet, 3.5f, 5.0f);
        }
        
        this.startCooldown(entity, 150);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Renpatsu Namari Boshi");
    }
}
