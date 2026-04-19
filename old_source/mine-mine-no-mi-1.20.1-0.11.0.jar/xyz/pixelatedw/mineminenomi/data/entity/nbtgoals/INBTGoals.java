package xyz.pixelatedw.mineminenomi.data.entity.nbtgoals;

import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NBTGoal;

public interface INBTGoals extends INBTSerializable<CompoundTag> {
   Set<NBTGoal> getGoals();
}
