package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.NemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.RenpatsuNamariBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class SniperTrainerEntity extends TrainerEntity implements RangedAttackMob {
   private static final int SWITCH_RANGE = 5;
   private NPCPhaseManager phaseManager;
   private NPCPhase<SniperTrainerEntity> meleePhase;
   private NPCPhase<SniperTrainerEntity> rangePhase;
   private ItemStack heldBow;

   public SniperTrainerEntity(EntityType<SniperTrainerEntity> type, Level world) {
      super(type, world, MobsHelper.SNIPER_TRAINER_TEXTURES);
      this.heldBow = ItemStack.f_41583_;
   }

   public void m_8099_() {
      super.m_8099_();
      this.phaseManager = new NPCPhaseManager(this);
      this.meleePhase = new SimplePhase<SniperTrainerEntity>("Melee Phase", this);
      this.rangePhase = new SimplePhase<SniperTrainerEntity>("Range Phase", this);
      EntityStatsCapability.get(this).ifPresent((props) -> {
         props.setFaction((Faction)ModFactions.CIVILIAN.get());
         props.setRace((Race)ModRaces.HUMAN.get());
         props.setFightingStyle((FightingStyle)ModFightingStyles.SNIPER.get());
         props.setDoriki((double)(4000 + this.m_217043_().m_188503_(1000)));
         props.setBelly((long)(500 + Math.round((float)this.m_217043_().m_188503_(500))));
      });
      HakiCapability.get(this).ifPresent((props) -> {
         props.setBusoshokuHakiExp((float)(60 + this.m_217043_().m_188503_(20)));
         props.setKenbunshokuHakiExp((float)(60 + this.m_217043_().m_188503_(20)));
      });
      this.m_8061_(EquipmentSlot.MAINHAND, Items.f_42411_.m_7968_());
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Monster.class, true, true));
      this.f_21345_.m_25352_(0, new StyleSwitchGoal(this, this::onStyleSwitched));
      if (this.m_217043_().m_188503_(3) == 0) {
         this.meleePhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
      } else {
         this.meleePhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get()));
      }

      this.meleePhase.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.15, true));
      this.rangePhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
      this.rangePhase.addGoal(1, new RangedAttackGoal(this, (double)1.0F, 60, 120.0F));
      WeightedList<Supplier<Goal>> rangedGoals = new WeightedList<Supplier<Goal>>(new Object[0]);
      rangedGoals.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(this, (AbilityCore)KaenBoshiAbility.INSTANCE.get()), 3);
      rangedGoals.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(this, (AbilityCore)SakuretsuSabotenBoshiAbility.INSTANCE.get()), 3);
      rangedGoals.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<TokuyoAburaBoshiAbility> goal = new ProjectileAbilityWrapperGoal<TokuyoAburaBoshiAbility>(this, (AbilityCore)TokuyoAburaBoshiAbility.INSTANCE.get());
         ((TokuyoAburaBoshiAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 3);
      rangedGoals.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(this, (AbilityCore)TetsuBoshiAbility.INSTANCE.get()), 2);
      rangedGoals.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<RenpatsuNamariBoshiAbility> goal = new ProjectileAbilityWrapperGoal<RenpatsuNamariBoshiAbility>(this, (AbilityCore)RenpatsuNamariBoshiAbility.INSTANCE.get());
         ((RenpatsuNamariBoshiAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 2);
      rangedGoals.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(this, (AbilityCore)KemuriBoshiAbility.INSTANCE.get()), 1);
      rangedGoals.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(this, (AbilityCore)NemuriBoshiAbility.INSTANCE.get()), 1);
      rangedGoals.pickN(this.m_217043_(), 5).forEach((goalx) -> this.rangePhase.addGoal(2, (Goal)goalx.get()));
      this.meleePhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get()));
      GrabAbilityWrapperGoal<SuplexAbility> goal = new GrabAbilityWrapperGoal<SuplexAbility>(this, (AbilityCore)SuplexAbility.INSTANCE.get());
      ((SuplexAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
      this.meleePhase.addGoal(2, goal);
      this.phaseManager.setPhase(this.rangePhase);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.28F).m_22268_(Attributes.f_22281_, (double)5.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)15.0F).m_22268_((Attribute)ModAttributes.TOUGHNESS.get(), (double)12.0F);
   }

   private FightingStyle onStyleSwitched(Mob entity, LivingEntity target, FightingStyle currentStyle) {
      if (currentStyle.equals(ModFightingStyles.SNIPER.get())) {
         boolean hasGroupAround = GoalHelper.hasEnoughTargetsAround(entity, 2, 5.0F);
         boolean hasTargetNear = GoalHelper.isWithinDistance(entity, target, (double)5.0F);
         if (hasGroupAround || hasTargetNear) {
            this.phaseManager.setPhase(this.meleePhase);
            this.heldBow = this.m_21205_();
            this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            return (FightingStyle)ModFightingStyles.BRAWLER.get();
         }
      } else {
         if (this.heldBow.m_41619_()) {
            return currentStyle;
         }

         boolean hasTargetOutsideRange = GoalHelper.isOutsideDistance(entity, target, (double)5.0F);
         if (hasTargetOutsideRange) {
            this.phaseManager.setPhase(this.rangePhase);
            this.m_8061_(EquipmentSlot.MAINHAND, this.heldBow);
            return (FightingStyle)ModFightingStyles.SNIPER.get();
         }
      }

      return currentStyle;
   }

   public void m_8024_() {
      this.phaseManager.tick();
   }

   public void m_6504_(LivingEntity target, float itemUseTime) {
      if (!this.m_6117_() && !this.m_21205_().m_41619_() && this.m_21205_().m_41720_().equals(Items.f_42411_)) {
         NuProjectileEntity proj = new NormalBulletProjectile(this.m_9236_(), this);
         if (this.isAboveNormalDifficulty()) {
            proj = new KairosekiBulletProjectile(this.m_9236_(), this);
         }

         double x = target.m_20185_() - this.m_20185_();
         double y = target.m_20227_(0.3) - proj.m_20186_();
         double z = target.m_20189_() - this.m_20189_();
         double d = Math.sqrt(x * x + z * z);
         proj.m_6686_(x, y + d * 0.05, z, 2.5F, 0.0F);
         this.m_9236_().m_7967_(proj);
      }
   }

   public List<QuestId<?>> getAvailableQuests(Player player) {
      return EMPTY;
   }
}
