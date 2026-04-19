package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.GoalMemories;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.whitewalkie.WhiteWalkieSleepGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class WhiteWalkieEntity extends TamableAnimal implements ContainerListener, HasCustomInventoryScreen, IGoalMemoriesEntity {
   private static final EntityDataAccessor<Byte> FLAGS;
   private static final EntityDataAccessor<Integer> CHESTS;
   private static final double TAME_CHANCE = 0.35;
   private static final Item[] SADDLES;
   private static final byte BITE_EVENT = 100;
   private static final byte SHAKE_EVENT = 101;
   private final GoalMemories goalMemories = new GoalMemories();
   private SimpleContainer inventory;
   private int currentInvPage;
   private long lastMountTime;
   private long lastBiteTime;
   private long lastShakeTime;
   private int clientBiteTime;
   private int clientShakeTime;

   public WhiteWalkieEntity(EntityType<? extends WhiteWalkieEntity> type, Level world) {
      super(type, world);
      this.createInventory();
      if (world != null && !world.f_46443_) {
         this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)40.0F);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new WhiteWalkieSleepGoal(this));
      this.f_21345_.m_25352_(2, new BreedGoal(this, (double)1.0F));
      this.f_21345_.m_25352_(3, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(3, new FollowParentGoal(this, 1.15));
      this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, (double)1.0F, false));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(FLAGS, (byte)0);
      this.m_20088_().m_135372_(CHESTS, 0);
   }

   public void m_213583_(Player player) {
      if (!this.m_9236_().f_46443_ && (!this.m_20160_() || this.m_20363_(player)) && this.m_21824_()) {
      }

   }

   protected void createInventory() {
      SimpleContainer inventory = this.inventory;
      this.inventory = new SimpleContainer(this.getInventorySize());
      if (inventory != null) {
         inventory.m_19181_(this);
         int i = Math.min(inventory.m_6643_(), this.inventory.m_6643_());

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = inventory.m_8020_(j);
            if (!itemstack.m_41619_()) {
               this.inventory.m_6836_(j, itemstack.m_41777_());
            }
         }
      }

      this.inventory.m_19164_(this);
      this.updateContainerEquipment();
   }

   protected void updateContainerEquipment() {
      if (!this.m_9236_().f_46443_) {
         this.setFlag(4, !this.inventory.m_8020_(0).m_41619_());
      }

   }

   public void openInventory(Player player) {
      if (!this.m_9236_().f_46443_ && (!this.m_20160_() || this.m_20363_(player)) && this.m_21824_()) {
         MenuProvider containerProvider = new SimpleMenuProvider((id, inv, playerEntity) -> new WhiteWalkieStorageContainer(id, inv, this.inventory, this), ModI18n.CONTAINER_WHITE_WALKIE_STORAGE);
         NetworkHooks.openScreen((ServerPlayer)player, containerProvider, (buf) -> {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128405_("entity", this.m_19879_());
            nbt.m_128405_("inventorySize", this.inventory.m_6643_());
            nbt.m_128405_("pageSize", this.getInventoryPageSize());
            buf.m_130079_(nbt);
         });
      }

   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128379_("IsSleeping", this.m_5803_());
      nbt.m_128405_("Chests", this.getChests());
      if (!this.inventory.m_8020_(0).m_41619_()) {
         nbt.m_128365_("SaddleItem", this.inventory.m_8020_(0).m_41739_(new CompoundTag()));
      }

      if (this.hasChest()) {
         ListTag listnbt = new ListTag();

         for(int i = 1; i < this.inventory.m_6643_(); ++i) {
            ItemStack itemstack = this.inventory.m_8020_(i);
            if (!itemstack.m_41619_()) {
               CompoundTag compoundnbt = new CompoundTag();
               compoundnbt.m_128344_("Slot", (byte)i);
               itemstack.m_41739_(compoundnbt);
               listnbt.add(compoundnbt);
            }
         }

         nbt.m_128365_("Items", listnbt);
      }

   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.setSleeping(nbt.m_128471_("IsSleeping"));
      this.setChest(nbt.m_128451_("Chests"));
      if (nbt.m_128425_("SaddleItem", 10)) {
         ItemStack itemstack = ItemStack.m_41712_(nbt.m_128469_("SaddleItem"));
         if (itemstack != null && !itemstack.m_41619_() && itemstack.m_41720_() == Items.f_42450_) {
            this.setSaddled(itemstack);
         }
      }

      if (this.hasChest()) {
         ListTag listnbt = nbt.m_128437_("Items", 10);
         this.createInventory();

         for(int i = 0; i < listnbt.size(); ++i) {
            CompoundTag compoundnbt = listnbt.m_128728_(i);
            int j = compoundnbt.m_128445_("Slot") & 255;
            if (j >= 1 && j < this.inventory.m_6643_()) {
               this.inventory.m_6836_(j, ItemStack.m_41712_(compoundnbt));
            }
         }
      }

      this.updateContainerEquipment();
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
               if (chance < 0.35 && !ForgeEventFactory.onAnimalTame(this, player)) {
                  this.m_9236_().m_7605_(this, (byte)7);
                  this.m_21828_(player);
               }

               return InteractionResult.SUCCESS;
            }
         } else {
            if (player.m_36341_()) {
               this.openInventory(player);
               return InteractionResult.m_19078_(this.m_9236_().f_46443_);
            }

            if (this.m_6898_(stack) && this.m_21223_() < this.m_21233_()) {
               this.m_142075_(player, hand, stack);
               this.m_5634_(4.0F);
               this.m_9236_().m_7605_(this, (byte)7);
               return InteractionResult.SUCCESS;
            }

            if (isSaddle && !this.isSaddled() && !this.m_6162_()) {
               this.setSaddled(stack.m_41777_());
               this.m_142075_(player, hand, stack);
               this.m_9236_().m_6269_((Player)null, this, SoundEvents.f_12034_, SoundSource.PLAYERS, 0.5F, 1.0F);
               return InteractionResult.SUCCESS;
            }

            if (stack.m_41720_() == Blocks.f_50087_.m_5456_() && !this.m_6162_() && this.getChests() < 2) {
               this.setChest(this.getChests() + 1);
               this.createInventory();
               this.m_142075_(player, hand, stack);
               return InteractionResult.SUCCESS;
            }

            if ((!this.m_6898_(stack) || this.m_6898_(stack) && !this.m_5957_()) && player == this.m_269323_() && !this.m_6162_()) {
               player.m_20329_(this);
               this.lastMountTime = this.m_9236_().m_46467_();
               return InteractionResult.SUCCESS;
            }
         }
      }

      return super.m_6071_(player, hand);
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
               this.m_7910_((float)this.m_21133_(Attributes.f_22279_) * 0.7F);
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

   public void m_8107_() {
      super.m_8107_();
      if (this.clientShakeTime > 0) {
         --this.clientShakeTime;
      }

      if (this.clientBiteTime > 0) {
         --this.clientBiteTime;
      }

      if (!this.m_9236_().f_46443_) {
         if (this.m_21824_() && this.m_20160_() && this.m_20197_().contains(this.m_269323_()) && this.m_269323_() != null && this.m_269323_().f_20911_ && this.m_9236_().m_46467_() >= this.lastMountTime + 20L && this.m_9236_().m_46467_() >= this.lastBiteTime + 20L) {
            this.m_6674_(InteractionHand.MAIN_HAND);
            Vec3 look = this.m_20182_().m_82549_(this.m_269323_().m_20154_().m_82542_((double)4.0F, (double)1.0F, (double)4.0F));
            Vec3 ogPos = new Vec3(look.m_7096_(), this.m_20186_(), look.m_7094_());
            List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(ogPos, this.m_9236_(), (double)1.0F, ModEntityPredicates.getEnemyFactions(this.m_269323_()));
            targets.remove(this);
            targets.remove(this.m_269323_());
            this.lastBiteTime = this.m_9236_().m_46467_();
            this.m_9236_().m_7605_(this, (byte)100);

            for(Entity e : targets) {
               e.m_6469_(this.m_269291_().m_269333_(this), (float)this.m_21133_(Attributes.f_22281_));
            }
         }

         if (this.lastShakeTime <= 0L) {
            this.lastShakeTime = this.m_9236_().m_46467_();
         }

         if (this.m_9236_().m_46467_() >= this.lastShakeTime + 1200L && !this.m_20160_()) {
            this.m_9236_().m_7605_(this, (byte)101);
            this.lastShakeTime = this.m_9236_().m_46467_();
         }
      }

   }

   public void m_8119_() {
      super.m_8119_();
   }

   public void m_8024_() {
      this.goalMemories.tick();
   }

   public void m_21011_(InteractionHand hand, boolean updateSelf) {
      if (this.m_5448_() != null) {
         this.m_9236_().m_7605_(this, (byte)100);
      }

      super.m_21011_(hand, updateSelf);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12280_;
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_12283_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12282_;
   }

   protected void m_7355_(BlockPos pos, BlockState block) {
      this.m_5496_(SoundEvents.f_12284_, 0.15F, 1.5F);
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public float m_6100_() {
      return this.m_6162_() ? 1.5F + this.f_19796_.m_188501_() / 2.0F : 1.25F + this.f_19796_.m_188501_() / 2.0F;
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
      this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
      return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob mate) {
      WhiteWalkieEntity offspring = (WhiteWalkieEntity)((EntityType)ModMobs.WHITE_WALKIE.get()).m_20615_(world);
      this.setOffspringAttributes(mate, offspring);
      return offspring;
   }

   protected void setOffspringAttributes(AgeableMob mate, WhiteWalkieEntity offspring) {
      double hp = this.m_21172_(Attributes.f_22276_) + mate.m_21172_(Attributes.f_22276_) + this.generateRandomHealth();
      offspring.m_21051_(Attributes.f_22276_).m_22100_(hp / (double)3.0F);
      double armor = this.m_21172_(Attributes.f_22284_) + mate.m_21172_(Attributes.f_22284_) + this.generateRandomArmor();
      offspring.m_21051_(Attributes.f_22284_).m_22100_(armor / (double)3.0F);
      double speed = this.m_21172_(Attributes.f_22279_) + mate.m_21172_(Attributes.f_22279_) + this.generateRandomSpeed();
      offspring.m_21051_(Attributes.f_22279_).m_22100_(speed / (double)3.0F);
      double damage = this.m_21172_(Attributes.f_22281_) + mate.m_21172_(Attributes.f_22281_) + this.generateRandomAttack();
      offspring.m_21051_(Attributes.f_22281_).m_22100_(damage / (double)3.0F);
   }

   protected double generateRandomArmor() {
      return (double)1.0F + this.f_19796_.m_188500_() * (double)2.5F;
   }

   protected double generateRandomAttack() {
      return (double)5.0F + this.f_19796_.m_188500_() * (double)2.0F;
   }

   protected double generateRandomHealth() {
      return (double)50.0F + this.f_19796_.m_188500_() * (double)10.0F;
   }

   protected double generateRandomSpeed() {
      return (double)0.23F + this.f_19796_.m_188500_() * 0.05;
   }

   public boolean m_6898_(ItemStack stack) {
      if (stack.m_41619_()) {
         return false;
      } else {
         FoodProperties food = stack.m_41720_().m_41473_();
         return food != null && food.m_38746_();
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
         case 100 -> this.clientBiteTime = 20;
         case 101 -> this.clientShakeTime = 40;
      }

      super.m_7822_(id);
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
      return 1.7;
   }

   public void m_5757_(Container inv) {
      ItemStack stack = this.inventory.m_8020_(0);
      boolean flag = stack != null && !stack.m_41619_() && stack.m_41720_() == Items.f_42450_;
      this.setFlag(0, flag);
      this.updateContainerEquipment();
      if (this.f_19797_ > 20 && !flag && this.isSaddled()) {
         this.m_5496_(SoundEvents.f_12034_, 0.5F, 1.0F);
      }

   }

   protected int getInventorySize() {
      return 3 + (this.hasChest() ? this.getInventoryPageSize() * this.getInventoryMaxPage() : 0);
   }

   public int getInventoryColumns() {
      return 5;
   }

   public int getInventoryPageSize() {
      return 15;
   }

   public void setInventoryPage(int page) {
      this.currentInvPage = page;
   }

   public int getInventoryPage() {
      return this.currentInvPage;
   }

   public int getInventoryMaxPage() {
      return 2;
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.inventory != null) {
         for(int i = 0; i < this.inventory.m_6643_(); ++i) {
            ItemStack itemstack = this.inventory.m_8020_(i);
            if (!itemstack.m_41619_() && !EnchantmentHelper.m_44924_(itemstack)) {
               this.m_19983_(itemstack);
            }
         }
      }

      if (this.hasChest()) {
         if (!this.m_9236_().f_46443_) {
            this.m_19998_(Blocks.f_50087_);
         }

         this.setChest(0);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public float getBiteAnimationProgress(float partialTicks) {
      return ((float)(20 - this.clientBiteTime) + partialTicks) / 20.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public float getShakeAnimationTime() {
      return (float)this.clientShakeTime;
   }

   public void setSaddled(@Nullable ItemStack stack) {
      boolean flag = true;
      if (stack == null) {
         stack = ItemStack.f_41583_;
         flag = false;
      }

      this.inventory.m_6836_(0, stack);
      this.setFlag(0, flag);
   }

   public void setSleeping(boolean flag) {
      this.setFlag(1, flag);
   }

   public void setChest(int chests) {
      this.m_20088_().m_135381_(CHESTS, chests);
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

   public boolean isIdling() {
      return !this.m_5803_() && !this.m_20160_();
   }

   public boolean isSaddled() {
      ItemStack stack = this.inventory.m_8020_(0);
      return stack != null && !stack.m_41619_() && stack.m_41720_() == Items.f_42450_ || this.getFlag(0);
   }

   public boolean m_5803_() {
      return this.getFlag(1);
   }

   public boolean hasChest() {
      return this.getChests() > 0;
   }

   public int getChests() {
      return (Integer)this.m_20088_().m_135370_(CHESTS);
   }

   public GoalMemories getGoalMemories() {
      return this.goalMemories;
   }

   static {
      FLAGS = SynchedEntityData.m_135353_(WhiteWalkieEntity.class, EntityDataSerializers.f_135027_);
      CHESTS = SynchedEntityData.m_135353_(WhiteWalkieEntity.class, EntityDataSerializers.f_135028_);
      SADDLES = new Item[]{Items.f_42450_};
   }
}
