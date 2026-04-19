package xyz.pixelatedw.mineminenomi.abilities.doctor;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MedicBagExplosionAbility extends Ability {
   public static final TargetPredicate ENEMY_AREA_CHECK = (new TargetPredicate()).testEnemyFaction();
   public static final TargetPredicate FRIENDLY_AREA_CHECK = (new TargetPredicate()).testFriendlyFaction();
   private static final int COOLDOWN = 800;
   private static final int RANGE = 10;
   private static final int MIN_HEAL = 5;
   private static final int MAX_HEAL = 50;
   public static final RegistryObject<AbilityCore<MedicBagExplosionAbility>> INSTANCE = ModRegistry.registerAbility("medic_bag_explosion", "Medic Bag Explosion", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By sacrificing the medic bag's durability the user can heal themselves with regeneration while applying debuffs to nearby enemies.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, MedicBagExplosionAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(800.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE), HealComponent.getTooltip(5.0F, 50.0F)).setNodeFactories(MedicBagExplosionAbility::createNode).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final HealComponent healComponent = new HealComponent(this);

   public MedicBagExplosionAbility(AbilityCore<MedicBagExplosionAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.healComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresMedicBag);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      boolean isHandEmpty = entity.m_21205_().m_41619_();
      Optional<ItemStack> medicBag = !isHandEmpty ? Optional.ofNullable(entity.m_21205_()) : ItemsHelper.findItemInSlot(entity, EquipmentSlot.CHEST, (Item)ModArmors.MEDIC_BAG.get());
      if (medicBag.isPresent()) {
         float heal = (float)Mth.m_14008_(WyHelper.percentage((double)20.0F, (double)entity.m_21233_()), (double)5.0F, (double)50.0F);
         this.healComponent.healTarget(entity, entity, heal);

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 10.0F, ENEMY_AREA_CHECK)) {
            FailedExperimentAbility.Mode mode = FailedExperimentAbility.Mode.values()[(int)WyHelper.randomWithRange(0, 5)];
            target.m_7292_(new MobEffectInstance(mode.getEffect(), 200, 1));
         }

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 10.0F, FRIENDLY_AREA_CHECK)) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 100, 2));
         }

         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.MEDIC_BAG_EXPLOSION.get(), entity, entity.m_20182_().f_82479_, entity.m_20182_().f_82480_, entity.m_20182_().f_82481_);
         this.cooldownComponent.startCooldown(entity, 800.0F);
         ((ItemStack)medicBag.get()).m_41622_(250, entity, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      }
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-11.0F, -3.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.DOCTOR.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)FirstAidAbility.INSTANCE.get()).getNode(entity), ((AbilityCore)AntidoteShotAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
