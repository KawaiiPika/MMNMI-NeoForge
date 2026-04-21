package xyz.pixelatedw.mineminenomi.quests.objectives;

import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;

public class SharedKillChecks {
    public static final KillEntityObjective.IKillCheck HAS_SWORD = (player, victim, source) -> {
        return ItemsHelper.isSword(player.getMainHandItem());
    };

    public static final KillEntityObjective.IKillCheck HAS_BOW = (player, victim, source) -> {
        return ItemsHelper.isBowOrGun(player.getMainHandItem());
    };

    public static final KillEntityObjective.IKillCheck HAS_EMPTY_HAND = (player, victim, source) -> {
        return player.getMainHandItem().isEmpty();
    };

    public static final KillEntityObjective.IKillCheck CRITICAL_KILL_CHECK = (player, victim, source) -> {
        boolean criticalFlag = player.fallDistance > 0.0F && !player.onGround() && !player.onClimbable() && !player.isInWater() && !player.isPassenger();
        return criticalFlag;
    };

    public static final KillEntityObjective.IKillCheck AIRBORNE_ENEMY_CHECK = (player, victim, source) -> {
        return !victim.onGround() && !victim.isInWater();
    };

    public static final KillEntityObjective.IKillCheck ON_FIRE_ENEMY_CHECK = (player, victim, source) -> {
        return victim.isOnFire();
    };

    public static final KillEntityObjective.IKillCheck ON_FIRE_PLAYER_CHECK = (player, victim, source) -> {
        return player.isOnFire();
    };
}
