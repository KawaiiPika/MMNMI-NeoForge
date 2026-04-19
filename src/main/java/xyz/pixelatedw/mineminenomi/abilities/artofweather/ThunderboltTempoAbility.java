package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class ThunderboltTempoAbility extends Ability {

    public ThunderboltTempoAbility() {}

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
            HitResult ray = entity.pick(20.0D, 0.0F, false);
            if (ray.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockRay = (BlockHitResult) ray;
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(entity.level());
                if (lightning != null) {
                    lightning.moveTo(blockRay.getLocation());
                    entity.level().addFreshEntity(lightning);
                    entity.sendSystemMessage(Component.literal("Thunderbolt Tempo!"));
                }
            }
        }
        
        this.startCooldown(entity, 200);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Thunderbolt Tempo");
    }
}
