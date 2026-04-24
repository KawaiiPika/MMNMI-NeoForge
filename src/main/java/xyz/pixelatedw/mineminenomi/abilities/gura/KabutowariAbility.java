package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Kabutowari — Helmet Breaker; user concentrates a shockwave in their fist.
 * Deals massive damage and ignores a portion of armor (simulated by high damage).
 */
public class KabutowariAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gura_gura_no_mi");
    public KabutowariAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GURA_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.2F);
             
             this.startCooldown(entity, 300);
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source, float amount) {
        return amount + 20.0F;
    }

    @Override
    public void onDamageTakenByTarget(LivingEntity entity, LivingEntity target, net.minecraft.world.damagesource.DamageSource source) {
        if (!entity.level().isClientSide) {
            target.level().addParticle(net.minecraft.core.particles.ParticleTypes.EXPLOSION, 
                target.getX(), target.getY() + 1.0, target.getZ(), 0, 0, 0);
            
            Vec3 look = entity.getLookAngle();
            target.setDeltaMovement(look.scale(3.0).add(0, 0.4, 0));
            target.hurtMarked = true;
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.kabutowari"); }
}
