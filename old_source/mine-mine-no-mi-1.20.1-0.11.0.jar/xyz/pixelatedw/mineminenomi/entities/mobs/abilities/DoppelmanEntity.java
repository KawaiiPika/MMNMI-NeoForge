package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.kage.BlackBoxAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.BrickBatAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.DoppelmanAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class DoppelmanEntity extends CloneEntity {
   private static final UUID BLACK_BOX_COOLDOWN_BONUS_UUID = UUID.fromString("8459d1b9-2362-4c63-b89c-af761a6c9f18");
   private static final EntityDataAccessor<Integer> SHADOWS;

   public DoppelmanEntity(EntityType<DoppelmanEntity> type, Level world) {
      super(type, world);
   }

   public DoppelmanEntity(Level world, LivingEntity owner) {
      super((EntityType)ModMobs.DOPPELMAN.get(), world, owner);
      if (world != null && !world.f_46443_) {
         this.m_6858_(false);
         this.setMaxAliveTicks(-1);
         this.disablePermaPanic();
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)10.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)2.0F);
         ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
         ProjectileAbilityWrapperGoal<BlackBoxAbility> blackBoxWrapper = new ProjectileAbilityWrapperGoal<BlackBoxAbility>(this, (AbilityCore)BlackBoxAbility.INSTANCE.get());
         ((BlackBoxAbility)blackBoxWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(BLACK_BOX_COOLDOWN_BONUS_UUID, "Black Box Cooldown Bonus", BonusOperation.ADD, 80.0F));
         this.f_21345_.m_25352_(2, new ProjectileAbilityWrapperGoal(this, (AbilityCore)BrickBatAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, blackBoxWrapper);
         AbilityHelper.forceCooldownOnAllAbilities(this, 40);
      }

   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new OpenDoorGoal(this, false));
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.28F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22278_, (double)1.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SHADOWS, 0);
   }

   public void m_142687_(Entity.RemovalReason reason) {
      if (this.getOwner() != null && this.m_21223_() <= 0.0F) {
         AbilityCapability.getLazy(this.getOwner()).ifPresent((props) -> {
            DoppelmanAbility abl = (DoppelmanAbility)props.getEquippedAbility((AbilityCore)DoppelmanAbility.INSTANCE.get());
            if (abl != null) {
               abl.doppelmanDeathTrigger(this.getOwner());
            }

         });
      }

      super.m_142687_(reason);
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (player == this.getOwner()) {
         ItemStack itemStack = player.m_21120_(hand);
         if (itemStack != null && !itemStack.m_41619_() && itemStack.m_41720_() == ModItems.SHADOW.get() && this.getShadows() < 15) {
            itemStack.m_41774_(1);
            this.addShadow();
            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.PASS;
   }

   public void m_7350_(EntityDataAccessor<?> key) {
      if (key.equals(SHADOWS)) {
         this.m_6210_();
      }

      super.m_7350_(key);
   }

   public EntityDimensions m_6972_(Pose pose) {
      float shadowsUsed = (float)this.getShadows();
      if (shadowsUsed > 0.0F) {
         EntityDimensions newSize = this.m_6095_().m_20680_().m_20388_(1.0F + shadowsUsed / 6.0F);
         return newSize;
      } else {
         return super.m_6972_(pose);
      }
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128405_("shadows", (Integer)this.f_19804_.m_135370_(SHADOWS));
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.f_19804_.m_135381_(SHADOWS, nbt.m_128451_("shadows"));
   }

   public void setOwner(LivingEntity owner) {
      super.setOwner(owner);
      this.unlockHakiOfOwner(owner);
   }

   public void addShadow() {
      this.f_19804_.m_135381_(SHADOWS, (Integer)this.f_19804_.m_135370_(SHADOWS) + 1);
   }

   public void setShadow(int value) {
      this.f_19804_.m_135381_(SHADOWS, value);
   }

   public int getShadows() {
      return (Integer)this.f_19804_.m_135370_(SHADOWS);
   }

   static {
      SHADOWS = SynchedEntityData.m_135353_(DoppelmanEntity.class, EntityDataSerializers.f_135028_);
   }
}
