package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class TetsuBoshiAbility extends SniperProjectileAbility {

    public TetsuBoshiAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        SniperPelletEntity pellet = new SniperPelletEntity(ModEntities.SNIPER_PELLET.get(), entity.level());
        pellet.setOwner(entity);
        pellet.teleportTo(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        pellet.setDamage(15.0f);
        
        this.shootProjectile(entity, pellet, 4.0f, 0.5f);
        this.startCooldown(entity, 150);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Tetsu Boshi");
    }
}
