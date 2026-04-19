package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.UUID;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.ExplosionImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.ExplosivePunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.KickBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.NoseFancyCannonAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FindPartnerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;

public class Mr5Entity extends OPBossEntity implements RangedAttackMob, IGoalPartner<MissValentineEntity> {
   private static final UUID HARD_BREEZE_BREATH_COOLDOWN_BONUS = UUID.fromString("f417a4af-840f-4f69-86f4-c44a15da5e9a");
   private static final int PAUSE_TICKS_AFTER_NOSE_FANCY = 40;
   private static final int SWITCH_RANGE = 5;
   private MissValentineEntity partner;
   private boolean lostGun;
   private ProjectileAbilityWrapperGoal<NoseFancyCannonAbility> noseFancyCannonWrapper;
   private ProjectileAbilityWrapperGoal<BreezeBreathBombAbility> breezeBreathBombWrapper;
   private NPCPhase<Mr5Entity> meleePhase;
   private NPCPhase<Mr5Entity> rangePhase;

   public Mr5Entity(EntityType<Mr5Entity> type, Level world) {
      super(type, world);
   }

   public Mr5Entity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.MR5.get(), challenge);
   }

   public void initBoss() {
      this.meleePhase = new SimplePhase<Mr5Entity>("Melee Phase", this);
      this.rangePhase = new SimplePhase<Mr5Entity>("Range Phase", this);
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.devilFruitData.setDevilFruit((AkumaNoMiItem)ModFruits.BOMU_BOMU_NO_MI.get());
      this.entityStats.setDoriki((double)2000.0F);
      worldData.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      boolean isStandard = this.getChallengeInfo().isDifficultyStandard();
      MobsHelper.addBasicNPCGoals(this);
      this.noseFancyCannonWrapper = new ProjectileAbilityWrapperGoal<NoseFancyCannonAbility>(this, (AbilityCore)NoseFancyCannonAbility.INSTANCE.get());
      this.breezeBreathBombWrapper = new ProjectileAbilityWrapperGoal<BreezeBreathBombAbility>(this, (AbilityCore)BreezeBreathBombAbility.INSTANCE.get());
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.f_21345_.m_25352_(0, new FindPartnerGoal(this, (EntityType)ModMobs.MISS_VALENTINE.get()));
      this.f_21345_.m_25352_(0, new StyleSwitchGoal(this, this::onStyleSwitched));
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)ExplosionImmunityAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(3, this.noseFancyCannonWrapper);
      this.rangePhase.addGoal(3, this.breezeBreathBombWrapper);
      this.meleePhase.addGoal(1, (new ImprovedMeleeAttackGoal(this, (double)0.95F, true)).setEarlyStop(this::isInRangeForAbilities));
      this.meleePhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)KickBombAbility.INSTANCE.get()));
      this.meleePhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ExplosivePunchAbility.INSTANCE.get()));
      this.getPhaseManager().setPhase(this.rangePhase);
      this.spawnGunIfPossible();
      if (isStandard) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.rangePhase.addGoal(0, new DashDodgeProjectilesGoal(this, 150.0F, 2.5F));
         this.rangePhase.addGoal(0, new DashDodgeTargetGoal(this, 80.0F, 5.0F));
         this.rangePhase.addGoal(1, new RangedAttackGoal(this, (double)1.0F, 40, 50.0F));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)3.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)6.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)400.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         ((BreezeBreathBombAbility)this.breezeBreathBombWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_BREEZE_BREATH_COOLDOWN_BONUS, "Hard Difficulty Breeze Breath Bonus", BonusOperation.ADD, -140.0F));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.rangePhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.rangePhase.addGoal(0, new DashDodgeProjectilesGoal(this, 100.0F, 2.5F));
         this.rangePhase.addGoal(0, new DashDodgeTargetGoal(this, 40.0F, 5.0F));
         this.rangePhase.addGoal(1, new RangedAttackGoal(this, (double)1.0F, 20, 50.0F));
         this.rangePhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get()));
         this.meleePhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()));
         this.meleePhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ShiganAbility.INSTANCE.get()));
      }

   }

   private void spawnGunIfPossible() {
      if (!this.lostGun) {
         if (this.getChallengeInfo().isDifficultyStandard()) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.FLINTLOCK.get()));
         } else {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.WALKER.get()));
            this.m_8061_(EquipmentSlot.OFFHAND, new ItemStack((ItemLike)ModWeapons.WALKER.get()));
         }

      }
   }

   private boolean isInRangePhase() {
      return this.getPhaseManager().getCurrentPhase().equals(this.rangePhase);
   }

   public void m_8061_(EquipmentSlot slot, ItemStack stack) {
      if (!this.m_9236_().m_5776_() && (stack == null || stack.m_41619_()) && this.isInRangePhase()) {
         this.lostGun = true;
      }

      super.m_8061_(slot, stack);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.28F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)150.0F).m_22268_(Attributes.f_22278_, 0.2).m_22268_(Attributes.f_22284_, (double)4.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_5448_() != null) {
         boolean canSee = this.m_142582_(this.m_5448_());
         double yDiff = Math.abs(this.m_20186_() - this.m_5448_().m_20186_());
         if (yDiff > (double)5.0F && !canSee) {
            ((BreezeBreathBombAbility)this.breezeBreathBombWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(this));
         }
      }

   }

   private FightingStyle onStyleSwitched(Mob entity, LivingEntity target, FightingStyle currentStyle) {
      if (currentStyle.equals(ModFightingStyles.SNIPER.get())) {
         boolean hasTargetNear = GoalHelper.isWithinDistance(entity, target, (double)5.0F);
         if (hasTargetNear) {
            this.getPhaseManager().setPhase(this.meleePhase);
            this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            if (this.getChallengeInfo().isDifficultyHard()) {
               this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
            }

            return (FightingStyle)ModFightingStyles.BRAWLER.get();
         }
      } else {
         boolean hasTargetOutsideRange = GoalHelper.isOutsideDistance(entity, target, (double)5.0F);
         if (hasTargetOutsideRange) {
            this.getPhaseManager().setPhase(this.rangePhase);
            this.spawnGunIfPossible();
            return (FightingStyle)ModFightingStyles.SNIPER.get();
         }
      }

      return currentStyle;
   }

   public void m_6504_(LivingEntity target, float distance) {
      ItemStack heldItem = this.m_21205_();
      if (!heldItem.m_41619_() && heldItem.m_41720_() instanceof ModGunItem) {
         if (this.noseFancyCannonWrapper.hasTimePassedSinceLastEnd(40.0F)) {
            if (target != null && target.m_6084_() && !AbilityHelper.isInCreativeOrSpectator(target)) {
               NuProjectileEntity bullet = new NormalBulletProjectile(this.m_9236_(), this);
               float damage = 8.0F;
               float speed = 2.0F;
               if (this.getChallengeInfo().isDifficultyHard()) {
                  bullet = new KairosekiBulletProjectile(this.m_9236_(), this);
                  damage = 10.0F;
                  speed = 2.5F;
               }

               bullet.setDamage(damage);
               this.m_9236_().m_7967_(bullet);
               bullet.m_37251_(this, this.m_146909_(), this.m_146908_(), 0.0F, speed, 0.0F);
            }
         }
      }
   }

   private boolean isInRangeForAbilities() {
      if (!GoalHelper.hasAliveTarget(this)) {
         return true;
      } else {
         return Math.abs(this.m_20270_(this.m_5448_())) < 30.0F;
      }
   }

   public MissValentineEntity getPartner() {
      return this.partner;
   }

   public void setPartner(MissValentineEntity partner) {
      this.partner = partner;
   }
}
