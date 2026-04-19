package xyz.pixelatedw.mineminenomi.api.entities.ai;

import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.enums.NBTGoalType;

public record NBTGoal(NBTGoalType type, int priority, AbilityCore<?> abilityId) {
}
