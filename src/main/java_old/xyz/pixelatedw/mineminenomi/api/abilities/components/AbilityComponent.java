package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.ComponentNotRegisteredException;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;

public class AbilityComponent<A extends IAbility> {
   private int tickRate = 0;
   private long timeToTick;
   private final AbilityComponentKey<?> key;
   private final A ability;
   private boolean isDisabled = false;
   private boolean isClientSided = false;
   private List<BonusManager> bonuses = new ArrayList();

   public AbilityComponent(AbilityComponentKey<? extends AbilityComponent<A>> key, A ability) {
      this.key = key;
      this.ability = ability;
   }

   public AbilityComponentKey<?> getKey() {
      return this.key;
   }

   public A getAbility() {
      return this.ability;
   }

   public Iterator<BonusManager> getBonusManagers() {
      return this.bonuses.iterator();
   }

   public void addBonusManager(BonusManager manager) {
      this.bonuses.add(manager);
   }

   public float getTpsFactor() {
      return TPSDelta.INSTANCE.getDeltaTime();
   }

   public void setTickRate(int tickRate) {
      this.tickRate = tickRate;
   }

   public final void tick(LivingEntity entity) {
      if (!this.isDisabled) {
         if (--this.timeToTick <= 0L) {
            this.timeToTick = (long)this.tickRate;
            this.doTick(entity);
         }

      }
   }

   protected void doTick(LivingEntity entity) {
   }

   public void postInit(IAbility ability) {
   }

   public boolean isDisabled() {
      return this.isDisabled;
   }

   public void setDisabled(boolean isDisabled) {
      this.isDisabled = isDisabled;
   }

   public boolean isClientSided() {
      return this.isClientSided;
   }

   public void setClientSide() {
      this.isClientSided = true;
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      if (!this.bonuses.isEmpty()) {
         ListTag bonusNBT = new ListTag();

         for(BonusManager manager : this.bonuses) {
            if (manager.getBonuses().size() > 0) {
               CompoundTag managerNBT = new CompoundTag();
               managerNBT.m_128362_("id", manager.getId());
               ListTag managerBonusesNBT = new ListTag();

               for(Map.Entry<UUID, BonusManager.BonusValue> entry : manager.getBonuses()) {
                  CompoundTag entryNBT = new CompoundTag();
                  entryNBT.m_128362_("id", (UUID)entry.getKey());
                  entryNBT.m_128359_("name", ((BonusManager.BonusValue)entry.getValue()).getName());
                  entryNBT.m_128405_("type", ((BonusManager.BonusValue)entry.getValue()).getType().ordinal());
                  entryNBT.m_128350_("value", ((BonusManager.BonusValue)entry.getValue()).getValue());
                  managerBonusesNBT.add(entryNBT);
               }

               managerNBT.m_128365_("list", managerBonusesNBT);
               bonusNBT.add(managerNBT);
            }
         }

         if (!bonusNBT.isEmpty()) {
            nbt.m_128365_("bonuses", bonusNBT);
         }
      }

      return nbt;
   }

   public void load(CompoundTag nbt) {
      ListTag bonusNBT = nbt.m_128437_("bonuses", 10);

      for(int i = 0; i < bonusNBT.size(); ++i) {
         CompoundTag managerNBT = bonusNBT.m_128728_(i);
         UUID id = managerNBT.m_128342_("id");
         BonusManager manager = (BonusManager)this.bonuses.stream().filter((m) -> m.getId().equals(id)).findFirst().orElse((Object)null);
         if (manager != null) {
            manager.clearBonuses();
            ListTag managerBonusesNBT = managerNBT.m_128437_("list", 10);

            for(int j = 0; j < managerBonusesNBT.size(); ++j) {
               CompoundTag entryNBT = managerBonusesNBT.m_128728_(j);
               UUID entryId = entryNBT.m_128342_("id");
               String name = entryNBT.m_128461_("name");
               BonusOperation op = BonusOperation.values()[entryNBT.m_128451_("type")];
               float value = entryNBT.m_128457_("value");
               manager.addBonus(entryId, name, op, value);
            }
         }
      }

   }

   public void ensureIsRegistered() {
      boolean hasComponent = this.getAbility().hasComponent(this.getKey());
      if (!hasComponent) {
         throw new ComponentNotRegisteredException(this);
      }
   }
}
