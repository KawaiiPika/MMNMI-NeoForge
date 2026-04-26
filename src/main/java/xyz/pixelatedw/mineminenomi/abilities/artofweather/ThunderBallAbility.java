package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SniperPelletEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ThunderBallAbility extends Ability {

    public ThunderBallAbility() {}

    @Override
    public Result canUse(LivingEntity entity) {
        ItemStack held = entity.getMainHandItem();
        if (held.isEmpty() || (!held.is(ModWeapons.CLIMA_TACT.get()) && !held.is(ModWeapons.PERFECT_CLIMA_TACT.get()) && !held.is(ModWeapons.SORCERY_CLIMA_TACT.get()))) {
            return Result.fail(Component.literal("You need a Clima Tact to use this!"));
        }
        return super.canUse(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            SniperPelletEntity ball = new SniperPelletEntity(ModEntities.SNIPER_PELLET.get(), entity.level());
            ball.setOwner(entity);
            ball.teleportTo(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
            ball.setDamage(8.0f);
            // Thunder effect could be lightning bolt on hit, but for now just higher damage
            
            double d0 = entity.getLookAngle().x;
            double d1 = entity.getLookAngle().y;
            double d2 = entity.getLookAngle().z;
            ball.shoot(d0, d1, d2, 3.5f, 0.5f);
            entity.level().addFreshEntity(ball);
        }
        
        this.startCooldown(entity, 100);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Thunder Ball");
    }
}
