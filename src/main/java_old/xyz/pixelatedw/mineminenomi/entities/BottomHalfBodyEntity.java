package xyz.pixelatedw.mineminenomi.entities;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class BottomHalfBodyEntity extends Mob implements TraceableEntity, IEntityAdditionalSpawnData {
   private LivingEntity owner;
   private Ability parentAbility;

   public BottomHalfBodyEntity(EntityType<? extends BottomHalfBodyEntity> type, Level world) {
      super(type, world);
      this.parentAbility = null;
   }

   public BottomHalfBodyEntity(Level world, LivingEntity owner) {
      this((EntityType)ModMobs.BOTTOM_HALF.get(), world);
      this.setOwner(owner);
   }

   public void setParentAbility(Ability ability) {
      this.parentAbility = ability;
   }

   private void setOwner(LivingEntity owner) {
      this.owner = owner;
      IEntityStats entityStats = (IEntityStats)EntityStatsCapability.get(this).orElse((Object)null);
      IEntityStats ownerStats = (IEntityStats)EntityStatsCapability.get(owner).orElse((Object)null);
      entityStats.setFaction((Faction)ownerStats.getFaction().orElse((Object)null));
      entityStats.setRace((Race)ownerStats.getRace().orElse((Object)null));
      ForgeRegistries.ATTRIBUTES.forEach((attr) -> {
         if (owner.m_21051_(attr) != null && this.m_21051_(attr) != null) {
            double val = owner.m_21051_(attr).m_22115_();
            this.m_21051_(attr).m_22100_(val);
         }

      });
      this.m_21153_(owner.m_21223_());
   }

   @Nullable
   public LivingEntity getOwner() {
      return this.owner;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      LivingEntity owner = this.getOwner();
      if (owner == null) {
         return false;
      } else {
         owner.m_6469_(ModDamageSources.getInstance().outOfBody(), amount);
         this.m_21153_(owner.m_21223_());
         return false;
      }
   }

   public void m_6667_(DamageSource cause) {
      LivingEntity owner = this.getOwner();
      if (owner != null) {
         owner.m_6469_(ModDamageSources.getInstance().outOfBody(), owner.m_21233_());
      }
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_) {
         LivingEntity owner = this.getOwner();
         boolean hasParentAbilityActive = false;
         if (owner != null) {
            IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(owner).orElse((Object)null);
            if (abilityProps == null) {
               return;
            }

            Ability parentAbilityInstance = (Ability)abilityProps.getEquippedAbility(this.parentAbility);
            if (parentAbilityInstance != null && parentAbilityInstance.isContinuous()) {
               hasParentAbilityActive = true;
               if ((AbilityHelper.isAffectedByWater(this) || this.m_20159_()) && this.getOwner() != null) {
                  ((ContinuousComponent)parentAbilityInstance.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).get()).stopContinuity(this.getOwner());
               }
            }
         }

         if (owner == null || !owner.m_6084_() || this.parentAbility == null || !hasParentAbilityActive) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         if (this.m_20270_(this.getOwner()) > 5.0F) {
            this.m_21573_().m_5624_(this.getOwner(), (double)4.0F);
         }
      }

      super.m_8119_();
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, (double)100.0F).m_22268_(Attributes.f_22278_, (double)1.0F);
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = this.owner != null;
      buffer.writeBoolean(hasOwner);
      if (hasOwner) {
         buffer.writeInt(this.owner.m_19879_());
      }

   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = buffer.readBoolean();
      if (hasOwner) {
         int ownerId = buffer.readInt();
         Entity var5 = this.m_9236_().m_6815_(ownerId);
         if (var5 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity)var5;
            this.owner = owner;
         }
      }

   }
}
