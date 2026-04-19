package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class MirageTempoAbility extends Ability {

    public MirageTempoAbility() {}

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
            entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 0));
            entity.sendSystemMessage(Component.literal("Mirage Tempo!"));
        }
        
        this.startCooldown(entity, 400);
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Mirage Tempo");
    }
}
