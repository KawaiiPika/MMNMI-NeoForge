package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.BlackLegPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ExtraHachisAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
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
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class BlackLegTrainerEntity extends TrainerEntity {
   public BlackLegTrainerEntity(EntityType<BlackLegTrainerEntity> type, Level world) {
      super(type, world, MobsHelper.BLACK_LEG_TRAINERS_TEXTURES);
   }

   public void m_8099_() {
      super.m_8099_();
      EntityStatsCapability.get(this).ifPresent((props) -> {
         props.setFaction((Faction)ModFactions.CIVILIAN.get());
         props.setRace((Race)ModRaces.HUMAN.get());
         props.setFightingStyle((FightingStyle)ModFightingStyles.BLACK_LEG.get());
         props.setDoriki((double)4000.0F + WyHelper.randomWithRange(0, 1000));
         props.setBelly(500L + Math.round(WyHelper.randomWithRange(0, 500)));
      });
      HakiCapability.get(this).ifPresent((props) -> {
         props.setBusoshokuHakiExp(80.0F);
         props.setKenbunshokuHakiExp(80.0F);
      });
      if (this.f_19796_.m_188500_() < 0.4) {
         this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModItems.CIGAR.get()));
      }

      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Monster.class, true, true));
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)BlackLegPassiveBonusesAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, 1.3, true));
      WeightedList<Supplier<Goal>> goals = new WeightedList<Supplier<Goal>>(new Object[0]);
      goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(this, (AbilityCore)ConcasseAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> new RepeaterAbilityWrapperGoal(this, (AbilityCore)ExtraHachisAbility.INSTANCE.get()), 3);
      goals.pickN(this.m_217043_(), 5).forEach((goal) -> this.f_21345_.m_25352_(2, (Goal)goal.get()));
      MobsHelper.getBasicHakiAbilities(this, 100).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.31F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)15.0F).m_22268_((Attribute)ModAttributes.TOUGHNESS.get(), (double)12.0F);
   }

   public List<QuestId<?>> getAvailableQuests(Player player) {
      return EMPTY;
   }
}
