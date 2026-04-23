#!/bin/bash
cat << 'JAVA_CODE' > src/main/java_old/xyz/pixelatedw/mineminenomi/entities/mobs/animals/WhiteWalkieEntity.java
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.api.entities.IGoalMemoriesEntity;
import xyz.pixelatedw.mineminenomi.api.math.WyMathHelper;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.whitewalkie.WhiteWalkieSleepGoal;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import net.minecraft.world.item.component.ItemContainerContents;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NbtOps;

public class WhiteWalkieEntity extends TamableAnimal implements ContainerListener, HasCustomInventoryScreen, IGoalMemoriesEntity {
   private static final EntityDataAccessor<Byte> FLAGS;
   private static final EntityDataAccessor<Integer> CHESTS;
   private static final Item[] SADDLES;
   private static final Ingredient FOOD_ITEMS;
   public int clientBiteTime;
   public int clientShakeTime;
   private int sitDuration;
   protected SimpleContainer inventory;
   private boolean isSleeping;

   public WhiteWalkieEntity(EntityType<? extends TamableAnimal> type, Level world) {
      super(type, world);
      this.f_21364_ = 1.0F;
      this.createInventory();
   }

   public static AttributeSupplier.Builder createAttributes() {
      return TamableAnimal.m_21813_().m_22268_(Attributes.f_22276_, 50.0).m_22268_(Attributes.f_22279_, (double)0.23F).m_22268_(Attributes.f_22284_, 1.0).m_22268_(Attributes.f_22281_, 5.0).m_22268_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get(), (double)1.0F);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new BreedGoal(this, 1.0));
      this.f_21345_.m_25352_(2, new WhiteWalkieSleepGoal(this));
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
         List<ItemStack> list = new ArrayList<>();
         for (int i = 1; i < this.inventory.m_6643_(); ++i) {
             list.add(this.inventory.m_8020_(i));
         }
         ItemContainerContents contents = ItemContainerContents.fromItems(list);
         ItemContainerContents.f_315335_.encodeStart(NbtOps.f_128958_, contents).ifSuccess(t -> nbt.m_128365_("ItemsData", t));
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
         this.createInventory();

         if (nbt.m_128441_("ItemsData")) {
             ItemContainerContents contents = ItemContainerContents.f_315335_.decode(NbtOps.f_128958_, nbt.m_128423_("ItemsData")).getOrThrow();
             NonNullList<ItemStack> tempList = NonNullList.withSize(this.inventory.m_6643_(), ItemStack.f_41583_);
             contents.copyInto(tempList);
             for(int i = 1; i < this.inventory.m_6643_(); i++) {
                 this.inventory.m_6836_(i, tempList.get(i - 1)); // -1 since 0 is saddle
             }
         } else if (nbt.m_128441_("Items")) {
             // Legacy fallback
             net.minecraft.nbt.ListTag listnbt = nbt.m_128437_("Items", 10);
             for(int i = 0; i < listnbt.size(); ++i) {
                CompoundTag compoundnbt = listnbt.m_128728_(i);
                int j = compoundnbt.m_128445_("Slot") & 255;
                if (j >= 1 && j < this.inventory.m_6643_()) {
                   this.inventory.m_6836_(j, ItemStack.m_41712_(compoundnbt));
                }
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
               this.m_5634_(stack.m_41720_().m_41473_().m_38744_());
               return InteractionResult.SUCCESS;
            }

            if (isSaddle && !this.isSaddled() && this.m_21824_()) {
               this.setSaddled(stack);
               if (!player.m_150110_().f_35937_) {
                  stack.m_41774_(1);
               }

               return InteractionResult.SUCCESS;
            }

            if (stack.m_41720_() == Items.f_42410_ && !this.hasChest() && this.m_21824_()) {
               this.setChest(1);
               if (!player.m_150110_().f_35937_) {
                  stack.m_41774_(1);
               }

               this.createInventory();
               return InteractionResult.SUCCESS;
            }

            if (stack.m_41720_() == ModBlocks.DESIGN_BARREL_BLOCK_ITEM.get() && !this.hasChest() && this.m_21824_()) {
               this.setChest(2);
               if (!player.m_150110_().f_35937_) {
                  stack.m_41774_(1);
               }

               this.createInventory();
               return InteractionResult.SUCCESS;
            }

            if (stack.m_41720_() == Items.f_42758_ && !this.m_5803_()) {
               this.m_9236_().m_7605_(this, (byte)101);
            }

            if (!this.m_21830_(player) || super.m_6071_(player, hand) == InteractionResult.SUCCESS) {
               return super.m_6071_(player, hand);
            }

            if (this.isSaddled()) {
               player.m_20329_(this);
               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.m_19078_(this.m_9236_().f_46443_);
      }
   }

   public boolean m_5803_() {
      return this.isSleeping;
   }

   public void setSleeping(boolean isSleeping) {
      this.isSleeping = isSleeping;
   }

   public boolean m_21825_() {
      return false;
   }

   public int getInventoryColumns() {
      return 5;
   }

   public int getInventoryPageSize() {
      return this.getInventoryColumns() * 3;
   }

   public int getInventoryPage() {
      return 0;
   }

   public int getInventorySize() {
      int size = 1;
      if (this.hasChest()) {
         size += this.getInventoryPageSize() * this.getChests();
      }

      return size;
   }

   public int m_8013_() {
      return this.getInventorySize();
   }

   public void m_5757_(Container inv) {
      this.updateContainerEquipment();
   }

   public boolean hasChest() {
      return this.getChests() > 0;
   }

   public int getChests() {
      return (Integer)this.m_20088_().m_135370_(CHESTS);
   }

   public void setChest(int chests) {
      this.m_20088_().m_135381_(CHESTS, chests);
   }

   public boolean isSaddled() {
      return this.getFlag(4);
   }

   protected void setSaddled(ItemStack stack) {
      this.m_5496_(SoundEvents.f_12285_, 0.15F, 1.0F);
      this.inventory.m_6836_(0, new ItemStack(stack.m_41720_()));
      this.updateContainerEquipment();
   }

   public boolean isSaddleable() {
      return this.m_20160_() && !this.isSaddled();
   }

   protected boolean getFlag(int flagId) {
      return ((Byte)this.m_20088_().m_135370_(FLAGS) & flagId) != 0;
   }

   protected void setFlag(int flagId, boolean set) {
      byte b0 = (Byte)this.m_20088_().m_135370_(FLAGS);
      if (set) {
         this.m_20088_().m_135381_(FLAGS, (byte)(b0 | flagId));
      } else {
         this.m_20088_().m_135381_(FLAGS, (byte)(b0 & ~flagId));
      }

   }

   public void m_7334_(Entity entityIn) {
      super.m_7334_(entityIn);
      if (entityIn instanceof Mob) {
         Mob mob = (Mob)entityIn;
         this.f_20883_ = mob.f_20883_;
      }

      float f1 = Mth.m_14031_(this.f_20883_ * 0.017453292F);
      float f2 = Mth.m_14089_(this.f_20883_ * 0.017453292F);
      float f3 = 0.5F;
      entityIn.m_6034_(this.m_20185_() - (double)(f3 * f1), this.m_20186_() + this.m_6048_() + entityIn.m_6058_(), this.m_20189_() + (double)(f3 * f2));
      if (entityIn instanceof LivingEntity) {
         ((LivingEntity)entityIn).f_20883_ = this.f_20883_;
      }

   }

   public void m_6043_() {
      super.m_6043_();
      if (!this.m_9236_().f_46443_ && this.m_20160_() && this.m_20159_()) {
         this.m_9236_().m_7605_(this, (byte)100);
      }

      if (this.isSleeping) {
         ++this.sitDuration;
      } else if (this.sitDuration > 0) {
         --this.sitDuration;
      }

      if (!this.m_9236_().f_46443_ && this.m_5803_() && this.m_5448_() != null) {
         this.isSleeping = false;
      }

   }

   public void m_27447_() {
      super.m_27447_();
      if (this.clientBiteTime > 0) {
         --this.clientBiteTime;
      }

      if (this.clientShakeTime > 0) {
         --this.clientShakeTime;
      }

   }

   public void m_21011_(InteractionHand hand, boolean updateSelf) {
      if (!this.m_9236_().f_46443_ && this.m_20160_() && this.m_20159_()) {
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

   static {
      FLAGS = SynchedEntityData.m_135353_(WhiteWalkieEntity.class, EntityDataSerializers.f_135027_);
      CHESTS = SynchedEntityData.m_135353_(WhiteWalkieEntity.class, EntityDataSerializers.f_135028_);
      SADDLES = new Item[]{Items.f_42450_};
      FOOD_ITEMS = Ingredient.m_43929_(new net.minecraft.world.level.ItemLike[]{ModItems.COOKED_SEA_KING_MEAT.get()});
   }
}
JAVA_CODE
./gradlew compileJava
