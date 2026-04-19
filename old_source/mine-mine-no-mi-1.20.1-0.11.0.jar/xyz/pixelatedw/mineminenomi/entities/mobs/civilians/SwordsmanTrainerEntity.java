package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.TrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class SwordsmanTrainerEntity extends TrainerEntity {
   protected static final List<Supplier<? extends Item>> SWORDS;

   public SwordsmanTrainerEntity(EntityType<SwordsmanTrainerEntity> type, Level world) {
      super(type, world, MobsHelper.SWORDSMAN_TRAINER_TEXTURES);
   }

   public void m_8099_() {
      super.m_8099_();
      EntityStatsCapability.get(this).ifPresent((props) -> {
         props.setFaction((Faction)ModFactions.CIVILIAN.get());
         props.setRace((Race)ModRaces.HUMAN.get());
         props.setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
         props.setDoriki((double)4000.0F + WyHelper.randomWithRange(0, 1000));
         props.setBelly(500L + Math.round(WyHelper.randomWithRange(0, 500)));
      });
      HakiCapability.get(this).ifPresent((props) -> {
         props.setBusoshokuHakiExp(80.0F);
         props.setKenbunshokuHakiExp(80.0F);
      });
      ItemStack randomSword = new ItemStack((ItemLike)((Supplier)SWORDS.get(this.f_19796_.m_188503_(SWORDS.size()))).get());
      randomSword.m_41784_().m_128379_("isClone", true);
      this.m_8061_(EquipmentSlot.MAINHAND, randomSword);
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Monster.class, true, true));
      this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, 1.2, true));
      WeightedList<Supplier<Goal>> goals = new WeightedList<Supplier<Goal>>(new Object[0]);
      goals.addEntry((Supplier)() -> new DashAbilityWrapperGoal(this, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<SanbyakurokujuPoundHoAbility> goal = new ProjectileAbilityWrapperGoal<SanbyakurokujuPoundHoAbility>(this, (AbilityCore)SanbyakurokujuPoundHoAbility.INSTANCE.get());
         ((SanbyakurokujuPoundHoAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 3);
      goals.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(this, (AbilityCore)HiryuKaenAbility.INSTANCE.get()), 3);
      goals.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)OTatsumakiAbility.INSTANCE.get()), 2);
      goals.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<YakkodoriAbility> goal = new ProjectileAbilityWrapperGoal<YakkodoriAbility>(this, (AbilityCore)YakkodoriAbility.INSTANCE.get());
         ((YakkodoriAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 2);
      goals.pickN(this.m_217043_(), 5).forEach((goal) -> this.f_21345_.m_25352_(2, (Goal)goal.get()));
      MobsHelper.getBasicHakiAbilities(this, 100).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)15.0F).m_22268_((Attribute)ModAttributes.TOUGHNESS.get(), (double)12.0F);
   }

   public List<QuestId<?>> getAvailableQuests(Player player) {
      IEntityStats entityProps = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (entityProps != null && questProps != null) {
         List<QuestId<?>> availableQuests = new ArrayList();
         if (entityProps.isSwordsman()) {
            availableQuests.addAll((Collection)ModQuests.SWORDSMAN_TRIALS.stream().map(RegistryObject::get).collect(Collectors.toList()));
         }

         return availableQuests;
      } else {
         return EMPTY;
      }
   }

   static {
      SWORDS = Arrays.asList(ModWeapons.SANDAI_KITETSU, ModWeapons.NIDAI_KITETSU, ModWeapons.WADO_ICHIMONJI, ModWeapons.KIKOKU, ModWeapons.GRYPHON, ModWeapons.AME_NO_HABAKIRI, ModWeapons.ENMA, () -> Items.f_42388_, () -> Items.f_42393_);
   }
}
