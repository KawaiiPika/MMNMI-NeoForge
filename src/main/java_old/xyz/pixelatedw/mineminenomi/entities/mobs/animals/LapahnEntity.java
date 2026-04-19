package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.LeapAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.PersonalSpaceTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn.LapahnChaseGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn.LapahnRageGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn.LapahnRestGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class LapahnEntity extends AgeableMob {
   private static final UUID LEAP_COOLDOWN_BONUS_UUID = UUID.fromString("5711f1b8-d776-44fb-9cd7-afdbd51a9837");
   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID;
   private static final int JUMP_ANIM_CYCLE = 20;
   private static final byte JUMP_EVENT = 100;
   private int clientJumpTick = 0;
   private int jumpTick = 0;

   public LapahnEntity(EntityType<? extends LapahnEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         CloseMeleeAbilityWrapperGoal<LeapAbility> leapWrapper = new CloseMeleeAbilityWrapperGoal<LeapAbility>(this, (AbilityCore)LeapAbility.INSTANCE.get());
         ((LeapAbility)leapWrapper.getAbility()).setLeapHeight(0.4);
         ((LeapAbility)leapWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(LEAP_COOLDOWN_BONUS_UUID, "Leap Cooldown Bonus", BonusOperation.MUL, 0.5F));
         this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
         this.f_21345_.m_25352_(0, new FloatGoal(this));
         this.f_21345_.m_25352_(0, new LapahnChaseGoal(this));
         this.f_21345_.m_25352_(0, new LapahnRageGoal(this));
         this.f_21345_.m_25352_(0, new LapahnRestGoal(this));
         this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, (double)1.0F, true));
         this.f_21345_.m_25352_(1, leapWrapper);
         this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
         this.f_21345_.m_25352_(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.f_21345_.m_25352_(4, new LookAtPlayerGoal(this, Rabbit.class, 8.0F));
         this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
         this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
         this.f_21346_.m_25352_(1, new PersonalSpaceTargetGoal(this));
         this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, HumandrillEntity.class, true));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)55.0F).m_22268_(Attributes.f_22279_, 0.2).m_22268_(Attributes.f_22281_, (double)5.0F).m_22268_(Attributes.f_22276_, (double)60.0F);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_9236_().f_46443_) {
         if (this.clientJumpTick > 0) {
            --this.clientJumpTick;
         }
      } else if (this.isIdling() && --this.jumpTick <= 0 && this.m_20096_()) {
         this.m_9236_().m_7605_(this, (byte)100);
         this.m_21569_().m_24901_();
         this.m_5496_(SoundEvents.f_12284_, 0.2F, 0.5F);
         this.jumpTick = 20;
      }

   }

   public List<LapahnEntity> getNearbyLapahns() {
      Vec3 var10000 = this.m_20182_();
      Level var10001 = this.m_9236_();
      Objects.requireNonNull(LapahnEntity.class);
      List<LapahnEntity> targets = WyHelper.<LapahnEntity>getNearbyLiving(var10000, var10001, (double)16.0F, LapahnEntity.class::isInstance);
      targets.remove(this);
      return targets;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(DATA_FLAGS_ID, (byte)0);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 100:
            this.clientJumpTick = 20;
         default:
            super.m_7822_(id);
      }
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
      spawnData = super.m_6518_(world, difficulty, reason, spawnData, dataTag);
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(this).orElseThrow();
      props.setDoriki((double)1000.0F + WyHelper.randomWithRange(0, 500));
      props.setBelly(0L);
      return spawnData;
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128379_("isEnraged", this.isEnraged());
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.setEnraged(nbt.m_128471_("isEnraged"));
   }

   public void setEnraged(boolean set) {
      this.setFlag(0, set);
   }

   public void setChasing(boolean flag) {
      this.setFlag(1, flag);
      if (flag) {
         this.setResting(false);
      }

   }

   public void setResting(boolean flag) {
      this.setFlag(2, flag);
   }

   private void setFlag(int flag, boolean set) {
      byte b0 = (Byte)this.f_19804_.m_135370_(DATA_FLAGS_ID);
      if (set) {
         this.f_19804_.m_135381_(DATA_FLAGS_ID, (byte)(b0 | 1 << flag));
      } else {
         this.f_19804_.m_135381_(DATA_FLAGS_ID, (byte)(b0 & ~(1 << flag)));
      }

   }

   private boolean getFlag(int id) {
      return ((Byte)this.f_19804_.m_135370_(DATA_FLAGS_ID) & 1 << id) != 0;
   }

   public boolean isIdling() {
      return !this.isResting() && !this.isChasing();
   }

   public boolean isEnraged() {
      return this.getFlag(0);
   }

   public boolean isChasing() {
      return this.getFlag(1);
   }

   public boolean isResting() {
      return this.getFlag(2);
   }

   @OnlyIn(Dist.CLIENT)
   public float getJumpAnimationProgress(float partialTicks) {
      return ((float)(20 - this.clientJumpTick) + partialTicks) / 20.0F;
   }

   public boolean m_6785_(double distance) {
      return false;
   }

   public AgeableMob m_142606_(ServerLevel level, AgeableMob mob) {
      return null;
   }

   static {
      DATA_FLAGS_ID = SynchedEntityData.m_135353_(LapahnEntity.class, EntityDataSerializers.f_135027_);
   }
}
