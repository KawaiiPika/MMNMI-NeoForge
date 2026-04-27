package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;

public class MeraLogiaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi");

    public MeraLogiaAbility() { super(FRUIT); }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
        if (stats != null && !stats.isLogia()) {
            stats.setLogia(true);
            stats.sync(entity);
        }
    }

    @Override
    public boolean isPassive() {
        return true;
    }

@Override
    protected void startUsing(LivingEntity entity) {}

@Override
    public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            entity.clearFire();
            return true; // Immune to fire
        }
        return false;
    }

@Override
    public void onLogiaDodge(LivingEntity entity, LivingEntity attacker) {
        super.onLogiaDodge(entity, attacker);
        // Side effect: Ignites attacker when dodged
        attacker.setRemainingFireTicks(60);

        if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME, entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 20, entity.getBbWidth(), entity.getBbHeight() / 2, entity.getBbWidth(), 0.05);
        }
    }

@Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.mera_logia"); }
}
