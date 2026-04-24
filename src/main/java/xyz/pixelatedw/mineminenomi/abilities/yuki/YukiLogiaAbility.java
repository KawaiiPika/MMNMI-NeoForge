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
