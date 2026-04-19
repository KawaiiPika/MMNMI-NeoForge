package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.GroundParticlesEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class JishinHoAbility extends Ability {
   private static final float COOLDOWN = 300.0F;
   private static final float DAMAGE = 25.0F;
   private static final float RANGE = 7.0F;
   private static final GroundParticlesEffect.Details PARTICLE_DETAILS = new GroundParticlesEffect.Details(7, 100);
   public static final RegistryObject<AbilityCore<JishinHoAbility>> INSTANCE = ModRegistry.registerAbility("jishin_ho", "Jishin Ho", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Punches the ground to cause a quake that damages everyone around", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, JishinHoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), DealDamageComponent.getTooltip(25.0F), RangeComponent.getTooltip(7.0F, RangeComponent.RangeType.AOE)).setSourceType(SourceType.FIST, SourceType.INDIRECT).setSourceElement(SourceElement.SHOCKWAVE).setSourceHakiNature(SourceHakiNature.HARDENING).setNodeFactories(JishinHoAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = new ContinuousComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public JishinHoAbility(AbilityCore<JishinHoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.dealDamageComponent, this.rangeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity player, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GROUND_PARTICLES.get(), player, player.m_20185_(), player.m_20186_(), player.m_20189_(), PARTICLE_DETAILS);
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(player, 7.0F);
      targets.remove(player);
      targets.removeIf((entity) -> !entity.m_20096_() && AbilityHelper.getDifferenceToFloor(player) > (double)1.5F);

      for(LivingEntity target : targets) {
         boolean flag = this.dealDamageComponent.hurtTarget(player, target, 25.0F);
         if (flag) {
            AbilityHelper.setDeltaMovement(target, (double)0.0F, (double)0.75F, (double)0.0F);
         }
      }

      this.cooldownComponent.startCooldown(player, 300.0F);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-4.0F, 6.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BRAWLER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)HakaiHoAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
