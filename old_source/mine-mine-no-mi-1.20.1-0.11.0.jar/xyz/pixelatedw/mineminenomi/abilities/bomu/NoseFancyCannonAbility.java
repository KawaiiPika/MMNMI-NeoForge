package xyz.pixelatedw.mineminenomi.abilities.bomu;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu.NoseFancyCannonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NoseFancyCannonAbility extends Ability {
   private static final float COOLDOWN = 80.0F;
   private static final int ANIMATION_TICKS = 7;
   public static final RegistryObject<AbilityCore<NoseFancyCannonAbility>> INSTANCE = ModRegistry.registerAbility("nose_fancy_cannon", "Nose Fancy Cannon", (id, name) -> {
      Component[] DESCRIPTION = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Shoots dried mucus at the opponent, which explodes on impact", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NoseFancyCannonAbility::new)).addDescriptionLine(DESCRIPTION).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public NoseFancyCannonAbility(AbilityCore<NoseFancyCannonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.animationComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, 80.0F);
      this.animationComponent.start(entity, ModAnimations.AIM_SNIPER, 7);
   }

   private NoseFancyCannonProjectile createProjectile(LivingEntity entity) {
      NoseFancyCannonProjectile proj = new NoseFancyCannonProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
