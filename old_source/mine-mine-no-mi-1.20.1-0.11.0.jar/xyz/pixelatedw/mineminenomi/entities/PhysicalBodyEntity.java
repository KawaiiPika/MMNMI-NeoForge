package xyz.pixelatedw.mineminenomi.entities;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class PhysicalBodyEntity extends Mob {
   private static final EntityDataAccessor<Optional<UUID>> OWNER;
   private Ability parentAbility;

   public PhysicalBodyEntity(EntityType<? extends PhysicalBodyEntity> type, Level world) {
      super(type, world);
      this.parentAbility = null;
   }

   public PhysicalBodyEntity(Level world) {
      this((EntityType)ModMobs.PHYSICAL_BODY.get(), world);
   }

   public void setParentAbility(Ability ability) {
      this.parentAbility = ability;
   }

   public void setOwner(LivingEntity owner) {
      this.f_19804_.m_135381_(OWNER, Optional.of(owner.m_20148_()));
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
   public UUID getOwnerUUID() {
      return ((Optional)this.m_20088_().m_135370_(OWNER)).isPresent() ? (UUID)((Optional)this.m_20088_().m_135370_(OWNER)).get() : null;
   }

   @Nullable
   public Player getOwner() {
      UUID uuid = this.getOwnerUUID();
      return uuid != null ? this.m_9236_().m_46003_(uuid) : null;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      Player owner = this.getOwner();
      if (owner == null) {
         return false;
      } else {
         owner.m_6469_(ModDamageSources.getInstance().outOfBody(), amount);
         this.m_21153_(owner.m_21223_());
         return false;
      }
   }

   public void m_6667_(DamageSource cause) {
      Player owner = this.getOwner();
      if (owner != null) {
         owner.m_6469_(ModDamageSources.getInstance().outOfBody(), owner.m_21233_());
      }
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_) {
         Player owner = this.getOwner();
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
      }

      super.m_8119_();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER, Optional.empty());
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22276_, (double)100.0F).m_22268_(Attributes.f_22278_, (double)1.0F);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      if (this.f_19804_.m_135370_(OWNER) != null) {
         compound.m_128359_("OwnerUUID", ((UUID)((Optional)this.f_19804_.m_135370_(OWNER)).get()).toString());
      }

   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.f_19804_.m_135381_(OWNER, Optional.of(UUID.fromString(compound.m_128461_("OwnerUUID"))));
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   static {
      OWNER = SynchedEntityData.m_135353_(PhysicalBodyEntity.class, EntityDataSerializers.f_135041_);
   }
}
