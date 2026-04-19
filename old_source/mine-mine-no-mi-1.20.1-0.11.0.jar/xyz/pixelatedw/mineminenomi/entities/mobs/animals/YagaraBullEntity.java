package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class YagaraBullEntity extends TamableAnimal implements IEntityAdditionalSpawnData {
   private static final EntityDataAccessor<Boolean> IS_SADDLED;
   private static final double TAME_CHANCE = 0.35;
   private static final Item[] SADDLES;
   private static final Item[] TAMING_FOOD;
   private static final Item[] FOOD;
   private Status status;
   private Status oldStatus;
   private double waterLevel;
   private int textureId;

   public YagaraBullEntity(EntityType<? extends YagaraBullEntity> type, Level world) {
      super(type, world);
      this.m_21573_().m_7008_(true);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new BreedGoal(this, (double)1.0F));
      this.f_21345_.m_25352_(1, new RandomStrollGoal(this, (double)1.0F));
      this.f_21345_.m_25352_(1, new RandomSwimmingGoal(this, (double)1.5F, 20));
      this.f_21345_.m_25352_(1, new PanicGoal(this, (double)1.25F));
      this.f_21345_.m_25352_(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(2, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(3, new TemptGoal(this, (double)1.25F, Ingredient.m_43929_(new ItemLike[]{Items.f_42528_}), true));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(IS_SADDLED, false);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, (double)0.2F).m_22268_(Attributes.f_22277_, (double)40.0F).m_22268_(Attributes.f_22276_, (double)30.0F);
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public boolean m_6063_() {
      return false;
   }

   public void m_8119_() {
      this.oldStatus = this.status;
      this.status = this.getStatus();
      super.m_8119_();
      if (this.m_6109_()) {
         double d1 = this.m_20068_() ? (double)0.0F : (double)-0.005F;
         double d2 = (double)0.0F;
         float invFriction = 0.05F;
         if (this.status == YagaraBullEntity.Status.IN_WATER) {
            d2 = (this.waterLevel - this.m_20186_()) / (double)this.m_20206_();
            invFriction = (float)this.m_21133_((Attribute)ForgeMod.SWIM_SPEED.get());
         } else if (this.status == YagaraBullEntity.Status.UNDER_WATER) {
            d2 = (double)0.01F;
            invFriction = 0.45F;
         } else if (this.status == YagaraBullEntity.Status.IN_AIR) {
            invFriction = 0.9F;
         }

         Vec3 Vec3 = this.m_20184_();
         AbilityHelper.setDeltaMovement(this, Vec3.f_82479_ * (double)invFriction, Vec3.f_82480_ + d1, Vec3.f_82481_ * (double)invFriction);
         if (d2 > (double)0.0F) {
            Vec3 Vec31 = this.m_20184_();
            AbilityHelper.setDeltaMovement(this, Vec31.f_82479_, (Vec31.f_82480_ + d2 * 0.06153846016296973) * (double)0.75F, Vec31.f_82481_);
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
      }

   }

   public void m_19956_(Entity passenger, Entity.MoveFunction func) {
      if (this.m_20363_(passenger)) {
         double y = this.m_20186_() + this.m_6048_() + passenger.m_6049_() + (double)0.25F;
         Vec3 posVec = (new Vec3(0.3, (double)0.0F, (double)0.0F)).m_82524_(-this.m_146908_() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
         passenger.m_6034_(this.m_20185_() + posVec.f_82479_, y, this.m_20189_() + posVec.f_82481_);
      }

   }

   protected void m_20101_() {
      AABB axisalignedbb = this.m_20191_();
      BlockPos blockpos = BlockPos.m_274561_(axisalignedbb.f_82288_ + 0.001, axisalignedbb.f_82289_ + 0.001, axisalignedbb.f_82290_ + 0.001);
      BlockPos blockpos1 = BlockPos.m_274561_(axisalignedbb.f_82291_ - 0.001, axisalignedbb.f_82292_ - 0.001, axisalignedbb.f_82293_ - 0.001);
      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
      if (this.m_9236_().m_46832_(blockpos, blockpos1)) {
         for(int i = blockpos.m_123341_(); i <= blockpos1.m_123341_(); ++i) {
            for(int j = blockpos.m_123342_(); j <= blockpos1.m_123342_(); ++j) {
               for(int k = blockpos.m_123343_(); k <= blockpos1.m_123343_(); ++k) {
                  pos.m_122178_(i, j, k);
                  BlockState state = this.m_9236_().m_8055_(pos);
                  if (state.m_60734_() == Blocks.f_50196_) {
                     this.m_9236_().m_46953_(pos, true, this);
                  }
               }
            }
         }
      }

   }

   public void m_7023_(Vec3 pTravelVector) {
      if (this.m_6084_()) {
         if (this.m_20160_() && this.isSaddled()) {
            LivingEntity livingentity = this.m_6688_();
            this.m_146922_(livingentity.m_146908_());
            this.f_19859_ = this.m_146908_();
            this.m_146926_(livingentity.m_146909_() * 0.5F);
            this.m_19915_(this.m_146908_(), this.m_146909_());
            this.f_20883_ = this.m_146908_();
            this.f_20885_ = this.f_20883_;
            float f = livingentity.f_20900_ * 0.3F;
            float f1 = livingentity.f_20902_;
            if (f1 <= 0.0F) {
               f1 *= 0.25F;
            }

            if (this.m_6109_()) {
               this.m_7910_((float)this.m_21133_(Attributes.f_22279_));
               super.m_7023_(new Vec3((double)f, pTravelVector.f_82480_, (double)f1));
            } else if (livingentity instanceof Player) {
               AbilityHelper.setDeltaMovement(this, Vec3.f_82478_);
            }

            this.m_267651_(false);
         } else {
            super.m_7023_(pTravelVector);
         }
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
      if (spawnData == null) {
         spawnData = new AgeableMob.AgeableMobGroupData(0.2F);
      }

      this.textureId = this.f_19796_.m_188503_(MobsHelper.YAGARA_BULL_TEXTURES.length);
      this.m_21051_(Attributes.f_22276_).m_22100_(this.generateRandomHealth());
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_(this.generateRandomSwimSpeed());
      this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
      return super.m_6518_(world, difficulty, reason, spawnData, dataTag);
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob mate) {
      YagaraBullEntity offspring = (YagaraBullEntity)((EntityType)ModMobs.YAGARA_BULL.get()).m_20615_(world);
      this.setOffspringAttributes(mate, offspring);
      return offspring;
   }

   protected void setOffspringAttributes(AgeableMob mate, YagaraBullEntity offspring) {
      double hp = this.m_21172_(Attributes.f_22276_) + mate.m_21172_(Attributes.f_22276_) + this.generateRandomHealth();
      offspring.m_21051_(Attributes.f_22276_).m_22100_(hp / (double)3.0F);
      double swimSpeed = this.m_21172_((Attribute)ForgeMod.SWIM_SPEED.get()) + mate.m_21172_((Attribute)ForgeMod.SWIM_SPEED.get()) + this.generateRandomSwimSpeed();
      offspring.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_(swimSpeed / (double)3.0F);
   }

   protected double generateRandomHealth() {
      return (double)30.0F + this.f_19796_.m_188500_() * (double)10.0F;
   }

   protected double generateRandomSwimSpeed() {
      return 1.1 + this.f_19796_.m_188500_() * 0.15;
   }

   protected boolean m_6107_() {
      return super.m_6107_() && this.m_20160_() && this.isSaddled();
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!player.m_9236_().f_46443_ && hand == InteractionHand.MAIN_HAND) {
         if (this.m_21824_() && player == this.m_269323_()) {
            ItemStack stack = player.m_21205_();
            if (!stack.m_41619_()) {
               Stream var10 = Arrays.stream(FOOD);
               Item var12 = stack.m_41720_();
               Objects.requireNonNull(var12);
               boolean isFood = var10.anyMatch(var12::equals);
               var10 = Arrays.stream(SADDLES);
               var12 = stack.m_41720_();
               Objects.requireNonNull(var12);
               boolean isSaddle = var10.anyMatch(var12::equals);
               if (isFood && this.m_21223_() < this.m_21233_()) {
                  stack.m_41774_(1);
                  this.m_5634_(4.0F);
                  this.spawnHeartParticles();
                  return InteractionResult.SUCCESS;
               }

               if (isSaddle && !this.isSaddled()) {
                  this.setSaddled(true);
                  this.m_9236_().m_5594_((Player)null, this.m_20183_(), SoundEvents.f_12034_, SoundSource.PLAYERS, 0.5F, 1.0F);
                  stack.m_41774_(1);
               }
            } else {
               player.m_20329_(this);
            }
         } else if (!this.m_21824_()) {
            ItemStack stack = player.m_21205_();
            if (!stack.m_41619_()) {
               Stream var10000 = Arrays.stream(TAMING_FOOD);
               Item var10001 = stack.m_41720_();
               Objects.requireNonNull(var10001);
               boolean isFood = var10000.anyMatch(var10001::equals);
               if (isFood) {
                  stack.m_41774_(1);
                  double chance = this.f_19796_.m_188500_();
                  if (chance < 0.35 && !ForgeEventFactory.onAnimalTame(this, player)) {
                     this.spawnHeartParticles();
                     this.m_21828_(player);
                  }

                  return InteractionResult.FAIL;
               }
            }
         }

         return InteractionResult.FAIL;
      } else {
         return InteractionResult.FAIL;
      }
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128405_("Texture", this.getTextureId());
      nbt.m_128379_("IsSaddled", (Boolean)this.f_19804_.m_135370_(IS_SADDLED));
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.textureId = nbt.m_128451_("Texture");
      this.f_19804_.m_135381_(IS_SADDLED, nbt.m_128471_("IsSaddled"));
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.textureId);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.textureId = buffer.readInt();
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

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6094_() {
      return true;
   }

   public double m_6048_() {
      return 0.6;
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6785_(double distance) {
      return !this.m_21824_() && distance > (double)1024.0F;
   }

   public void spawnHeartParticles() {
      for(int i = 0; i < 5; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123750_, this.m_20185_() + offsetX, this.m_20186_() + (double)1.0F + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public void setSaddled(boolean flag) {
      this.f_19804_.m_135381_(IS_SADDLED, flag);
   }

   public boolean isSaddled() {
      return (Boolean)this.f_19804_.m_135370_(IS_SADDLED);
   }

   public int getTextureId() {
      return this.textureId;
   }

   @Nullable
   private Status isUnderwater() {
      AABB axisalignedbb = this.m_20191_();
      double d0 = axisalignedbb.f_82292_ + 0.002;
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82289_);
      int l = Mth.m_14165_(d0);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      boolean flag = false;
      BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutable.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.m_9236_().m_6425_(blockpos$mutable);
               if (fluidstate.m_205070_(FluidTags.f_13131_) && d0 < (double)((float)blockpos$mutable.m_123342_() + fluidstate.m_76155_(this.m_9236_(), blockpos$mutable))) {
                  if (!fluidstate.m_76170_()) {
                     return YagaraBullEntity.Status.UNDER_FLOWING_WATER;
                  }

                  flag = true;
               }
            }
         }
      }

      return flag ? YagaraBullEntity.Status.UNDER_WATER : null;
   }

   private boolean checkInWater() {
      AABB axisalignedbb = this.m_20191_();
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82289_);
      int l = Mth.m_14165_(axisalignedbb.f_82289_ + 0.001);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      boolean flag = false;
      this.waterLevel = Double.MIN_VALUE;
      BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutable.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.m_9236_().m_6425_(blockpos$mutable);
               if (fluidstate.m_205070_(FluidTags.f_13131_)) {
                  float f = (float)l1 + fluidstate.m_76155_(this.m_9236_(), blockpos$mutable);
                  this.waterLevel = Math.max((double)f, this.waterLevel);
                  flag |= axisalignedbb.f_82289_ < (double)f;
               }
            }
         }
      }

      return flag;
   }

   public float getGroundFriction() {
      AABB axisalignedbb = this.m_20191_();
      AABB axisalignedbb1 = new AABB(axisalignedbb.f_82288_, axisalignedbb.f_82289_ - 0.001, axisalignedbb.f_82290_, axisalignedbb.f_82291_, axisalignedbb.f_82289_, axisalignedbb.f_82293_);
      int i = Mth.m_14107_(axisalignedbb1.f_82288_) - 1;
      int j = Mth.m_14165_(axisalignedbb1.f_82291_) + 1;
      int k = Mth.m_14107_(axisalignedbb1.f_82289_) - 1;
      int l = Mth.m_14165_(axisalignedbb1.f_82292_) + 1;
      int i1 = Mth.m_14107_(axisalignedbb1.f_82290_) - 1;
      int j1 = Mth.m_14165_(axisalignedbb1.f_82293_) + 1;
      VoxelShape voxelshape = Shapes.m_83064_(axisalignedbb1);
      float f = 0.0F;
      int k1 = 0;
      BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

      for(int l1 = i; l1 < j; ++l1) {
         for(int i2 = i1; i2 < j1; ++i2) {
            int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
            if (j2 != 2) {
               for(int k2 = k; k2 < l; ++k2) {
                  if (j2 <= 0 || k2 != k && k2 != l - 1) {
                     blockpos$mutable.m_122178_(l1, k2, i2);
                     BlockState blockstate = this.m_9236_().m_8055_(blockpos$mutable);
                     if (!(blockstate.m_60734_() instanceof WaterlilyBlock) && Shapes.m_83157_(blockstate.m_60812_(this.m_9236_(), blockpos$mutable).m_83216_((double)l1, (double)k2, (double)i2), voxelshape, BooleanOp.f_82689_)) {
                        f += blockstate.getFriction(this.m_9236_(), blockpos$mutable, this);
                        ++k1;
                     }
                  }
               }
            }
         }
      }

      return f / (float)k1;
   }

   private Status getStatus() {
      Status status = this.isUnderwater();
      if (status != null) {
         this.waterLevel = this.m_20191_().f_82292_;
         return status;
      } else if (this.checkInWater()) {
         return YagaraBullEntity.Status.IN_WATER;
      } else {
         float f = this.getGroundFriction();
         return f > 0.0F ? YagaraBullEntity.Status.ON_LAND : YagaraBullEntity.Status.IN_AIR;
      }
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public static boolean checkSpawnRules(EntityType<YagaraBullEntity> entity, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      return world.m_8055_(pos.m_7495_()).m_60819_().m_205070_(FluidTags.f_13131_);
   }

   static {
      IS_SADDLED = SynchedEntityData.m_135353_(YagaraBullEntity.class, EntityDataSerializers.f_135035_);
      SADDLES = new Item[]{Items.f_42450_};
      TAMING_FOOD = new Item[]{Items.f_42528_, Items.f_41867_, Items.f_41868_};
      FOOD = new Item[]{Items.f_42530_, Items.f_42531_, Items.f_42526_, Items.f_42527_, Items.f_42529_, Items.f_42528_};
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;

      // $FF: synthetic method
      private static Status[] $values() {
         return new Status[]{IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER, ON_LAND, IN_AIR};
      }
   }
}
