package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class HiNoToriBoshiAbility extends SniperProjectileAbility {

    public HiNoToriBoshiAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        SniperPelletEntity pellet = new SniperPelletEntity(ModEntities.SNIPER_PELLET.get(), entity.level());
        pellet.setOwner(entity);
        pellet.setPos(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        pellet.setDamage(20.0f);
        pellet.setFireTicks(200);
        pellet.setExplosive(true);
        
        this.shootProjectile(entity, pellet, 4.0f, 0.0f);
        this.startCooldown(entity, 400);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Hi No Tori Boshi");
    }
}
