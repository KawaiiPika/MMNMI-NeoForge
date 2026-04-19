package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.FishSwimMoveController;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.LandFishLookController;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidBlockGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.AvoidCoatedBoatGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.EatNearbyFishGoal;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class FlyingFishEntity extends TamableAnimal {
   private static final EntityDataAccessor<Boolean> IS_SADDLED;
   private static final String SADDLED_TAG = "isSaddled";
   private static final double TAME_CHANCE = (double)0.25F;
   private static final Item[] SADDLES;
   private static final FoodProperties[] FOODS;

   public FlyingFishEntity(EntityType<? extends FlyingFishEntity> type, Level world) {
      super(type, world);
      this.f_21342_ = new FishSwimMoveController(this);
      this.f_21365_ = new LandFishLookController(this, 10);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.m_21573_().m_7008_(true);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new TryFindWaterGoal(this));
      this.f_21345_.m_25352_(0, new AvoidBlockGoal(this, ModTags.Blocks.KAIROSEKI));
      this.f_21345_.m_25352_(0, new AvoidCoatedBoatGoal(this));
      this.f_21345_.m_25352_(0, new EatNearbyFishGoal(this));
      this.f_21345_.m_25352_(1, new RandomSwimmingGoal(this, (double)1.0F, 10));
      this.f_21345_.m_25352_(1, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(2, new LookAtPlayerGoal(this, Player.class, 12.0F));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(IS_SADDLED, false);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, (double)1.3F).m_22268_(Attributes.f_22277_, (double)55.0F).m_22268_(Attributes.f_22281_, (double)0.0F).m_22268_(Attributes.f_22276_, (double)20.0F);
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
      return super.m_6048_();
   }

   public void m_19956_(Entity passenger, Entity.MoveFunction func) {
      if (this.m_20363_(passenger)) {
         double y = this.m_20186_() + this.m_6048_() + passenger.m_6049_();
         Vec3 posVec = (new Vec3(0.4, (double)0.0F, (double)0.0F)).m_82524_(-this.m_146908_() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
         passenger.m_6034_(this.m_20185_() + posVec.f_82479_, y, this.m_20189_() + posVec.f_82481_);
      }

   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!player.m_9236_().f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(player.m_7655_() != null ? player.m_7655_() : InteractionHand.MAIN_HAND);
         Stream var10000 = Arrays.stream(SADDLES);
         Item var10001 = stack.m_41720_();
         Objects.requireNonNull(var10001);
         boolean isSaddle = var10000.anyMatch(var10001::equals);
         if (!this.m_21824_()) {
            if (!stack.m_41619_() && this.m_6898_(stack)) {
               this.m_142075_(player, hand, stack);
               double chance = this.f_19796_.m_188500_();
               if (chance < (double)0.25F && !ForgeEventFactory.onAnimalTame(this, player)) {
                  this.m_9236_().m_7605_(this, (byte)7);
                  this.m_21828_(player);
               }

               return InteractionResult.SUCCESS;
            }
         } else {
            if (this.m_6898_(stack) && this.m_21223_() < this.m_21233_()) {
               this.m_5634_(4.0F);
               this.m_142075_(player, hand, stack);
               this.m_9236_().m_7605_(this, (byte)7);
               return InteractionResult.SUCCESS;
            }

            if (isSaddle && !this.isSaddled() && !this.m_6162_()) {
               this.setSaddled(true);
               this.m_9236_().m_6269_((Player)null, this, SoundEvents.f_12034_, SoundSource.PLAYERS, 0.5F, 1.0F);
               this.m_142075_(player, hand, stack);
               return InteractionResult.SUCCESS;
            }

            if (this.isSaddled() && (!this.m_6898_(stack) || this.m_6898_(stack) && !this.m_5957_()) && player == this.m_269323_() && !this.m_6162_()) {
               player.m_20329_(this);
               return InteractionResult.SUCCESS;
            }
         }
      }

      return super.m_6071_(player, hand);
   }

   public boolean m_6898_(ItemStack stack) {
      if (stack.m_41619_()) {
         return false;
      } else {
         FoodProperties food = stack.m_41720_().m_41473_();
         boolean var3;
         if (food != null) {
            Stream var10000 = Arrays.stream(FOODS);
            Objects.requireNonNull(food);
            if (var10000.anyMatch(food::equals)) {
               var3 = true;
               return var3;
            }
         }

         var3 = false;
         return var3;
      }
   }

   public void m_7023_(Vec3 travelVector) {
      if (this.m_6084_()) {
         if (this.m_20160_() && this.isSaddled()) {
            LivingEntity controller = this.m_6688_();
            this.m_146922_(controller.m_146908_());
            this.f_19859_ = this.m_146908_();
            this.m_146926_(controller.m_146909_() * 0.5F);
            this.m_19915_(this.m_146908_(), this.m_146909_());
            this.f_20883_ = this.m_146908_();
            this.f_20885_ = this.f_20883_;
            if (this.m_6109_()) {
               this.m_7910_((float)this.m_21133_(Attributes.f_22279_) * 0.7F);
               int isMoving = controller.f_20902_ > 0.0F ? 0 : 1;
               if (isMoving == 0) {
                  float speed = 0.5F;
                  Vec3 vec = controller.m_20154_();
                  double x = vec.f_82479_ * (double)speed;
                  double y = vec.f_82480_ * (double)speed;
                  double z = vec.f_82481_ * (double)speed;
                  AbilityHelper.setDeltaMovement(this, x, y, z);
               } else {
                  double y = (double)0.0F;
                  if (!this.m_5842_()) {
                     y = this.m_20184_().f_82480_ * 0.99;
                     y = Math.max(y, (double)-1.0F);
                  }

                  AbilityHelper.setDeltaMovement(this, this.m_20184_().f_82479_, y, this.m_20184_().f_82481_);
               }

               super.m_7023_(this.m_20184_());
            } else if (controller instanceof Player) {
               AbilityHelper.setDeltaMovement(this, Vec3.f_82478_);
            }

            this.m_267651_(false);
         } else {
            super.m_7023_(travelVector);
         }
      }

   }

   public int m_8132_() {
      return 1;
   }

   public int m_8085_() {
      return 1;
   }

   public boolean m_6040_() {
      return true;
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public boolean m_6914_(LevelReader pLevel) {
      return pLevel.m_45784_(this);
   }

   protected void handleAirSupply(int airSupply) {
      if (this.m_6084_() && !this.m_20072_()) {
         this.m_20301_(airSupply - 1);
         if (this.m_20146_() == -20) {
            this.m_20301_(0);
            this.m_6469_(this.m_269291_().m_269063_(), 2.0F);
         }
      } else {
         this.m_20301_(300);
      }

   }

   public void m_6075_() {
      int i = this.m_20146_();
      super.m_6075_();
      this.handleAirSupply(i);
   }

   public boolean m_6063_() {
      return false;
   }

   public boolean m_6573_(Player pPlayer) {
      return true;
   }

   public boolean m_6785_(double distance) {
      return !this.m_21824_() && distance > (double)1024.0F;
   }

   protected PathNavigation m_6037_(Level level) {
      return new WaterBoundPathNavigation(this, level);
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128379_("isSaddled", (Boolean)this.f_19804_.m_135370_(IS_SADDLED));
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.f_19804_.m_135381_(IS_SADDLED, nbt.m_128471_("isSaddled"));
   }

   public void setSaddled(boolean flag) {
      this.f_19804_.m_135381_(IS_SADDLED, flag);
   }

   public boolean isSaddled() {
      return (Boolean)this.f_19804_.m_135370_(IS_SADDLED);
   }

   public AgeableMob m_142606_(ServerLevel pServerLevel, AgeableMob pMate) {
      return null;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public static boolean checkSpawnRules(EntityType<FlyingFishEntity> entity, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      return world.m_8055_(pos.m_7495_()).m_60819_().m_205070_(FluidTags.f_13131_);
   }

   static {
      IS_SADDLED = SynchedEntityData.m_135353_(FlyingFishEntity.class, EntityDataSerializers.f_135035_);
      SADDLES = new Item[]{Items.f_42450_};
      FOODS = new FoodProperties[]{Foods.f_38819_, Foods.f_38805_, Foods.f_38809_, Foods.f_38822_, Foods.f_38826_, Foods.f_38800_};
   }
}
