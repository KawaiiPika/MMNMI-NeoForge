package xyz.pixelatedw.mineminenomi.data.entity.combat;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ClientBossExtraEvent;

public interface ICombatData extends INBTSerializable<CompoundTag> {
   void tick();

   void setInCombatCache(@Nullable LivingEntity var1);

   boolean isInCombatCache();

   @Nullable LivingEntity getLastAttacker();

   long getLastAttackTime();

   Map<UUID, ClientBossExtraEvent> getExtraBossInfo();

   void addExtraBossInfo(UUID var1, ClientBossExtraEvent var2);

   void setStoredDamage(float var1);

   float getStoredDamage();

   void setHitTriggers(Set<HitTriggerComponent> var1);

   Set<HitTriggerComponent> getHitTriggers();
}
