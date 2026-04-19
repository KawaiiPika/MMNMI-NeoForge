package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class BrawlerTrainerEntity extends TrainerEntity {
   public BrawlerTrainerEntity(EntityType<BrawlerTrainerEntity> type, Level world) {
      super(type, world, MobsHelper.BRAWLER_TRAINER_TEXTURES);
   }

   public void m_8099_() {
      super.m_8099_();
      EntityStatsCapability.get(this).ifPresent((props) -> {
         props.setFaction((Faction)ModFactions.CIVILIAN.get());
         props.setRace((Race)ModRaces.HUMAN.get());
         props.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
         props.setDoriki((double)4000.0F + WyHelper.randomWithRange(0, 1000));
         props.setBelly(500L + Math.round(WyHelper.randomWithRange(0, 500)));
      });
      HakiCapability.get(this).ifPresent((props) -> {
         props.setBusoshokuHakiExp(80.0F);
         props.setKenbunshokuHakiExp(80.0F);
      });
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Monster.class, true, true));
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)BrawlerPassiveBonusesAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, 1.2, true));
      WeightedList<Supplier<Goal>> goals = new WeightedList<Supplier<Goal>>(new Object[0]);
      goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<GenkotsuMeteorAbility> goal = new ProjectileAbilityWrapperGoal<GenkotsuMeteorAbility>(this, (AbilityCore)GenkotsuMeteorAbility.INSTANCE.get());
         ((GenkotsuMeteorAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 2);
      goals.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(this, (AbilityCore)SpinningBrawlAbility.INSTANCE.get()), 2);
      goals.addEntry((Supplier)() -> {
         GrabAbilityWrapperGoal<SuplexAbility> goal = new GrabAbilityWrapperGoal<SuplexAbility>(this, (AbilityCore)SuplexAbility.INSTANCE.get());
         ((SuplexAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 2);
      goals.pickN(this.m_217043_(), 5).forEach((goal) -> this.f_21345_.m_25352_(2, (Goal)goal.get()));
      MobsHelper.getBasicHakiAbilities(this, 100).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)15.0F).m_22268_((Attribute)ModAttributes.TOUGHNESS.get(), (double)12.0F);
   }

   public List<QuestId<?>> getAvailableQuests(Player player) {
      return EMPTY;
   }
}
