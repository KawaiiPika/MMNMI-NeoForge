package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class BakuretsuKazanAbility extends Ability {

    private static final int CHARGE_TIME = 100;

    public BakuretsuKazanAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "magu_magu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Charging started
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration >= CHARGE_TIME) {
            Level level = entity.level();
            if (!level.isClientSide) {
                int range = 16;
                MaguHelper.generateLavaPool(level, entity.blockPosition(), range);
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), xyz.pixelatedw.mineminenomi.init.ModSounds.BAKURETSU_KAZAN.get(), net.minecraft.sounds.SoundSource.PLAYERS, 10.0F, 1.0F);
                this.startCooldown(entity, 600);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.bakuretsu_kazan");
    }
}
