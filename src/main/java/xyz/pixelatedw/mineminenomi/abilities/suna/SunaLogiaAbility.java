package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SunaLogiaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi");

    public SunaLogiaAbility() { super(FRUIT); }

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
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.logia_invulnerability_suna"); }
}
