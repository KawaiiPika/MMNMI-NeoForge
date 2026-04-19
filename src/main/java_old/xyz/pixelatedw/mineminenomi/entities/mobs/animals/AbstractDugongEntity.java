package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.GoalMemories;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsBase;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongCheerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongHappyGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongRageGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongRestGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong.DugongTrainGoal;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;

public abstract class AbstractDugongEntity extends TamableAnimal implements IGoalMemoriesEntity {
   private static final Item[] FOOD;
   private static final EntityDataAccessor<Byte> FLAGS;
   private static final EntityDataAccessor<Integer> TRAINING_MODE;
   private final GoalMemories goalMemories = new GoalMemories();
   private LivingEntity cheerTarget;
   private LivingEntity rageTarget;

   protected AbstractDugongEntity(EntityType<? extends TamableAnimal> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(this).orElse(new EntityStatsBase(this));
         props.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      }

   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)BrawlerPassiveBonusesAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(0, new DugongRageGoal(this));
      this.f_21345_.m_25352_(0, new DugongTrainGoal(this));
      if (!(this instanceof WanderingDugongEntity)) {
         this.f_21345_.m_25352_(0, new DugongHappyGoal(this));
         this.f_21345_.m_25352_(0, new DugongCheerGoal(this));
         this.f_21345_.m_25352_(0, new DugongRestGoal(this));
      }

      this.f_21345_.m_25352_(3, new FollowOwnerGoal(this, (double)1.0F, 6.0F, 2.0F, false));
      this.f_21345_.m_25352_(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, AbstractDugongEntity.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.m_33035_().m_22266_(Attributes.f_22283_).m_22268_(Attributes.f_22277_, (double)40.0F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(FLAGS, (byte)0);
      this.m_20088_().m_135372_(TRAINING_MODE, 0);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   public SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_12485_;
   }

   public SoundEvent m_5592_() {
      return SoundEvents.f_12532_;
   }

   public void m_7355_(BlockPos pos, BlockState block) {
      this.m_5496_(SoundEvents.f_12488_, 0.2F, 0.5F);
   }

   public float m_6121_() {
      return 0.4F;
   }

   public float m_6100_() {
      return this.m_6162_() ? 0.6F + this.f_19796_.m_188501_() / 4.0F : 0.4F + this.f_19796_.m_188501_() / 4.0F;
   }

   private void broadcastAngerTarget(LivingEntity target) {
      this.getNearbyDugongs().forEach((other) -> {
         if (!other.m_21830_(target)) {
            other.setEnraged(target);
         }
      });
   }

   private void broadcastCheeringTarget(LivingEntity target) {
      this.getNearbyDugongs().forEach((other) -> {
         if (!other.isEnraged() && !GoalHelper.hasAliveTarget(other)) {
            GoalHelper.lookAtEntity(other, this);
            other.setCheering(this);
         }
      });
   }

   public List<AbstractDugongEntity> getNearbyDugongs() {
      if (this.hasMemoryValue((MemoryModuleType)ModMemoryModuleTypes.NEARBY_ADULT_DUGONGS.get())) {
         return (List)this.getMemory((MemoryModuleType)ModMemoryModuleTypes.NEARBY_ADULT_DUGONGS.get()).get();
      } else {
         List<AbstractDugongEntity> targets = TargetHelper.<AbstractDugongEntity>getEntitiesInArea(this.m_9236_(), this, (double)16.0F, (TargetPredicate)null, AbstractDugongEntity.class);
         targets.remove(this);
         this.setMemoryWithExpiry((MemoryModuleType)ModMemoryModuleTypes.NEARBY_ADULT_DUGONGS.get(), targets, 600L);
         return targets;
      }
   }

   public void m_8024_() {
      this.goalMemories.tick();
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (this.m_21824_() && this.m_21830_(player) && !player.m_9236_().f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(player.m_7655_() != null ? player.m_7655_() : InteractionHand.MAIN_HAND);
         if (!stack.m_41619_() && this.m_21223_() < this.m_21233_() && this.m_6898_(stack)) {
            stack.m_41774_(1);
            this.m_5634_(4.0F);
            this.m_9236_().m_7605_(this, (byte)7);
            return InteractionResult.SUCCESS;
         }

         if (stack.m_41619_()) {
            this.m_21839_(!this.m_21827_());
            this.f_20899_ = false;
            this.f_21344_.m_26573_();
            this.m_6710_((LivingEntity)null);
            return InteractionResult.SUCCESS;
         }
      }

      return super.m_6071_(player, hand);
   }

   public void m_8119_() {
      super.m_8119_();
   }

   public void m_8107_() {
      this.m_21203_();
      super.m_8107_();
   }

   public boolean m_6469_(DamageSource damageSource, float amount) {
      boolean flag = super.m_6469_(damageSource, amount);
      if (!flag) {
         return false;
      } else {
         Entity entity = damageSource.m_7639_();
         if (!this.m_9236_().f_46443_ && !this.m_21824_() && entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            boolean hasEmptyHand = living.m_21120_(living.m_7655_()).m_41619_();
            if (hasEmptyHand) {
               if (living instanceof Player) {
                  Player player = (Player)living;
                  this.broadcastCheeringTarget(living);
                  if (!this.isEnraged() && this.m_21223_() < this.m_21233_() / 2.0F && !ForgeEventFactory.onAnimalTame(this, player)) {
                     this.m_21828_(player);
                  }
               }
            } else {
               this.setEnraged(living);
               this.broadcastAngerTarget(living);
            }
         }

         return true;
      }
   }

   public boolean m_21827_() {
      return super.m_21827_();
   }

   public boolean m_7757_(LivingEntity target, LivingEntity owner) {
      if (!(target instanceof Ghast) && !(target instanceof Blaze)) {
         LivingEntity entityTamer = MobsHelper.getTamer(target);
         if (entityTamer != null) {
            LivingEntity targetTamer = MobsHelper.getTamer(target);
            if (targetTamer.equals(entityTamer)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void m_21828_(Player player) {
      super.m_21828_(player);
      this.f_21344_.m_26573_();
      this.m_21839_(true);
      this.m_6710_((LivingEntity)null);
      this.broadcastCheeringTarget((LivingEntity)null);
      this.m_9236_().m_7605_(this, (byte)7);
   }

   public AgeableMob m_142606_(ServerLevel pServerLevel, AgeableMob pMate) {
      return null;
   }

   public boolean canDrownInFluidType(FluidType type) {
      return type != ForgeMod.WATER_TYPE.get();
   }

   public boolean m_6898_(ItemStack stack) {
      Stream var10000 = Arrays.stream(FOOD);
      Item var10001 = stack.m_41720_();
      Objects.requireNonNull(var10001);
      return var10000.anyMatch(var10001::equals);
   }

   public boolean hasHakiLearned() {
      IAbilityData props = (IAbilityData)AbilityCapability.get(this).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         boolean hasHardening = props.hasUnlockedAbility((AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get());
         boolean hasFullbody = props.hasUnlockedAbility((AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get());
         return hasHardening || hasFullbody;
      }
   }

   public boolean m_6785_(double distance) {
      return false;
   }

   public boolean isTraining() {
      return (Integer)this.f_19804_.m_135370_(TRAINING_MODE) > 0;
   }

   public boolean isIdling() {
      return !this.isTraining() && !this.isResting() && !this.isCheering();
   }

   public TrainingMethod getTrainingMode() {
      return AbstractDugongEntity.TrainingMethod.values()[(Integer)this.f_19804_.m_135370_(TRAINING_MODE) - 1];
   }

   public void stopTraining() {
      this.setTraining(0);
   }

   public void setTraining(int mode) {
      this.f_19804_.m_135381_(TRAINING_MODE, mode);
   }

   public void setCheering(@Nullable LivingEntity target) {
      boolean set = true;
      if (target == null || this.m_21830_(target)) {
         set = false;
         target = null;
      }

      this.setFlag(0, set);
      this.cheerTarget = target;
   }

   public void setHappy(boolean flag) {
      this.setFlag(1, flag);
   }

   public void setResting(boolean flag) {
      this.setFlag(2, flag);
   }

   public void setEnraged(@Nullable LivingEntity attacker) {
      boolean set = true;
      if (attacker == null || this.m_21830_(attacker)) {
         set = false;
         attacker = null;
      }

      this.setFlag(3, set);
      this.rageTarget = attacker;
   }

   private void setFlag(int flag, boolean set) {
      byte b0 = (Byte)this.f_19804_.m_135370_(FLAGS);
      if (set) {
         this.f_19804_.m_135381_(FLAGS, (byte)(b0 | 1 << flag));
      } else {
         this.f_19804_.m_135381_(FLAGS, (byte)(b0 & ~(1 << flag)));
      }

   }

   private boolean getFlag(int id) {
      return ((Byte)this.f_19804_.m_135370_(FLAGS) & 1 << id) != 0;
   }

   public boolean isCheering() {
      return this.getFlag(0);
   }

   public boolean isHappy() {
      return this.getFlag(1);
   }

   public boolean isResting() {
      return this.getFlag(2);
   }

   public boolean isEnraged() {
      return this.getFlag(3);
   }

   public boolean isWandering() {
      return this.getFlag(4);
   }

   public @Nullable LivingEntity getCheerTarget() {
      return this.cheerTarget;
   }

   public @Nullable LivingEntity getRageTarget() {
      return this.rageTarget;
   }

   public GoalMemories getGoalMemories() {
      return this.goalMemories;
   }

   static {
      FOOD = new Item[]{Items.f_42580_, Items.f_42582_, Items.f_42530_, Items.f_42659_, Items.f_42486_, Items.f_42698_, Items.f_42531_, Items.f_41910_, Items.f_42576_};
      FLAGS = SynchedEntityData.m_135353_(AbstractDugongEntity.class, EntityDataSerializers.f_135027_);
      TRAINING_MODE = SynchedEntityData.m_135353_(AbstractDugongEntity.class, EntityDataSerializers.f_135028_);
   }

   public static enum TrainingMethod {
      SHADOW_BOXING,
      PUSHUPS;

      // $FF: synthetic method
      private static TrainingMethod[] $values() {
         return new TrainingMethod[]{SHADOW_BOXING, PUSHUPS};
      }
   }
}
