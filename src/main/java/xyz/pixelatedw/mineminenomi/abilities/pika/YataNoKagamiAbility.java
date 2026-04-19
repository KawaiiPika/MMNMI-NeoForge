package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class YataNoKagamiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi");
    public YataNoKagamiAbility() { super(FRUIT); }

    private static final int CHARGE_TICKS = 30; // 1.5 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.PIKA_CHARGE_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 2 == 0) {
                 net.minecraft.world.phys.HitResult mop = entity.pick(64.0D, 0.0F, false);
                 Vec3 hitPos = mop.getLocation();
                 for(int i = 0; i < 3; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.END_ROD, 
                        hitPos.x + (entity.getRandom().nextDouble() - 0.5), 
                        hitPos.y + (entity.getRandom().nextDouble() - 0.5), 
                        hitPos.z + (entity.getRandom().nextDouble() - 0.5), 
                        0, 0, 0);
                 }
                 // Beam particle simulation
                 Vec3 start = entity.getEyePosition();
                 Vec3 dir = hitPos.subtract(start).normalize();
                 for (int i = 0; i < 5; i++) {
                     Vec3 p = start.add(dir.scale(entity.getRandom().nextDouble() * start.distanceTo(hitPos)));
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.END_ROD, p.x, p.y, p.z, 0, 0, 0);
                 }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                net.minecraft.world.phys.HitResult mop = entity.pick(64.0D, 0.0F, false);
                Vec3 hitPos = mop.getLocation();
                
                entity.teleportTo(hitPos.x, hitPos.y, hitPos.z);
                entity.resetFallDistance();
                
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    xyz.pixelatedw.mineminenomi.init.ModSounds.PIKA_SFX.get(), 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                    xyz.pixelatedw.mineminenomi.init.ModSounds.TELEPORT_SFX.get(), 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.2F);
                
                this.startCooldown(entity, 60);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.yata_no_kagami"); }
}
