package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.DamageAbsorptionAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenTrainerDialogueScreenPacket;

public class LegendaryMasterDugongEntity extends AbstractDugongEntity implements ITrainer {
   private static final Predicate<LivingEntity> SHOULD_ATTACK = (target) -> {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         return props.isBandit() || props.isPirate();
      }
   };

   public LegendaryMasterDugongEntity(EntityType<? extends LegendaryMasterDugongEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.f_46443_) {
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)8.0F);
         HakiCapability.get(this).ifPresent((props) -> {
            props.setBusoshokuHakiExp((float)(45 + this.m_217043_().m_188503_(10)));
            props.setKenbunshokuHakiExp((float)(25 + this.m_217043_().m_188503_(10)));
         });
         this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22135_());
         this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.2F, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Monster.class, true, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Mob.class, 10, true, true, SHOULD_ATTACK));
         WeightedList<Supplier<Goal>> goals = new WeightedList<Supplier<Goal>>(new Object[0]);
         goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()), 3);
         goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()), 3);
         goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 3);
         goals.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(this, (AbilityCore)SpinningBrawlAbility.INSTANCE.get()), 2);
         goals.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(this, (AbilityCore)SuplexAbility.INSTANCE.get()), 1);
         goals.pickN(this.m_217043_(), 5).forEach((goal) -> this.f_21345_.m_25352_(2, (Goal)goal.get()));
         MobsHelper.getBasicHakiAbilities(this, 100).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         this.f_21345_.m_25352_(2, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)DamageAbsorptionAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22281_, (double)15.0F).m_22268_(Attributes.f_22279_, (double)0.25F).m_22268_(Attributes.f_22276_, WyHelper.randomWithRange(100, 120)).m_22268_(Attributes.f_22284_, (double)10.0F);
   }

   public List<QuestId<?>> getAvailableQuests(Player player) {
      IEntityStats entityProps = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (entityProps == null) {
         return List.of();
      } else {
         List<QuestId<?>> availableQuests = new ArrayList();
         return availableQuests;
      }
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (hand != InteractionHand.MAIN_HAND) {
         return InteractionResult.FAIL;
      } else if (player.m_6047_()) {
         return super.m_6071_(player, hand);
      } else if (!player.m_9236_().f_46443_) {
         ModNetwork.sendTo(new SOpenTrainerDialogueScreenPacket(player, this), player);
         return InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public AgeableMob m_142606_(ServerLevel world, AgeableMob ageable) {
      return null;
   }
}
