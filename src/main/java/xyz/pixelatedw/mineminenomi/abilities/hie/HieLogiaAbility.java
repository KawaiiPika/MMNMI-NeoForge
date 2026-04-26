package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class HieLogiaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi");

    public HieLogiaAbility() { super(FRUIT); }

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
        if (source.is(DamageTypeTags.IS_FREEZING)) {
            return true; // Immune to freezing
        }
        return false;
    }

@Override
    public void onLogiaDodge(LivingEntity entity, LivingEntity attacker) {
        // Side effect: Freezes attacker when dodged
        attacker.addEffect(new MobEffectInstance(ModEffects.FROZEN, 40, 0));
    }

@Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.logia_invulnerability_hie"); }
}
