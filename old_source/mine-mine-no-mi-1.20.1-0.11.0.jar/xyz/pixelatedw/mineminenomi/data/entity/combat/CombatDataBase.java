package xyz.pixelatedw.mineminenomi.data.entity.combat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ClientBossExtraEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.UpdateCombatStateEvent;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SUpdateCombatStatePacket;

public class CombatDataBase implements ICombatData {
   private LivingEntity owner;
   private @Nullable LivingEntity lastAttacker;
   private long lastAttackTime;
   private float storedDamage;
   private Map<UUID, ClientBossExtraEvent> extraInfos = new HashMap();
   private Set<HitTriggerComponent> hitTriggers = new HashSet();

   public CombatDataBase(LivingEntity owner) {
      this.owner = owner;
   }

   public void setInCombatCache(@Nullable LivingEntity attacker) {
      if (attacker != null) {
         this.lastAttackTime = 300L;
      } else {
         this.lastAttackTime = 0L;
      }

      if (this.lastAttacker != attacker) {
         this.lastAttacker = attacker;
         boolean hasAttacker = this.lastAttacker != null;
         UpdateCombatStateEvent event = new UpdateCombatStateEvent(this.owner, this.lastAttacker, hasAttacker);
         MinecraftForge.EVENT_BUS.post(event);
         LivingEntity var5 = this.owner;
         if (var5 instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)var5;
            ModNetwork.sendTo(new SUpdateCombatStatePacket(this.lastAttacker), player);
         }
      }

   }

   public boolean isInCombatCache() {
      return this.lastAttackTime > 0L;
   }

   public @Nullable LivingEntity getLastAttacker() {
      return this.lastAttacker;
   }

   public long getLastAttackTime() {
      return this.lastAttackTime;
   }

   public void tick() {
      if (this.owner != null) {
         boolean isAttackerAlive = this.lastAttacker != null && this.lastAttacker.m_6084_();
         boolean isAttackerInDistance = isAttackerAlive && Math.abs(this.lastAttacker.m_20280_(this.owner)) <= (double)10000.0F;
         boolean isTheTarget = isAttackerAlive && this.lastAttacker instanceof Mob && ((Mob)this.lastAttacker).m_5448_() == this.owner;
         boolean canTickDown = !isAttackerAlive || !isAttackerInDistance || !isTheTarget;
         if (canTickDown && this.lastAttackTime > 0L) {
            --this.lastAttackTime;
         } else if (this.lastAttackTime <= 0L && this.lastAttacker != null) {
            this.setInCombatCache((LivingEntity)null);
         }

      }
   }

   public Map<UUID, ClientBossExtraEvent> getExtraBossInfo() {
      return this.extraInfos;
   }

   public void addExtraBossInfo(UUID id, ClientBossExtraEvent info) {
      this.extraInfos.put(id, info);
   }

   public void setStoredDamage(float damage) {
      this.storedDamage = damage;
   }

   public float getStoredDamage() {
      return this.storedDamage;
   }

   public void setHitTriggers(Set<HitTriggerComponent> triggers) {
      this.hitTriggers = triggers;
   }

   public Set<HitTriggerComponent> getHitTriggers() {
      return this.hitTriggers;
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
   }
}
