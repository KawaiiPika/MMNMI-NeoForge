package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class YukiLogiaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi");

    public YukiLogiaAbility() { super(FRUIT); }

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
    public void onLogiaDodge(LivingEntity entity, LivingEntity attacker) {
        super.onLogiaDodge(entity, attacker);
        if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 20, entity.getBbWidth(), entity.getBbHeight() / 2, entity.getBbWidth(), 0.05);
        }
    }

@Override
    protected void startUsing(LivingEntity entity) {}

@Override
    public boolean checkInvulnerability(LivingEntity entity, DamageSource source) {
        if (source.is(DamageTypeTags.IS_FREEZING)) {
            return true; // Immune to freezing
        }
        return false;
    }

@Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.logia_invulnerability_yuki"); }
}
