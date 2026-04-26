package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ImprovedMeleeAttackGoal extends MeleeAttackGoal {
    public ImprovedMeleeAttackGoal(PathfinderMob entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
    }
}
