package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class MeraLogiaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi");
    public MeraLogiaAbility() { super(FRUIT); }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isLogia();
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setLogia(true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setLogia(false);
            stats.sync(entity);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.mera_logia"); }
}
