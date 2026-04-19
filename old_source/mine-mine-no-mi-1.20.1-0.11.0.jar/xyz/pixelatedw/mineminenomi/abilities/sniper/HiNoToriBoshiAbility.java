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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.HiNoToriBoshiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HiNoToriBoshiAbility extends Ability {
   private static final float COOLDOWN = 100.0F;
   public static final RegistryObject<AbilityCore<HiNoToriBoshiAbility>> INSTANCE = ModRegistry.registerAbility("hi_no_tori_boshi", "Hi no Tori Boshi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires a flaming pellet in the form of a phoenix, that sets the target on fire", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, HiNoToriBoshiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip()).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.FIRE).setSourceType(SourceType.BULLET).setNodeFactories(HiNoToriBoshiAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = new ContinuousComponent(this);
   private final BowTriggerComponent bowTriggerComponent = (new BowTriggerComponent(this)).addShootEvent(this::shoot);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public HiNoToriBoshiAbility(AbilityCore<HiNoToriBoshiAbility> core) {
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
         this.projectileComponent.shoot(entity, 5.0F, 1.0F);
         this.continuousComponent.stopContinuity(entity);
         this.cooldownComponent.startCooldown(entity, 100.0F);
         return true;
      } else {
         return false;
      }
   }

   public HiNoToriBoshiProjectile createProjectile(LivingEntity entity) {
      HiNoToriBoshiProjectile proj = new HiNoToriBoshiProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-8.0F, -9.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.SNIPER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
