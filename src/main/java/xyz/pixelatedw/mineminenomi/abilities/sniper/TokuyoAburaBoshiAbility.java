package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class TokuyoAburaBoshiAbility extends SniperProjectileAbility {

    public TokuyoAburaBoshiAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        SniperPelletEntity pellet = new SniperPelletEntity(ModEntities.SNIPER_PELLET.get(), entity.level());
        pellet.setOwner(entity);
        pellet.teleportTo(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        pellet.setDamage(2.0f);
        pellet.setEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 3));
        
        this.shootProjectile(entity, pellet, 3.0f, 1.0f);
        this.startCooldown(entity, 250);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Tokuyo Abura Boshi");
    }
}
