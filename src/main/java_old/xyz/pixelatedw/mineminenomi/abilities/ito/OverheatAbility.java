package xyz.pixelatedw.mineminenomi.abilities.ito;

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
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito.OverheatProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class OverheatAbility extends Ability {
   private static final float COOLDOWN = 300.0F;
   public static final float DAMAGE = 60.0F;
   public static final RegistryObject<AbilityCore<OverheatAbility>> INSTANCE = ModRegistry.registerAbility("overheat", "Overheat", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user shoots a rope made of heated strings at the opponent, exploding upon impact", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, OverheatAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);

   public OverheatAbility(AbilityCore<OverheatAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.projectileComponent, this.explosionComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity);
      this.animationComponent.start(entity, ModAnimations.POINT_RIGHT_ARM, 7);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private OverheatProjectile createProjectile(LivingEntity entity) {
      return new OverheatProjectile(entity.m_9236_(), entity, this);
   }
}
