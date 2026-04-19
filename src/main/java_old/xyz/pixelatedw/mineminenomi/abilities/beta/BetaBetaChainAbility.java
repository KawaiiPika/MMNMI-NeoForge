package xyz.pixelatedw.mineminenomi.abilities.beta;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta.BetaBetaChainProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BetaBetaChainAbility extends Ability {
   private static final int HOLD_TIME = 600;
   private static final int COOLDOWN = 120;
   public static final RegistryObject<AbilityCore<BetaBetaChainAbility>> INSTANCE = ModRegistry.registerAbility("beta_beta_chain", "Beta Beta Chain", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user shoots a mucus chain which will propel the user towards where it hits.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BetaBetaChainAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.SLIME).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final SwingTriggerComponent swingTriggerComponent = (new SwingTriggerComponent(this)).addSwingEvent(this::swingEvent);
   private final SkinOverlayComponent skinOverlayComponent;

   public BetaBetaChainAbility(AbilityCore<BetaBetaChainAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.projectileComponent, this.swingTriggerComponent, this.skinOverlayComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 600.0F);
   }

   private void swingEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      this.projectileComponent.shoot(entity, 4.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 120.0F);
      AbilityHelper.slowEntityFall(entity, 2);
   }

   private BetaBetaChainProjectile createProjectile(LivingEntity entity) {
      BetaBetaChainProjectile projectile = new BetaBetaChainProjectile(entity.m_9236_(), entity, this);
      return projectile;
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.ARM).setTexture(ModResources.BETA_COATING).setColor(WyHelper.hexToRGB("#FFFFFFA6")).build();
   }
}
