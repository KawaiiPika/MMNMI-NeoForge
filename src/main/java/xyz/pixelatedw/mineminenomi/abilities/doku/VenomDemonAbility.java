package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class VenomDemonAbility extends Ability {

    private static final int MAX_DURATION = 1200;

    public VenomDemonAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "doku_doku_no_mi"));
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive(this.getId().toString());
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), false);
            stats.sync(entity);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            // Apply buffs
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 10, 4, false, false));
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 10, 1, false, false));
            entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 10, 2, false, false));

            // Leave poison blocks
            net.minecraft.core.BlockPos pos = entity.blockPosition();
            if (entity.level().isEmptyBlock(pos) && entity.onGround()) {
                // Assuming we use simple slime or something similar as placeholder until custom blocks port
                entity.level().setBlockAndUpdate(pos, net.minecraft.world.level.block.Blocks.SLIME_BLOCK.defaultBlockState());
            }

            if (duration >= MAX_DURATION) {
                this.stop(entity);
                this.startCooldown(entity, 2000);
            }
        } else {
             if (duration % 2 == 0) {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SQUID_INK,
                     entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2,
                     entity.getY() + entity.getRandom().nextDouble() * 3,
                     entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2,
                     0, 0, 0);
             }
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "venom_demon");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.venom_demon");
    }
}
