package xyz.pixelatedw.mineminenomi.data.entity.nbtgoals;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NBTGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NBTGoalType;
import xyz.pixelatedw.mineminenomi.ModMain;

public class NBTGoalsBase implements INBTGoals {
   private Set<NBTGoal> goals = new HashSet();

   public Set<NBTGoal> getGoals() {
      return this.goals;
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      ListTag goalsTag = new ListTag();

      for(NBTGoal goal : this.goals) {
         CompoundTag goalTag = new CompoundTag();
         goalTag.m_128359_("type", goal.type().toString());
         goalTag.m_128405_("priority", goal.priority());
         goalTag.m_128359_("abilityId", goal.abilityId().getRegistryKey().toString());
         goalsTag.add(goalTag);
      }

      nbt.m_128365_("goals", goalsTag);
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
      ListTag goals = nbt.m_128437_("goals", 10);

      for(int i = 0; i < goals.size(); ++i) {
         try {
            CompoundTag goalTag = goals.m_128728_(i);
            NBTGoalType type = NBTGoalType.valueOf(goalTag.m_128461_("type"));
            int priority = goalTag.m_128451_("priority");
            AbilityCore<?> core = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(ResourceLocation.parse(goalTag.m_128461_("abilityId")));
            if (core != null) {
               NBTGoal goal = new NBTGoal(type, priority, core);
               this.goals.add(goal);
            }
         } catch (Exception e) {
            ModMain.LOGGER.error("Failed to deserialize NBTGoal", e);
         }
      }

   }
}
