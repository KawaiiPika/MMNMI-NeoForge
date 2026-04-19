package xyz.pixelatedw.mineminenomi.data.entity.carry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.events.entity.EntityCarryEvent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SCarryEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SLeashPlayerPacket;

public class CarryDataBase implements ICarryData {
   private LivingEntity owner;
   private @Nullable LivingEntity carryTarget;
   private @Nullable LivingEntity carrier;
   private @Nullable LivingEntity leashHolder;

   public CarryDataBase(LivingEntity owner) {
      this.owner = owner;
   }

   public void startCarrying(@Nullable LivingEntity target) {
      Level level = this.owner.m_9236_();
      if (level != null) {
         if (target == null) {
            this.stopCarrying();
         } else {
            if (target.m_20159_()) {
               target.m_8127_();
            }

            this.carryTarget = target;
            CarryCapability.get(target).ifPresent((data) -> data.setCarrier(this.owner));
            EntityCarryEvent event = new EntityCarryEvent(this.owner, target, true);
            MinecraftForge.EVENT_BUS.post(event);
            if (!level.f_46443_) {
               LivingEntity var5 = this.owner;
               if (var5 instanceof Player) {
                  Player player = (Player)var5;
                  ModNetwork.sendTo(new SCarryEntityPacket(target), player);
               }
            }

         }
      }
   }

   public void stopCarrying() {
      Level level = this.owner.m_9236_();
      if (level != null) {
         EntityCarryEvent event = new EntityCarryEvent(this.owner, this.carryTarget, false);
         boolean isCancelled = MinecraftForge.EVENT_BUS.post(event);
         if (!isCancelled) {
            if (this.carryTarget != null) {
               CarryCapability.get(this.carryTarget).ifPresent((props) -> props.setCarrier((LivingEntity)null));
            }

            this.carryTarget = null;
            this.owner.m_21195_((MobEffect)ModEffects.CARRYING.get());
            if (!level.f_46443_) {
               LivingEntity var5 = this.owner;
               if (var5 instanceof Player) {
                  Player player = (Player)var5;
                  ModNetwork.sendTo(new SCarryEntityPacket((LivingEntity)null), player);
               }
            }

         }
      }
   }

   public @Nullable LivingEntity getCarry() {
      return this.carryTarget;
   }

   public boolean isCarrying() {
      return this.carryTarget != null && this.carryTarget.m_6084_();
   }

   public void setCarrier(@Nullable LivingEntity entity) {
      this.carrier = entity;
   }

   public @Nullable LivingEntity getCarrier() {
      return this.carrier;
   }

   public boolean isCarried() {
      return this.carrier != null && this.carrier.m_6084_();
   }

   public void setLeashedTo(LivingEntity entity) {
      Level level = this.owner.m_9236_();
      if (level != null) {
         this.leashHolder = entity;
         if (this.owner.m_20159_()) {
            this.owner.m_8127_();
         }

         if (!level.f_46443_ && entity instanceof Player) {
            Player targetPlayer = (Player)entity;
            LivingEntity var5 = this.owner;
            if (var5 instanceof Player) {
               Player ownerPlayer = (Player)var5;
               ModNetwork.sendToAllTrackingAndSelf(new SLeashPlayerPacket(ownerPlayer, targetPlayer), targetPlayer);
            }
         }

      }
   }

   public void dropLeash() {
      Level level = this.owner.m_9236_();
      if (level != null) {
         Vec3 leashHolderPos = this.leashHolder.m_20182_().m_82520_((double)0.0F, (double)this.leashHolder.m_20192_(), (double)0.0F);
         this.leashHolder = null;
         ItemEntity entity = new ItemEntity(this.owner.m_9236_(), leashHolderPos.f_82479_, leashHolderPos.f_82480_, leashHolderPos.f_82481_, Items.f_42655_.m_7968_());
         entity.m_32060_();
         this.owner.m_9236_().m_7967_(entity);
         if (!level.f_46443_) {
            LivingEntity var5 = this.owner;
            if (var5 instanceof Player) {
               Player player = (Player)var5;
               ModNetwork.sendToAllTrackingAndSelf(new SLeashPlayerPacket(player, (Player)null), player);
            }
         }

      }
   }

   public @Nullable LivingEntity getLeashHolder() {
      return this.leashHolder;
   }

   public boolean isLeashed() {
      return this.leashHolder != null;
   }

   public boolean canBeLeashed(LivingEntity entity) {
      return this.leashHolder == null;
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
   }
}
