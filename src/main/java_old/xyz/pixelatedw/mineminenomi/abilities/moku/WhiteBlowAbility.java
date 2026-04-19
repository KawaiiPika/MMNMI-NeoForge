package xyz.pixelatedw.mineminenomi.abilities.moku;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku.WhiteBlowProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class WhiteBlowAbility extends Ability {
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<WhiteBlowAbility>> INSTANCE = ModRegistry.registerAbility("white_blow", "White Blow", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoots a cloud of smoke to engulf the opponent and trap them", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, WhiteBlowAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceElement(SourceElement.SMOKE).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public WhiteBlowAbility(AbilityCore<WhiteBlowAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER, 7);
      this.projectileComponent.shoot(entity, 2.75F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private WhiteBlowProjectile createProjectile(LivingEntity entity) {
      WhiteBlowProjectile proj = new WhiteBlowProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
