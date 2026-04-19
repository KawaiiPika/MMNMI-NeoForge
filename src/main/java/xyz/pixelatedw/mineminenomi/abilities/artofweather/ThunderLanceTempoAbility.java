package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ThunderLanceTempoAbility extends Ability {

    public ThunderLanceTempoAbility() {}

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
            Vec3 look = entity.getLookAngle();
            for (int i = 1; i <= 15; i++) {
                Vec3 pos = entity.getEyePosition().add(look.scale(i));
                AABB area = new AABB(pos.subtract(1.0, 1.0, 1.0), pos.add(1.0, 1.0, 1.0));
                
                for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                    if (target != entity) {
                        target.hurt(entity.damageSources().mobAttack(entity), 20.0f);
                        // Lighting particles could be added here
                    }
                }
            }
            entity.sendSystemMessage(Component.literal("Thunder Lance Tempo!"));
        }
        
        this.startCooldown(entity, 300);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Thunder Lance Tempo");
    }
}
