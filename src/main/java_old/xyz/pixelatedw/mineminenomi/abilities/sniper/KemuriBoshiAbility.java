package xyz.pixelatedw.mineminenomi.abilities.sniper;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.KemuriBoshiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KemuriBoshiAbility extends Ability {
   private static final float COOLDOWN = 120.0F;
   public static final RegistryObject<AbilityCore<KemuriBoshiAbility>> INSTANCE = ModRegistry.registerAbility("kemuri_boshi", "Kemuri Boshi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a pellet that releases smoke on impact, poisoning and confusing targets", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, KemuriBoshiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F), ContinuousComponent.getTooltip()).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.BULLET).setNodeFactories(KemuriBoshiAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = new ContinuousComponent(this);
   private final BowTriggerComponent bowTriggerComponent = (new BowTriggerComponent(this)).addShootEvent(this::shoot);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public KemuriBoshiAbility(AbilityCore<KemuriBoshiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.bowTriggerComponent, this.projectileComponent});
      this.addUseEvent(this::onUseEvent);
   }

   public void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   public boolean shoot(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         boolean isHitScan = HissatsuAbility.checkHitScan(entity);
         this.projectileComponent.setHitScan(isHitScan);
         this.projectileComponent.shoot(entity, 4.0F, 1.0F);
         this.continuousComponent.stopContinuity(entity);
         super.cooldownComponent.startCooldown(entity, 120.0F);
         return true;
      } else {
         return false;
      }
   }

   public KemuriBoshiProjectile createProjectile(LivingEntity entity) {
      KemuriBoshiProjectile proj = new KemuriBoshiProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-10.0F, -10.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SNIPER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)HiNoToriBoshiAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
