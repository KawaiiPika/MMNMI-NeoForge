package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiEmissionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FactionHurtByTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public abstract class CloneEntity extends PathfinderMob implements IEntityAdditionalSpawnData, TraceableEntity {
   protected IEntityStats props;
   private LivingEntity owner;
   private UUID ownerUUID;
   private int maxAliveTicks = 200;
   private boolean usesOwnerTexture;
   private boolean permaPanic = true;

   public CloneEntity(EntityType<? extends CloneEntity> type, Level world) {
      super(type, world);
   }

   public CloneEntity(EntityType<? extends CloneEntity> type, Level world, LivingEntity owner) {
      super(type, world);
      this.f_21364_ = 0;
      this.props = (IEntityStats)EntityStatsCapability.get(this).orElse((Object)null);
      this.setOwner(owner);
      if (world != null && !world.f_46443_ && this.props != null) {
         this.props.setHeart(false);
         this.props.setShadow(true);
         this.m_6858_(true);
      }

   }

   public void disablePermaPanic() {
      this.permaPanic = false;
   }

   public boolean isPermaPanic() {
      return this.permaPanic;
   }

   public void setUseOwnerTexture() {
      this.usesOwnerTexture = true;
   }

   public boolean isUsingOwnerTexture() {
      return this.usesOwnerTexture;
   }

   public void setMaxAliveTicks(int ticks) {
      this.maxAliveTicks = ticks;
   }

   public void copyOwnerEquipment() {
      if (this.getOwner() != null && this.getOwner().m_6084_()) {
         for(EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = this.getOwner().m_6844_(slot);
            this.m_8061_(slot, stack);
         }

      }
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new PanicGoal(this, (double)1.25F));
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.getOwner() == null || this.props == null) {
            this.m_146870_();
            return;
         }

         if (this.isPermaPanic()) {
            this.m_6703_(this.getOwner());
         }

         if (this.maxAliveTicks > 0 && this.f_19797_ >= this.maxAliveTicks) {
            this.m_146870_();
         }
      }

   }

   public void m_8107_() {
      this.m_21203_();
      super.m_8107_();
   }

   protected void m_7472_(DamageSource source, int looting, boolean recentlyHitIn) {
   }

   public void setOwner(LivingEntity owner) {
      this.owner = owner;
      this.ownerUUID = owner.m_20148_();
      if (this.props != null) {
         Faction ownerFaction = (Faction)EntityStatsCapability.get(owner).map((props) -> (Faction)props.getFaction().orElse((Object)null)).orElse((Object)null);
         this.props.setFaction(ownerFaction);
         Predicate<Entity> factionScope = ModEntityPredicates.getEnemyFactions(this);
         Predicate<LivingEntity> factionScope2 = (target) -> FactionHelper.isEnemyFactions(this, target);
         this.f_21346_.m_25352_(1, new FactionHurtByTargetGoal(this, factionScope, new Class[0]));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, true, factionScope2));
      }
   }

   public void unlockHakiOfOwner(LivingEntity owner) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(owner).orElse((Object)null);
      if (abilityProps != null) {
         if (abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get())) {
            this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         }

         if (abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get())) {
            this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiEmissionAbility.INSTANCE.get()));
         }

         if (abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get())) {
            this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get()));
         }

         if (abilityProps.hasUnlockedAbility((AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get())) {
            this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         }

      }
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null) {
         Level var2 = this.m_9236_();
         if (var2 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var2;
            Entity entity = serverLevel.m_8791_(this.ownerUUID);
            if (entity instanceof LivingEntity) {
               this.owner = (LivingEntity)entity;
            }
         }
      }

      return this.owner;
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      if (this.ownerUUID != null) {
         nbt.m_128362_("ownerId", this.ownerUUID);
      }

      nbt.m_128379_("isTextured", this.usesOwnerTexture);
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      if (nbt.m_128441_("ownerId")) {
         this.ownerUUID = nbt.m_128342_("ownerId");
      }

      this.usesOwnerTexture = nbt.m_128471_("isTextured");
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = this.owner != null;
      buffer.writeBoolean(hasOwner);
      if (hasOwner) {
         buffer.writeInt(this.owner.m_19879_());
      }

      buffer.writeBoolean(this.usesOwnerTexture);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      boolean hasOwner = buffer.readBoolean();
      if (hasOwner) {
         int ownerId = buffer.readInt();
         Entity var5 = this.m_9236_().m_6815_(ownerId);
         if (var5 instanceof LivingEntity) {
            LivingEntity owner = (LivingEntity)var5;
            this.setOwner(owner);
         }
      }

      this.usesOwnerTexture = buffer.readBoolean();
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
