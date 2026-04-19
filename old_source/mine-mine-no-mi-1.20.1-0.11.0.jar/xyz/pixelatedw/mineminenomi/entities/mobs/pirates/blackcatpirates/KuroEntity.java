package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.ShakushiAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealthFootAbility;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.GankingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class KuroEntity extends OPBossEntity {
   private static final UUID SHAKUSHI_RANGE_BONUS_UUID = UUID.fromString("80e205c9-4fc8-4fed-97ad-f3d8cec36bd6");
   private static final UUID HARD_SORU_COOLDOWN_BONUS_UUID = UUID.fromString("600830a0-5796-489b-892e-45432d273d29");
   private RevengeMeter revengeMeter;
   private NPCPhase<KuroEntity> normalPhase;
   private NPCPhase<KuroEntity> ultiPhase;
   private ActiveGuardAbilityWrapperGoal<ShakushiAbility> shakushiWrapper;

   public KuroEntity(EntityType<KuroEntity> type, Level world) {
      super(type, world);
   }

   public KuroEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.KURO.get(), challenge);
   }

   public void initBoss() {
      this.revengeMeter = new RevengeMeter(this, 100, 1);
      this.normalPhase = new SimplePhase<KuroEntity>("Normal Phase", this);
      this.ultiPhase = new SimplePhase<KuroEntity>("Ulti Phase", this);
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES, this);
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.CAT_CLAWS.get()));
      this.m_8061_(EquipmentSlot.OFFHAND, new ItemStack((ItemLike)ModWeapons.CAT_CLAWS.get()));
      this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_((double)3.5F);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
      this.revengeMeter.addCheck(new DeadzoneRevengeCheck(3));
      this.revengeMeter.addCheck(new GankingRevengeCheck(10, 5.0F));
      this.shakushiWrapper = new ActiveGuardAbilityWrapperGoal<ShakushiAbility>(this, (AbilityCore)ShakushiAbility.INSTANCE.get());
      this.shakushiWrapper.setMaxDistance(100.0F);
      ((ShakushiAbility)this.shakushiWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.addStartEvent((e, a) -> {
            if (this.ultiPhase.isActive(this) && comp.isOnCooldown()) {
               this.getPhaseManager().setPhase(this.normalPhase);
            }

         }));
      ((ShakushiAbility)this.shakushiWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.RANGE.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(SHAKUSHI_RANGE_BONUS_UUID, "Shakushi Range Bonus", BonusOperation.ADD, 40.0F));
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.normalPhase.addGoal(0, new SprintTowardsTargetGoal(this));
      this.normalPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 300.0F, 2.5F));
      this.normalPhase.addGoal(0, new DashDodgeTargetGoal(this, 350.0F, 3.0F));
      this.normalPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.normalPhase.addGoal(2, new AntiAirAbilityWrapperGoal(this, (AbilityCore)TakedownKickAbility.INSTANCE.get()));
      this.normalPhase.addGoal(2, (new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)StealthFootAbility.INSTANCE.get())).setActivationDistance(10.0F));
      this.ultiPhase.addGoal(0, this.shakushiWrapper);
      this.getPhaseManager().setPhase(this.normalPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)3.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)6.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)300.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.5F);
         this.m_21051_(Attributes.f_22283_).m_22100_((double)6.0F);
         this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(2));
         this.revengeMeter.addCheck(new TargetRunningAwayRevengeCheck(5, 10.0F));
         ProjectileAbilityWrapperGoal<RankyakuAbility> rankyakuWrapper = new ProjectileAbilityWrapperGoal<RankyakuAbility>(this, (AbilityCore)RankyakuAbility.INSTANCE.get());
         ((RankyakuAbility)rankyakuWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 100.0F));
         DashAbilityWrapperGoal<SoruAbility> soruWrapper = new DashAbilityWrapperGoal<SoruAbility>(this, (AbilityCore)SoruAbility.INSTANCE.get());
         ((SoruAbility)soruWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_SORU_COOLDOWN_BONUS_UUID, "Hard Soru Cooldown Bonus", BonusOperation.MUL, 0.5F));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.normalPhase.addGoal(1, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)GeppoAbility.INSTANCE.get()));
         this.normalPhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()));
         this.normalPhase.addGoal(2, new DashAbilityWrapperGoal(this, (AbilityCore)SoruAbility.INSTANCE.get()));
         this.normalPhase.addGoal(2, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.normalPhase.addGoal(3, rankyakuWrapper);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.31F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)8.0F).m_22268_(Attributes.f_22278_, 0.15).m_22268_(Attributes.f_22283_, (double)5.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_6084_()) {
         boolean isUltiAvailable = (Boolean)((ShakushiAbility)this.shakushiWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.isOnCooldown()).orElse(false);
         if (this.normalPhase.isActive(this) && !isUltiAvailable && (this.revengeMeter.isRevengeMaxed() || (double)this.m_21223_() <= WyHelper.percentage((double)20.0F, (double)this.m_21233_()))) {
            this.getPhaseManager().setPhase(this.ultiPhase);
            this.revengeMeter.setRevengeValue(0);
         }
      }

   }

   public Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet(new RevengeMeter[]{this.revengeMeter});
   }
}
