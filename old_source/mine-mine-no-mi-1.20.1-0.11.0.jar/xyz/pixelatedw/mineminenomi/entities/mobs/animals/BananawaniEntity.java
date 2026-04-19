package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.PersonalSpaceTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class BananawaniEntity extends TamableAnimal {
   private static final EntityDataAccessor<Boolean> IS_SADDLED;
   private static final EntityDataAccessor<Integer> SADDLE_COLOR;
   private static final String SADDLED_TAG = "isSaddled";
   private static final String SADDLE_COLOR_TAG = "saddleColor";
   private static final double TAME_CHANCE = (double)0.25F;
   private static final FoodProperties[] FISH_FOODS;
   private static final Item[] SADDLES;
   private static final byte BITE_EVENT = 100;
   private long lastMountTime;
   private long lastBiteTime;
   private int clientBiteTime;

   public BananawaniEntity(EntityType<? extends BananawaniEntity> type, Level world) {
      super(type, world);
      this.m_21573_().m_7008_(true);
      if (world != null && !world.f_46443_) {
         this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)40.0F);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new BreedGoal(this, (double)1.0F));
      this.f_21345_.m_25352_(3, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(3, new FollowParentGoal(this, 1.15));
      this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, (double)1.0F, false));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Chicken.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(1, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(1, new PersonalSpaceTargetGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Chicken.class, true));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(IS_SADDLED, false);
      this.m_20088_().m_135372_(SADDLE_COLOR, DyeColor.RED.m_41060_());
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128379_("isSaddled", (Boolean)this.f_19804_.m_135370_(IS_SADDLED));
      nbt.m_128405_("saddleColor", (Integer)this.f_19804_.m_135370_(SADDLE_COLOR));
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.f_19804_.m_135381_(IS_SADDLED, nbt.m_128471_("isSaddled"));
      this.f_19804_.m_135381_(SADDLE_COLOR, nbt.m_128451_("saddleColor"));
   }

   protected void m_7355_(BlockPos pos, BlockState block) {
      this.m_5496_(SoundEvents.f_12237_, 0.15F, 1.5F);
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public float m_6100_() {
      return this.m_6162_() ? 1.5F + this.f_19796_.m_188501_() / 2.0F : 1.25F + this.f_19796_.m_188501_() / 2.0F;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_6109_() && this.m_20069_()) {
         double waterHeight = this.m_20186_() - this.getFluidTypeHeight(Fluids.f_76193_.getFluidType());
         double upforce = (this.m_20186_() - waterHeight) / (double)this.m_20206_() - 0.35;
         Vec3 lookVec = this.m_20154_().m_82542_((double)2.0F, (double)1.0F, (double)2.0F);
         BlockPos frontPos = this.m_20183_().m_7918_((int)lookVec.f_82479_, 0, (int)lookVec.f_82481_);
         BlockState state = this.m_9236_().m_8055_(frontPos);
         if (!state.m_60795_() && state.m_60819_().m_76178_()) {
            upforce += 0.35;
         }

         if (upforce > (double)0.0F) {
            double swimSpeed = this.m_21133_((Attribute)ForgeMod.SWIM_SPEED.get());
            Vec3 movement = this.m_20184_();
            AbilityHelper.setDeltaMovement(this, movement.f_82479_ * swimSpeed, (movement.f_82480_ + upforce * 0.061) * (double)0.75F, movement.f_82481_ * swimSpeed);
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
      }

   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      Level level = super.m_9236_();
      if (level == null) {
         return super.m_6071_(player, hand);
      } else {
         if (!level.f_46443_ && hand == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.m_21120_(player.m_7655_() != null ? player.m_7655_() : InteractionHand.MAIN_HAND);
            Stream var10000 = Arrays.stream(SADDLES);
            Item var10001 = stack.m_41720_();
            Objects.requireNonNull(var10001);
            boolean isSaddle = var10000.anyMatch(var10001::equals);
            boolean isDye = !stack.m_41619_() && stack.m_41720_() instanceof DyeItem;
            if (!this.m_21824_()) {
               if (!stack.m_41619_() && this.m_6898_(stack)) {
                  this.m_142075_(player, hand, stack);
                  double chance = this.f_19796_.m_188500_();
                  if (chance < (double)0.25F && !ForgeEventFactory.onAnimalTame(this, player)) {
                     level.m_7605_(this, (byte)7);
                     this.m_21828_(player);
                  }

                  return InteractionResult.SUCCESS;
               }
            } else if (isDye) {
               DyeColor dyecolor = ((DyeItem)stack.m_41720_()).m_41089_();
               if (dyecolor != this.getSaddleColor()) {
                  this.setSaddleColor(dyecolor);
                  this.m_142075_(player, hand, stack);
                  return InteractionResult.SUCCESS;
               }
            } else {
               if (this.m_6898_(stack) && this.m_21223_() < this.m_21233_()) {
                  this.m_5634_(4.0F);
                  this.m_142075_(player, hand, stack);
                  level.m_7605_(this, (byte)7);
                  return InteractionResult.SUCCESS;
               }

               if (isSaddle && !this.isSaddled() && !this.m_6162_()) {
                  this.setSaddled(true);
                  level.m_6269_((Player)null, this, SoundEvents.f_12034_, SoundSource.PLAYERS, 0.5F, 1.0F);
                  this.m_142075_(player, hand, stack);
                  return InteractionResult.SUCCESS;
               }

               if ((!this.m_6898_(stack) || this.m_6898_(stack) && !this.m_5957_()) && player == this.m_269323_() && !this.m_6162_()) {
                  player.m_20329_(this);
                  this.lastMountTime = level.m_46467_();
                  return InteractionResult.SUCCESS;
               }
            }
         }

         return super.m_6071_(player, hand);
      }
   }

   public void m_7023_(Vec3 travelVector) {
      if (this.m_6084_()) {
         if (this.m_20160_() && this.isSaddled()) {
            LivingEntity livingentity = this.m_6688_();
            this.m_146922_(livingentity.m_146908_());
            this.f_19859_ = this.m_146908_();
            this.m_146926_(livingentity.m_146909_() * 0.5F);
            this.m_19915_(this.m_146908_(), this.m_146909_());
            this.f_20883_ = this.m_146908_();
            this.f_20885_ = this.f_20883_;
            float f = livingentity.f_20900_ * 0.1F;
            float f1 = livingentity.f_20902_;
            if (f1 <= 0.0F) {
               f1 *= 0.25F;
            }

            if (this.m_6109_()) {
               this.m_7910_((float)this.m_21133_(Attributes.f_22279_) * 0.7F);
               super.m_7023_(new Vec3((double)f, travelVector.f_82480_, (double)f1));
            } else if (livingentity instanceof Player) {
               AbilityHelper.setDeltaMovement(this, Vec3.f_82478_);
            }

            this.m_267651_(false);
         } else {
            super.m_7023_(travelVector);
         }
      }

   }

   public void m_8107_() {
      super.m_8107_();
      Level level = super.m_9236_();
      if (level != null) {
         if (this.clientBiteTime > 0) {
            --this.clientBiteTime;
         }

         if (!level.f_46443_ && this.m_21824_() && this.m_20160_() && this.m_20197_().contains(this.m_269323_()) && this.m_269323_() != null && this.m_269323_().f_20911_ && level.m_46467_() >= this.lastMountTime + 20L && level.m_46467_() >= this.lastBiteTime + 20L) {
            this.m_6674_(InteractionHand.MAIN_HAND);
            Vec3 look = this.m_20182_().m_82549_(this.m_269323_().m_20154_().m_82542_((double)4.0F, (double)1.0F, (double)4.0F));
            Vec3 ogPos = new Vec3(look.m_7096_(), this.m_20186_(), look.m_7094_());
            List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(ogPos, level, (double)1.0F, ModEntityPredicates.getEnemyFactions(this.m_269323_()));
            targets.remove(this);
            targets.remove(this.m_269323_());
            this.lastBiteTime = level.m_46467_();
            level.m_7605_(this, (byte)100);

            for(Entity e : targets) {
               e.m_6469_(this.m_269291_().m_269333_(this), (float)this.m_21133_(Attributes.f_22281_));
            }
         }

      }
   }

   protected void m_20101_() {
      Level level = super.m_9236_();
      if (level != null) {
         AABB axisalignedbb = this.m_20191_();
         BlockPos blockpos = BlockPos.m_274561_(axisalignedbb.f_82288_ + 0.001, axisalignedbb.f_82289_ + 0.001, axisalignedbb.f_82290_ + 0.001);
         BlockPos blockpos1 = BlockPos.m_274561_(axisalignedbb.f_82291_ - 0.001, axisalignedbb.f_82292_ - 0.001, axisalignedbb.f_82293_ - 0.001);
         BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
         if (level.m_46832_(blockpos, blockpos1)) {
            for(int i = blockpos.m_123341_(); i <= blockpos1.m_123341_(); ++i) {
               for(int j = blockpos.m_123342_(); j <= blockpos1.m_123342_(); ++j) {
                  for(int k = blockpos.m_123343_(); k <= blockpos1.m_123343_(); ++k) {
                     pos.m_122178_(i, j, k);
                     BlockState state = level.m_8055_(pos);
                     if (state.m_60734_() == Blocks.f_50196_) {
                        level.m_46953_(pos, true, this);
                     }
                  }
               }
            }
         }

      }
   }

   public void m_21011_(InteractionHand hand, boolean updateSelf) {
      if (super.m_9236_() != null && this.m_5448_() != null) {
         super.m_9236_().m_7605_(this, (byte)100);
      }

      super.m_21011_(hand, updateSelf);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
      if (pSpawnData == null) {
         pSpawnData = new AgeableMob.AgeableMobGroupData(0.2F);
      }

      this.m_21051_(Attributes.f_22276_).m_22100_(this.generateRandomHealth());
      this.m_21051_(Attributes.f_22284_).m_22100_(this.generateRandomArmor());
      this.m_21051_(Attributes.f_22281_).m_22100_(this.generateRandomAttack());
      this.m_21051_(Attributes.f_22279_).m_22100_(this.generateRandomSpeed());
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_(this.generateRandomSwimSpeed());
      this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
      return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob mate) {
      BananawaniEntity offspring = (BananawaniEntity)((EntityType)ModMobs.BANANAWANI.get()).m_20615_(world);
      this.setOffspringAttributes(mate, offspring);
      return offspring;
   }

   protected void setOffspringAttributes(AgeableMob mate, BananawaniEntity offspring) {
      double hp = this.m_21172_(Attributes.f_22276_) + mate.m_21172_(Attributes.f_22276_) + this.generateRandomHealth();
      offspring.m_21051_(Attributes.f_22276_).m_22100_(hp / (double)3.0F);
      double armor = this.m_21172_(Attributes.f_22284_) + mate.m_21172_(Attributes.f_22284_) + this.generateRandomArmor();
      offspring.m_21051_(Attributes.f_22284_).m_22100_(armor / (double)3.0F);
      double speed = this.m_21172_(Attributes.f_22279_) + mate.m_21172_(Attributes.f_22279_) + this.generateRandomSpeed();
      offspring.m_21051_(Attributes.f_22279_).m_22100_(speed / (double)3.0F);
      double damage = this.m_21172_(Attributes.f_22281_) + mate.m_21172_(Attributes.f_22281_) + this.generateRandomAttack();
      offspring.m_21051_(Attributes.f_22281_).m_22100_(damage / (double)3.0F);
      double swimSpeed = this.m_21172_((Attribute)ForgeMod.SWIM_SPEED.get()) + mate.m_21172_((Attribute)ForgeMod.SWIM_SPEED.get()) + this.generateRandomSwimSpeed();
      offspring.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_(swimSpeed / (double)3.0F);
   }

   protected double generateRandomArmor() {
      return (double)2.0F + this.f_19796_.m_188500_() * (double)2.0F;
   }

   protected double generateRandomAttack() {
      return (double)7.0F + this.f_19796_.m_188500_() * (double)2.0F;
   }

   protected double generateRandomHealth() {
      return (double)50.0F + this.f_19796_.m_188500_() * (double)10.0F;
   }

   protected double generateRandomSpeed() {
      return (double)0.25F + this.f_19796_.m_188500_() * 0.07;
   }

   protected double generateRandomSwimSpeed() {
      return (double)1.0F + this.f_19796_.m_188500_() * 0.15;
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6898_(ItemStack stack) {
      if (stack.m_41619_()) {
         return false;
      } else {
         boolean var3;
         label29: {
            FoodProperties food = stack.m_41720_().m_41473_();
            if (food != null) {
               if (food.m_38746_()) {
                  break label29;
               }

               Stream var10000 = Arrays.stream(FISH_FOODS);
               Objects.requireNonNull(food);
               if (var10000.anyMatch(food::equals)) {
                  break label29;
               }
            }

            var3 = false;
            return var3;
         }

         var3 = true;
         return var3;
      }
   }

   protected boolean m_6107_() {
      return super.m_6107_() && this.m_20160_() && this.isSaddled();
   }

   public boolean m_6063_() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 100:
            this.clientBiteTime = 20;
         default:
            super.m_7822_(id);
      }
   }

   @Nullable
   public LivingEntity m_6688_() {
      if (this.isSaddled()) {
         Entity var2 = this.m_146895_();
         if (var2 instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)var2;
            return living;
         }
      }

      return null;
   }

   public double m_6048_() {
      return 1.4;
   }

   public void setSaddled(boolean flag) {
      this.f_19804_.m_135381_(IS_SADDLED, flag);
   }

   public boolean isSaddled() {
      return (Boolean)this.f_19804_.m_135370_(IS_SADDLED);
   }

   public DyeColor getSaddleColor() {
      return DyeColor.m_41053_((Integer)this.f_19804_.m_135370_(SADDLE_COLOR));
   }

   public void setSaddleColor(DyeColor pCollarColor) {
      this.f_19804_.m_135381_(SADDLE_COLOR, pCollarColor.m_41060_());
   }

   @OnlyIn(Dist.CLIENT)
   public float getBiteAnimationProgress(float partialTicks) {
      return ((float)(20 - this.clientBiteTime) + partialTicks) / 20.0F;
   }

   static {
      IS_SADDLED = SynchedEntityData.m_135353_(BananawaniEntity.class, EntityDataSerializers.f_135035_);
      SADDLE_COLOR = SynchedEntityData.m_135353_(BananawaniEntity.class, EntityDataSerializers.f_135028_);
      FISH_FOODS = new FoodProperties[]{Foods.f_38819_, Foods.f_38805_, Foods.f_38809_, Foods.f_38822_, Foods.f_38826_, Foods.f_38800_};
      SADDLES = new Item[]{Items.f_42450_};
   }
}
