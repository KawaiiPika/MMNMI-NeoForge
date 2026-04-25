package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.passives.StatBonusAbility;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import java.util.function.Predicate;

public class MaguPassivesAbility extends StatBonusAbility {

    public MaguPassivesAbility() {
        super(ModFruits.MAGU_MAGU_NO_MI.getId());
    }

    @Override
    public Predicate<LivingEntity> getCheck() {
        return entity -> true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.magu_passives");
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (entity instanceof net.minecraft.world.entity.player.Player player) {
            lavaMovementBoost(player);
        }
    }

    private void lavaMovementBoost(net.minecraft.world.entity.player.Player player) {
        if (player.isInLava() && !player.getAbilities().flying) {
            float a = player.zza == 0.0F && player.xxa == 0.0F ? 0.5F : 2.0F;
            float y = player.onGround() && !(player.getY() - player.yo > 0.0D) ? 2.0F : 0.0F;
            net.minecraft.world.phys.Vec3 vec = player.getDeltaMovement().multiply(a, y, a);

            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.setDeltaMovement(player, vec.x, vec.y, vec.z);
        }
    }

    public static boolean canSeeThroughFire(net.minecraft.world.entity.player.Player player) {
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(player);
        return stats != null && stats.isAbilityActive("mineminenomi:magu_passives");
    }

    public static boolean canSeeInsideLava(net.minecraft.world.entity.player.Player player) {
        xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(player);
        return stats != null && stats.isAbilityActive("mineminenomi:magu_passives");
    }
}
