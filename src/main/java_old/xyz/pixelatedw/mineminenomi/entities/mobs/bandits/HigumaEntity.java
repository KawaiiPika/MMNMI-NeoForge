package xyz.pixelatedw.mineminenomi.entities.mobs.bandits;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetFlyStallingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class HigumaEntity extends OPBossEntity {
   private RevengeMeter revengeMeter;
   private RevengeMeter hitsMeter;

   public HigumaEntity(EntityType<? extends HigumaEntity> type, Level world) {
      super(type, world);
   }

   public HigumaEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.HIGUMA.get(), challenge);
   }

   public void initBoss() {
      this.revengeMeter = new RevengeMeter(this, 100, 1);
      this.hitsMeter = new RevengeMeter(this, 3, 0);
      this.entityStats.setFaction((Faction)ModFactions.BANDIT.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)2.5F);
      this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(1));
      this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
      this.revengeMeter.addCheck(new TargetFlyStallingRevengeCheck(10));
      this.hitsMeter.addCheck(new PhysicalHitRevengeCheck(1));
      DashAbilityWrapperGoal<ShiShishiSonsonAbility> shishishiWrapper = new DashAbilityWrapperGoal<ShiShishiSonsonAbility>(this, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get());
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(1, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(2, shishishiWrapper);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.m_8061_(EquipmentSlot.MAINHAND, ((ModSwordItem)ModWeapons.CUTLASS.get()).m_7968_());
         this.entityStats.setDoriki((double)100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         ((ShiShishiSonsonAbility)shishishiWrapper.getAbility()).addCanUseCheck((entity, ability) -> this.revengeMeter.getRevengeValue() >= 10 ? Result.success() : Result.fail((Component)null));
         ((ShiShishiSonsonAbility)shishishiWrapper.getAbility()).addUseEvent((entity, ability) -> this.revengeMeter.reduceRevengeValue(10));
         this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 250.0F, 2.0F));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)6.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)16.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)250.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
         this.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)ModArmors.PIRATE_CAPTAIN_CAPE.get()));
         this.m_8061_(EquipmentSlot.MAINHAND, ((ModSwordItem)ModWeapons.KATANA.get()).m_7968_());
         ReactiveRetreatAbilityWrapperGoal<SoruAbility> soruWrapper = new ReactiveRetreatAbilityWrapperGoal<SoruAbility>(this, this.hitsMeter, (AbilityCore)SoruAbility.INSTANCE.get());
         soruWrapper.setMaxDistance(100.0F);
         ReactiveRetreatAbilityWrapperGoal<GeppoAbility> geppoWrapper = new ReactiveRetreatAbilityWrapperGoal<GeppoAbility>(this, this.hitsMeter, (AbilityCore)GeppoAbility.INSTANCE.get());
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 200.0F, 3.0F));
         this.f_21345_.m_25352_(0, new DashDodgeProjectilesGoal(this, 200.0F, 1.25F));
         this.f_21345_.m_25352_(0, soruWrapper);
         this.f_21345_.m_25352_(0, geppoWrapper);
         this.f_21345_.m_25352_(2, new DashAbilityWrapperGoal(this, (AbilityCore)SoruAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new ActiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new ProjectileAbilityWrapperGoal(this, (AbilityCore)YakkodoriAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)OTatsumakiAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new JumpAbilityWrapperGoal(this, (AbilityCore)HiryuKaenAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.31F).m_22268_(Attributes.f_22281_, (double)2.0F).m_22268_(Attributes.f_22276_, (double)100.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   public Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet(new RevengeMeter[]{this.revengeMeter, this.hitsMeter});
   }
}
