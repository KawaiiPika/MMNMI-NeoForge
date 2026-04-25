package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Sube Sube Deflect — Immunity to physical attacks. */
public class SubeSubeDeflectAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "sube_sube_no_mi");
    
    public SubeSubeDeflectAbility() { 
        super(FRUIT); 
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.sube_sube_deflect.on"));
    }

    @Override
    public boolean checkInvulnerability(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) {
        if (isUsing(entity)) {
            if (source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) return false;
            // Check if it's a physical attack (simplified: not magic, not fire, not explosion)
            if (!source.is(net.minecraft.world.damagesource.DamageTypes.MAGIC) && 
                !source.is(net.minecraft.world.damagesource.DamageTypes.EXPLOSION) &&
                !source.is(net.minecraft.world.damagesource.DamageTypes.IN_FIRE) &&
                !source.is(net.minecraft.world.damagesource.DamageTypes.ON_FIRE)) {
                
                // WOW visual effect: attack slips off
                if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.END_ROD, entity.getX(), entity.getY() + 1, entity.getZ(), 5, 0.1, 0.1, 0.1, 0.02);
                }
                return true;
            }
        }
        return false;
    }

    // Modern deflection hooked via event if needed, or handled inside invulnerability if we want to reverse.
    public net.minecraft.world.entity.projectile.ProjectileDeflection getDeflection(net.minecraft.world.entity.projectile.Projectile projectile, LivingEntity entity) {
        if (isUsing(entity)) {
            if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.END_ROD, entity.getX(), entity.getY() + 1, entity.getZ(), 5, 0.1, 0.1, 0.1, 0.02);
            }
            return net.minecraft.world.entity.projectile.ProjectileDeflection.REVERSE;
        }
        return net.minecraft.world.entity.projectile.ProjectileDeflection.NONE;
    }

    @Override
    public Component getDisplayName() { 
        return Component.translatable("ability.mineminenomi.sube_sube_deflect"); 
    }
}
