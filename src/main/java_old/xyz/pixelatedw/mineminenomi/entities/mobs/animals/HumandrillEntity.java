package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.GapCloserGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.PersonalSpaceTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class HumandrillEntity extends PathfinderMob implements IEntityAdditionalSpawnData {
   protected static final List<Supplier<? extends Item>> SWORDS;
   private static final float MIN_SIZE = 0.8F;
   private static final float MAX_SIZE = 2.0F;
   private float size;

   public HumandrillEntity(EntityType<? extends HumandrillEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         boolean hasSword = false;
         double armorChance = 0.2;
         double diamondChance = 0.1;
         double bigMonkeChance = 0.1;
         ItemStack headStack = ItemStack.f_41583_;
         ItemStack bodyStack = ItemStack.f_41583_;
         ItemStack pantsStack = ItemStack.f_41583_;
         ItemStack shoesStack = ItemStack.f_41583_;
         ItemStack swordStack = ItemStack.f_41583_;
         DifficultyInstance difficulty = this.m_9236_().m_6436_(this.m_20183_());
         if (difficulty.m_19056_() < 1.0F) {
            armorChance = 0.1;
            diamondChance = 0.01;
            bigMonkeChance = (double)0.0F;
         } else if (difficulty.m_19056_() > 2.2F) {
            armorChance = (double)0.5F;
            diamondChance = 0.4;
         }

         if (this.m_217043_().m_188500_() < armorChance) {
            headStack = this.m_217043_().m_188500_() < diamondChance ? Items.f_42472_.m_7968_() : Items.f_42468_.m_7968_();
         }

         if (this.m_217043_().m_188500_() < armorChance) {
            bodyStack = this.m_217043_().m_188500_() < diamondChance ? Items.f_42473_.m_7968_() : Items.f_42469_.m_7968_();
         }

         if (this.m_217043_().m_188500_() < 0.01) {
            bodyStack = ((Item)ModArmors.LUFFY_SHIRT.get()).m_7968_();
            pantsStack = ((Item)ModArmors.LUFFY_PANTS.get()).m_7968_();
            shoesStack = ((Item)ModArmors.LUFFY_SANDALS.get()).m_7968_();
         }

         if (this.m_217043_().m_188500_() < 0.7) {
            if (this.f_19796_.m_188500_() > bigMonkeChance) {
               swordStack = new ItemStack((ItemLike)((Supplier)SWORDS.get(this.f_19796_.m_188503_(SWORDS.size()))).get());
            } else {
               this.size = 2.5F;
               swordStack = new ItemStack((ItemLike)ModWeapons.YORU.get());
               headStack = Items.f_42480_.m_7968_();
            }

            swordStack.m_41784_().m_128379_("isClone", true);
            hasSword = true;
         }

         this.m_8061_(EquipmentSlot.MAINHAND, swordStack);
         this.m_8061_(EquipmentSlot.HEAD, headStack);
         this.m_8061_(EquipmentSlot.CHEST, bodyStack);
         this.m_8061_(EquipmentSlot.LEGS, pantsStack);
         this.m_8061_(EquipmentSlot.FEET, shoesStack);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)(3.0F + (this.getSize() > 1.0F ? this.getSize() : 0.0F)));
         this.m_21051_(Attributes.f_22276_).m_22100_((double)(60.0F + this.getSize() * 10.0F));
         this.m_21051_(Attributes.f_22278_).m_22100_((double)(this.getSize() * 0.2F));
         this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_((double)(1.5F + this.getSize() * 0.5F));
         this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(this).orElseThrow();
         props.setDoriki((double)1500.0F + WyHelper.randomWithRange(300, 600));
         props.setBelly(0L);
         this.f_21345_.m_25352_(0, new FloatGoal(this));
         if (this.f_19796_.m_188501_() < 0.6F) {
            this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
         }

         this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
         this.f_21345_.m_25352_(0, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
         this.f_21345_.m_25352_(2, new GapCloserGoal(this));
         this.f_21345_.m_25352_(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
         this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Animal.class, 8.0F));
         this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, HumandrillEntity.class, 8.0F));
         this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
         this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
         this.f_21346_.m_25352_(1, new PersonalSpaceTargetGoal(this));
         this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, LapahnEntity.class, true));
         WeightedList<Supplier<Goal>> goals = new WeightedList<Supplier<Goal>>(new Object[0]);
         if (hasSword) {
            goals.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(this, (AbilityCore)HiryuKaenAbility.INSTANCE.get()), 4);
            goals.addEntry((Supplier)() -> new DashAbilityWrapperGoal(this, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()), 3);
            goals.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(this, (AbilityCore)YakkodoriAbility.INSTANCE.get()), 1);
         } else if ((double)this.f_19796_.m_188501_() < 0.8) {
            goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 4);
            goals.addEntry((Supplier)() -> new DashAbilityWrapperGoal(this, (AbilityCore)TackleAbility.INSTANCE.get()), 2);
            goals.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(this, (AbilityCore)SuplexAbility.INSTANCE.get()), 1);
         } else {
            goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()), 4);
            goals.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(this, (AbilityCore)ConcasseAbility.INSTANCE.get()), 3);
            goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()), 1);
         }

         goals.pickN(this.m_217043_(), 2).forEach((goal) -> this.f_21345_.m_25352_(3, (Goal)goal.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)80.0F).m_22268_((Attribute)ModAttributes.FALL_RESISTANCE.get(), (double)10.0F).m_22268_(Attributes.f_22279_, (double)0.27F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.size = Math.min(0.8F + this.f_19796_.m_188501_(), 2.0F);
   }

   public void m_8107_() {
      this.m_21203_();
      super.m_8107_();
   }

   public EntityDimensions m_6972_(Pose pose) {
      float scale = this.getSize();
      EntityDimensions newSize = this.m_6095_().m_20680_().m_20388_(scale);
      return newSize;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128350_("size", this.size);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.size = compound.m_128457_("size");
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12280_;
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_12187_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12180_;
   }

   protected void m_7355_(BlockPos pos, BlockState block) {
      this.m_5496_(SoundEvents.f_12284_, 0.15F, 1.5F);
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public float m_6100_() {
      return this.m_6162_() ? 0.5F + this.f_19796_.m_188501_() / 2.0F : 0.2F + this.f_19796_.m_188501_() / 3.0F;
   }

   public float getSize() {
      return this.size;
   }

   public boolean m_6785_(double distance) {
      return false;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeFloat(this.size);
   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      this.size = buffer.readFloat();
      this.m_6210_();
   }

   static {
      SWORDS = Arrays.asList(() -> Items.f_42383_, () -> Items.f_42425_, ModWeapons.SANDAI_KITETSU, ModWeapons.NIDAI_KITETSU, ModWeapons.WADO_ICHIMONJI, () -> Items.f_42388_);
   }
}
