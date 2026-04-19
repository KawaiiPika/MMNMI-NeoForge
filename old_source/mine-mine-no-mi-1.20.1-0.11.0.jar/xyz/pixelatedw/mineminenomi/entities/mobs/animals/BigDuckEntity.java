package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class BigDuckEntity extends TamableAnimal implements PlayerRideableJumping, Saddleable {
   private static final EntityDataAccessor<Boolean> IS_SADDLED;
   private static final String SADDLED_TAG = "isSaddled";
   private static final double TAME_CHANCE = 0.2;
   private static final Item[] SADDLES;
   private static final Item[] FOOD;
   private static final int QUACK_ANIM_CYCLE = 20;
   private static final byte QUACK_EVENT = 100;
   private float playerJumpPendingScale;
   private boolean isJumping;
   private double floating;
   private int clientQuackTick = 0;

   public BigDuckEntity(EntityType<? extends BigDuckEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22266_((Attribute)ModAttributes.FLOATING_TIME.get()).m_22266_(Attributes.f_22288_).m_22268_(Attributes.f_22281_, (double)1.0F).m_22268_(Attributes.f_22277_, (double)40.0F).m_22268_(Attributes.f_22279_, 0.27).m_22268_(Attributes.f_22276_, (double)25.0F);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new BreedGoal(this, (double)1.0F));
      this.f_21345_.m_25352_(3, new TemptGoal(this, 1.2, Ingredient.m_43929_(new ItemLike[]{Items.f_42780_}), false));
      this.f_21345_.m_25352_(3, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(3, new FollowParentGoal(this, 1.15));
      this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, (double)1.0F, false));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 0.6));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(IS_SADDLED, false);
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128379_("isSaddled", (Boolean)this.f_19804_.m_135370_(IS_SADDLED));
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.f_19804_.m_135381_(IS_SADDLED, nbt.m_128471_("isSaddled"));
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.BIG_DUCK_QUACK.get();
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return (SoundEvent)ModSounds.BIG_DUCK_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.BIG_DUCK_HURT.get();
   }

   protected void m_7355_(BlockPos pos, BlockState block) {
      this.m_5496_(SoundEvents.f_11754_, 0.2F, 0.5F);
   }

   protected float m_6121_() {
      return 0.8F;
   }

   public float m_6100_() {
      return this.m_6162_() ? 1.5F + this.f_19796_.m_188501_() / 3.0F : 1.0F + this.f_19796_.m_188501_() / 3.0F;
   }

   public void m_8032_() {
      this.m_9236_().m_7605_(this, (byte)100);
      super.m_8032_();
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_9236_() != null && this.m_9236_().f_46443_ && this.clientQuackTick > 0) {
         --this.clientQuackTick;
      }

      Vec3 vector3d = this.m_20184_();
      if (!this.m_20096_() && this.floating > (double)0.0F) {
         if (vector3d.f_82480_ < (double)0.0F) {
            AbilityHelper.setDeltaMovement(this, vector3d.m_82542_((double)1.0F, 0.6, (double)1.0F));
         }

         this.f_19789_ = 0.0F;
         --this.floating;
      } else if (this.m_20096_() && !this.isJumping()) {
         this.floating = this.m_21133_((Attribute)ModAttributes.FLOATING_TIME.get());
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 100:
            this.clientQuackTick = 20;
         default:
            super.m_7822_(id);
      }
   }

   public boolean m_142535_(float distance, float damage, DamageSource damageSource) {
      boolean dealDamage = damage > 0.0F && this.floating <= (double)0.0F;
      this.floating = this.m_21133_((Attribute)ModAttributes.FLOATING_TIME.get());
      return dealDamage ? super.m_142535_(distance, damage, damageSource) : false;
   }

   protected void m_6475_(DamageSource source, float amount) {
      this.m_9236_().m_7605_(this, (byte)100);
      super.m_6475_(source, amount);
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      Level level = player.m_9236_();
      if (level != null && !level.f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(player.m_7655_() != null ? player.m_7655_() : InteractionHand.MAIN_HAND);
         Stream var10000 = Arrays.stream(SADDLES);
         Item var10001 = stack.m_41720_();
         Objects.requireNonNull(var10001);
         boolean isSaddle = var10000.anyMatch(var10001::equals);
         if (!this.m_21824_()) {
            if (!stack.m_41619_() && this.m_6898_(stack)) {
               this.m_142075_(player, hand, stack);
               double chance = this.f_19796_.m_188500_();
               if (chance < 0.2 && !ForgeEventFactory.onAnimalTame(this, player)) {
                  level.m_7605_(this, (byte)7);
                  this.m_21828_(player);
               }

               return InteractionResult.SUCCESS;
            }
         } else {
            if (this.m_6898_(stack) && this.m_21223_() < this.m_21233_()) {
               this.m_142075_(player, hand, stack);
               this.m_5634_(4.0F);
               level.m_7605_(this, (byte)7);
               return InteractionResult.SUCCESS;
            }

            if (isSaddle && !this.m_6254_() && !this.m_6162_()) {
               this.setSaddled(true);
               level.m_6269_((Player)null, this, SoundEvents.f_12034_, SoundSource.PLAYERS, 0.5F, 1.0F);
               this.m_142075_(player, hand, stack);
               return InteractionResult.SUCCESS;
            }

            if ((!this.m_6898_(stack) || this.m_6898_(stack) && !this.m_5957_()) && player == this.m_269323_() && !this.m_6162_()) {
               player.m_20329_(this);
               return InteractionResult.SUCCESS;
            }
         }

         return super.m_6071_(player, hand);
      } else {
         return super.m_6071_(player, hand);
      }
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
      if (pSpawnData == null) {
         pSpawnData = new AgeableMob.AgeableMobGroupData(0.2F);
      }

      this.m_21051_(Attributes.f_22288_).m_22100_(this.generateRandomJumpStrength());
      this.m_21051_(Attributes.f_22279_).m_22100_(this.generateRandomSpeed());
      this.m_21051_(Attributes.f_22276_).m_22100_(this.generateRandomHealth());
      double floatingTime = this.generateRandomFloatingTime();
      this.m_21051_((Attribute)ModAttributes.FLOATING_TIME.get()).m_22100_(floatingTime);
      this.floating = floatingTime;
      this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
      return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob mate) {
      BigDuckEntity duck = (BigDuckEntity)((EntityType)ModMobs.BIG_DUCK.get()).m_20615_(world);
      this.setOffspringAttributes(mate, duck);
      return duck;
   }

   protected void setOffspringAttributes(AgeableMob mate, BigDuckEntity offspring) {
      double hp = this.m_21172_(Attributes.f_22276_) + mate.m_21172_(Attributes.f_22276_) + this.generateRandomHealth();
      offspring.m_21051_(Attributes.f_22276_).m_22100_(hp / (double)3.0F);
      double jump = this.m_21172_(Attributes.f_22288_) + mate.m_21172_(Attributes.f_22288_) + this.generateRandomJumpStrength();
      offspring.m_21051_(Attributes.f_22288_).m_22100_(jump / (double)3.0F);
      double speed = this.m_21172_(Attributes.f_22279_) + mate.m_21172_(Attributes.f_22279_) + this.generateRandomSpeed();
      offspring.m_21051_(Attributes.f_22279_).m_22100_(speed / (double)3.0F);
      double floating = this.m_21172_((Attribute)ModAttributes.FLOATING_TIME.get()) + mate.m_21172_((Attribute)ModAttributes.FLOATING_TIME.get()) + this.generateRandomFloatingTime();
      offspring.m_21051_((Attribute)ModAttributes.FLOATING_TIME.get()).m_22100_(floating / (double)3.0F);
   }

   protected double generateRandomJumpStrength() {
      return 0.6 + this.f_19796_.m_188500_() * 0.4;
   }

   protected double generateRandomSpeed() {
      return 0.28 + this.f_19796_.m_188500_() * 0.08;
   }

   protected double generateRandomHealth() {
      return (double)20.0F + this.f_19796_.m_188500_() * (double)10.0F;
   }

   protected double generateRandomFloatingTime() {
      return (double)50.0F + WyHelper.randomDouble(this.f_19796_) * (double)0.5F * (double)30.0F;
   }

   public void m_7023_(Vec3 pTravelVector) {
      if (this.m_6084_()) {
         if (this.m_20160_() && this.m_6254_()) {
            LivingEntity livingentity = this.m_6688_();
            this.m_146922_(livingentity.m_146908_());
            this.f_19859_ = this.m_146908_();
            this.m_146926_(livingentity.m_146909_() * 0.5F);
            this.m_19915_(this.m_146908_(), this.m_146909_());
            this.f_20883_ = this.m_146908_();
            this.f_20885_ = this.f_20883_;
            float f = livingentity.f_20900_ * 0.5F;
            float f1 = livingentity.f_20902_;
            if (f1 <= 0.0F) {
               f1 *= 0.25F;
            }

            if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.m_20096_()) {
               double d0 = this.m_21133_(Attributes.f_22288_) * (double)this.playerJumpPendingScale * (double)this.m_20098_();
               double d1;
               if (this.m_21023_(MobEffects.f_19603_)) {
                  d1 = d0 + (double)((float)(this.m_21124_(MobEffects.f_19603_).m_19564_() + 1) * 0.1F);
               } else {
                  d1 = d0;
               }

               Vec3 vector3d = this.m_20184_();
               AbilityHelper.setDeltaMovement(this, vector3d.f_82479_, d1, vector3d.f_82481_);
               this.setIsJumping(true);
               this.f_19812_ = true;
               ForgeHooks.onLivingJump(this);
               if (f1 > 0.0F) {
                  float f2 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F));
                  float f3 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F));
                  AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82520_((double)(-0.4F * f2 * this.playerJumpPendingScale), (double)0.0F, (double)(0.4F * f3 * this.playerJumpPendingScale)));
               }

               this.playerJumpPendingScale = 0.0F;
            }

            if (this.m_6109_()) {
               this.m_7910_((float)this.m_21133_(Attributes.f_22279_));
               super.m_7023_(new Vec3((double)f, pTravelVector.f_82480_, (double)f1));
            } else if (livingentity instanceof Player) {
               AbilityHelper.setDeltaMovement(this, Vec3.f_82478_);
            }

            if (this.m_20096_()) {
               this.playerJumpPendingScale = 0.0F;
               this.setIsJumping(false);
            }

            this.m_267651_(false);
         } else {
            super.m_7023_(pTravelVector);
         }
      }

   }

   public boolean m_6898_(ItemStack stack) {
      boolean var2;
      if (!stack.m_41619_()) {
         Stream var10000 = Arrays.stream(FOOD);
         Item var10001 = stack.m_41720_();
         Objects.requireNonNull(var10001);
         if (var10000.anyMatch(var10001::equals)) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   protected boolean m_6107_() {
      return super.m_6107_() && this.m_20160_() && this.m_6254_();
   }

   @Nullable
   public LivingEntity m_6688_() {
      if (this.m_6254_()) {
         Entity var2 = this.m_146895_();
         if (var2 instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)var2;
            return living;
         }
      }

      return null;
   }

   public double m_6048_() {
      return 1.2;
   }

   public void setSaddled(boolean flag) {
      this.f_19804_.m_135381_(IS_SADDLED, flag);
   }

   public boolean m_6254_() {
      return (Boolean)this.f_19804_.m_135370_(IS_SADDLED);
   }

   public void m_7888_(int jumpPower) {
      if (this.m_6254_()) {
         if (jumpPower < 0) {
            jumpPower = 0;
         }

         if (jumpPower >= 90) {
            this.playerJumpPendingScale = 1.0F;
         } else {
            this.playerJumpPendingScale = 0.4F + 0.4F * (float)jumpPower / 90.0F;
         }
      }

   }

   public void setIsJumping(boolean isJumping) {
      this.isJumping = isJumping;
   }

   public boolean isJumping() {
      return this.isJumping;
   }

   public boolean m_7132_() {
      return this.m_6254_();
   }

   public void m_7199_(int power) {
   }

   public void m_8012_() {
   }

   @OnlyIn(Dist.CLIENT)
   public float getQuackAnimationProgress(float partialTicks) {
      return ((float)(20 - this.clientQuackTick) + partialTicks) / 20.0F;
   }

   public boolean m_6741_() {
      return this.m_6084_() && !this.m_6162_() && this.m_21824_();
   }

   public void m_5853_(SoundSource source) {
   }

   static {
      IS_SADDLED = SynchedEntityData.m_135353_(BigDuckEntity.class, EntityDataSerializers.f_135035_);
      SADDLES = new Item[]{Items.f_42450_};
      FOOD = new Item[]{Items.f_42780_, Items.f_42733_, Items.f_42578_, Items.f_42577_, Items.f_42404_};
   }
}
