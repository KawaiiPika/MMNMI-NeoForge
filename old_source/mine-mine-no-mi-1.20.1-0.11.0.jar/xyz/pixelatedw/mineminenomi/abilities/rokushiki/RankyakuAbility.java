package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki.RankyakuProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class RankyakuAbility extends Ability {
   public static final float COOLDOWN = 240.0F;
   private static final int DORIKI = 5000;
   private static final int MARKSMANSHIP_POINTS = 10;
   private static final int MARTIAL_ARTS_POINTS = 20;
   public static final RegistryObject<AbilityCore<RankyakuAbility>> INSTANCE = ModRegistry.registerAbility("rankyaku", "Rankyaku", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By kicking at a very high speed, the user launches an air blade at the opponent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, RankyakuAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.AIR).setSourceType(SourceType.SLASH, SourceType.FIST, SourceType.INDIRECT).setNodeFactories(RankyakuAbility::createNode).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public RankyakuAbility(AbilityCore<RankyakuAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.explosionComponent});
      this.explosionComponent.setBlocksAffectedLimit(256);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 3.25F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private RankyakuProjectile createProjectile(LivingEntity entity) {
      RankyakuProjectile proj = new RankyakuProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode rankyaku = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(5.0F, 0.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.HUMAN.get()).and(DorikiUnlockCondition.atLeast((double)5000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      rankyaku.setUnlockRule(unlockCondition, unlockAction);
      return rankyaku;
   }

   private static boolean canUnlock(LivingEntity user) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         boolean raceCheck = props.isHuman();
         return raceCheck && props.getDoriki() >= (double)5000.0F;
      }
   }
}
