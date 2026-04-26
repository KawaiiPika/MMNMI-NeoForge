package xyz.pixelatedw.mineminenomi.abilities.doa;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
public class AirDoorAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doa_doa_no_mi");
    private static final int MAX_DURATION = 1200;
    public AirDoorAbility() { super(FRUIT); }
    @Override protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.INVISIBILITY, MAX_DURATION, 0, false, false));
            entity.level().playSound(null, entity.blockPosition(), ModSounds.DOA_IN_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
        }
    }
    @Override protected void onTick(LivingEntity entity, long duration) {
        if (duration >= MAX_DURATION) { this.stop(entity); }
    }
    @Override protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.removeEffect(net.minecraft.world.effect.MobEffects.INVISIBILITY);
            entity.level().playSound(null, entity.blockPosition(), ModSounds.DOA_OUT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            this.startCooldown(entity, 200 + this.getDuration(entity) / 2);
        }
    }
    @Override public boolean checkInvulnerability(LivingEntity entity, net.minecraft.world.damagesource.DamageSource source) { return this.isUsing(entity); }
    @Override public Component getDisplayName() { return Component.translatable("ability.mineminenomi.air_door"); }
}
