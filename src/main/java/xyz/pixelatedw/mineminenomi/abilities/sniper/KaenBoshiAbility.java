package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class KaenBoshiAbility extends SniperProjectileAbility {

    public KaenBoshiAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        SniperPelletEntity pellet = new SniperPelletEntity(ModEntities.SNIPER_PELLET.get(), entity.level());
        pellet.setOwner(entity);
        pellet.setPos(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        pellet.setDamage(8.0f);
        pellet.setFireTicks(100); // 5 seconds
        
        this.shootProjectile(entity, pellet, 3.0f, 1.0f);
        this.startCooldown(entity, 100);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Kaen Boshi");
    }
}
